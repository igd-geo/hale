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

package eu.esdihumboldt.hale.ui.views.report.properties.details;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import eu.esdihumboldt.hale.common.core.report.Report;
import eu.esdihumboldt.hale.ui.views.report.properties.details.tree.ReportTreeContentProvider;
import eu.esdihumboldt.hale.ui.views.report.properties.details.tree.ReportTreeLabelProvider;

/**
 * Default details page for {@link Report}s.
 * 
 * @author Andreas Burchert
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 */
public abstract class ReportDetailsPage extends AbstractPropertySection {
	
	/**
	 * The FilteredTree
	 */
	protected FilteredTree tree;
	
	/**
	 * Contains the report for which details should be displayed
	 */
	protected Report<?> report;
	
	/**
	 * Composite for the tabbed property page
	 */
	private Composite composite;
	
	/**
	 * @see AbstractPropertySection#createControls(Composite, TabbedPropertySheetPage)
	 */
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(GridLayoutFactory.fillDefaults().create());
		
		PatternFilter filter = new PatternFilter();
		tree = new FilteredTree(composite, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL, filter, true);

		TreeViewer viewer = tree.getViewer();
		viewer.setContentProvider(new ReportTreeContentProvider());
		viewer.setLabelProvider(new ReportTreeLabelProvider());
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}
	
}
