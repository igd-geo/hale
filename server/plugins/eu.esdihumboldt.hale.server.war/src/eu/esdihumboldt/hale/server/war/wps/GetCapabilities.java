//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.01 at 08:57:42 AM MEZ 
//


package eu.esdihumboldt.hale.server.war.wps;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="AcceptVersions" type="{http://www.opengis.net/ows/1.1}AcceptVersionsType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="service" use="required" type="{http://www.opengis.net/ows/1.1}ServiceType" fixed="WPS" />
 *       &lt;attribute name="language" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "acceptVersions"
})
@XmlRootElement(name = "GetCapabilities", namespace = "http://www.opengis.net/wps/1.0.0")
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
public class GetCapabilities {

    @XmlElement(name = "AcceptVersions", namespace = "http://www.opengis.net/wps/1.0.0")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected AcceptVersionsType acceptVersions;
    @XmlAttribute(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected String service;
    @XmlAttribute
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected String language;

    /**
     * Gets the value of the acceptVersions property.
     * 
     * @return
     *     possible object is
     *     {@link AcceptVersionsType }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public AcceptVersionsType getAcceptVersions() {
        return acceptVersions;
    }

    /**
     * Sets the value of the acceptVersions property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcceptVersionsType }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setAcceptVersions(AcceptVersionsType value) {
        this.acceptVersions = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public String getService() {
        if (service == null) {
            return "WPS";
        } else {
            return service;
        }
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setService(String value) {
        this.service = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2011-12-01T08:57:42+01:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setLanguage(String value) {
        this.language = value;
    }

}
