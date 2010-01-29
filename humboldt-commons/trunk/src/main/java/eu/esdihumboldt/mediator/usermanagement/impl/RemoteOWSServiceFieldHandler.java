/*
 * HUMBOLDT: A Framework for Data Harmonistation and Service Integration.
 * EU Integrated Project #030962                  01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this website:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to : http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2010.
 */
package eu.esdihumboldt.mediator.usermanagement.impl;



import org.exolab.castor.mapping.GeneralizedFieldHandler;

import eu.esdihumboldt.mediator.constraints.portrayal.RemoteOWS.Service;



public class RemoteOWSServiceFieldHandler extends GeneralizedFieldHandler {

	/**
	 * Default Constructor
	 */
	public RemoteOWSServiceFieldHandler(){
		super();
	}
	/**
     * This method is used to convert the value when the
     * getValue method is called. The getValue method will
     * obtain the actual field value from given 'parent' object.
     * This convert method is then invoked with the field's
     * value. The value returned from this method will be
     * the actual value returned by getValue method.
     *
     * @param should be a MetadataType object
     * @return String of MetadataType
     */
	@Override
	public Object convertUponGet(Object service) {	
		
		return (service == null) ? null : ((Service)service).toString();
	}
	
	/**
     * This method is used to convert the value when the
     * setValue method is called. The setValue method will
     * call this method to obtain the converted value.
     * The converted value will then be used as the value to
     * set for the field.
     *
     * @param String representation of ConstraintSource
     * @return ConstraintSource
     */
	@Override
	public Object convertUponSet(Object stringService) {
		return (stringService == null)? null : Service.valueOf((String)stringService);
	}

	/**
     * Returns the class type for the field that this
     * GeneralizedFieldHandler converts to and from. This
     * should be the type that is used in the
     * object model.
     *
     * @return String.class
     */
	@SuppressWarnings("unchecked")
	@Override
	public Class getFieldType() {
		return eu.esdihumboldt.mediator.constraints.portrayal.RemoteOWS.Service.class;
	}

}




