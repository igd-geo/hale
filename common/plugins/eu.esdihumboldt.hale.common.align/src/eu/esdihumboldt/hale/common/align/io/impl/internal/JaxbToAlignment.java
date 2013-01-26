/*
 * Copyright (c) 2013 Data Harmonisation Panel
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

package eu.esdihumboldt.hale.common.align.io.impl.internal;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import eu.esdihumboldt.hale.common.align.io.impl.internal.generated.AbstractEntityType;
import eu.esdihumboldt.hale.common.align.io.impl.internal.generated.AlignmentType;
import eu.esdihumboldt.hale.common.align.io.impl.internal.generated.CellType;
import eu.esdihumboldt.hale.common.align.io.impl.internal.generated.ChildContextType;
import eu.esdihumboldt.hale.common.align.io.impl.internal.generated.ClassType;
import eu.esdihumboldt.hale.common.align.io.impl.internal.generated.ConditionType;
import eu.esdihumboldt.hale.common.align.io.impl.internal.generated.NamedEntityType;
import eu.esdihumboldt.hale.common.align.io.impl.internal.generated.ParameterType;
import eu.esdihumboldt.hale.common.align.io.impl.internal.generated.PropertyType;
import eu.esdihumboldt.hale.common.align.io.impl.internal.generated.QNameType;
import eu.esdihumboldt.hale.common.align.model.ChildContext;
import eu.esdihumboldt.hale.common.align.model.Condition;
import eu.esdihumboldt.hale.common.align.model.Entity;
import eu.esdihumboldt.hale.common.align.model.MutableAlignment;
import eu.esdihumboldt.hale.common.align.model.MutableCell;
import eu.esdihumboldt.hale.common.align.model.ParameterValue;
import eu.esdihumboldt.hale.common.align.model.Type;
import eu.esdihumboldt.hale.common.align.model.impl.DefaultAlignment;
import eu.esdihumboldt.hale.common.align.model.impl.DefaultCell;
import eu.esdihumboldt.hale.common.align.model.impl.DefaultProperty;
import eu.esdihumboldt.hale.common.align.model.impl.DefaultType;
import eu.esdihumboldt.hale.common.align.model.impl.PropertyEntityDefinition;
import eu.esdihumboldt.hale.common.align.model.impl.TypeEntityDefinition;
import eu.esdihumboldt.hale.common.core.io.report.IOReporter;
import eu.esdihumboldt.hale.common.core.io.report.impl.IOMessageImpl;
import eu.esdihumboldt.hale.common.instance.extension.filter.FilterDefinitionManager;
import eu.esdihumboldt.hale.common.instance.model.Filter;
import eu.esdihumboldt.hale.common.schema.SchemaSpaceID;
import eu.esdihumboldt.hale.common.schema.model.ChildDefinition;
import eu.esdihumboldt.hale.common.schema.model.DefinitionGroup;
import eu.esdihumboldt.hale.common.schema.model.TypeDefinition;
import eu.esdihumboldt.hale.common.schema.model.TypeIndex;
import eu.esdihumboldt.util.Pair;

/**
 * Converts an {@link AlignmentType} loaded with JAXB to a
 * {@link MutableAlignment}.
 * 
 * @author Simon Templer
 */
public class JaxbToAlignment {

	private final TypeIndex targetTypes;
	private final TypeIndex sourceTypes;
	private final IOReporter reporter;
	private final AlignmentType alignment;

	/**
	 * @param alignment the alignment read using JAXB
	 * @param reporter where to report problems to, may be <code>null</code>
	 * @param sourceTypes the source types for resolving source entities
	 * @param targetTypes the target types for resolving target entities
	 */
	public JaxbToAlignment(AlignmentType alignment, IOReporter reporter, TypeIndex sourceTypes,
			TypeIndex targetTypes) {
		this.alignment = alignment;
		this.reporter = reporter;
		this.sourceTypes = sourceTypes;
		this.targetTypes = targetTypes;
	}

	/**
	 * Create the converted alignment.
	 * 
	 * @return the resolved alignment
	 */
	public MutableAlignment convert() {
		MutableAlignment result = new DefaultAlignment();

		for (CellType cellType : alignment.getCell()) {
			MutableCell cell = convert(cellType);
			if (cell != null) {
				result.addCell(cell);
			}
		}

		return result;
	}

	private MutableCell convert(CellType cell) {
		MutableCell result = new DefaultCell();

		result.setTransformationIdentifier(cell.getRelation());

		if (!cell.getParameter().isEmpty()) {
			ListMultimap<String, ParameterValue> parameters = ArrayListMultimap.create();
			for (ParameterType param : cell.getParameter()) {
				parameters.put(param.getName(),
						new ParameterValue(param.getType(), param.getValue()));
			}
			result.setTransformationParameters(parameters);
		}

		try {
			result.setSource(convertEntities(cell.getSource(), sourceTypes, SchemaSpaceID.SOURCE));
			result.setTarget(convertEntities(cell.getTarget(), targetTypes, SchemaSpaceID.TARGET));
		} catch (Throwable e) {
			if (reporter != null) {
				reporter.error(new IOMessageImpl("Could not create cell", e));
			}
			return null;
		}

		return result;
	}

	private ListMultimap<String, ? extends Entity> convertEntities(
			List<NamedEntityType> namedEntities, TypeIndex types, SchemaSpaceID schemaSpace) {
		if (namedEntities == null || namedEntities.isEmpty()) {
			return null;
		}

		ListMultimap<String, Entity> result = ArrayListMultimap.create();

		for (NamedEntityType namedEntity : namedEntities) {
			result.put(namedEntity.getName(),
					convert(namedEntity.getAbstractEntity().getValue(), types, schemaSpace));
		}

		return result;
	}

	private Entity convert(AbstractEntityType entity, TypeIndex types, SchemaSpaceID schemaSpace) {
		// must first check for PropertyType as it inherits from ClassType
		if (entity instanceof PropertyType) {
			return convert((PropertyType) entity, types, schemaSpace);
		}
		if (entity instanceof ClassType) {
			return convert((ClassType) entity, types, schemaSpace);
		}
		throw new IllegalArgumentException("Illegal type of entity");
	}

	private Type convert(ClassType classType, TypeIndex types, SchemaSpaceID schemaSpace) {
		TypeDefinition typeDef = types.getType(asName(classType.getType()));

		Filter filter = getTypeFilter(classType);

		TypeEntityDefinition tef = new TypeEntityDefinition(typeDef, schemaSpace, filter);

		return new DefaultType(tef);
	}

	private Filter getTypeFilter(ClassType classType) {
		if (classType.getType() != null && classType.getType().getCondition() != null) {
			// FIXME filter type currently encoded in string
			return FilterDefinitionManager.getInstance().parse(
					classType.getType().getCondition().getValue());
		}
		return null;
	}

	private Entity convert(PropertyType property, TypeIndex types, SchemaSpaceID schemaSpace) {
		TypeDefinition typeDef = types.getType(asName(property.getType()));

		Filter filter = getTypeFilter(property);

		List<ChildContext> path = new ArrayList<ChildContext>();

		DefinitionGroup parent = typeDef;
		for (ChildContextType childContext : property.getChild()) {
			if (parent == null) {
				throw new IllegalStateException(
						"Could not resolve property entity definition: child not present");
			}

			Pair<ChildDefinition<?>, List<ChildDefinition<?>>> childs = PropertyBean.findChild(
					parent, asName(childContext));

			ChildDefinition<?> child = childs.getFirst();

			// if the child is still null throw an exception
			if (child == null) {
				throw new IllegalStateException(
						"Could not resolve property entity definition: child not found");
			}

			if (childs.getSecond() != null) {
				for (ChildDefinition<?> pathElems : childs.getSecond()) {
					path.add(new ChildContext(contextName(childContext.getContext()),
							contextIndex(childContext.getIndex()), createCondition(childContext
									.getCondition()), pathElems));
				}
			}

			path.add(new ChildContext(contextName(childContext.getContext()),
					contextIndex(childContext.getIndex()), createCondition(childContext
							.getCondition()), child));

			if (child instanceof DefinitionGroup) {
				parent = (DefinitionGroup) child;
			}
			else if (child.asProperty() != null) {
				parent = child.asProperty().getPropertyType();
			}
			else {
				parent = null;
			}
		}

		PropertyEntityDefinition ped = new PropertyEntityDefinition(typeDef, path, schemaSpace,
				filter);
		return new DefaultProperty(ped);
	}

	/**
	 * Create a condition.
	 * 
	 * @param conditionFilter the condition filter
	 * @return the condition or <code>null</code>
	 */
	private Condition createCondition(ConditionType conditionFilter) {
		if (conditionFilter == null)
			return null;

		// FIXME only uses filter value!
		Filter filter = FilterDefinitionManager.getInstance().parse(conditionFilter.getValue());
		if (filter != null) {
			return new Condition(filter);
		}
		return null;
	}

	private Integer contextName(String name) {
		if (name == null)
			return null;

		return Integer.valueOf(name);
	}

	private Integer contextIndex(BigInteger index) {
		if (index == null)
			return null;

		return index.intValue();
	}

	private QName asName(QNameType qname) {
		if (qname.getNs() == null || qname.getNs().isEmpty()) {
			return new QName(qname.getName());
		}
		return new QName(qname.getNs(), qname.getName());
	}

}
