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

package eu.esdihumboldt.hale.ui.io.instance;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import eu.esdihumboldt.hale.common.core.io.ExportProvider;
import eu.esdihumboldt.hale.common.core.io.Value;
import eu.esdihumboldt.hale.common.core.io.extension.IOProviderDescriptor;
import eu.esdihumboldt.hale.common.core.io.project.model.IOConfiguration;
import eu.esdihumboldt.hale.common.instance.io.InstanceWriter;
import eu.esdihumboldt.hale.ui.io.config.AbstractConfigurationPage;

/**
 * Wizard page to select the instance export configuration which should be
 * loaded in the project
 * 
 * @author Patrick Lieb
 */
public class SelectLoadConfigurationInstanceExportPage extends
		AbstractConfigurationPage<InstanceWriter, LoadConfigurationInstanceExportWizard> implements
		InstanceExportConfigurations {

	/**
	 * Default Constructor
	 */
	public SelectLoadConfigurationInstanceExportPage() {
		super("sel.InstanceExportConf");
		setTitle("Select Configuration");
		setDescription("Select the configuration for the export");
	}

	/**
	 * @see eu.esdihumboldt.hale.ui.HaleWizardPage#createContent(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createContent(Composite page) {
		// page has a grid layout with one column
		page.setLayout(new GridLayout(1, false));

		// create list viewer to select provider
		ListViewer configurations = new ListViewer(page, SWT.DROP_DOWN | SWT.READ_ONLY | SWT.BORDER);
		configurations.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		configurations.setContentProvider(ArrayContentProvider.getInstance());

		// set all available export configurations in the list viewer
		List<IOConfiguration> confs = getWizard().getExportConfigurations();
		configurations.setInput(confs);
		configurations.setLabelProvider(new LabelProvider() {

			/**
			 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object element) {
				if (element instanceof IOConfiguration) {
					Map<String, Value> providerConf = ((IOConfiguration) element)
							.getProviderConfiguration();
					String name = providerConf.get(param_configurationName)
							.getStringRepresentation();
					String contentType = providerConf.get(ExportProvider.PARAM_CONTENT_TYPE)
							.getStringRepresentation();
					return name + "  (" + contentType + ")";
				}
				return super.getText(element);
			}
		});
		configurations.setSelection(new StructuredSelection(confs.iterator().next()), true);

		// process current selection
		ISelection selection = configurations.getSelection();
		setPageComplete(!selection.isEmpty());
		updateWizard(selection);

		// process selection changes
		configurations.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				setPageComplete(!selection.isEmpty());
				updateWizard(selection);
			}
		});
	}

	/**
	 * Update the wizard with the correct provider factory and the selected IO
	 * configuration
	 * 
	 * @param selection the current selection in the list viewer
	 */
	private void updateWizard(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;
			Object element = sel.getFirstElement();
			// set the selected configuration in the wizard
			getWizard().setConfiguration((IOConfiguration) element);
			List<IOProviderDescriptor> factories = getWizard().getFactories();
			for (IOProviderDescriptor factory : factories) {
				// provider factory is already defined, so we have to set it
				if (getWizard().getConfiguration().getProviderId().equals(factory.getIdentifier())) {
					getWizard().setProviderFactory(factory);
					break;
				}
			}
		}
	}

	/**
	 * @see eu.esdihumboldt.hale.ui.io.IOWizardPage#updateConfiguration(eu.esdihumboldt.hale.common.core.io.IOProvider)
	 */
	@Override
	public boolean updateConfiguration(InstanceWriter provider) {
		provider.loadConfiguration(getWizard().getConfiguration().getProviderConfiguration());
		return true;
	}

	/**
	 * @see eu.esdihumboldt.hale.ui.io.config.AbstractConfigurationPage#enable()
	 */
	@Override
	public void enable() {
		// do nothing
	}

	/**
	 * @see eu.esdihumboldt.hale.ui.io.config.AbstractConfigurationPage#disable()
	 */
	@Override
	public void disable() {
		// do nothing
	}
}
