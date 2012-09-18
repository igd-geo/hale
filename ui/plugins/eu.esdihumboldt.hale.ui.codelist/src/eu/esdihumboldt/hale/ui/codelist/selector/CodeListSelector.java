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

package eu.esdihumboldt.hale.ui.codelist.selector;

import org.eclipse.swt.widgets.Control;

import eu.esdihumboldt.hale.common.codelist.CodeList;

/**
 * Interface for components to select code lists from.
 * 
 * @author Simon Templer
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @version $Id$
 */
public interface CodeListSelector {

	/**
	 * Get the control.
	 * 
	 * @return the control
	 */
	public Control getControl();

	/**
	 * Get the selected code list.
	 * 
	 * @return the selected code list or <code>null</code>
	 */
	public CodeList getCodeList();
}
