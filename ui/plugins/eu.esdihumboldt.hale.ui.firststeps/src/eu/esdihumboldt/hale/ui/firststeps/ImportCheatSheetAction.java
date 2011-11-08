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

package eu.esdihumboldt.hale.ui.firststeps;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import eu.esdihumboldt.hale.ui.io.action.IOWizardAction;

/**
 * Action to open dialogs via an actionId from within a cheatsheet. <br />
 * It needs one param, the actionId.
 * 
 * @author Kai Schwierczek
 */
public class ImportCheatSheetAction extends Action implements ICheatSheetAction {
	/**
	 * @see org.eclipse.ui.cheatsheets.ICheatSheetAction#run(java.lang.String[],
	 *      org.eclipse.ui.cheatsheets.ICheatSheetManager)
	 */
	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		if (params.length > 0) {
			IOWizardAction action = new IOWizardAction(params[0]);
			if (action.isEnabled()) {
				action.addPropertyChangeListener(new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if (IAction.RESULT.equals(event.getProperty()))
							notifyResult((Boolean) event.getNewValue());
					}
				});
				action.run();
			} else {
				MessageDialog.openWarning(
						Display.getCurrent().getActiveShell(),
						"Requirment missing", action.getFactory()
								.getDisabledReason());
				notifyResult(false);
			}
			action.dispose();
		}
	}
}
