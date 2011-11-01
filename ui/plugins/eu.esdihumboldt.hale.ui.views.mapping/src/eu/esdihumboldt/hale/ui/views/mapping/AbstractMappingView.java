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

package eu.esdihumboldt.hale.ui.views.mapping;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.part.WorkbenchPart;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import eu.esdihumboldt.hale.ui.views.mapping.graph.GraphLabelProvider;
import eu.esdihumboldt.hale.ui.views.mapping.graph.SimpleCellContentProvider;

/**
 * TODO Type description
 * @author Simon Templer
 */
public abstract class AbstractMappingView extends ViewPart implements IZoomableWorkbenchPart {
	
	private GraphViewer viewer;

	/**
	 * @see WorkbenchPart#createPartControl(Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		viewer = new GraphViewer(parent, SWT.BORDER);
		viewer.setContentProvider(new SimpleCellContentProvider());
		viewer.setLabelProvider(new GraphLabelProvider());
		viewer.setInput(null);
		LayoutAlgorithm layout = createLayout();
		viewer.setLayoutAlgorithm(layout, true);
		viewer.applyLayout();
		fillToolBar();
		
		getSite().setSelectionProvider(getViewer());
	}
	
	private LayoutAlgorithm createLayout() {
		LayoutAlgorithm layout;
		// layout = new
		// SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		layout = new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		// layout = new
		// GridLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		// layout = new
		// HorizontalTreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		// layout = new
		// RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		return layout;
	}
	
	private void fillToolBar() {
		ZoomContributionViewItem toolbarZoomContributionViewItem = new ZoomContributionViewItem(
				this);
		IActionBars bars = getViewSite().getActionBars();
		bars.getMenuManager().add(toolbarZoomContributionViewItem);
	}

	/**
	 * @see WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	/**
	 * @return the viewer
	 */
	protected GraphViewer getViewer() {
		return viewer;
	}

	/**
	 * @see IZoomableWorkbenchPart#getZoomableViewer()
	 */
	@Override
	public AbstractZoomableViewer getZoomableViewer() {
		return viewer;
	}

}
