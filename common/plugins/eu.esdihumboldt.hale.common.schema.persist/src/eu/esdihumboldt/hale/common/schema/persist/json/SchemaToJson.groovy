/*
 * Copyright (c) 2014 Data Harmonisation Panel
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
 *     Data Harmonisation Panel <http://www.dhpanel.eu>
 */

package eu.esdihumboldt.hale.common.schema.persist.json

import eu.esdihumboldt.hale.common.schema.model.ChildDefinition
import eu.esdihumboldt.hale.common.schema.model.GroupPropertyDefinition
import eu.esdihumboldt.hale.common.schema.model.Schema
import eu.esdihumboldt.hale.common.schema.model.TypeDefinition
import eu.esdihumboldt.util.groovy.json.JsonStreamBuilder


/**
 * Creates a JSON representation for visualization of the HALE Schema Model.
 * 
 * @author Simon Templer
 */
class SchemaToJson {

	/**
	 * Create a JSON representation of the given set of schemas.
	 * 
	 * @param writer the writer to write the JSON to
	 * @param schemas the schemas to serialize
	 */
	static void schemasToJson(Writer writer, def schemas) {
		JsonStreamBuilder json = new JsonStreamBuilder(writer)
		List<Element> elements = []
		json {
			// tree nodes
			for (Schema schema in schemas) {
				schemaToJson(json, schema, elements)
			}
			// elements
			elements.each { Element ele ->
				ele.toJson(json)
			}
			//TODO associations
		}
	}

	static void schemaToJson(JsonStreamBuilder json, Schema schema, List<Element> elements) {
		json.'treeNodes[]' {
			nodeLabel schema.namespace
			nodeQName schema.namespace
			nodeType 'package'

			// list to collect references to types
			def typeIndices = []

			// subnodes (= types)
			schema.mappingRelevantTypes.each { TypeDefinition type ->
				typeToJson(json, type, elements)

				// create element representing type
				Element element = new Element(label: type.displayName, name: type.name)
				if (type.description) {
					element.description = type.description
				}

				// add to elements, remember index
				elements << element
				typeIndices << elements.size() - 1
			}

			nodeElements typeIndices
		}
	}

	static void typeToJson(JsonStreamBuilder json, TypeDefinition type, List<Element> elements) {
		json.'subNodes[]' {
			nodeLabel type.displayName
			nodeQName type.name as String
			nodeType 'class'

			// list to collect references to children
			def childIndices = []

			// subnodes (= groups) & elements
			type.children.each { ChildDefinition child ->
				// create element representing child
				Element element = new Element(label: child.displayName, name: child.name)
				if (child.description) {
					element.description = child.description
				}

				if (child.asProperty() != null) {
					// child is a property
					element.typeName = child?.propertyType?.name
				}

				if (child.asGroup() != null) {
					groupToJson(json, child, elements)
				}

				// add to elements, remember index
				elements << element
				childIndices << elements.size() - 1
			}

			nodeElements childIndices
		}
	}

	static void groupToJson(JsonStreamBuilder json, GroupPropertyDefinition group, List<Element> elements) {
		json.'subNodes[]' {
			nodeLabel group.displayName
			nodeQName group.name as String
			nodeType 'group'

			// list to collect references to children
			def childIndices = []

			// subnodes (= groups) & elements
			group.declaredChildren.each { ChildDefinition child ->
				// create element representing child
				Element element = new Element(label: child.displayName, name: child.name)
				if (child.description) {
					element.description = child.description
				}

				if (child.asProperty() != null) {
					// child is a property
					element.typeName = child?.propertyType?.name
				}

				if (child.asGroup() != null) {
					groupToJson(json, child, elements)
				}

				// add to elements, remember index
				elements << element
				childIndices << elements.size() - 1
			}

			nodeElements childIndices
		}
	}

}
