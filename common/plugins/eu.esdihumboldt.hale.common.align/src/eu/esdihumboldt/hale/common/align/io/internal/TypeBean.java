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

package eu.esdihumboldt.hale.common.align.io.internal;

import eu.esdihumboldt.hale.common.align.model.Type;
import eu.esdihumboldt.hale.common.align.model.impl.TypeEntityDefinition;
import eu.esdihumboldt.hale.common.schema.model.TypeDefinition;
import eu.esdihumboldt.hale.common.schema.model.TypeIndex;

/**
 * Represents a {@link Type}
 * @author Simon Templer
 */
public class TypeBean extends EntityBean<TypeEntityDefinition> {
	
	/**
	 * Default constructor 
	 */
	public TypeBean() {
		super();
	}

	/**
	 * Creates a type entity bean based on the given type entity
	 * @param type the type entity
	 */
	public TypeBean(Type type) {
		super(type.getDefinition().getDefinition().getName());
	}

	/**
	 * @see EntityBean#createEntityDefinition(TypeIndex)
	 */
	@Override
	public TypeEntityDefinition createEntityDefinition(TypeIndex index) {
		TypeDefinition typeDef = index.getType(getTypeName());
		return new TypeEntityDefinition(typeDef);
	}

}
