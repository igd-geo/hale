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

package eu.esdihumboldt.hale.common.filter;

import org.geotools.factory.CommonFactoryFinder;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.filter.text.ecql.ECQL;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory;
import org.opengis.filter.FilterVisitor;

import eu.esdihumboldt.hale.common.instance.model.Instance;




/**
 * TODO Type description
 * @author Basti
 */
public class FilterGeoCqlImpl implements eu.esdihumboldt.hale.common.filter.Filter  {

	
	private String filterTerm;
	private Filter internFilter;
	
	/**
	 * @param filterTerm
	 * @throws CQLException 
	 */
	public FilterGeoCqlImpl (String filterTerm) throws CQLException {
		this.filterTerm = filterTerm;
		
			internFilter = CQL.toFilter(this.filterTerm);
			if (internFilter == Filter.EXCLUDE){
				//TODO Fehler
				System.out.println("Error");
			}
			
			
			
			
	}


/**
 * @see eu.esdihumboldt.hale.common.filter.Filter#match(eu.esdihumboldt.hale.instance.model.TypeDefinition)
 */
@Override
public boolean match(Instance instance) {

	return internFilter.evaluate(instance);
	
}



}
