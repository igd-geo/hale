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
package eu.esdihumboldt.hale.ui.application;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.geotools.referencing.CRS;
import org.slf4j.bridge.SLF4JBridgeHandler;

import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;
import eu.esdihumboldt.hale.ui.common.crs.WKTPreferencesCRSFactory;
import eu.esdihumboldt.hale.ui.util.proxy.ProxySettings;

/**
 * This class controls all aspects of the application's execution.
 * 
 * @author Thorsten Reitz
 * @author Simon Templer
 */
public class Application implements IApplication {

	private static ALogger _log = ALoggerFactory.getLogger(Application.class);

	/**
	 * @see IApplication#start(IApplicationContext)
	 */
	@Override
	public Object start(IApplicationContext context) {
		// install SLF4J JUL bridge
		SLF4JBridgeHandler.install();

		WKTPreferencesCRSFactory.install();

		// init HSQL database
		try {
			CRS.decode("EPSG:4326"); //$NON-NLS-1$
		} catch (Exception e) {
			_log.error("Error while initializing epsg database", e); //$NON-NLS-1$
		}

		// find base path of the application.
//		URL location = this.getClass().getProtectionDomain().getCodeSource().getLocation();
//		String location_path = location.getPath().replace(" ", "+"); //$NON-NLS-1$ //$NON-NLS-2$
//		location_path = location_path.replace("bin/", ""); //$NON-NLS-1$ //$NON-NLS-2$
//		_log.debug(location_path);
//		Application.basepath = location_path;

		// read and set proxy settings
		try {
			ProxySettings.install();
		} catch (Exception ex) {
			_log.warn("Setting the Proxy configuration failed: " + ex.getMessage()); //$NON-NLS-1$
		}

		// initialize UI
		Display display = PlatformUI.createDisplay();

		// register listener for OpenDoc events
		OpenDocumentEventProcessor openDocProcessor = new OpenDocumentEventProcessor();
		display.addListener(SWT.OpenDocument, openDocProcessor);

		// run application
		try {
			int returnCode = PlatformUI.createAndRunWorkbench(display,
					new ApplicationWorkbenchAdvisor(openDocProcessor));
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}
			return IApplication.EXIT_OK;
		} finally {
			display.dispose();
		}

	}

	/**
	 * @see IApplication#stop()
	 */
	@Override
	public void stop() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}

}
