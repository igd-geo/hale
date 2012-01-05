/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2011.
 */

package eu.esdihumboldt.hale.common.align.extension.function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.vividsolutions.jts.geom.Geometry;

import de.cs3d.util.eclipse.extension.ExtensionUtil;
import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;

import eu.esdihumboldt.hale.common.align.model.condition.PropertyCondition;
import eu.esdihumboldt.hale.common.align.model.condition.PropertyTypeCondition;
import eu.esdihumboldt.hale.common.align.model.condition.impl.BindingCondition;
import eu.esdihumboldt.hale.common.align.model.condition.impl.GeometryCondition;

/**
 * Represents a source or target property as parameter to a 
 * {@link PropertyFunction}
 * @author Simon Templer
 */
public final class PropertyParameter extends AbstractParameter {
	
	private static final ALogger log = ALoggerFactory.getLogger(PropertyParameter.class);

	private final List<PropertyCondition> conditions;

	/**
	 * @see AbstractParameter#AbstractParameter(IConfigurationElement)
	 */
	public PropertyParameter(IConfigurationElement conf) {
		super(conf);
		
		conditions = createConditions(conf);
	}
	
	private static List<PropertyCondition> createConditions(
			IConfigurationElement conf) {
		List<PropertyCondition> result = new ArrayList<PropertyCondition>();
		
		IConfigurationElement[] children = conf.getChildren();
		if (children != null) {
			for (IConfigurationElement child : children) {
				String name = child.getName();
				if (name.equals("propertyCondition")) {
					try {
						PropertyCondition condition = (PropertyCondition) child.createExecutableExtension("class");
						result.add(condition);
					} catch (CoreException e) {
						log.error("Error creating property condition from extension", e);
					}
				}
				else if (name.equals("bindingCondition")) {
					BindingCondition bc = createBindingCondition(child);
					if (bc != null) {
						result.add(new PropertyTypeCondition(bc));
					}
				}
				else if (name.equals("geometryCondition")) {
					GeometryCondition gc = createGeometryCondition(child);
					if (gc != null) {
						result.add(new PropertyTypeCondition(gc));
					}
				}
				else {
					// ignore
//					log.error("Unrecognized property condition");
				}
			}
		}
		
		return result;
	}

	private static GeometryCondition createGeometryCondition(
			IConfigurationElement child) {
		Collection<Class<? extends Geometry>> bindings = null;
		
		IConfigurationElement[] types = child.getChildren("geometryType");
		if (types != null) {
			for (IConfigurationElement type : types) {
				String className = type.getAttribute("type");
				try {
					@SuppressWarnings("unchecked")
					Class<? extends Geometry> geometryType = (Class<? extends Geometry>) ExtensionUtil
							.loadClass(child, className);
					if (bindings == null) {
						bindings = new HashSet<Class<? extends Geometry>>();
					}
					bindings.add(geometryType);
				} catch (Throwable e) {
					log.error("Could not load geometry class defined in extension", e);
				}
			}
		}
		
		boolean allowCollection = true; //TODO configurable?
		boolean allowConversion = true; //TODO configurable?
		return new GeometryCondition(bindings, allowConversion, allowCollection);
	}

	private static BindingCondition createBindingCondition(
			IConfigurationElement child) {
		Class<?> bindingClass = ExtensionUtil.loadClass(child, "compatibleClass");
		if (bindingClass != null) {
			boolean allowConversion = Boolean.parseBoolean(
					child.getAttribute("allowConversion")); // defaults to false
			boolean allowCollection = Boolean.parseBoolean(
					child.getAttribute("allowCollection")); // defaults to false
			
			return new BindingCondition(bindingClass, allowConversion, 
					allowCollection);
		}
		else {
			log.error("Could not load class for binding condition: " + 
				child.getAttribute("compatibleClass"));
		}
		
		return null;
	}

	/**
	 * Get the property conditions
	 * @return the property conditions
	 */
	public List<PropertyCondition> getConditions() {
		return Collections.unmodifiableList(conditions);
	}

}
