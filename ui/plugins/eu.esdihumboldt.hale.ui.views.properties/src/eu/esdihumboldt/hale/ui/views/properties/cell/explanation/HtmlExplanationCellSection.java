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

package eu.esdihumboldt.hale.ui.views.properties.cell.explanation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;
import eu.esdihumboldt.hale.common.align.extension.function.AbstractFunction;
import eu.esdihumboldt.hale.common.align.extension.function.FunctionUtil;
import eu.esdihumboldt.hale.common.align.model.Cell;
import eu.esdihumboldt.hale.common.align.model.CellExplanation;
import eu.esdihumboldt.hale.ui.views.properties.cell.AbstractCellSection;

/**
 * HTML cell explanation section.
 * 
 * @author Simon Templer
 */
public class HtmlExplanationCellSection extends AbstractCellSection {

	private static final ALogger log = ALoggerFactory.getLogger(HtmlExplanationCellSection.class);

	private Browser browser;

	private Text textField;

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);

		Composite page = getWidgetFactory().createComposite(parent);
		page.setLayout(new FillLayout());

		try {
			browser = new Browser(page, SWT.NONE);
		} catch (Throwable e) {
			log.warn("Could not create embedded browser, using text field as fall-back", e);
			textField = new Text(page, SWT.MULTI | SWT.WRAP);
		}
	}

	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}

	@Override
	public void refresh() {
		super.refresh();

		Cell cell = getCell();
		if (cell != null) {
			AbstractFunction<?> function = FunctionUtil.getFunction(cell
					.getTransformationIdentifier());
			if (function != null) {
				CellExplanation explanation = function.getExplanation();
				if (explanation != null) {
					if (browser != null) {
						String text = explanation.getExplanationAsHtml(cell);
						if (text == null) {
							text = explanation.getExplanation(cell);
						}
						if (text != null) {
							browser.setText(text);
							return;
						}
					}
					else if (textField != null) {
						String text = explanation.getExplanation(cell);
						if (text != null) {
							textField.setText(text);
						}
					}
				}
			}
		}

		String text = "Sorry, no explanation available.";
		if (browser != null) {
			browser.setText(text);
		}
		else if (textField != null) {
			textField.setText(text);
		}
	}

}
