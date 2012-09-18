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


package eu.esdihumboldt.hale.io.oml.internal.model.generated.oml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CellType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CellType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.omwg.org/TR/d7/ontology/alignment}label" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.omwg.org/TR/d7/ontology/alignment}entity1"/>
 *         &lt;element ref="{http://www.omwg.org/TR/d7/ontology/alignment}entity2"/>
 *         &lt;element ref="{http://knowledgeweb.semanticweb.org/heterogeneity/alignment}measure" minOccurs="0"/>
 *         &lt;element name="relation" type="{http://knowledgeweb.semanticweb.org/heterogeneity/alignment}relationEnumType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://www.w3.org/1999/02/22-rdf-syntax-ns#}about"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CellType", namespace = "http://knowledgeweb.semanticweb.org/heterogeneity/alignment", propOrder = {
    "label",
    "entity1",
    "entity2",
    "measure",
    "relation"
})
public class CellType {

    @XmlElement(namespace = "http://www.omwg.org/TR/d7/ontology/alignment")
    protected List<String> label;
    @XmlElement(namespace = "http://www.omwg.org/TR/d7/ontology/alignment", required = true)
    protected Entity1 entity1;
    @XmlElement(namespace = "http://www.omwg.org/TR/d7/ontology/alignment", required = true)
    protected Entity2 entity2;
    protected Float measure;
    protected RelationEnumType relation;
    @XmlAttribute(namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
    @XmlSchemaType(name = "anyURI")
    protected String about;

    /**
     * Gets the value of the label property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the label property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLabel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLabel() {
        if (label == null) {
            label = new ArrayList<String>();
        }
        return this.label;
    }

    /**
     * Gets the value of the entity1 property.
     * 
     * @return
     *     possible object is
     *     {@link Entity1 }
     *     
     */
    public Entity1 getEntity1() {
        return entity1;
    }

    /**
     * Sets the value of the entity1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Entity1 }
     *     
     */
    public void setEntity1(Entity1 value) {
        this.entity1 = value;
    }

    /**
     * Gets the value of the entity2 property.
     * 
     * @return
     *     possible object is
     *     {@link Entity2 }
     *     
     */
    public Entity2 getEntity2() {
        return entity2;
    }

    /**
     * Sets the value of the entity2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Entity2 }
     *     
     */
    public void setEntity2(Entity2 value) {
        this.entity2 = value;
    }

    /**
     * Gets the value of the measure property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getMeasure() {
        return measure;
    }

    /**
     * Sets the value of the measure property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setMeasure(Float value) {
        this.measure = value;
    }

    /**
     * Gets the value of the relation property.
     * 
     * @return
     *     possible object is
     *     {@link RelationEnumType }
     *     
     */
    public RelationEnumType getRelation() {
        return relation;
    }

    /**
     * Sets the value of the relation property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationEnumType }
     *     
     */
    public void setRelation(RelationEnumType value) {
        this.relation = value;
    }

    /**
     * Gets the value of the about property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbout() {
        return about;
    }

    /**
     * Sets the value of the about property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbout(String value) {
        this.about = value;
    }

}
