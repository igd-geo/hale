//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.30 at 02:45:13 PM CET 
//

package eu.esdihumboldt.hale.common.core.io.project.model.internal.generated;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for ProjectType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="author" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{}preserveWhitespaceString" minOccurs="0"/>
 *         &lt;element name="created" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="modified" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="save-config" type="{}IOConfigurationType" minOccurs="0"/>
 *         &lt;element name="resource" type="{}IOConfigurationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="export-config" type="{}IOConfigurationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="file" type="{}ProjectFileType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}AbstractProperty" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectType", propOrder = { "name", "author", "description", "created",
		"modified", "saveConfig", "resource", "exportConfigs", "file", "abstractProperty" })
public class ProjectType {

	@XmlElement(required = true)
	protected String name;
	protected String author;
	protected String description;
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar created;
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar modified;
	@XmlElement(name = "save-config")
	protected IOConfigurationType saveConfig;
	@XmlElement(name = "export-config")
	protected List<IOConfigurationType> exportConfigs;
	protected List<IOConfigurationType> resource;
	protected List<ProjectFileType> file;
	@XmlElementRef(name = "AbstractProperty", type = JAXBElement.class)
	protected List<JAXBElement<?>> abstractProperty;
	@XmlAttribute
	protected String version;

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the author property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the value of the author property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setAuthor(String value) {
		this.author = value;
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * Gets the value of the created property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getCreated() {
		return created;
	}

	/**
	 * Sets the value of the created property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setCreated(XMLGregorianCalendar value) {
		this.created = value;
	}

	/**
	 * Gets the value of the modified property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getModified() {
		return modified;
	}

	/**
	 * Sets the value of the modified property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setModified(XMLGregorianCalendar value) {
		this.modified = value;
	}

	/**
	 * Gets the value of the saveConfig property.
	 * 
	 * @return possible object is {@link IOConfigurationType }
	 * 
	 */
	public IOConfigurationType getSaveConfig() {
		return saveConfig;
	}

	/**
	 * Sets the value of the saveConfig property.
	 * 
	 * @param value allowed object is {@link IOConfigurationType }
	 * 
	 */
	public void setSaveConfig(IOConfigurationType value) {
		this.saveConfig = value;
	}

	/**
	 * Gets the value of the resource property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the resource property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getResource().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link IOConfigurationType }
	 * 
	 * 
	 */
	public List<IOConfigurationType> getResource() {
		if (resource == null) {
			resource = new ArrayList<IOConfigurationType>();
		}
		return this.resource;
	}

	/**
	 * Gets the value of the export configuration property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the resource property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getExportConfigs().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link IOConfigurationType }
	 * 
	 * @return the export configurations
	 */
	public List<IOConfigurationType> getExportConfigs() {
		if (exportConfigs == null) {
			exportConfigs = new ArrayList<IOConfigurationType>();
		}
		return this.exportConfigs;
	}

	/**
	 * Gets the value of the file property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the file property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getFile().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link ProjectFileType }
	 * 
	 * 
	 */
	public List<ProjectFileType> getFile() {
		if (file == null) {
			file = new ArrayList<ProjectFileType>();
		}
		return this.file;
	}

	/**
	 * Gets the value of the abstractProperty property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the abstractProperty property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAbstractProperty().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link JAXBElement }{@code <}{@link Object }{@code >} {@link JAXBElement }
	 * {@code <}{@link ComplexPropertyType }{@code >} {@link JAXBElement }{@code <}
	 * {@link PropertyType }{@code >}
	 * 
	 * 
	 */
	public List<JAXBElement<?>> getAbstractProperty() {
		if (abstractProperty == null) {
			abstractProperty = new ArrayList<JAXBElement<?>>();
		}
		return this.abstractProperty;
	}

	/**
	 * Gets the value of the version property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the value of the version property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setVersion(String value) {
		this.version = value;
	}

}
