package de.gishmo.gwt.mvp4g2.processor.handler.eventHandler;

import com.squareup.javapoet.*;
import de.gishmo.gwt.mvp4g2.client.ui.annotation.Presenter;
import de.gishmo.gwt.mvp4g2.client.ui.internal.EventHandlerMetaData;
import de.gishmo.gwt.mvp4g2.client.ui.internal.PresenterHandlerMetaData;
import de.gishmo.gwt.mvp4g2.processor.ProcessorException;
import de.gishmo.gwt.mvp4g2.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import java.io.IOException;
import java.util.Set;

// TODO check, that @Eventhandler is annoted at a class that extends AbstractPresent!
public class PresenterAnnotationHandler {

  private final static String IMPL_NAME = "MetaData";

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;
  private RoundEnvironment      roundEnvironment;

  @SuppressWarnings("unused")
  private PresenterAnnotationHandler(Builder builder) {
    super();

    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;

    setUp();
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public void process()
    throws ProcessorException, IOException {
    // check, whether we have o do something ...
    if (roundEnvironment.getElementsAnnotatedWith(Presenter.class)
                        .size() == 0) {
      return;
    }
    // valildate @Application annotation
    this.validate();
    // generate code
    for (Element element : this.roundEnvironment.getElementsAnnotatedWith(Presenter.class)) {
      this.generate(element);
    }
  }

  private void validate()
    throws ProcessorException {
    // get elements annotated with Presenter annotation
    Set<? extends Element> elementsWithPresenterAnnotation = this.roundEnvironment.getElementsAnnotatedWith(Presenter.class);
    for (Element element : elementsWithPresenterAnnotation) {
      if (element instanceof TypeElement) {
        TypeElement typeElement = (TypeElement) element;
        // check, that the presenter annotion is only used with classes
        if (!typeElement.getKind()
                        .isClass()) {
          throw new ProcessorException(typeElement.getSimpleName().toString() + ": @Presenter can only be used with as class!");
        }
        // check, that the view attribute is a class
        TypeElement viewClassElement = this.getViewClassTypeElement(element.getAnnotation(Presenter.class));
        TypeElement viewInterfaceElement = this.getViewInterfaceTypeElement(element.getAnnotation(Presenter.class));
        // check, that the viewClass is a class
        if (!viewClassElement.getKind()
                             .isClass()) {
          throw new ProcessorException(typeElement.getSimpleName().toString() + ": the viewClass-attribute of a @Presenter must be a class!");
        }
        // chekc if the vioewInterface is a interface
        if (!viewInterfaceElement.getKind()
                                 .isInterface()) {
          throw new ProcessorException(typeElement.getSimpleName().toString() + ": the viewInterface-attribute of a @Presenter must be a interface!");
        }
        // check, if viewClass is implementing viewInterface
        if (!ProcessorUtils.implementsInterface(this.processingEnvironment,
                                                viewClassElement,
                                                viewInterfaceElement.asType())) {
          throw new ProcessorException(typeElement.getSimpleName().toString() + ": the viewClass-attribute of a @Presenter must implement the viewInterface!");
        }
      }
    }
  }

  private void generate(Element element)
    throws IOException {
    Presenter presenter = element.getAnnotation(Presenter.class);
    TypeElement typeElement = (TypeElement) element;

    TypeElement viewClassElement = this.getViewClassTypeElement(presenter);
    TypeElement viewInterfaceElement = this.getViewInterfaceTypeElement(presenter);

    String className = this.processorUtils.createFullClassName(ProcessorUtils.getPackageAsString(element),
                                                               element.getSimpleName()
                                                                      .toString()) + PresenterAnnotationHandler.IMPL_NAME;
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(this.processorUtils.setFirstCharacterToUpperCase(className))
                                        .superclass(ParameterizedTypeName.get(ClassName.get(PresenterHandlerMetaData.class),
                                                                              ClassName.get(ProcessorUtils.getPackageAsString(typeElement),
                                                                                            typeElement.getSimpleName()
                                                                                                       .toString()),
                                                                              ClassName.get(ProcessorUtils.getPackageAsString(viewInterfaceElement),
                                                                                            viewInterfaceElement.getSimpleName()
                                                                                                                .toString())))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL);

    // constructor ...
    MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                                               .addModifiers(Modifier.PUBLIC)
                                               .addStatement("super($S, $T.PRESENTER, $N, $T.$L)",
                                                             ProcessorUtils.getCanonicalClassName(typeElement),
                                                             ClassName.get(EventHandlerMetaData.Kind.class),
                                                             presenter.multiple() ? "true" : "false",
                                                             ClassName.get(Presenter.VIEW_CREATION_METHOD.class),
                                                             getCreator(typeElement));
    constructor.addStatement("super.presenter = new $T()",
                             ClassName.get(ProcessorUtils.getPackageAsString(typeElement),
                                           typeElement.getSimpleName()
                                                      .toString()));
    if (Presenter.VIEW_CREATION_METHOD.FRAMEWORK.equals(getCreator(typeElement))) {
      constructor.addStatement("super.view = ($T) new $T()",
                               ClassName.get(ProcessorUtils.getPackageAsString(viewClassElement),
                                             viewInterfaceElement.getSimpleName()
                                                                 .toString()),
                               ClassName.get(ProcessorUtils.getPackageAsString(viewClassElement),
                                             viewClassElement.getSimpleName()
                                                             .toString()));
    } else {
      constructor.addStatement("super.view = presenter.createView()");
    }
    typeSpec.addMethod(constructor.build());

    JavaFile javaFile = JavaFile.builder(ProcessorUtils.getPackageAsString(element),
                                         typeSpec.build())
                                .build();
    javaFile.writeTo(this.processingEnvironment.getFiler());
  }

  private TypeElement getViewClassTypeElement(Presenter presenterAnnotation) {
    try {
      presenterAnnotation.viewClass();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeElement getViewInterfaceTypeElement(Presenter presenterAnnotation) {
    try {
      presenterAnnotation.viewInterface();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private Presenter.VIEW_CREATION_METHOD getCreator(TypeElement element) {
    return element.getAnnotation(Presenter.class)
                  .viewCreator();
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;
    RoundEnvironment      roundEnvironment;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public PresenterAnnotationHandler build() {
      return new PresenterAnnotationHandler(this);
    }

  }
}
