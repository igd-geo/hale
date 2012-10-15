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

package eu.esdihumboldt.cst.functions.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ListMultimap;

import eu.esdihumboldt.hale.common.align.model.functions.ClassificationMappingFunction;
import eu.esdihumboldt.hale.common.align.model.impl.PropertyEntityDefinition;
import eu.esdihumboldt.hale.common.align.transformation.engine.TransformationEngine;
import eu.esdihumboldt.hale.common.align.transformation.function.PropertyValue;
import eu.esdihumboldt.hale.common.align.transformation.function.TransformationException;
import eu.esdihumboldt.hale.common.align.transformation.function.impl.AbstractSingleTargetPropertyTransformation;
import eu.esdihumboldt.hale.common.align.transformation.report.TransformationLog;

/**
 * Classification mapping function to map values of an attribute to a different
 * classification system.
 * 
 * @author Kai Schwierczek
 */
public class ClassificationMapping extends
		AbstractSingleTargetPropertyTransformation<TransformationEngine> implements
		ClassificationMappingFunction {

	@Override
	protected Object evaluate(String transformationIdentifier, TransformationEngine engine,
			ListMultimap<String, PropertyValue> variables, String resultName,
			PropertyEntityDefinition resultProperty, Map<String, String> executionParameters,
			TransformationLog log) throws TransformationException {
		checkParameter(PARAMETER_CLASSIFICATIONS, 1);

		List<String> mappings = getTransformedParameters().get(PARAMETER_CLASSIFICATIONS);
		String source = variables.values().iterator().next().getValueAs(String.class);
		try {
			String sourceValue = URLEncoder.encode(source, "UTF-8");
			for (String s : mappings)
				if (s.contains(' ' + sourceValue + ' ') || s.endsWith(' ' + sourceValue))
					return URLDecoder.decode(s.substring(0, s.indexOf(' ')), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// UTF-8 should be everywhere
		}

		String notClassifiedAction = USE_NULL_ACTION;
		if (getParameters().get(PARAMETER_NOT_CLASSIFIED_ACTION) != null
				&& !getParameters().get(PARAMETER_NOT_CLASSIFIED_ACTION).isEmpty()) {
			notClassifiedAction = getTransformedParameters().get(PARAMETER_NOT_CLASSIFIED_ACTION)
					.get(0);
		}

		if (USE_SOURCE_ACTION.equals(notClassifiedAction))
			return source;
		else if (notClassifiedAction.startsWith(USE_FIXED_VALUE_ACTION_PREFIX))
			return notClassifiedAction.substring(notClassifiedAction.indexOf(':') + 1);
		else
			// USE_NULL_ACTION or null or something unknown
			return null;
	}
}
