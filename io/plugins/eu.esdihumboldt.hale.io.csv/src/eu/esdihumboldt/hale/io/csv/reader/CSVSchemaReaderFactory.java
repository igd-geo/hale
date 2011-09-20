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

package eu.esdihumboldt.hale.io.csv.reader;

import eu.esdihumboldt.hale.common.core.io.HaleIO;
import eu.esdihumboldt.hale.common.core.io.IOProviderFactory;
import eu.esdihumboldt.hale.common.core.io.impl.AbstractIOProviderFactory;
import eu.esdihumboldt.hale.io.csv.CSVFileIO;
import eu.esdihumboldt.hale.io.csv.reader.internal.CSVSchemaReader;
import eu.esdihumboldt.hale.common.schema.io.*;

/**
 * Factory for CSVfile {@link SchemaReader}s
 * 
 * @author Kevin Mais
 */
public class CSVSchemaReaderFactory extends
		AbstractIOProviderFactory<SchemaReader> implements SchemaReaderFactory {

	private static final String PROVIDER_ID = "eu.esdihumboldt.hale.io.csv.reader.schema";

	/**
	 * Default constructor
	 */
	public CSVSchemaReaderFactory() {
		super(PROVIDER_ID);

		addSupportedContentType(CSVFileIO.CSVFILE_CT_ID);
	}

	/**
	 * @see IOProviderFactory#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return HaleIO.getDisplayName(CSVFileIO.CSVFILE_CT);
	}

	/**
	 * @see IOProviderFactory#createProvider()
	 */
	@Override
	public SchemaReader createProvider() {
		return new CSVSchemaReader();
	}

}
