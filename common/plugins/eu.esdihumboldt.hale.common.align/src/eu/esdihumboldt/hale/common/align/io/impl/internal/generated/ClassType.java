//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.26 at 03:06:50 PM CET 
//


package eu.esdihumboldt.hale.common.align.io.impl.internal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClassType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClassType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.esdi-humboldt.eu/hale/alignment}AbstractEntityType">
 *       &lt;sequence>
 *         &lt;element name="type">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.esdi-humboldt.eu/hale/alignment}QNameType">
 *                 &lt;sequence>
 *                   &lt;element name="condition" type="{http://www.esdi-humboldt.eu/hale/alignment}ConditionType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClassType", propOrder = {
    "type"
})
@XmlSeeAlso({
    PropertyType.class
})
public class ClassType
    extends AbstractEntityType
{

    @XmlElement(required = true)
    protected ClassType.Type type;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ClassType.Type }
     *     
     */
    public ClassType.Type getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassType.Type }
     *     
     */
    public void setType(ClassType.Type value) {
        this.type = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://www.esdi-humboldt.eu/hale/alignment}QNameType">
     *       &lt;sequence>
     *         &lt;element name="condition" type="{http://www.esdi-humboldt.eu/hale/alignment}ConditionType" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "condition"
    })
    public static class Type
        extends QNameType
    {

        protected ConditionType condition;

        /**
         * Gets the value of the condition property.
         * 
         * @return
         *     possible object is
         *     {@link ConditionType }
         *     
         */
        public ConditionType getCondition() {
            return condition;
        }

        /**
         * Sets the value of the condition property.
         * 
         * @param value
         *     allowed object is
         *     {@link ConditionType }
         *     
         */
        public void setCondition(ConditionType value) {
            this.condition = value;
        }

    }

}
