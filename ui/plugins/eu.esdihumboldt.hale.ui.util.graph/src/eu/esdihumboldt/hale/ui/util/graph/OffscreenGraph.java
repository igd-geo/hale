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

package eu.esdihumboldt.hale.ui.util.graph;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import eu.esdihumboldt.hale.ui.util.swing.SwingRcpUtilities;

/**
 * Renders a graph to an image.
 * @author Simon Templer
 */
public abstract class OffscreenGraph {
	
	private final Graph graph;

	/**
	 * Create an off-screen graph.
	 * @param width the graph width
	 * @param height the graph height
	 */
	public OffscreenGraph(int width, int height) {
		Shell shell = new Shell();
		shell.setSize(width, height);
		shell.setLayout(new FillLayout());
		
	    Composite composite = new Composite(shell, SWT.NONE);
	    composite.setLayout(new FillLayout());
		composite.setVisible(true);
		
		//Workaround to draw in background -->
		graph = new Graph(composite, SWT.NONE);
		GraphViewer viewer = new GraphViewer(graph);
		
		configureViewer(viewer);
		
		if (graph.getLayoutAlgorithm() == null) {
			graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(TreeLayoutAlgorithm.LEFT_RIGHT), true);
		}
		graph.setBounds(0, 0, width, height);
		graph.getViewport().setBounds(new Rectangle(0, 0, width, height));
		shell.setVisible(false);
		
		graph.applyLayoutNow();
		
		IFigure root = graph.getRootLayer();
		root.getUpdateManager().performUpdate();
	}

	/**
	 * Configure the viewer.
	 * @param viewer the graph viewer
	 */
	protected abstract void configureViewer(GraphViewer viewer);
	
	/**
	 * Save the graph as image to an output stream.
	 * @param out the output stream to write the image to
	 * @param format the informal name of the  image format, if <code>null</code>
	 *   defaults to <code>png</code> 
	 * @throws IOException if writing the image fails
	 *   
	 * @see ImageIO#write(java.awt.image.RenderedImage, String, OutputStream)
	 */
	public void save(OutputStream out, String format) throws IOException {
		save(graph.getRootLayer(), out, format);
	}
	
	/**
	 * Save a figure as image to an output stream.
	 * @param root the figure to draw 
	 * @param out the output stream to write the image to
	 * @param format the informal name of the  image format, if <code>null</code>
	 *   defaults to <code>png</code> 
	 * @throws IOException if writing the image fails
	 *   
	 * @see ImageIO#write(java.awt.image.RenderedImage, String, OutputStream)
	 */
	public static void save(IFigure root, OutputStream out, String format) throws IOException {
		if (format == null) {
			format = "png";
		}
		
		Image drawImage = new Image(Display.getCurrent(), root.getSize().width, 
				root.getSize().height);
		final GC gc = new GC(drawImage);
		SWTGraphics graphics = new SWTGraphics(gc); 
		try {
			gc.setAntialias(SWT.ON);
			gc.setInterpolation(SWT.HIGH);
			
			// paint the graph to an image
			root.paint(graphics);
			BufferedImage bufferedImage = SwingRcpUtilities
					.convertToAWT(drawImage.getImageData());
			ImageIO.write(bufferedImage, format, out);
		}
		finally {
			gc.dispose();
			drawImage.dispose();
			out.close();
		}
	}

}
