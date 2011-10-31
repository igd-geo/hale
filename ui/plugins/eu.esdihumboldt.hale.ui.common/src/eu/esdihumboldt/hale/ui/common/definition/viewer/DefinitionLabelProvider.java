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

package eu.esdihumboldt.hale.ui.common.definition.viewer;

import java.util.List;

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import eu.esdihumboldt.hale.common.align.model.ChildContext;
import eu.esdihumboldt.hale.common.align.model.EntityDefinition;
import eu.esdihumboldt.hale.common.schema.model.Definition;
import eu.esdihumboldt.hale.ui.common.definition.DefinitionImages;

/**
 * Basic label provider for {@link Definition}s
 * @author Simon Templer
 */
public class DefinitionLabelProvider extends LabelProvider {
	
	private final DefinitionImages images = new DefinitionImages();

	/**
	 * @see LabelProvider#getText(Object)
	 */
	@Override
	public String getText(Object element) {
		boolean defContext = true;
		if (element instanceof EntityDefinition) {
			EntityDefinition entityDef = (EntityDefinition) element;
			element = entityDef.getDefinition();
			
			List<ChildContext> path = entityDef.getPropertyPath();
			if (path != null && !path.isEmpty()) {
				ChildContext lastContext = path.get(path.size() - 1);
				defContext = lastContext.getContextName() == null;
			}
		}
		
		if (element instanceof Definition<?>) {
			String name = ((Definition<?>) element).getDisplayName();
			if (!defContext) {
				name = "(" + name + ")";
			}
			return name;
		}
		
		return super.getText(element);
	}
	
	/**
	 * Returns an adjusted image depending on the type of the object passed in.
	 * @return an Image
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof EntityDefinition) {
			element = ((EntityDefinition) element).getDefinition();
		}
		
		if (element instanceof Definition<?>) {
			return images.getImage((Definition<?>) element);
		}
		
		return super.getImage(element);
	}

	/**
	 * @see BaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		images.dispose();
		
		super.dispose();
	}

}
