/*
 * Copyright (c) 2017 wetransform GmbH
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
 *     wetransform GmbH <http://www.wetransform.to>
 */

package eu.esdihumboldt.hale.io.haleconnect;

import java.io.InputStream;
import java.util.List;

import com.google.common.util.concurrent.ListenableFuture;

import eu.esdihumboldt.hale.common.core.io.supplier.LocatableInputSupplier;

/**
 * Facade for the hale connect microservices
 * 
 * @author Florian Esser
 */
public interface HaleConnectService {

	/**
	 * @return the {@link BasePathManager} for this service implementation
	 */
	BasePathManager getBasePathManager();

	/**
	 * Adds a listener
	 * 
	 * @param listener the listener to add
	 */
	void addListener(HaleConnectServiceListener listener);

	/**
	 * Removes a listener
	 * 
	 * @param listener the listener to remove
	 */
	void removeListener(HaleConnectServiceListener listener);

	/*
	 * User service methods
	 */

	/**
	 * Login to hale connect
	 * 
	 * @param username user name
	 * @param password password
	 * @return true, if the login attempt was successful, false otherwise
	 * @throws HaleConnectException thrown on any API errors that do not simply
	 *             indicate invalid credentials
	 */
	boolean login(String username, String password) throws HaleConnectException;

	/**
	 * Verify that the given credentials are valid
	 * 
	 * @param username user name
	 * @param password password
	 * @return true, if the credentials were accepted, false otherwise
	 * @throws HaleConnectException thrown on any API errors that do not simply
	 *             indicate invalid credentials
	 */
	boolean verifyCredentials(String username, String password) throws HaleConnectException;

	/**
	 * @return the currently active session or null
	 */
	HaleConnectSession getSession();

	/**
	 * Deletes all session information
	 */
	void clearSession();

	/**
	 * @return true if a login token was issued by hale connect
	 */
	boolean isLoggedIn();

	/**
	 * Get the profile of a user.
	 * 
	 * @param userId ID of the user
	 * @return user profile
	 * @throws HaleConnectException thrown on any API exception
	 */
	HaleConnectUserInfo getUserInfo(String userId) throws HaleConnectException;

	/**
	 * Get information about an organisation
	 * 
	 * @param orgId ID of the organisation
	 * @return org profile
	 * @throws HaleConnectException thrown on any API exception
	 */
	HaleConnectOrganisationInfo getOrganisationInfo(String orgId) throws HaleConnectException;

	/*
	 * Project store methods
	 */

	/**
	 * Get a list of available hale connect transformation projects
	 * 
	 * @return a list of available projects
	 * @throws HaleConnectException thrown on any API error
	 */
	List<HaleConnectProjectInfo> getProjects() throws HaleConnectException;

	/**
	 * Get a list of available hale connect transformation projects
	 *
	 * @return {@link ListenableFuture} of the result
	 * @throws HaleConnectException thrown on any API error
	 */
	ListenableFuture<List<HaleConnectProjectInfo>> getProjectsAsync() throws HaleConnectException;

	/**
	 * Load a transformation from hale connect
	 * 
	 * @param owner Project owner
	 * @param projectId Project ID
	 * @return A LocatableInputSupplier with an InputStream of the project data
	 * @throws HaleConnectException thrown on any API errors
	 */
	LocatableInputSupplier<InputStream> loadProject(Owner owner, String projectId)
			throws HaleConnectException;

}