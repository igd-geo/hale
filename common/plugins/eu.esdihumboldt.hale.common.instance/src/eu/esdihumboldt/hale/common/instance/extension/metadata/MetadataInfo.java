/*
 * Copyright (c) 2012 Data Harmonisation Panel
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     HUMBOLDT EU Integrated Project #030962
 *     Data Harmonisation Panel <http://www.dhpanel.eu>
 */

package eu.esdihumboldt.hale.common.instance.extension.metadata;

import org.eclipse.core.runtime.IConfigurationElement;

import de.cs3d.util.eclipse.extension.ExtensionUtil;
import de.cs3d.util.eclipse.extension.simple.IdentifiableExtension.Identifiable;

/**
 * Represents a declared Metadata Info
 * 
 * @author Sebastian Reinhardt
 */
public class MetadataInfo implements Identifiable {

	private final String key;
	private final String label;
	private final String description;
	private final Class<? extends MetadataGenerator> generator;

	/**
	 * Create a metadata object from a configuration element.
	 * 
	 * @param key the data key
	 * @param conf the configuration element
	 */
	@SuppressWarnings("unchecked")
	public MetadataInfo(String key, IConfigurationElement conf) {
		super();

		this.key = key;
		this.label = conf.getAttribute("label");
		this.description = conf.getAttribute("description");
		if (conf.getAttribute("generator") != null) {
			this.generator = (Class<? extends MetadataGenerator>) ExtensionUtil.loadClass(conf,
					"generator");
		}
		else {
			this.generator = null;
		}
	}

	/**
	 * @see de.cs3d.util.eclipse.extension.simple.IdentifiableExtension.Identifiable#getId()
	 */
	@Override
	public String getId() {
		return key;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the generator class
	 */
	public Class<? extends MetadataGenerator> getGenerator() {
		return generator;
	}

}
