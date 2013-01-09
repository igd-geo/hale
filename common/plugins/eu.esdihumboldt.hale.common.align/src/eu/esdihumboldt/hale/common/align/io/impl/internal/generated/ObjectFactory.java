//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.30 at 08:26:17 AM CET 
//

package eu.esdihumboldt.hale.common.align.io.impl.internal.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the
 * eu.esdihumboldt.hale.common.align.io.impl.internal.generated package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _Cell_QNAME = new QName(
			"http://www.esdi-humboldt.eu/hale/alignment", "cell");
	private final static QName _AbstractEntity_QNAME = new QName(
			"http://www.esdi-humboldt.eu/hale/alignment", "AbstractEntity");
	private final static QName _ComplexParameter_QNAME = new QName(
			"http://www.esdi-humboldt.eu/hale/alignment", "complexParameter");
	private final static QName _Parameter_QNAME = new QName(
			"http://www.esdi-humboldt.eu/hale/alignment", "parameter");
	private final static QName _Alignment_QNAME = new QName(
			"http://www.esdi-humboldt.eu/hale/alignment", "alignment");
	private final static QName _AbstractParameter_QNAME = new QName(
			"http://www.esdi-humboldt.eu/hale/alignment", "AbstractParameter");
	private final static QName _Modifier_QNAME = new QName(
			"http://www.esdi-humboldt.eu/hale/alignment", "modifier");
	private final static QName _Property_QNAME = new QName(
			"http://www.esdi-humboldt.eu/hale/alignment", "property");
	private final static QName _Class_QNAME = new QName(
			"http://www.esdi-humboldt.eu/hale/alignment", "class");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package:
	 * eu.esdihumboldt.hale.common.align.io.impl.internal.generated
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link ClassType.Type }
	 * 
	 */
	public ClassType.Type createClassTypeType() {
		return new ClassType.Type();
	}

	/**
	 * Create an instance of {@link QNameType }
	 * 
	 */
	public QNameType createQNameType() {
		return new QNameType();
	}

	/**
	 * Create an instance of {@link DocumentationType }
	 * 
	 */
	public DocumentationType createDocumentationType() {
		return new DocumentationType();
	}

	/**
	 * Create an instance of {@link ComplexParameterType }
	 * 
	 */
	public ComplexParameterType createComplexParameterType() {
		return new ComplexParameterType();
	}

	/**
	 * Create an instance of {@link PropertyType }
	 * 
	 */
	public PropertyType createPropertyType() {
		return new PropertyType();
	}

	/**
	 * Create an instance of {@link AlignmentType }
	 * 
	 */
	public AlignmentType createAlignmentType() {
		return new AlignmentType();
	}

	/**
	 * Create an instance of {@link ModifierType }
	 * 
	 */
	public ModifierType createModifierType() {
		return new ModifierType();
	}

	/**
	 * Create an instance of {@link CellType }
	 * 
	 */
	public CellType createCellType() {
		return new CellType();
	}

	/**
	 * Create an instance of {@link AlignmentType.Base }
	 * 
	 */
	public AlignmentType.Base createAlignmentTypeBase() {
		return new AlignmentType.Base();
	}

	/**
	 * Create an instance of {@link ClassType }
	 * 
	 */
	public ClassType createClassType() {
		return new ClassType();
	}

	/**
	 * Create an instance of {@link AbstractParameterType }
	 * 
	 */
	public AbstractParameterType createAbstractParameterType() {
		return new AbstractParameterType();
	}

	/**
	 * Create an instance of {@link NamedEntityType }
	 * 
	 */
	public NamedEntityType createNamedEntityType() {
		return new NamedEntityType();
	}

	/**
	 * Create an instance of {@link ModifierType.DisableFor }
	 * 
	 */
	public ModifierType.DisableFor createModifierTypeDisableFor() {
		return new ModifierType.DisableFor();
	}

	/**
	 * Create an instance of {@link ChildContextType }
	 * 
	 */
	public ChildContextType createChildContextType() {
		return new ChildContextType();
	}

	/**
	 * Create an instance of {@link ConditionType }
	 * 
	 */
	public ConditionType createConditionType() {
		return new ConditionType();
	}

	/**
	 * Create an instance of {@link ParameterType }
	 * 
	 */
	public ParameterType createParameterType() {
		return new ParameterType();
	}

	/**
	 * Create an instance of {@link AnnotationType }
	 * 
	 */
	public AnnotationType createAnnotationType() {
		return new AnnotationType();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CellType }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.esdi-humboldt.eu/hale/alignment", name = "cell")
	public JAXBElement<CellType> createCell(CellType value) {
		return new JAXBElement<CellType>(_Cell_QNAME, CellType.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link AbstractEntityType }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.esdi-humboldt.eu/hale/alignment", name = "AbstractEntity")
	public JAXBElement<AbstractEntityType> createAbstractEntity(AbstractEntityType value) {
		return new JAXBElement<AbstractEntityType>(_AbstractEntity_QNAME, AbstractEntityType.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link ComplexParameterType }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.esdi-humboldt.eu/hale/alignment", name = "complexParameter", substitutionHeadNamespace = "http://www.esdi-humboldt.eu/hale/alignment", substitutionHeadName = "AbstractParameter")
	public JAXBElement<ComplexParameterType> createComplexParameter(ComplexParameterType value) {
		return new JAXBElement<ComplexParameterType>(_ComplexParameter_QNAME,
				ComplexParameterType.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ParameterType }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.esdi-humboldt.eu/hale/alignment", name = "parameter", substitutionHeadNamespace = "http://www.esdi-humboldt.eu/hale/alignment", substitutionHeadName = "AbstractParameter")
	public JAXBElement<ParameterType> createParameter(ParameterType value) {
		return new JAXBElement<ParameterType>(_Parameter_QNAME, ParameterType.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link AlignmentType }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.esdi-humboldt.eu/hale/alignment", name = "alignment")
	public JAXBElement<AlignmentType> createAlignment(AlignmentType value) {
		return new JAXBElement<AlignmentType>(_Alignment_QNAME, AlignmentType.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link AbstractParameterType }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.esdi-humboldt.eu/hale/alignment", name = "AbstractParameter")
	public JAXBElement<AbstractParameterType> createAbstractParameter(AbstractParameterType value) {
		return new JAXBElement<AbstractParameterType>(_AbstractParameter_QNAME,
				AbstractParameterType.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ModifierType }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.esdi-humboldt.eu/hale/alignment", name = "modifier")
	public JAXBElement<ModifierType> createModifier(ModifierType value) {
		return new JAXBElement<ModifierType>(_Modifier_QNAME, ModifierType.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link PropertyType }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.esdi-humboldt.eu/hale/alignment", name = "property", substitutionHeadNamespace = "http://www.esdi-humboldt.eu/hale/alignment", substitutionHeadName = "AbstractEntity")
	public JAXBElement<PropertyType> createProperty(PropertyType value) {
		return new JAXBElement<PropertyType>(_Property_QNAME, PropertyType.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ClassType }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.esdi-humboldt.eu/hale/alignment", name = "class", substitutionHeadNamespace = "http://www.esdi-humboldt.eu/hale/alignment", substitutionHeadName = "AbstractEntity")
	public JAXBElement<ClassType> createClass(ClassType value) {
		return new JAXBElement<ClassType>(_Class_QNAME, ClassType.class, null, value);
	}

}
