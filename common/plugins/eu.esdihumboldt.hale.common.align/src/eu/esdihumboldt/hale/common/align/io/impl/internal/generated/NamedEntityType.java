//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.26 at 07:56:35 AM CET 
//


package eu.esdihumboldt.hale.common.align.io.impl.internal.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NamedEntityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NamedEntityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.esdi-humboldt.eu/hale/alignment}AbstractEntity"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NamedEntityType", propOrder = {
    "abstractEntity"
})
public class NamedEntityType {

    @XmlElementRef(name = "AbstractEntity", namespace = "http://www.esdi-humboldt.eu/hale/alignment", type = JAXBElement.class)
    protected JAXBElement<? extends AbstractEntityType> abstractEntity;
    @XmlAttribute
    protected String name;

    /**
     * Gets the value of the abstractEntity property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link AbstractEntityType }{@code >}
     *     {@link JAXBElement }{@code <}{@link ClassType }{@code >}
     *     {@link JAXBElement }{@code <}{@link PropertyType }{@code >}
     *     
     */
    public JAXBElement<? extends AbstractEntityType> getAbstractEntity() {
        return abstractEntity;
    }

    /**
     * Sets the value of the abstractEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link AbstractEntityType }{@code >}
     *     {@link JAXBElement }{@code <}{@link ClassType }{@code >}
     *     {@link JAXBElement }{@code <}{@link PropertyType }{@code >}
     *     
     */
    public void setAbstractEntity(JAXBElement<? extends AbstractEntityType> value) {
        this.abstractEntity = ((JAXBElement<? extends AbstractEntityType> ) value);
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
