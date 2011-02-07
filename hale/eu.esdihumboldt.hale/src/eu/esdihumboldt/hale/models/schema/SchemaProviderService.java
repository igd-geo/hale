/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                  01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to this website:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to : http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2010.
 *
 * Component    : HALE
 * Created on   : Jun 3, 2009 -- 4:50:10 PM
 */
package eu.esdihumboldt.hale.models.schema;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;
import de.cs3d.util.logging.ATransaction;
import eu.esdihumboldt.hale.models.SchemaService;
import eu.esdihumboldt.hale.schemaprovider.ProgressIndicator;
import eu.esdihumboldt.hale.schemaprovider.Schema;
import eu.esdihumboldt.hale.schemaprovider.SchemaProvider;
import eu.esdihumboldt.hale.schemaprovider.model.AttributeDefinition;
import eu.esdihumboldt.hale.schemaprovider.model.Definition;
import eu.esdihumboldt.hale.schemaprovider.model.SchemaElement;
import eu.esdihumboldt.hale.schemaprovider.provider.ApacheSchemaProvider;
import eu.esdihumboldt.hale.schemaprovider.provider.ShapeSchemaProvider;

/**
 * Implementation of {@link SchemaService}. It uses a {@link SchemaProvider}
 * for actually loading the Schema
 * 
 * @author Simon Templer, Fraunhofer IGD
 * @version $Id$
 */
public class SchemaProviderService 
	extends AbstractSchemaService {
	
	private static ALogger log = ALoggerFactory.getLogger(SchemaProviderService.class); 
	
	/**
	 * Source schema
	 */
	private Schema sourceSchema = Schema.EMPTY_SCHEMA;

	/** 
	 * Target schema 
	 */
	private Schema targetSchema = Schema.EMPTY_SCHEMA;
	
	private final Set<SchemaProvider> providers = new HashSet<SchemaProvider>();

	private String sourceSchemaFormat;

	private String targetSchemaFormat;
	
	/**
	 * Creates the schema service
	 */
	public SchemaProviderService() {
		super();
		
		providers.add(new ApacheSchemaProvider());
		providers.add(new ShapeSchemaProvider());
	}

	/**
	 * @see SchemaService#cleanSourceSchema()
	 */
	public boolean cleanSourceSchema() {
		sourceSchema = Schema.EMPTY_SCHEMA;
		notifySchemaChanged(SchemaType.SOURCE);
		
		return true;
	}

	/**
	 * @see SchemaService#cleanTargetSchema()
	 */
	public boolean cleanTargetSchema() {
		targetSchema = Schema.EMPTY_SCHEMA;
		notifySchemaChanged(SchemaType.TARGET);
		
		return true;
	}

	/**
	 * @see SchemaService#getSourceSchemaElements()
	 */
	public Collection<SchemaElement> getSourceSchemaElements() {
		return sourceSchema.getElements().values();
	}

	/**
	 * @see SchemaService#getTargetSchemaElements()
	 */
	public Collection<SchemaElement> getTargetSchemaElements() {
		return targetSchema.getElements().values();
	}

	/**
	 * @see SchemaService#loadSchema(URI, String, SchemaType, ProgressIndicator)
	 */
	@Override
	public boolean loadSchema(URI location, String schemaFormat, SchemaType type, ProgressIndicator progress) throws IOException {
		ATransaction logTrans = log.begin("Loading " + type + " schema from " + location.toString());
		try {
			if (schemaFormat == null) {
				schemaFormat = determineSchemaFormat(location);
			}
			SchemaProvider provider = getSchemaProvider(schemaFormat);
			Schema schema = provider.loadSchema(location, progress);
			
			if (type.equals(SchemaType.SOURCE)) {
				sourceSchema = schema;
				sourceSchemaFormat = schemaFormat;
			} 
			else {
				targetSchema = schema;
				targetSchemaFormat = schemaFormat;
			}
			
			notifySchemaChanged(type);
			return true;
		} finally {
			logTrans.end();
		}
	}

	/**
	 * @see SchemaService#getSourceSchemaFormat()
	 */
	@Override
	public String getSourceSchemaFormat() {
		return sourceSchemaFormat;
	}

	/**
	 * @see SchemaService#getTargetSchemaFormat()
	 */
	@Override
	public String getTargetSchemaFormat() {
		return targetSchemaFormat;
	}

	private SchemaProvider getSchemaProvider(String format) {
		for (SchemaProvider provider : providers) {
			if (provider.supportsSchemaFormat(format)) {
				return provider;
			}
		}
		
		throw new IllegalArgumentException("No schema provider for the given format: " + format);
	}

	private String determineSchemaFormat(URI location) {
		String loc = location.toString();
		
		// special cases
		
		// WFS describe feature type
		if (loc.toLowerCase().contains("request=describefeaturetype")) {
			return "xsd";
		}
		
		int index = loc.lastIndexOf('.');
		if (index < 0) {
			throw new IllegalArgumentException("Unable to automatically determine schema format");
		}
		else {
			return loc.substring(index + 1);
		}
	}

	/**
	 * @see SchemaService#getTargetSchema()
	 */
	@Override
	public Schema getTargetSchema() {
		return targetSchema;
	}

	/**
	 * @see SchemaService#getSourceSchema()
	 */
	@Override
	public Schema getSourceSchema() {
		return sourceSchema;
	}

	/**
	 * @see SchemaService#getSourceNameSpace()
	 */
	public String getSourceNameSpace() {
		return sourceSchema.getNamespace();
	}

	/**
	 * @see SchemaService#getSourceURL()
	 */
	public URL getSourceURL() {
		return sourceSchema.getLocation();
	}

	/**
	 * @see SchemaService#getTargetNameSpace()
	 */
	public String getTargetNameSpace() {
		return targetSchema.getNamespace();
	}

	/**
	 * @see SchemaService#getTargetURL()
	 */
	public URL getTargetURL() {
		return targetSchema.getLocation();
	}

	/**
	 * @see SchemaService#getElementByName(String)
	 */
	public SchemaElement getElementByName(String name) {
		SchemaElement result = null;
		// handles cases where a full name was given.
		if (!getSourceNameSpace().equals("") && name.contains(getSourceNameSpace())) {
			for (SchemaElement element : getSourceSchemaElements()) {
				if (element.getElementName().getLocalPart().equals(name)) {
					result = element;
					break;
				}
			}
		}
		else if (!getTargetNameSpace().equals("") && name.contains(getTargetNameSpace())) {
			for (SchemaElement element : getTargetSchemaElements()) {
				if (element.getElementName().getLocalPart().equals(name)) {
					result = element;
					break;
				}
			}
		}
		// handle case where only the local part was given.
		else {
			Collection<SchemaElement> allElements = new HashSet<SchemaElement>();
			allElements.addAll(getSourceSchemaElements());
			allElements.addAll(getTargetSchemaElements());
			for (SchemaElement element : allElements) {
				if (element.getElementName().getLocalPart().equals(name)) {
					result = element;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * @see SchemaService#getSchema(SchemaService.SchemaType)
	 */
	public Collection<SchemaElement> getSchema(SchemaType schemaType) {
		if (SchemaType.SOURCE.equals(schemaType)) {
			return getSourceSchemaElements();
		}
		else {
			return getTargetSchemaElements();
		}
	}

	/**
	 * @see SchemaService#getDefinition(String)
	 */
	@Override
	public Definition getDefinition(String identifier) {
		//XXX improve implementation?
		Definition result = getDefinition(identifier, sourceSchema);
		if (result == null) {
			result = getDefinition(identifier, targetSchema);
		}
		
		return result;
	}

	/**
	 * @see SchemaService#getDefinition(String, SchemaType)
	 */
	@Override
	public Definition getDefinition(String identifier, SchemaType schema) {
		return getDefinition(identifier, (schema == SchemaType.SOURCE)?(sourceSchema):(targetSchema));
	}

	private Definition getDefinition(String identifier, Schema schema) {
		//XXX improve implementation?
		Definition result = schema.getElements().get(identifier);
		
		if (result == null) {
			// not found as type, may be attribute
			int index = identifier.lastIndexOf('/');
			if (index > 0) {
				String subIdentifier = identifier.substring(0, index);
				String attributeName = identifier.substring(index + 1);
				
				SchemaElement type = schema.getElements().get(subIdentifier);
				
				if (type != null) {
					// try to find attribute
					for (AttributeDefinition attribute : type.getType().getAttributes()) {
						if (attribute.getName().equals(attributeName)) {
							return attribute;
						}
					}
				}
			}
		}
		
		return result;
	}

	/**
	 * @see SchemaService#getSupportedSchemaFormats()
	 */
	@Override
	public Set<String> getSupportedSchemaFormats() {
		Set<String> result = new HashSet<String>();
		
		for (SchemaProvider provider : providers) {
			result.addAll(provider.getSupportedSchemaFormats());
		}
		
		return result;
	}
	
}
