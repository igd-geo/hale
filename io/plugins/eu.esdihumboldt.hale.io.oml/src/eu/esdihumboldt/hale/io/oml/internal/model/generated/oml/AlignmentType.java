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
 * <p>Java class for AlignmentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlignmentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="level" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="onto1">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://knowledgeweb.semanticweb.org/heterogeneity/alignment}Ontology"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="onto2">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://knowledgeweb.semanticweb.org/heterogeneity/alignment}Ontology"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="map" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://knowledgeweb.semanticweb.org/heterogeneity/alignment}Cell"/>
 *                 &lt;/sequence>
 *                 &lt;attribute ref="{http://www.w3.org/1999/02/22-rdf-syntax-ns#}about"/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.esdi-humboldt.eu/goml}ValueClass" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "AlignmentType", namespace = "http://knowledgeweb.semanticweb.org/heterogeneity/alignment", propOrder = {
    "level",
    "onto1",
    "onto2",
    "map",
    "valueClass"
})
public class AlignmentType {

    @XmlElement(required = true)
    protected String level;
    @XmlElement(required = true)
    protected AlignmentType.Onto1 onto1;
    @XmlElement(required = true)
    protected AlignmentType.Onto2 onto2;
    protected List<AlignmentType.Map> map;
    @XmlElement(name = "ValueClass", namespace = "http://www.esdi-humboldt.eu/goml")
    protected List<ValueClassType> valueClass;
    @XmlAttribute(namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
    @XmlSchemaType(name = "anyURI")
    protected String about;

    /**
     * Gets the value of the level property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevel(String value) {
        this.level = value;
    }

    /**
     * Gets the value of the onto1 property.
     * 
     * @return
     *     possible object is
     *     {@link AlignmentType.Onto1 }
     *     
     */
    public AlignmentType.Onto1 getOnto1() {
        return onto1;
    }

    /**
     * Sets the value of the onto1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlignmentType.Onto1 }
     *     
     */
    public void setOnto1(AlignmentType.Onto1 value) {
        this.onto1 = value;
    }

    /**
     * Gets the value of the onto2 property.
     * 
     * @return
     *     possible object is
     *     {@link AlignmentType.Onto2 }
     *     
     */
    public AlignmentType.Onto2 getOnto2() {
        return onto2;
    }

    /**
     * Sets the value of the onto2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlignmentType.Onto2 }
     *     
     */
    public void setOnto2(AlignmentType.Onto2 value) {
        this.onto2 = value;
    }

    /**
     * Gets the value of the map property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the map property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMap().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AlignmentType.Map }
     * 
     * 
     */
    public List<AlignmentType.Map> getMap() {
        if (map == null) {
            map = new ArrayList<AlignmentType.Map>();
        }
        return this.map;
    }

    /**
     * Gets the value of the valueClass property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueClass property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueClass().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValueClassType }
     * 
     * 
     */
    public List<ValueClassType> getValueClass() {
        if (valueClass == null) {
            valueClass = new ArrayList<ValueClassType>();
        }
        return this.valueClass;
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


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://knowledgeweb.semanticweb.org/heterogeneity/alignment}Cell"/>
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
    @XmlType(name = "", propOrder = {
        "cell"
    })
    public static class Map {

        @XmlElement(name = "Cell", namespace = "http://knowledgeweb.semanticweb.org/heterogeneity/alignment", required = true)
        protected CellType cell;
        @XmlAttribute(namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
        @XmlSchemaType(name = "anyURI")
        protected String about;

        /**
         * Gets the value of the cell property.
         * 
         * @return
         *     possible object is
         *     {@link CellType }
         *     
         */
        public CellType getCell() {
            return cell;
        }

        /**
         * Sets the value of the cell property.
         * 
         * @param value
         *     allowed object is
         *     {@link CellType }
         *     
         */
        public void setCell(CellType value) {
            this.cell = value;
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


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://knowledgeweb.semanticweb.org/heterogeneity/alignment}Ontology"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ontology"
    })
    public static class Onto1 {

        @XmlElement(name = "Ontology", namespace = "http://knowledgeweb.semanticweb.org/heterogeneity/alignment", required = true)
        protected OntologyType ontology;

        /**
         * Gets the value of the ontology property.
         * 
         * @return
         *     possible object is
         *     {@link OntologyType }
         *     
         */
        public OntologyType getOntology() {
            return ontology;
        }

        /**
         * Sets the value of the ontology property.
         * 
         * @param value
         *     allowed object is
         *     {@link OntologyType }
         *     
         */
        public void setOntology(OntologyType value) {
            this.ontology = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://knowledgeweb.semanticweb.org/heterogeneity/alignment}Ontology"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ontology"
    })
    public static class Onto2 {

        @XmlElement(name = "Ontology", namespace = "http://knowledgeweb.semanticweb.org/heterogeneity/alignment", required = true)
        protected OntologyType ontology;

        /**
         * Gets the value of the ontology property.
         * 
         * @return
         *     possible object is
         *     {@link OntologyType }
         *     
         */
        public OntologyType getOntology() {
            return ontology;
        }

        /**
         * Sets the value of the ontology property.
         * 
         * @param value
         *     allowed object is
         *     {@link OntologyType }
         *     
         */
        public void setOntology(OntologyType value) {
            this.ontology = value;
        }

    }

}
