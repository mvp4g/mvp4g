/*
 * Copyright 2015-2017 Frank Hossfeld
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.gishmo.gwt.mvp4g2.processor.handler.eventbus.type;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.mvp4g2.client.eventbus.annotation.Debug;
import de.gishmo.gwt.mvp4g2.client.eventbus.annotation.Filters;
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

// TODO check, that @Filter is annoted at a interface that extends IsEventBus! and other tests ...
public class FilterAnnotationGenerator {

  private ProcessorUtils processorUtils;
  private EventBusUtils  eventBusUtils;

  private RoundEnvironment      roundEnvironment;
  private ProcessingEnvironment processingEnvironment;
  private TypeSpec.Builder      typeSpec;
  private TypeElement           eventBusTypeElement;

  @SuppressWarnings("unused")
  private FilterAnnotationGenerator() {
  }

  private FilterAnnotationGenerator(Builder builder) {
    this.roundEnvironment = builder.roundEnvironment;
    this.processingEnvironment = builder.processingEnvironment;
    this.typeSpec = builder.typeSpec;
    this.eventBusTypeElement = builder.eventBusTypeElement;

    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
    this.eventBusUtils = EventBusUtils.builder()
                                      .processingEnvironment(this.processingEnvironment)
                                      .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
    throws ProcessorException, IOException {
    this.validate();
    this.generateLoadFilterConfigurationMethod();
  }

  private void validate()
    throws ProcessorException {
    // get elements annotated with EventBus annotation
    Set<? extends Element> elementsWithDebugAnnotation = this.roundEnvironment.getElementsAnnotatedWith(Filters.class);
//    // at least there should only one Application annotation!
//    if (elementsWithDebugAnnotation.size() > 1) {
//      throw new ProcessorException("There should be at least only one interface, that is annotated with @Debug");
//    }
//    // annotated element has to be a interface
//    for (Element element : elementsWithDebugAnnotation) {
//      if (element instanceof TypeElement) {
//        TypeElement typeElement = (TypeElement) element;
//        if (!typeElement.getKind()
//                        .isInterface()) {
//          throw new ProcessorException("@Debug can only be used with an interface");
//        }
//      }
//    }
  }

  private void generateLoadFilterConfigurationMethod() {
    // method msut always be created!
    MethodSpec.Builder loadDebugConfigurationMethod = MethodSpec.methodBuilder("loadFilterConfiguration")
                                                                .addAnnotation(Override.class)
                                                                .addModifiers(Modifier.PUBLIC);

    // get elements annotated with EventBus annotation
    Set<? extends Element> elementsWithFiltersAnnotation = this.roundEnvironment.getElementsAnnotatedWith(Filters.class);
    if (elementsWithFiltersAnnotation.size() == 0) {
      loadDebugConfigurationMethod.addStatement("super.setFiltersEnable(false)");
    } else {
//      elementsWithFiltersAnnotation.forEach(element -> loadDebugConfigurationMethod.addStatement("super.setDebugEnable(true)")
//                                                                                 .addStatement("super.setLogger(new $T())",
//                                                                                               ClassName.get(getLogger(element.getAnnotation(Debug.class))))
//                                                                                 .addStatement("super.setLogLevel($T.LogLevel.$L)",
//                                                                                               ClassName.get(Debug.class),
//                                                                                               element.getAnnotation(Debug.class)
//                                                                                                      .logLevel()));
    }
    typeSpec.addMethod(loadDebugConfigurationMethod.build());
  }

  private TypeElement getLogger(Debug debugAnnotation) {
    try {
      debugAnnotation.logger();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  public static final class Builder {

    RoundEnvironment      roundEnvironment;
    ProcessingEnvironment processingEnvironment;
    TypeSpec.Builder      typeSpec;
    TypeElement           eventBusTypeElement;

    /**
     * Set the round envirement
     *
     * @param roundEnvironment the round envirement
     * @return the Builder
     */
    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

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

    /**
     * Set the eventbus type element
     *
     * @param eventBusTypeElement the eventbvus type element
     * @return the Builder
     */
    public Builder eventBusTypeElement(TypeElement eventBusTypeElement) {
      this.eventBusTypeElement = eventBusTypeElement;
      return this;
    }

    /**
     * Set the typeSpec of the currently generated eventBus
     *
     * @param typeSpec ttype spec of the crruent eventbus
     * @return the Builder
     */
    public Builder typeSpec(TypeSpec.Builder typeSpec) {
      this.typeSpec = typeSpec;
      return this;
    }

    public FilterAnnotationGenerator build() {
      return new FilterAnnotationGenerator(this);
    }
  }
}
