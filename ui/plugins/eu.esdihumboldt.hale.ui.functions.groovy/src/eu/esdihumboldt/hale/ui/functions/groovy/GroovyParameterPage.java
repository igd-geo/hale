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
package eu.esdihumboldt.hale.ui.functions.groovy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.codehaus.groovy.syntax.SyntaxException;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;

import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;
import eu.esdihumboldt.cst.functions.groovy.GroovyConstants;
import eu.esdihumboldt.cst.functions.groovy.GroovyTransformation;
import eu.esdihumboldt.hale.common.align.model.CellUtil;
import eu.esdihumboldt.hale.common.align.model.EntityDefinition;
import eu.esdihumboldt.hale.common.align.model.Property;
import eu.esdihumboldt.hale.common.align.model.impl.PropertyEntityDefinition;
import eu.esdihumboldt.hale.common.align.transformation.function.PropertyValue;
import eu.esdihumboldt.hale.common.align.transformation.function.impl.PropertyValueImpl;
import eu.esdihumboldt.hale.common.core.io.Value;
import eu.esdihumboldt.hale.common.instance.groovy.InstanceBuilder;
import eu.esdihumboldt.hale.common.schema.SchemaSpaceID;
import eu.esdihumboldt.hale.common.schema.model.PropertyDefinition;
import eu.esdihumboldt.hale.common.schema.model.TypeDefinition;
import eu.esdihumboldt.hale.common.schema.model.constraint.type.Binding;
import eu.esdihumboldt.hale.common.schema.model.constraint.type.HasValueFlag;
import eu.esdihumboldt.hale.common.schema.model.impl.DefaultPropertyDefinition;
import eu.esdihumboldt.hale.common.schema.model.impl.DefaultTypeDefinition;
import eu.esdihumboldt.hale.ui.functions.core.SourceListParameterPage;
import eu.esdihumboldt.hale.ui.functions.core.SourceViewerParameterPage;
import eu.esdihumboldt.hale.ui.functions.groovy.TypeStructureTray.TypeProvider;
import eu.esdihumboldt.hale.ui.scripting.groovy.InstanceTestValues;
import eu.esdihumboldt.hale.ui.scripting.groovy.TestValues;
import eu.esdihumboldt.hale.ui.util.IColorManager;
import eu.esdihumboldt.hale.ui.util.groovy.GroovyColorManager;
import eu.esdihumboldt.hale.ui.util.groovy.GroovySourceViewerUtil;
import eu.esdihumboldt.hale.ui.util.groovy.SimpleGroovySourceViewerConfiguration;
import eu.esdihumboldt.hale.ui.util.source.SimpleAnnotationUtil;
import eu.esdihumboldt.hale.ui.util.source.SimpleAnnotations;
import eu.esdihumboldt.hale.ui.util.source.ValidatingSourceViewer;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

/**
 * Parameter page for Groovy property function.
 * 
 * @author Kai Schwierczek
 * @author Simon Templer
 */
public class GroovyParameterPage extends SourceViewerParameterPage implements GroovyConstants {

	private static final ALogger log = ALoggerFactory.getLogger(GroovyParameterPage.class);

	private Iterable<EntityDefinition> variables;
	private final TestValues testValues;
	private final IColorManager colorManager = new GroovyColorManager();
	private final IAnnotationModel annotationModel = new AnnotationModel();

	/**
	 * Default constructor.
	 */
	public GroovyParameterPage() {
		super("script");

		setTitle("Function parameters");
		setDescription("Specify a Groovy script to determine the target property value");

		testValues = new InstanceTestValues();
	}

	/**
	 * @see SourceViewerParameterPage#onShowPage(boolean)
	 */
	@Override
	protected void onShowPage(boolean firstShow) {
		super.onShowPage(firstShow);

		// variables may have changed
		forceValidation();
	}

	/**
	 * @see SourceViewerParameterPage#getParameterName()
	 */
	@Override
	protected String getParameterName() {
		return PARAMETER_SCRIPT;
	}

	/**
	 * @see SourceViewerParameterPage#getSourcePropertyName()
	 */
	@Override
	protected String getSourcePropertyName() {
		return ENTITY_VARIABLE;
	}

	/**
	 * @see SourceViewerParameterPage#validate(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected boolean validate(String document) {
		List<PropertyValue> values = new ArrayList<PropertyValue>();
		if (variables != null) {
			for (EntityDefinition var : variables) {
				if (var instanceof PropertyEntityDefinition) {
					PropertyEntityDefinition property = (PropertyEntityDefinition) var;
					values.add(new PropertyValueImpl(testValues.get(property), property));
				}
			}
		}

		// clear annotations
		List<Annotation> annotations = new ArrayList<>();
		Iterators.addAll(annotations, annotationModel.getAnnotationIterator());
		for (Annotation annotation : annotations) {
			annotationModel.removeAnnotation(annotation);
		}

		Property targetProperty = (Property) CellUtil.getFirstEntity(getWizard()
				.getUnfinishedCell().getTarget());
		InstanceBuilder builder = GroovyTransformation
				.createBuilder(targetProperty.getDefinition());

		// TODO specify classloader?
		boolean useInstanceValues = CellUtil.getOptionalParameter(getWizard().getUnfinishedCell(),
				GroovyTransformation.PARAM_INSTANCE_VARIABLES, Value.of(false)).as(Boolean.class);
		GroovyShell shell = new GroovyShell(GroovyTransformation.createGroovyBinding(values, null,
				builder, useInstanceValues));
		Script script = null;
		try {
			script = shell.parse(document);

			GroovyTransformation.evaluate(script, builder, targetProperty.getDefinition()
					.getDefinition().getPropertyType());
		} catch (final Exception e) {
			getShell().getDisplay().syncExec(new Runnable() {

				@Override
				public void run() {
					setMessage(e.getMessage(), ERROR);
				}
			});
			addErrorAnnotation(script, e);
			// return valid if NPE, as this might be caused by null test values
			return e instanceof NullPointerException;
//			return false;
		}

		getShell().getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				setMessage(null);
			}
		});
		return true;
	}

	@Override
	protected void addActions(ToolBar toolbar, ValidatingSourceViewer viewer) {
		super.addActions(toolbar, viewer);

		TypeStructureTray.createToolItem(toolbar, this, SchemaSpaceID.SOURCE, new TypeProvider() {

			@Override
			public Collection<? extends TypeDefinition> getTypes() {
				// create a dummy type with the variables as children
				DefaultTypeDefinition dummy = new DefaultTypeDefinition(
						TypeStructureTray.VARIABLES_TYPE_NAME);

				boolean useInstanceValues = CellUtil.getOptionalParameter(
						getWizard().getUnfinishedCell(),
						GroovyTransformation.PARAM_INSTANCE_VARIABLES, Value.of(false)).as(
						Boolean.class);

				if (variables != null) {
					for (EntityDefinition variable : variables) {
						if (variable.getDefinition() instanceof PropertyDefinition) {
							PropertyDefinition prop = (PropertyDefinition) variable.getDefinition();

							TypeDefinition propertyType;
							if (useInstanceValues) {
								// use instance type
								propertyType = prop.getPropertyType();
							}
							else {
								// use dummy type with only the
								// binding/HasValueFlag copied
								DefaultTypeDefinition crippledType = new DefaultTypeDefinition(prop
										.getPropertyType().getName());
								crippledType.setConstraint(prop.getPropertyType().getConstraint(
										Binding.class));
								crippledType.setConstraint(prop.getPropertyType().getConstraint(
										HasValueFlag.class));
								propertyType = crippledType;
							}

							new DefaultPropertyDefinition(new QName(getVariableName(variable)),
									dummy, propertyType);
						}
					}
				}

				return Collections.singleton(dummy);
			}
		});

		TypeStructureTray.createToolItem(toolbar, this, SchemaSpaceID.TARGET, new TypeProvider() {

			@Override
			public Collection<? extends TypeDefinition> getTypes() {
				Property targetProperty = (Property) CellUtil.getFirstEntity(getWizard()
						.getUnfinishedCell().getTarget());
				if (targetProperty != null) {
					return Collections.singleton(targetProperty.getDefinition().getDefinition()
							.getPropertyType());
				}
				return Collections.emptyList();
			}
		});
	}

	private void addErrorAnnotation(Script script, Exception e) {
		addGroovyErrorAnnotation(annotationModel, getDocument(), script, e);
	}

	/**
	 * Add an error annotation to the given annotation model based on an
	 * exception that occurred while compiling or executing the Groovy Script.
	 * 
	 * @param annotationModel the annotation model
	 * @param document the current document
	 * @param script the executed script, or <code>null</code>
	 * @param exception the occurred exception
	 */
	public static void addGroovyErrorAnnotation(IAnnotationModel annotationModel,
			IDocument document, Script script, Exception exception) {
		// handle multiple groovy compilation errors
		if (exception instanceof MultipleCompilationErrorsException) {
			ErrorCollector errors = ((MultipleCompilationErrorsException) exception)
					.getErrorCollector();
			for (int i = 0; i < errors.getErrorCount(); i++) {
				SyntaxException ex = errors.getSyntaxError(i);
				if (ex != null) {
					addGroovyErrorAnnotation(annotationModel, document, script, ex);
				}
			}
			return;
		}

		Annotation annotation = new Annotation(SimpleAnnotations.TYPE_ERROR, false,
				exception.getLocalizedMessage());
		Position position = null;

		// single syntax exception
		if (exception instanceof SyntaxException) {
			int line = ((SyntaxException) exception).getStartLine() - 1;
			if (line >= 0) {
				try {
					position = new Position(document.getLineOffset(line));
				} catch (BadLocationException e1) {
					log.warn("Wrong error position in document", e1);
				}
			}
		}

		// try to determine position from stack trace of script execution
		if (position == null && script != null) {
			for (StackTraceElement ste : exception.getStackTrace()) {
				if (ste.getClassName().startsWith(script.getClass().getName())) {
					int line = ste.getLineNumber() - 1;
					if (line >= 0) {
						try {
							position = new Position(document.getLineOffset(line));
							break;
						} catch (BadLocationException e1) {
							log.warn("Wrong error position in document", e1);
						}
					}
				}
			}
		}

		// fallback
		if (position == null) {
			position = new Position(0);
		}

		annotationModel.addAnnotation(annotation, position);
	}

	/**
	 * @see SourceListParameterPage#sourcePropertiesChanged(Iterable)
	 */
	@Override
	protected void sourcePropertiesChanged(Iterable<EntityDefinition> variables) {
		this.variables = variables;
	}

	/**
	 * @see SourceViewerParameterPage#getVariableName(EntityDefinition)
	 */
	@Override
	protected String getVariableName(EntityDefinition variable) {
		// dots are not allowed in variable names, an underscore is used instead
		return super.getVariableName(variable).replace('.', '_');
	}

	@Override
	protected SourceViewerConfiguration createConfiguration() {
		return new SimpleGroovySourceViewerConfiguration(colorManager, ImmutableList.of(
				BINDING_BUILDER, BINDING_TARGET));
	}

	/**
	 * @see eu.esdihumboldt.hale.ui.functions.core.SourceViewerParameterPage#createOverviewRuler()
	 */
	@Override
	protected IOverviewRuler createOverviewRuler() {
		IOverviewRuler ruler = SimpleAnnotationUtil.createDefaultOverviewRuler(14, colorManager,
				annotationModel);
		return ruler;
	}

	/**
	 * @see eu.esdihumboldt.hale.ui.functions.core.SourceViewerParameterPage#createVerticalRuler()
	 */
	@Override
	protected IVerticalRuler createVerticalRuler() {
		final Display display = Display.getCurrent();
		CompositeRuler ruler = new CompositeRuler(3);

		AnnotationRulerColumn annotations = SimpleAnnotationUtil
				.createDefaultAnnotationRuler(annotationModel);

		ruler.addDecorator(0, annotations);

		LineNumberRulerColumn lineNumbers = new LineNumberRulerColumn();
		lineNumbers.setBackground(display.getSystemColor(SWT.COLOR_GRAY)); // SWT.COLOR_INFO_BACKGROUND));
		lineNumbers.setForeground(display.getSystemColor(SWT.COLOR_BLACK)); // SWT.COLOR_INFO_FOREGROUND));
		lineNumbers.setFont(JFaceResources.getTextFont());

		ruler.addDecorator(1, lineNumbers);
		return ruler;
	}

	@Override
	protected void createAndSetDocument(SourceViewer viewer) {
		IDocument doc = new Document();
		GroovySourceViewerUtil.setupDocument(doc);
		annotationModel.connect(doc);
		doc.set(""); //$NON-NLS-1$

		viewer.setDocument(doc, annotationModel);
	}

	@Override
	public void dispose() {
		colorManager.dispose();

		super.dispose();
	}

}
