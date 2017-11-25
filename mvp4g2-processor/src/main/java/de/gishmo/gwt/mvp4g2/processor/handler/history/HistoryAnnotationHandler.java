package de.gishmo.gwt.mvp4g2.processor.handler.history;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.mvp4g2.client.history.annotation.History;
import de.gishmo.gwt.mvp4g2.client.history.internal.HistoryMetaData;
import de.gishmo.gwt.mvp4g2.processor.ProcessorException;
import de.gishmo.gwt.mvp4g2.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.io.IOException;

// TODO validation
public class HistoryAnnotationHandler {
//
//  private final static String IMPL_NAME = "Impl";

  private ProcessorUtils processorUtils;
  private HistoryUtils   historyUtils;

  private ProcessingEnvironment processingEnvironment;
  private RoundEnvironment      roundEnvironment;

  @SuppressWarnings("unused")
  private HistoryAnnotationHandler(Builder builder) {
    super();

    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;

    setUp();
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
    this.historyUtils = HistoryUtils.builder()
                                    .processingEnvironment(this.processingEnvironment)
                                    .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public void process()
    throws ProcessorException, IOException {
    for (Element element : this.roundEnvironment.getElementsAnnotatedWith(History.class)) {
      this.validate();
      this.generate(element);
    }
  }

  private void validate()
    throws ProcessorException {
//    // get elements annotated with Applicaiton annotation
//    Set<? extends Element> elementsWithApplicaitonAnnotation = this.roundEnvironment.getElementsAnnotatedWith(Application.class);
//    // at least there should exatly one Application annotation!
//    if (elementsWithApplicaitonAnnotation.size() == 0) {
//      throw new ProcessorException("Missing Mvp4g Application interface");
//    }
//    // at least there should only one Application annotation!
//    if (elementsWithApplicaitonAnnotation.size() > 1) {
//      throw new ProcessorException("There should be at least only one interface, that is annotated with @Application");
//    }
//    // annotated element has to be a interface
//    for (Element element : elementsWithApplicaitonAnnotation) {
//      if (element instanceof TypeElement) {
//        TypeElement typeElement = (TypeElement) element;
//        if (!typeElement.getKind()
//                        .isInterface()) {
//          throw new ProcessorException("@Application can only be used with an interface");
//        }
//      }
//    }
  }

  private void generate(Element element)
    throws IOException {
    History historyAnnotation = element.getAnnotation(History.class);

    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(this.historyUtils.createHistoryMetaDataClassName((TypeElement) element))
                                        .superclass(ClassName.get(HistoryMetaData.class))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL);
    // constructor ...
    MethodSpec constructor = MethodSpec.constructorBuilder()
                                       .addModifiers(Modifier.PUBLIC)
                                       .addStatement("super($T.$L)",
                                                     ClassName.get(History.HistoryConverterType.class),
                                                     historyAnnotation.type()
                                                                      .toString())
                                       .build();
    typeSpec.addMethod(constructor);

    JavaFile javaFile = JavaFile.builder(ProcessorUtils.getPackageAsString(element),
                                         typeSpec.build())
                                .build();
    javaFile.writeTo(this.processingEnvironment.getFiler());
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

    public HistoryAnnotationHandler build() {
      return new HistoryAnnotationHandler(this);
    }

  }
}
