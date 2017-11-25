package de.gishmo.gwt.mvp4g2.processor.handler.application;

import com.squareup.javapoet.*;
import de.gishmo.gwt.mvp4g2.client.application.AbstractApplication;
import de.gishmo.gwt.mvp4g2.client.application.IsApplicationLoader;
import de.gishmo.gwt.mvp4g2.client.application.annotation.Application;
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

public class ApplicationAnnotationHandler {

  private final static String IMPL_NAME = "Impl";

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;
  private RoundEnvironment      roundEnvironment;

  @SuppressWarnings("unused")
  private ApplicationAnnotationHandler(Builder builder) {
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
    if (roundEnvironment.getElementsAnnotatedWith(Application.class)
                        .size() == 0) {
      return;
    }
    // valildate @Application annotation
    this.validate();
    // generate code
    for (Element element : this.roundEnvironment.getElementsAnnotatedWith(Application.class)) {
      this.generate(element);
    }
  }

  private void validate()
    throws ProcessorException {
    // get elements annotated with Applicaiton annotation
    Set<? extends Element> elementsWithApplicaitonAnnotation = this.roundEnvironment.getElementsAnnotatedWith(Application.class);
    // at least there should exatly one Application annotation!
    if (elementsWithApplicaitonAnnotation.size() == 0) {
      throw new ProcessorException("Missing Mvp4g Application interface");
    }
    // at least there should only one Application annotation!
    if (elementsWithApplicaitonAnnotation.size() > 1) {
      throw new ProcessorException("There should be at least only one interface, that is annotated with @Application");
    }
    // annotated element has to be a interface
    for (Element element : elementsWithApplicaitonAnnotation) {
      if (element instanceof TypeElement) {
        TypeElement typeElement = (TypeElement) element;
        if (!typeElement.getKind()
                        .isInterface()) {
          throw new ProcessorException("@Application can only be used with an interface");
        }
      }
    }
  }

  private void generate(Element element)
    throws IOException {
    Application applicationAnnotation = element.getAnnotation(Application.class);

    TypeElement eventBusTypeElement = this.getEventBusTypeElement(applicationAnnotation);
    TypeElement apllicaitonLoaderTypeElement = this.getApplicationLoaderTypeElement(applicationAnnotation);

    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(element.getSimpleName() + ApplicationAnnotationHandler.IMPL_NAME)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractApplication.class),
                                                                              ClassName.get(ProcessorUtils.getPackageAsString(eventBusTypeElement),
                                                                                            eventBusTypeElement.getSimpleName()
                                                                                                               .toString())))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(ClassName.get(ProcessorUtils.getPackageAsString(element),
                                                                         element.getSimpleName()
                                                                                .toString()));

    // constructor ...
    MethodSpec constructor = MethodSpec.constructorBuilder()
                                       .addModifiers(Modifier.PUBLIC)
                                       .addStatement("super()")
                                       .addStatement("super.eventBus = new $N.$N()",
                                                     ProcessorUtils.getPackageAsString(eventBusTypeElement),
                                                     eventBusTypeElement.getSimpleName()
                                                                        .toString() + ApplicationAnnotationHandler.IMPL_NAME)
                                       .build();
    typeSpec.addMethod(constructor);

    // method "getApplicaitonLoader"
    MethodSpec getApplicaitonLaoderMethod = MethodSpec.methodBuilder("getApplicationLoader")
                                                      .addModifiers(Modifier.PUBLIC)
                                                      .addAnnotation(Override.class)
                                                      .returns(IsApplicationLoader.class)
                                                      .addStatement("return new $T()",
                                                                    ClassName.get(ProcessorUtils.getPackageAsString(apllicaitonLoaderTypeElement),
                                                                                  apllicaitonLoaderTypeElement.getSimpleName()
                                                                                                              .toString()))
                                                      .build();
    typeSpec.addMethod(getApplicaitonLaoderMethod);


    JavaFile javaFile = JavaFile.builder(ProcessorUtils.getPackageAsString(element),
                                         typeSpec.build())
                                .build();
    javaFile.writeTo(this.processingEnvironment.getFiler());
  }

  private TypeElement getEventBusTypeElement(Application applicationAnnotation) {
    try {
      applicationAnnotation.eventBus();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeElement getApplicationLoaderTypeElement(Application applicationAnnotation) {
    try {
      applicationAnnotation.loader();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
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

    public ApplicationAnnotationHandler build() {
      return new ApplicationAnnotationHandler(this);
    }

  }
}
