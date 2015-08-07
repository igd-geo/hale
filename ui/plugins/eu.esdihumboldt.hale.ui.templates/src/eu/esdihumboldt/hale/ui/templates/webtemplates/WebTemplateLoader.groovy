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

package eu.esdihumboldt.hale.ui.templates.webtemplates

import java.net.Proxy.Type

import org.apache.http.auth.AuthScope
import org.apache.http.auth.Credentials
import org.apache.http.auth.UsernamePasswordCredentials

import eu.esdihumboldt.hale.ui.templates.internal.TemplatesUIPlugin
import eu.esdihumboldt.hale.ui.templates.preferences.WebTemplatesPreferences
import eu.esdihumboldt.util.http.ProxyUtil
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient

/**
 * Loads web templates.
 * @author Simon Templer
 */
class WebTemplateLoader {

	static List<WebTemplate> load() throws Exception {
		load(TemplatesUIPlugin.getDefault().preferenceStore.getString(WebTemplatesPreferences.PREF_WEB_TEMPLATES_URL))
	}

	static List<WebTemplate> load(String templatesUrl) throws Exception {
		List<WebTemplate> result = []

		def client = new RESTClient(templatesUrl)
		applyProxy(client)
		def resp = client.get(path: 'api/all')
		assert resp.status == 200

		resp.data.templates.each { result << new WebTemplate(id: it.id, name: it.name, project: URI.create(it.project), site: URI.create(it.site)) }

		result
	}
	/**
	 * Applies the proxy if there is any to the builder. 
	 * Sets the credential configured in the HALE settings.
	 * 
	 * @param builder a http builder
	 */
	public static void applyProxy(HTTPBuilder builder){

		Proxy proxy = ProxyUtil.findProxy(builder.defaultURI.base)

		if (proxy != null && proxy.type() == Type.HTTP) {

			InetSocketAddress proxyAddress = proxy.address()

			String userName = System.getProperty("http.proxyUser");
			String password = System.getProperty("http.proxyPassword");
			boolean useProxyAuth = userName != null && !userName.isEmpty();

			if(useProxyAuth){

				Credentials cred = new UsernamePasswordCredentials(userName, password);

				builder.getClient()getCredentialsProvider().setCredentials(new AuthScope(proxyAddress.getHostName(),proxyAddress.getPort()),cred)
			}

			builder.setProxy(proxyAddress.getHostName(), proxyAddress.getPort(), "http")
		}
	}
}
