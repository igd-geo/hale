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

package eu.esdihumboldt.hale.common.align.model;

/**
 * Mutable {@link Alignment} which is used where changes to the alignment are
 * allowed.
 * 
 * @author Simon Templer
 */
public interface MutableAlignment extends Alignment {

	/**
	 * Add a cell to the alignment
	 * 
	 * @param cell the cell to add. It should be already configured, especially
	 *            with the cell target
	 */
	public void addCell(Cell cell);

	/**
	 * Remove a cell
	 * 
	 * @param cell the cell to remove
	 * @return if the cell was present and removed
	 */
	public boolean removeCell(Cell cell);

	/**
	 * Sets the base alignment.
	 * 
	 * @param alignment the new base alignment
	 */
	public void setBaseAlignment(Alignment alignment);

	/**
	 * Sets the next cell id to assign. This is used for consistency reasons
	 * with alignments extending this alignment.
	 * 
	 * @param nextCellId the next cell id to use
	 */
	public void setNextCellId(int nextCellId);

}
