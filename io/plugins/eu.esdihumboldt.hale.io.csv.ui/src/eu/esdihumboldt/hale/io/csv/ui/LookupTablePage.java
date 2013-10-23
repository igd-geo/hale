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

package eu.esdihumboldt.hale.io.csv.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import au.com.bytecode.opencsv.CSVReader;
import eu.esdihumboldt.hale.common.core.io.Value;
import eu.esdihumboldt.hale.common.lookup.LookupTableImport;
import eu.esdihumboldt.hale.io.csv.reader.internal.CSVLookupReader;
import eu.esdihumboldt.hale.io.csv.reader.internal.CSVUtil;
import eu.esdihumboldt.hale.io.csv.writer.LookupTableExportConstants;
import eu.esdihumboldt.hale.ui.lookup.LookupTableImportConfigurationPage;

/**
 * The page to specify which column should be matched with which column
 * 
 * @author Dominik Reuter
 */
@SuppressWarnings("restriction")
public class LookupTablePage extends LookupTableImportConfigurationPage implements
		SelectionListener {

	private Combo choose;
	private Combo keyColumn;
	private Combo valueColumn;
	private Label l;
	private boolean skip;

	/**
	 * Default Constructor
	 */
	public LookupTablePage() {
		super("LookupTablePage");
		setTitle("Specify which column will be connected with which column");
	}

	/**
	 * @see eu.esdihumboldt.hale.ui.HaleWizardPage#createContent(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createContent(Composite page) {
		page.setLayout(new GridLayout());
		// head composite
		Composite head = new Composite(page, SWT.NONE);
		head.setLayout(new GridLayout(2, false));

		Label withHeadlines = new Label(head, SWT.NONE);
		withHeadlines.setText("Select if the first row contains headlines");

		choose = new Combo(head, SWT.READ_ONLY);
		String[] selection = new String[] { "Yes", "No" };
		choose.setItems(selection);
		choose.addSelectionListener(this);

		// Label
		l = new Label(page, SWT.NONE);
		l.setText("Specify which column will be connected with which column");
		l.setVisible(false);

		// composite
		Composite middle = new Composite(page, SWT.NONE);
		middle.setLayout(new GridLayout(2, false));

		GridData layoutData = new GridData();
		layoutData.widthHint = 200;

		keyColumn = new Combo(middle, SWT.READ_ONLY);
		keyColumn.setLayoutData(GridDataFactory.copyData(layoutData));
		keyColumn.setVisible(false);
		keyColumn.addSelectionListener(this);

		valueColumn = new Combo(middle, SWT.READ_ONLY);
		valueColumn.setLayoutData(GridDataFactory.copyData(layoutData));
		valueColumn.setVisible(false);
		valueColumn.addSelectionListener(this);

		setPageComplete(false);
	}

	/**
	 * @see eu.esdihumboldt.hale.ui.io.IOWizardPage#updateConfiguration(eu.esdihumboldt.hale.common.core.io.IOProvider)
	 */
	@Override
	public boolean updateConfiguration(LookupTableImport provider) {
		provider.setParameter(LookupTableExportConstants.PARAM_SKIP_FIRST_LINE, Value.of(skip));

		if (keyColumn.getSelectionIndex() != -1 && valueColumn.getSelectionIndex() != -1) {
			provider.setParameter(LookupTableExportConstants.LOOKUP_KEY_COLUMN,
					Value.of(keyColumn.getSelectionIndex()));
			provider.setParameter(LookupTableExportConstants.LOOKUP_VALUE_COLUMN,
					Value.of(valueColumn.getSelectionIndex()));
			return true;
		}
		else {
			setErrorMessage("You have to match the columns");
			return false;
		}
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() != null) {
			if (e.getSource().equals(choose)) {
				l.setVisible(true);
				keyColumn.setVisible(true);
				valueColumn.setVisible(true);
				String[] header = readHeader();
				if (((Combo) e.getSource()).getSelectionIndex() == 0) {
					// yes is selected
					// Load the csv-file to get the column informations
					skip = true;
					keyColumn.setItems(header);
					valueColumn.setItems(header);
					setPageComplete(true);
				}
				else {
					// no is selected
					skip = false;
					int numberOfColumns = header.length;
					String[] items = new String[numberOfColumns];
					for (int i = 0; i < numberOfColumns; i++) {
						int tmp = i + 1;
						items[i] = "Column " + tmp;
					}
					keyColumn.setItems(items);
					valueColumn.setItems(items);
					setPageComplete(true);
				}
			}
		}
	}

	private String[] readHeader() {
		LookupTableImport provider = getWizard().getProvider();
		List<String> items = new ArrayList<String>();
		try {
			if (provider instanceof CSVLookupReader) {
				CSVReader reader = CSVUtil.readFirst(getWizard().getProvider());
				return reader.readNext();
			}
			else {
				Workbook workbook;
				// write xls file
				String file = provider.getSource().getLocation().getPath();
				String fileExtension = file.substring(file.lastIndexOf("."), file.length());
				if (fileExtension.equals(".xls")) {
					workbook = new HSSFWorkbook(provider.getSource().getInput());
				}
				// write xlsx file
				else if (fileExtension.equals(".xlsx")) {
					workbook = new XSSFWorkbook(provider.getSource().getInput());
				}
				else
					return new String[0];
				Sheet sheet = workbook.getSheetAt(0);
				Row currentRow = sheet.getRow(0);
				for (int cell = 0; cell < currentRow.getPhysicalNumberOfCells(); cell++) {
					items.add(currentRow.getCell(cell).getStringCellValue());
				}
				return items.toArray(new String[0]);
			}
		} catch (IOException e) {
			// TODO logging
			e.printStackTrace();
		}
		return new String[0];
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// nothing to do here.
	}

	/**
	 * @see eu.esdihumboldt.hale.ui.io.config.AbstractConfigurationPage#enable()
	 */
	@Override
	public void enable() {
		// nothing to do here.
	}

	/**
	 * @see eu.esdihumboldt.hale.ui.io.config.AbstractConfigurationPage#disable()
	 */
	@Override
	public void disable() {
		// nothing to do here.
	}
}
