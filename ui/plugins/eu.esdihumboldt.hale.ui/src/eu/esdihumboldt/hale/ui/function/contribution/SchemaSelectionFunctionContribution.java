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

package eu.esdihumboldt.hale.ui.function.contribution;

import java.util.List;
import java.util.Set;

import org.eclipse.ui.PlatformUI;

import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;
import eu.esdihumboldt.hale.common.align.extension.function.AbstractFunction;
import eu.esdihumboldt.hale.common.align.extension.function.AbstractParameter;
import eu.esdihumboldt.hale.common.align.extension.function.PropertyFunction;
import eu.esdihumboldt.hale.common.align.extension.function.PropertyParameter;
import eu.esdihumboldt.hale.common.align.extension.function.TypeFunction;
import eu.esdihumboldt.hale.common.align.extension.function.TypeParameter;
import eu.esdihumboldt.hale.common.align.model.EntityDefinition;
import eu.esdihumboldt.hale.common.align.model.Property;
import eu.esdihumboldt.hale.common.align.model.Type;
import eu.esdihumboldt.hale.common.align.model.condition.EntityCondition;
import eu.esdihumboldt.hale.common.align.model.condition.PropertyCondition;
import eu.esdihumboldt.hale.common.align.model.condition.TypeCondition;
import eu.esdihumboldt.hale.common.align.model.impl.DefaultProperty;
import eu.esdihumboldt.hale.common.align.model.impl.DefaultType;
import eu.esdihumboldt.hale.common.align.model.impl.PropertyEntityDefinition;
import eu.esdihumboldt.hale.common.align.model.impl.TypeEntityDefinition;
import eu.esdihumboldt.hale.ui.common.service.compatibility.CompatibilityService;
import eu.esdihumboldt.hale.ui.function.contribution.internal.AbstractWizardAction;
import eu.esdihumboldt.hale.ui.function.contribution.internal.SchemaSelectionWizardAction;
import eu.esdihumboldt.hale.ui.function.extension.FunctionWizardDescriptor;
import eu.esdihumboldt.hale.ui.selection.SchemaSelection;
import eu.esdihumboldt.hale.ui.selection.SchemaSelectionHelper;
import eu.esdihumboldt.hale.ui.service.align.AlignmentService;

/**
 * Function wizard contribution based on {@link SchemaSelection}
 * 
 * @author Simon Templer
 */
public class SchemaSelectionFunctionContribution extends AbstractFunctionWizardContribution {

	private static final ALogger log = ALoggerFactory
			.getLogger(SchemaSelectionFunctionContribution.class);

	/**
	 * @see AbstractFunctionWizardContribution#createWizardAction(FunctionWizardDescriptor,
	 *      AlignmentService)
	 */
	@Override
	protected AbstractWizardAction<?> createWizardAction(FunctionWizardDescriptor<?> descriptor,
			AlignmentService alignmentService) {
		return new SchemaSelectionWizardAction(this, descriptor, alignmentService);
	}

	/**
	 * @see AbstractFunctionWizardContribution#isActive(FunctionWizardDescriptor)
	 */
	@Override
	public boolean isActive(FunctionWizardDescriptor<?> descriptor) {
		AbstractFunction<?> function = descriptor.getFunction();
		// rule out functions not supported by the compatibility mode
		if (((CompatibilityService) PlatformUI.getWorkbench()
				.getService(CompatibilityService.class)).getCurrent().supportsFunction(
				function.getId())) {
			if (function instanceof TypeFunction) {
				TypeFunction tf = (TypeFunction) function;
				// match selection against function definition
				return matchTypeFunction(tf, getSelection());
			}
			else if (function instanceof PropertyFunction) {
				PropertyFunction pf = (PropertyFunction) function;
				// match selection against function definition
				return matchPropertyFunction(pf, getSelection());
			}
			else {
				log.error("Unsupported function deactivated");
				return false;
			}
		}
		else
			return false;
	}

	/**
	 * Test if the given function definition matches the given selection
	 * 
	 * @param function the function definition
	 * @param selection the schema selection
	 * @return <code>true</code> if the definition matches the selection,
	 *         <code>false</code> otherwise
	 */
	private boolean matchPropertyFunction(PropertyFunction function, SchemaSelection selection) {
		/*
		 * Deactivated restriction that property cells can only be created with
		 * existing type cells.
		 */
//		AlignmentService as = (AlignmentService) PlatformUI.getWorkbench().getService(
//				AlignmentService.class);
//
//		if (!AlignmentUtil.hasTypeRelation(as.getAlignment())) {
//			// don't allow creating property relations if there are no type
//			// relations present
//			return false;
//		}

		if (selection == null || selection.isEmpty()) {
			// for no selection always allow creating a new cell if there are
			// type relations present
			return true;
		}

		// check types
		Set<EntityDefinition> sourceItems = selection.getSourceItems();
		if (!checkType(sourceItems, PropertyEntityDefinition.class)) {
			return false;
		}
		Set<EntityDefinition> targetItems = selection.getTargetItems();
		if (!checkType(targetItems, PropertyEntityDefinition.class)) {
			return false;
		}

		// TODO check if properties have the same parent type? what about joins?

		// check counts
		if (!checkCount(sourceItems.size(), function.getSource(), false)) {
			return false;
		}
		if (!checkCount(targetItems.size(), function.getTarget(), true)) {
			return false;
		}

		// check mandatory source/target with special conditions
		if (!checkMandatoryConditions(sourceItems, function.getSource())) {
			return false;
		}
		if (!checkMandatoryConditions(targetItems, function.getTarget())) {
			return false;
		}

		// TODO other checks?

		return true;
	}

	/**
	 * Checks if all entities that are mandatory in the function definition and
	 * have specific attached conditions can be met by at least one of the given
	 * schema entities.
	 * 
	 * @param schemaEntities the schema entities
	 * @param functionEntities the entities as defined in the function
	 * @return if the conditions on mandatory function entities can be met
	 */
	protected boolean checkMandatoryConditions(Set<EntityDefinition> schemaEntities,
			Iterable<? extends AbstractParameter> functionEntities) {
		for (AbstractParameter functionEntity : functionEntities) {
			if (functionEntity.getMinOccurrence() != 0) {
				// entity is mandatory

				if (functionEntity instanceof PropertyParameter) {
					PropertyParameter pp = (PropertyParameter) functionEntity;
					if (!pp.getConditions().isEmpty()) {
						// check if there is an entity given that matches the
						// conditions
						if (!checkConditions(pp.getConditions(), schemaEntities)) {
							// conditions not met
							return false;
						}
					}
				}
				else if (functionEntity instanceof TypeParameter) {
					TypeParameter tp = (TypeParameter) functionEntity;
					if (!tp.getConditions().isEmpty()) {
						// check if there is an entity given that matches the
						// conditions
						if (!checkConditions(tp.getConditions(), schemaEntities)) {
							// conditions not met
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Check if the given conditions can be met by at least on of the given
	 * schema entities.
	 * 
	 * @param conditions the conditions to be met
	 * @param schemaEntities the schema entities to test
	 * @return if the conditions are met by one of the entities
	 */
	private boolean checkConditions(List<? extends EntityCondition<?>> conditions,
			Set<EntityDefinition> schemaEntities) {
		for (EntityDefinition entity : schemaEntities) {
			boolean entityValid = true;
			for (EntityCondition<?> condition : conditions) {
				if (!checkCondition(condition, entity)) {
					entityValid = false;
				}
			}
			if (entityValid) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the given condition is met by the given entity.
	 * 
	 * @param condition the condition
	 * @param entity the entity to test
	 * @return if the entity meets the condition
	 */
	private boolean checkCondition(EntityCondition<?> condition, EntityDefinition entity) {
		if (condition instanceof PropertyCondition) {
			if (entity instanceof PropertyEntityDefinition) {
				Property p = new DefaultProperty((PropertyEntityDefinition) entity);
				return ((PropertyCondition) condition).accept(p);
			}
		}
		else if (condition instanceof TypeCondition) {
			if (entity instanceof TypeEntityDefinition) {
				Type t = new DefaultType((TypeEntityDefinition) entity);
				return ((TypeCondition) condition).accept(t);
			}
		}

		return false;
	}

	/**
	 * Test if the given function definition matches the given selection
	 * 
	 * @param function the function definition
	 * @param selection the schema selection
	 * @return <code>true</code> if the definition matches the selection,
	 *         <code>false</code> otherwise
	 */
	private boolean matchTypeFunction(TypeFunction function, SchemaSelection selection) {
		// check types
		Set<EntityDefinition> sourceItems = selection.getSourceItems();
		if (!checkType(sourceItems, TypeEntityDefinition.class)) {
			return false;
		}
		Set<EntityDefinition> targetItems = selection.getTargetItems();
		if (!checkType(targetItems, TypeEntityDefinition.class)) {
			return false;
		}

		// check counts
		if (!checkCount(sourceItems.size(), function.getSource(), true)) {
			return false;
		}
		if (!checkCount(targetItems.size(), function.getTarget(), false)) {
			return false;
		}

		// check mandatory source/target with special conditions
		if (!checkMandatoryConditions(sourceItems, function.getSource())) {
			return false;
		}
		if (!checkMandatoryConditions(targetItems, function.getTarget())) {
			return false;
		}

		// TODO other checks?

		return true;
	}

	/**
	 * Checks if each item is of the given type
	 * 
	 * @param items the items
	 * @param type the type
	 * @return <code>true</code> if all items are of the given type
	 */
	protected boolean checkType(Iterable<?> items, Class<?> type) {
		for (Object item : items) {
			if (!type.isAssignableFrom(item.getClass())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if the given entity count is compatible with the given set of
	 * entity definitions
	 * 
	 * @param count the entity count
	 * @param entities the entity definitions
	 * @param isTarget if the entities are target entities
	 * @return if then entity count is compatible with the definitions
	 */
	protected boolean checkCount(int count, Set<? extends AbstractParameter> entities,
			boolean isTarget) {
		int min = 0;
		int max = 0;

		for (AbstractParameter param : entities) {
			min += param.getMinOccurrence();
			if (max != AbstractParameter.UNBOUNDED) {
				int pMax = param.getMaxOccurrence();
				if (pMax == AbstractParameter.UNBOUNDED) {
					max = pMax;
				}
				else {
					max += pMax;
				}
			}
		}

		// check minimum
		if (count < min) {
			return false;
		}

		if (max == 0 && !isTarget) {
			// allow augmentations
			return true;
		}

		// check maximum
		if (max != AbstractParameter.UNBOUNDED && count > max) {
			return false;
		}

		return true;
	}

	/**
	 * Get the schema selection
	 * 
	 * @return the schema selection to use
	 */
	public SchemaSelection getSelection() {
//		ISelectionService selectionService = PlatformUI.getWorkbench()
//			.getActiveWorkbenchWindow().getSelectionService();
//		ISelection selection = selectionService.getSelection();

//		if (selection instanceof SchemaSelection) {
//			return (SchemaSelection) selection;
//		}
//		else {
		return SchemaSelectionHelper.getSchemaSelection();
//		}
	}

}
