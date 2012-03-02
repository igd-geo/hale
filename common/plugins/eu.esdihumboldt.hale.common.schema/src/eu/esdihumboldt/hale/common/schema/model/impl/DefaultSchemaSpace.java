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

package eu.esdihumboldt.hale.common.schema.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import eu.esdihumboldt.hale.common.schema.model.Definition;
import eu.esdihumboldt.hale.common.schema.model.Schema;
import eu.esdihumboldt.hale.common.schema.model.SchemaSpace;
import eu.esdihumboldt.hale.common.schema.model.TypeConstraint;
import eu.esdihumboldt.hale.common.schema.model.TypeDefinition;
import eu.esdihumboldt.hale.common.schema.model.TypeIndex;
import eu.esdihumboldt.hale.common.schema.model.constraint.type.MappingRelevantFlag;

/**
 * Default {@link SchemaSpace} implementation
 * @author Simon Templer
 */
public class DefaultSchemaSpace implements SchemaSpace {
	
	private final Set<Schema> schemas = new HashSet<Schema>();
	
	private Collection<TypeDefinition> allTypes;
	
	private Collection<TypeDefinition> mappingRelevantTypes;
	
	/**
	 * Adds a schema
	 * 
	 * @param schema the schema to add
	 */
	public void addSchema(Schema schema) {
		synchronized (this) {
			schemas.add(schema);
			if (allTypes != null) {
				allTypes.addAll(schema.getTypes());
			}
			if (mappingRelevantTypes != null) {
				mappingRelevantTypes.addAll(schema.getMappingRelevantTypes());
			}
		}
	}
	
	//XXX needed? will result in problems with shared types because the load order of the schemas is not documented
//	/**
//	 * Removes a schema
//	 * 
//	 * @param schema the schema to remove
//	 */
//	public void removeSchema(Schema schema) {
//		
//	}

	/**
	 * @see TypeIndex#getTypes()
	 */
	@Override
	public Collection<? extends TypeDefinition> getTypes() {
		synchronized (this) {
			if (allTypes == null) {
				allTypes = new HashSet<TypeDefinition>();
				for (Schema schema : schemas) {
					allTypes.addAll(schema.getTypes());
				}
			}
			return allTypes;
		}
	}

	/**
	 * @see TypeIndex#getType(QName)
	 */
	@Override
	public TypeDefinition getType(QName name) {
		synchronized (this) {
			for (Schema schema : schemas) {
				TypeDefinition result = schema.getType(name);
				if (result != null) {
					return result;
				}
			}
			
			return null;
		}
	}

	/**
	 * @see TypeIndex#getMappingRelevantTypes()
	 */
	@Override
	public Collection<? extends TypeDefinition> getMappingRelevantTypes() {
		synchronized (this) {
			if (mappingRelevantTypes == null) {
				mappingRelevantTypes = new HashSet<TypeDefinition>();
				for (Schema schema : schemas) {
					mappingRelevantTypes.addAll(schema.getMappingRelevantTypes());
				}
			}
			return mappingRelevantTypes;
		}
	}

	/**
	 * @see SchemaSpace#getSchemas()
	 */
	@Override
	public Iterable<? extends Schema> getSchemas() {
		return new ArrayList<Schema>(schemas);
	}

	/**
	 * @see eu.esdihumboldt.hale.common.schema.model.TypeIndex#toggleMappingRelevant(java.util.Collection)
	 */
	@Override
	public void toggleMappingRelevant(Collection<? extends TypeDefinition> types) {
		synchronized (this) {
			for (TypeDefinition type : types) {
				Schema container = null;
				for (Schema schema : schemas)
					if (schema.getTypes().contains(type)) {
						container = schema;
						break;
					}
				// toggle type in its schema
				if (container != null)
					container.toggleMappingRelevant(Collections.singletonList(type));
				else {
					// shouldn't happen, but to be safe toggle it in this case too
					Definition<TypeConstraint> def = type;
					((AbstractDefinition<TypeConstraint>) def).setConstraint(MappingRelevantFlag.get(!type.getConstraint(MappingRelevantFlag.class).isEnabled()));
				}
				// was toggled, update own list
				if (mappingRelevantTypes != null)
					if (type.getConstraint(MappingRelevantFlag.class).isEnabled())
						mappingRelevantTypes.add(type);
					else
						mappingRelevantTypes.remove(type);
			}
		}
	}
}
