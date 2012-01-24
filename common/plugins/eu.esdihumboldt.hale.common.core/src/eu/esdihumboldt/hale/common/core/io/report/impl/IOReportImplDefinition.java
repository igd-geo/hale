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

package eu.esdihumboldt.hale.common.core.io.report.impl;

import java.net.URI;
import java.util.Properties;

import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;
import eu.esdihumboldt.hale.common.core.io.report.IOReport;
import eu.esdihumboldt.hale.common.core.io.report.IOReporter;
import eu.esdihumboldt.hale.common.core.io.supplier.DefaultInputSupplier;
import eu.esdihumboldt.hale.common.core.report.impl.AbstractReportDefinition;


/**
* Object definition for {@link IOReporter}.
* @author Andreas Burchert
* @partner 01 / Fraunhofer Institute for Computer Graphics Research
*/
public class IOReportImplDefinition extends AbstractReportDefinition<IOReport, IOReporter> {

	private static final ALogger _log = ALoggerFactory.getLogger(IOReportImplDefinition.class);
	
	/**
	 * Key for target
	 */
	public static final String KEY_IOREPORT_TARGET = "target";
	
	/**
	 * Constructor
	 */
	public IOReportImplDefinition() {
		super(IOReport.class, "io");
	}

	/**
	 * @see eu.esdihumboldt.hale.common.core.report.impl.AbstractReportDefinition#createReport(java.util.Properties)
	 */
	@Override
	protected IOReporter createReport(Properties props) {
		return new DefaultIOReporter(null, props.getProperty(KEY_REPORT_TASKNAME), false);
	}

	/**
	 * @see eu.esdihumboldt.hale.common.core.report.impl.AbstractReportDefinition#configureReport(eu.esdihumboldt.hale.common.core.report.Report, java.util.Properties)
	 */
	@Override
	protected IOReport configureReport(IOReporter reporter, Properties props) {
		try {
			AbstractReportDefinition.configureBasicReporter(reporter, props);
			
			// restore location
			reporter.setTarget(new DefaultInputSupplier(URI.create(props.getProperty(KEY_IOREPORT_TARGET))));
		} catch (Exception e) {
			_log.error("Error while parsing a report", e.getStackTrace());
		}
		
		return reporter;
	}

	@Override
	protected Properties asProperties(IOReport report) {
		Properties props = super.asProperties(report);
		
		props.setProperty(KEY_IOREPORT_TARGET, report.getTarget().getLocation().toString());
		
		return props;
	}
}
