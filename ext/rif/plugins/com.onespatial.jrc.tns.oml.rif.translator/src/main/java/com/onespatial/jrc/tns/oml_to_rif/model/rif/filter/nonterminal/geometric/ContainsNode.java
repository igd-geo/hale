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
 *     1Spatial PLC <http://www.1spatial.com>
 *     HUMBOLDT EU Integrated Project #030962
 *     Data Harmonisation Panel <http://www.dhpanel.eu>
 */
package com.onespatial.jrc.tns.oml_to_rif.model.rif.filter.nonterminal.geometric;

import com.onespatial.jrc.tns.oml_to_rif.model.rif.filter.nonterminal.NodeType;

/**
 * A node within a predicate tree that expresses the geometric "contains"
 * predicate.
 * 
 * @author Simon Payne (Simon.Payne@1spatial.com) / 1Spatial Group Ltd.
 * @author Richard Sunderland (Richard.Sunderland@1spatial.com) / 1Spatial Group Ltd.
 */
public class ContainsNode extends AbstractGeometricNode
{
    /**
     * IRI for the predicate to be deployed in the RIF output.
     */
    public static final String CONTAINS_PREDICATE_IRI = "http://" //$NON-NLS-1$
            + "www.opengeospatial.org/standards/sfa/Geometry#contains"; //$NON-NLS-1$

    /**
     * Set as protected in case this class is extended.
     */
    protected NodeType nodeType = NodeType.CONTAINS_NODE;

    @Override
    public NodeType getNodeType()
    {
        return nodeType;
    }
}
