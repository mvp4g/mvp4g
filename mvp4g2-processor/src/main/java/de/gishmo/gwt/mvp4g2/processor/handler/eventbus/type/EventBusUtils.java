package de.gishmo.gwt.mvp4g2.processor.handler.eventbus.type;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.mvp4g2.client.eventbus.annotation.Event;
import de.gishmo.gwt.mvp4g2.client.ui.AbstractPresenter;
import de.gishmo.gwt.mvp4g2.processor.ProcessorConstants;
import de.gishmo.gwt.mvp4g2.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EventBusUtils {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;
  private TypeElement           eventBusTypeElement;
  private TypeSpec.Builder      typeSpec;

  private EventBusUtils() {
  }

  private EventBusUtils(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
//      this.eventBusTypeElement = builder.eventBusTypeElement;
//      this.typeSpec = builder.typeSpec;

    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public String createEventHandlerMetaDataClassName(String className) {
    StringBuilder sb = new StringBuilder();
    sb.append(className.substring(0,
                                  className.lastIndexOf(".")))
      .append(".")
      .append(this.processorUtils.setFirstCharacterToUpperCase(className.replace(".",
                                                                                 "_")))
      .append(ProcessorConstants.META_DATA);
    return sb.toString();
  }

  public String createEventMetaDataClassName(TypeElement element,
                                             ExecutableElement executableElement) {
    return this.processorUtils.setFirstCharacterToUpperCase(this.createEventMetaDataVariableName(element,
                                                                                                 executableElement));
  }

  public String createEventMetaDataVariableName(TypeElement element,
                                                ExecutableElement executableElement) {
    return this.processorUtils.createFullClassName(element.asType()
                                                          .toString() + "_" + executableElement.getSimpleName()
                                                                                               .toString());
  }

  public void generatePresenterBinding(MethodSpec.Builder methodToGenerate,
                                       String eventHandlerClassName,
                                       String metaDataVariableName) {
    methodToGenerate.addStatement("super.putPresenterHandlerMetaData($S, $N)",
                                  eventHandlerClassName,
                                  metaDataVariableName);
    // set eventbus statement
    methodToGenerate.addStatement("$N.getPresenter().setEventBus(this)",
                                  metaDataVariableName);
    // set view statement in presenter
    methodToGenerate.addStatement("$N.getPresenter().setView($N.getView())",
                                  metaDataVariableName,
                                  metaDataVariableName);
    // set presenter statement in view
    methodToGenerate.addStatement("$N.getView().setPresenter($N.getPresenter())",
                                  metaDataVariableName,
                                  metaDataVariableName);
  }

  public List<String> getHandlerElementsAsList(ExecutableElement executableElement,
                                               String attribute) {
    Element eventAnnotation = this.processingEnvironment.getElementUtils()
                                                        .getTypeElement(Event.class.getName());
    TypeMirror eventAnnotationAsTypeMirror = eventAnnotation.asType();
    return executableElement.getAnnotationMirrors()
                            .stream()
                            .filter(annotationMirror -> annotationMirror.getAnnotationType()
                                                                        .equals(eventAnnotationAsTypeMirror))
                            .flatMap(annotationMirror -> annotationMirror.getElementValues()
                                                                         .entrySet()
                                                                         .stream())
                            .filter(entry -> attribute.equals(entry.getKey()
                                                                   .getSimpleName()
                                                                   .toString()))
                            .findFirst().<List<String>>map(entry -> Arrays.stream(entry.getValue()
                                                                                       .toString()
                                                                                       .replace("{",
                                                                                                "")
                                                                                       .replace("}",
                                                                                                "")
                                                                                       .replace(" ",
                                                                                                "")
                                                                                       .split(","))
                                                                          .map((v) -> v.substring(0,
                                                                                                  v.indexOf(".class")))
                                                                          .collect(Collectors.toList())).orElse(null);
  }

  public boolean isPresenter(String eventHandlerClassName) {
    return this.processorUtils.isSuperClass(this.processingEnvironment.getTypeUtils(),
                                            this.processingEnvironment.getElementUtils()
                                                                      .getTypeElement(eventHandlerClassName),
                                            AbstractPresenter.class);
  }


  public static final class Builder {

    ProcessingEnvironment processingEnvironment;
//      TypeElement           eventBusTypeElement;
//      TypeSpec.Builder      typeSpec;
//

    /**
     * Set the processing envirement
     *
     * @param processingEnvirement the processing envirement
     * @return the Builder
     */
    public Builder processingEnvironment(ProcessingEnvironment processingEnvirement) {
      this.processingEnvironment = processingEnvirement;
      return this;
    }
//
//      /**
//       * Set the eventbus type element
//       *
//       * @param eventBusTypeElement the eventbvus type element
//       * @return the Builder
//       */
//      public de.gishmo.gwt.mvp4g2.processor.handler.eventbus.type.EventHandlingMethodGenerator.Builder eventBusTypeElement(TypeElement eventBusTypeElement) {
//        this.eventBusTypeElement = eventBusTypeElement;
//        return this;
//      }
//
//      /**
//       * Set the typeSpec of the currently generated eventBus
//       *
//       * @param typeSpec ttype spec of the crruent eventbus
//       * @return the Builder
//       */
//      public de.gishmo.gwt.mvp4g2.processor.handler.eventbus.type.EventHandlingMethodGenerator.Builder typeSpec(TypeSpec.Builder typeSpec) {
//        this.typeSpec = typeSpec;
//        return this;
//      }

    public EventBusUtils build() {
      return new EventBusUtils(this);
    }
  }
}
