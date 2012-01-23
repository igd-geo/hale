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

package eu.esdihumboldt.hale.common.core.report.impl;

import java.util.Properties;

import eu.esdihumboldt.hale.common.core.report.Message;
import eu.esdihumboldt.hale.common.core.report.Report;
import eu.esdihumboldt.hale.common.core.report.Reporter;

/**
* Object definition for {@link DefaultReporter}.
* @author Andreas Burchert
* @partner 01 / Fraunhofer Institute for Computer Graphics Research
*/
@SuppressWarnings("rawtypes")
public class ReportImplDefintion extends AbstractReportDefinition<Report, Reporter<?>> {
	
	/**
	 * Default constructor.
	 */
	public ReportImplDefintion() {
		super(Report.class, "default");
	}

	/**
	 * @see eu.esdihumboldt.hale.common.core.report.impl.AbstractReportDefinition#createReport(java.util.Properties)
	 */
	@Override
	protected DefaultReporter<?> createReport(Properties props) {
		// TODO Auto-generated method stub
		return new DefaultReporter<Message>(props.getProperty(KEY_REPORT_TASKNAME), 
				null, false);
	}

	/**
	 * @see eu.esdihumboldt.hale.common.core.report.impl.AbstractReportDefinition#configureReport(eu.esdihumboldt.hale.common.core.report.Report, java.util.Properties)
	 */
	@Override
	protected Report configureReport(Reporter<?> reporter,
			Properties props) {
		reporter.setSummary(props.getProperty(KEY_REPORT_SUMMARY));
		// TODO extract this function to create a static version in superclass
		// add infos, warnings and errors
//				report.getWarnings().addAll(Arrays.asList(StringUtils.split(props.getProperty(KEY_REPORT_WARNINGS), ";"))); // TODO add a proper way of adding old warnings and stuff
		
		
		return reporter;
	}

	
}
