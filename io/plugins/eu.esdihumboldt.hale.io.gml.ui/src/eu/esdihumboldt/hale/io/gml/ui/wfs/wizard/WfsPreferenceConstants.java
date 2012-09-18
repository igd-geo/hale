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

package eu.esdihumboldt.hale.io.gml.ui.wfs.wizard;

/**
 * 
 * 
 * @author Simon Templer
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 */
public interface WfsPreferenceConstants {

	/**
	 * The maximum number of recent WFS
	 */
	public final int MAX_RECENT_WFS = 10;

	/**
	 * The name of the recent WFS key prefix
	 */
	public final String KEY_RECENT_WFS_PREFIX = "wfs.recent.url"; //$NON-NLS-1$

	/**
	 * The name of the recent WFS count key
	 */
	public final String KEY_RECENT_WFS_COUNT = "wfs.recent.count"; //$NON-NLS-1$

}
