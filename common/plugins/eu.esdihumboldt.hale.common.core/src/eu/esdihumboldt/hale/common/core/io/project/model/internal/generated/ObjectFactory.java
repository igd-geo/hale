//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.01 at 01:21:23 PM MEZ 
//


package eu.esdihumboldt.hale.common.core.io.project.model.internal.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.esdihumboldt.hale.common.core.io.project.model.internal.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Setting_QNAME = new QName("", "setting");
    private final static QName _ComplexSetting_QNAME = new QName("", "complex-setting");
    private final static QName _AbstractProperty_QNAME = new QName("", "AbstractProperty");
    private final static QName _Property_QNAME = new QName("", "property");
    private final static QName _AbstractSetting_QNAME = new QName("", "AbstractSetting");
    private final static QName _HaleProject_QNAME = new QName("", "hale-project");
    private final static QName _ComplexProperty_QNAME = new QName("", "complex-property");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.esdihumboldt.hale.common.core.io.project.model.internal.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProjectType }
     * 
     */
    public ProjectType createProjectType() {
        return new ProjectType();
    }

    /**
     * Create an instance of {@link PropertyType }
     * 
     */
    public PropertyType createPropertyType() {
        return new PropertyType();
    }

    /**
     * Create an instance of {@link ComplexPropertyType }
     * 
     */
    public ComplexPropertyType createComplexPropertyType() {
        return new ComplexPropertyType();
    }

    /**
     * Create an instance of {@link ProjectFileType }
     * 
     */
    public ProjectFileType createProjectFileType() {
        return new ProjectFileType();
    }

    /**
     * Create an instance of {@link IOConfigurationType }
     * 
     */
    public IOConfigurationType createIOConfigurationType() {
        return new IOConfigurationType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "setting", substitutionHeadNamespace = "", substitutionHeadName = "AbstractSetting")
    public JAXBElement<PropertyType> createSetting(PropertyType value) {
        return new JAXBElement<PropertyType>(_Setting_QNAME, PropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComplexPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "complex-setting", substitutionHeadNamespace = "", substitutionHeadName = "AbstractSetting")
    public JAXBElement<ComplexPropertyType> createComplexSetting(ComplexPropertyType value) {
        return new JAXBElement<ComplexPropertyType>(_ComplexSetting_QNAME, ComplexPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AbstractProperty")
    public JAXBElement<Object> createAbstractProperty(Object value) {
        return new JAXBElement<Object>(_AbstractProperty_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "property", substitutionHeadNamespace = "", substitutionHeadName = "AbstractProperty")
    public JAXBElement<PropertyType> createProperty(PropertyType value) {
        return new JAXBElement<PropertyType>(_Property_QNAME, PropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AbstractSetting")
    public JAXBElement<Object> createAbstractSetting(Object value) {
        return new JAXBElement<Object>(_AbstractSetting_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProjectType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "hale-project")
    public JAXBElement<ProjectType> createHaleProject(ProjectType value) {
        return new JAXBElement<ProjectType>(_HaleProject_QNAME, ProjectType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComplexPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "complex-property", substitutionHeadNamespace = "", substitutionHeadName = "AbstractProperty")
    public JAXBElement<ComplexPropertyType> createComplexProperty(ComplexPropertyType value) {
        return new JAXBElement<ComplexPropertyType>(_ComplexProperty_QNAME, ComplexPropertyType.class, null, value);
    }

}
