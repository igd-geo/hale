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

package eu.esdihumboldt.hale.server.war.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.velocity.VelocityContext;
import org.springframework.web.HttpRequestHandler;

import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;
import eu.esdihumboldt.hale.prefixmapper.NamespacePrefixMapperImpl;
import eu.esdihumboldt.hale.server.war.WpsException;
import eu.esdihumboldt.hale.server.war.WpsException.WpsErrorCode;
import eu.esdihumboldt.hale.server.war.WpsUtil;
import eu.esdihumboldt.hale.server.war.handler.ExecuteProcess;
import eu.esdihumboldt.hale.server.war.wps.CodeType;
import eu.esdihumboldt.hale.server.war.wps.DataInputsType;
import eu.esdihumboldt.hale.server.war.wps.DocumentOutputDefinitionType;
import eu.esdihumboldt.hale.server.war.wps.Execute;
import eu.esdihumboldt.hale.server.war.wps.InputReferenceType;
import eu.esdihumboldt.hale.server.war.wps.InputType;
import eu.esdihumboldt.hale.server.war.wps.OutputDefinitionType;
import eu.esdihumboldt.hale.server.war.wps.ResponseDocumentType;
import eu.esdihumboldt.hale.server.war.wps.ResponseFormType;

/**
 * This implements a generic web client for CST-WPS.
 * 
 * @author Andreas Burchert
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 */
public class Client implements HttpRequestHandler {

	private final ALogger _log = ALoggerFactory.getLogger(Client.class);
	
	private JAXBContext context;

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(true);
			
			// session should not timeout
			session.setMaxInactiveInterval(-1);
			
			_log.info("Session ID: "+ session.getId());
			if (context == null) {
				try {
					context = JAXBContext.newInstance(eu.esdihumboldt.hale.server.war.wps.ObjectFactory.class);
				} catch (JAXBException e) {
					throw new WpsException("Error initializing JAXB context.",
							WpsErrorCode.NoApplicableCode, e, null);
				}
			}
			
			// create a writer
			
			// handle upload data
			if (request.getParameter("upload") != null) {
				// check if the workspace is available
				ExecuteProcess.prepareWorkspace(request);
				
				try {
					Execute exec = this.handleUploadData(request, session.getAttribute("workspace").toString());
					
					Marshaller marshaller = context.createMarshaller();
					marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", //$NON-NLS-1$
							new NamespacePrefixMapperImpl());
					
					StringWriter sw = new StringWriter();
					marshaller.marshal(exec, sw);
					
					// generate "virtual" request
					Map<String, String> params = new HashMap<String, String>();
					params.put("request", sw.toString());
					
					// execute process
					new ExecuteProcess(params, response, request);
				} catch (Exception e) {
					throw new WpsException("Error processing form input.",
							WpsErrorCode.NoApplicableCode, e, null);
				}
			}
			// delete workspace
			else if (request.getParameter("deleteAll") != null) {
				// check if a session is available AND a workspace is set
				if (session.getId() != null && session.getAttribute("workspace") != null) {
					// delete workspace
					ExecuteProcess.deleteAll(request);
				} else if (request.getParameter("id") != null) {
					// set the given session id
					session.setAttribute("id", request.getParameter("id"));
					
					// create the workspace
					// this is needed as we can't guess where the workspace is
					ExecuteProcess.prepareWorkspace(request);
					
					// and delete it
					ExecuteProcess.deleteAll(request);
				}
			}
			// nothing requested, just show the upload form
			else {
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer = response.getWriter();
				try {
					this.showForm(request, writer);
				} catch (Exception e) {
					throw new WpsException("Could not load client template.",
							WpsErrorCode.NoApplicableCode, e, null);
				} finally {
					// close output stream
					writer.close();
				}
			}
		} catch (Exception e) {
			WpsUtil.printError(e, null, response);
		}
	}
	
	/**
	 * Loads the static form.
	 * 
	 * @param request the request
	 * @param writer outputstream
	 * @throws Exception if something goes wrong
	 */
	private void showForm(HttpServletRequest request, PrintWriter writer) throws Exception {
		HttpSession session = request.getSession();
		
		VelocityContext context = new VelocityContext();
		context.put("session_id", session.getId());
		
		WpsUtil.mergeTemplate(
				"cst-wps-static/client/client.html",
				context, "UTF-8", writer);
	}
	
	private Execute handleUploadData(HttpServletRequest request, String path) throws Exception {
		if (ServletFileUpload.isMultipartContent(request)) {
			Execute exec = new Execute();
			
			boolean asReference = false;
			
			// set identifier
			CodeType codeType = new CodeType();
			codeType.setValue("translate");
			exec.setIdentifier(codeType);
			exec.setService("WPS");
			exec.setVersion("1.0.0");
			
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			//
			DataInputsType dataInputType = new DataInputsType();
			
			try {
				@SuppressWarnings("unchecked")
				List<FileItem> items = upload.parseRequest(request);
				
				for (FileItem item : items) {
					URL fileURL = null;
					InputType input = new InputType();
					
					// remove chars so we can use the fieldname as element data
					String fieldName = item.getFieldName().replace("[]", "");
					fieldName = fieldName.replace("URL", "");
					
					// display check
					if (fieldName.equals("save")) {
						// save the value to session
						if (item.getString().equals("link")) {
							asReference = true;
						}
						continue;
					}
					
					// Process a regular form field
					if (item.isFormField()) {
						String filePath = item.getString();
						
						// skip if no url is given
						if (filePath.equals("")) {
							continue;
						}
						else {
							fileURL = new URL(filePath);
						}
					} else {
						String fileName = item.getName();
						
						if (fileName.equals("")) {
							continue;
						}
						
						File file = new File(path + fileName);
						fileURL = file.toURI().toURL();
						
						InputStream is = item.getInputStream();
						
						// create file
						FileOutputStream fos = new FileOutputStream(file);
						BufferedOutputStream os = new BufferedOutputStream(fos);
						
						int avail = is.available();
						byte[] data = new byte[avail];
						while (is.read(data, 0, avail)>0) {
							os.write(data);
							os.flush();
							avail = is.available();
							data = new byte[avail];
						}
						
						// flush and close
						os.flush();
						os.close();
						fos.close();
						is.close();
					}
					
					if (fileURL != null) {
						// create data identifier
						CodeType ct = new CodeType();
						ct.setValue(fieldName);
						
						// set identifier
						input.setIdentifier(ct);
						
						InputReferenceType inputReference = new InputReferenceType();
						inputReference.setHref(fileURL.toExternalForm());
						
						input.setReference(inputReference);
						
						// add to <wps:DataInputs>
						dataInputType.getInput().add(input);
					}
				}
			} catch (FileUploadException e) {
				_log.error(e.getMessage(), "Error during multipart parsing.");
			}
			
			//
			exec.setDataInputs(dataInputType);
			
			ResponseFormType formType = new ResponseFormType();
			
			if (asReference) {
				ResponseDocumentType documentType = new ResponseDocumentType();
				DocumentOutputDefinitionType type = new DocumentOutputDefinitionType();
				CodeType targetData = new CodeType();
				targetData.setValue("TargetData");
				type.setIdentifier(targetData);
				
				// display as reference
				type.setAsReference(true);
				documentType.setStoreExecuteResponse(false);
				
				documentType.getOutput().add(type);
				formType.setResponseDocument(documentType);
			}
			else {
				// request raw data output
				OutputDefinitionType outputDef = new OutputDefinitionType();
				CodeType outputIdentifier = new CodeType();
				outputIdentifier.setValue("TargetData");
				outputDef.setIdentifier(outputIdentifier );
				formType.setRawDataOutput(outputDef );
			}
			
			exec.setResponseForm(formType);
			
			return exec;
		}
		
		return null;
	}
}
