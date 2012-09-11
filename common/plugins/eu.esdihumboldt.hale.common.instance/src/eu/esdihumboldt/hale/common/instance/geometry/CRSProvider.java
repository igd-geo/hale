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

package eu.esdihumboldt.hale.common.instance.geometry;

import eu.esdihumboldt.hale.common.schema.geometry.CRSDefinition;
import eu.esdihumboldt.hale.common.schema.model.PropertyDefinition;

/**
 * Interface for classes that provide CRS definitions based on
 * {@link PropertyDefinition}s.
 * 
 * @author Simon Templer
 */
public interface CRSProvider {

	/**
	 * Get the CRS definition for values of the given property definition.
	 * 
	 * FIXME the property definition is not really a good tool for this, because
	 * it doesn't represent the full path in an instance
	 * 
	 * @param property the property definition
	 * @return the CRS definition or <code>null</code> if it can't be determined
	 */
	public CRSDefinition getCRS(PropertyDefinition property);

}
