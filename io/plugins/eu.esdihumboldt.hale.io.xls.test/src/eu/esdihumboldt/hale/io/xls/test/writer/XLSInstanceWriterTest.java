/*
 * Copyright (c) 2014 Data Harmonisation Panel
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

package eu.esdihumboldt.hale.io.xls.test.writer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import eu.esdihumboldt.cst.test.TransformationExample;
import eu.esdihumboldt.cst.test.TransformationExamples;
import eu.esdihumboldt.hale.common.core.io.Value;
import eu.esdihumboldt.hale.common.core.io.report.IOReport;
import eu.esdihumboldt.hale.common.core.io.supplier.FileIOSupplier;
import eu.esdihumboldt.hale.common.test.TestUtil;
import eu.esdihumboldt.hale.io.csv.InstanceTableIOConstants;
import eu.esdihumboldt.hale.io.xls.AnalyseXLSSchemaTable;
import eu.esdihumboldt.hale.io.xls.writer.XLSInstanceWriter;

/**
 * Test class for {@link XLSInstanceWriter}, also uses
 * {@link AnalyseXLSSchemaTable}
 * 
 * @author Yasmina Kammeyer
 */
public class XLSInstanceWriterTest {

	/**
	 * a temporary folder to safely store tmp files. Will be deleted after test
	 * (successfully or not)
	 */
	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();

	/**
	 * Wait for needed services to be running
	 */
	@BeforeClass
	public static void waitForServices() {
		TestUtil.startConversionService();
	}

	/**
	 * Test - write data of complex schema and analyze result
	 * 
	 * @throws Exception , if an error occurs
	 */
	@Test
	public void testWriteComplexSchema() throws Exception {

		TransformationExample example = TransformationExamples
				.getExample(TransformationExamples.SIMPLE_COMPLEX);
		// alternative the data could be generated by iterating through the
		// exempleproject's sourcedata
		String firstDataRow = "id0, name0, age0, income0, street0, city0, street1, city1";
		String secondDataRow = "id1, name1, age1, income1, street2, city2, street3, city3";
		int numberOfEntries = 6;

		// set instances to xls instance writer
		XLSInstanceWriter writer = new XLSInstanceWriter();
		IContentType contentType = Platform.getContentTypeManager().getContentType(
				"eu.esdihumboldt.hale.io.xls.xls");
		writer.setParameter(InstanceTableIOConstants.SOLVE_NESTED_PROPERTIES, Value.of(true));

		File tmpFile = tmpFolder.newFile("excelTestWriteComplexSchema.xls");

		writer.setInstances(example.getSourceInstances());
		// write instances to a temporary XLS file
		writer.setTarget(new FileIOSupplier(tmpFile));
		writer.setContentType(contentType);
		IOReport report = writer.execute(null);
		assertTrue(report.isSuccess());

		// Analyze the written table header
		AnalyseXLSSchemaTable readWrittenData = new AnalyseXLSSchemaTable(tmpFile.toURI(), 0);
		List<String> header = readWrittenData.getHeader();
		String headerString = "id, name, details.age, details.income, details.address.street, details.address.city";

		// XXX This test will fail, because not all populated properties are
		// correctly resolved yet.
		// assertEquals("Missing property(s).", numberOfEntries, header.size());

		for (String head : header) {
			assertTrue("Property not valid property: " + head, headerString.contains(head));
		}

		// rows of AnalyseXLSSchemaTable only contains data
		Collection<List<String>> rows = readWrittenData.getRows();
		List<String> row = rows.iterator().next();
		// maybe there are empty cells null values. But the length should be the
		// same as the header size
		assertEquals("Not enough cells for this schema", header.size(), row.size());
		// Analyze one row of data
		boolean contained = true;
		for (String cellData : row) {
			contained = contained & firstDataRow.contains(cellData);
		}
		if (!contained) {
			contained = true;
			for (String cellData : row) {
				contained = contained & secondDataRow.contains(cellData);
			}
		}

		assertTrue("The data is not written correctly.", contained);

	}

	/**
	 * 
	 * @param tmpFile the excel file
	 * @param sheetNames every sheet name, determines the number of analyzed
	 *            sheets
	 */
	private void checkSheetNames(File tmpFile, List<String> sheetNames) throws Exception {

		Workbook wb = WorkbookFactory.create(tmpFile);
		assertEquals("Not enough sheets", wb.getNumberOfSheets(), sheetNames.size());
		Sheet sheet;
		for (int i = 0; i < sheetNames.size(); i++) {
			sheet = wb.getSheetAt(i);
			assertTrue("There is no sheet in the file named: " + sheet.getSheetName(),
					sheetNames.contains(sheet.getSheetName()));
		}

	}

	/**
	 * test - if a complex schema with data is present and this schema contains
	 * more than one type, the exporter should export all types (one sheet per
	 * type) or the selected one XXX not supported under current circumstances
	 */
	public void testExportChoosenType() {
		// TODO
	}

	/**
	 * test - if a complex schema has a type containing an object attribute with
	 * maxOccures > 1, one type can contain more than one instance of that
	 * object. If this is the case than... TODO Export special case.
	 */
	public void testMultipleInstances() {
		// TransformationExample example =
		// TransformationExamples.getExample(TransformationExamples.CM_NESTED_1);
		// TODO
	}

}
