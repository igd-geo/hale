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

package eu.esdihumboldt.hale.ui.views.styledmap;

import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.jdesktop.swingx.mapviewer.TileOverlayPainter;

import de.cs3d.util.eclipse.extension.selective.SelectiveExtension.SelectiveExtensionListener;
import de.fhg.igd.mapviewer.view.MapView;
import de.fhg.igd.mapviewer.view.MapViewExtension;
import de.fhg.igd.mapviewer.view.overlay.ITileOverlayService;
import de.fhg.igd.mapviewer.view.overlay.TileOverlayFactory;
import eu.esdihumboldt.hale.ui.views.styledmap.painter.AbstractInstancePainter;

/**
 * Map view extension for the styled map.
 * @author Simon Templer
 */
public class StyledMapExtra implements MapViewExtension, IPartListener2 {

	private MapView mapView;

	/**
	 * @see MapViewExtension#setMapView(MapView)
	 */
	@Override
	public void setMapView(MapView mapView) {
		this.mapView = mapView;
		
		/*
		 * Listen for activated/deactivated scene painters
		 * 
		 * - remove listeners for deactivated painters and clear the waypoints
		 * - update activated listeners and add the corresponding listeners
		 */
		ITileOverlayService overlayService = (ITileOverlayService) PlatformUI.getWorkbench().getService(ITileOverlayService.class);
		overlayService.addListener(new SelectiveExtensionListener<TileOverlayPainter, TileOverlayFactory>() {
			
			@Override
			public void deactivated(TileOverlayPainter object,
					TileOverlayFactory definition) {
				if (object instanceof AbstractInstancePainter) {
					AbstractInstancePainter painter = (AbstractInstancePainter) object;
						
					// get services
					ISelectionService selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
					
					// remove listeners
					selection.removeSelectionListener(painter);
					//TODO instance and style
					
					// clear way-points
					painter.clearWaypoints();
				}
			}
			
			@Override
			public void activated(TileOverlayPainter object,
					TileOverlayFactory definition) {
				if (object instanceof AbstractInstancePainter) {
					AbstractInstancePainter painter = (AbstractInstancePainter) object;
					
					// get services
					ISelectionService selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
					
					// update
					painter.update(selection.getSelection());
					
					// add listeners
					selection.addSelectionListener(painter);
					//TODO instance and style
				}
			}
		});
		
		IPartService partService = (IPartService)mapView.getSite().getService(IPartService.class);
        partService.addPartListener(this);
	}
	
	/**
	 * @see IPartListener2#partClosed(org.eclipse.ui.IWorkbenchPartReference)
	 */
	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
		if (partRef.getPart(false) == mapView) {
			// get services
			ISelectionService selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
			
			// remove listeners
	        disableScenePainterListeners(selection);
		}
	}

	/**
	 * @see IPartListener2#partOpened(org.eclipse.ui.IWorkbenchPartReference)
	 */
	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
		if (partRef.getPart(false) == mapView) {
			// get services
			ISelectionService selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
			
			// update
			updateScenePainters(selection);
				
			// add listeners
			enableScenePainterListeners(selection);
		}
	}
	
	/**
	 * Add the instance painters as listeners.
	 * @param selection the selection service
	 */
	private void enableScenePainterListeners(ISelectionService selection) {
		for (AbstractInstancePainter painter : mapView.getMapKit().getTilePainters(AbstractInstancePainter.class)) {
			selection.addSelectionListener(painter);
			//TODO add instance and style service listener
		}
	}

	/**
	 * Update the instance painters.
	 * @param selection the selection service
	 */
	private void updateScenePainters(ISelectionService selection) {
		for (AbstractInstancePainter painter : mapView.getMapKit().getTilePainters(AbstractInstancePainter.class)) {
			painter.update(selection.getSelection());
		}
	}
	
	/**
	 * Remove the instance painters as listeners.
	 * @param selection the selection service
	 */
	private void disableScenePainterListeners(ISelectionService selection) {
		for (AbstractInstancePainter painter : mapView.getMapKit().getTilePainters(AbstractInstancePainter.class)) {
			selection.removeSelectionListener(painter);
			//TODO remove instance and style service listener
		}
	}

	/**
	 * @see IPartListener2#partActivated(IWorkbenchPartReference)
	 */
	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
		// ignore
	}

	/**
	 * @see IPartListener2#partBroughtToTop(IWorkbenchPartReference)
	 */
	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
		// ignore
	}

	/**
	 * @see IPartListener2#partHidden(IWorkbenchPartReference)
	 */
	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
		// ignore
	}

	/**
	 * @see IPartListener2#partVisible(IWorkbenchPartReference)
	 */
	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
		// ignore
	}
	/**
	 * @see IPartListener2#partDeactivated(org.eclipse.ui.IWorkbenchPartReference)
	 */
	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
		// ignore
	}

	/**
	 * @see IPartListener2#partInputChanged(org.eclipse.ui.IWorkbenchPartReference)
	 */
	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {
		// ignore
	}

}
