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

import com.squareup.javapoet.*;
import de.gishmo.gwt.mvp4g2.client.eventbus.PresenterRegistration;
import de.gishmo.gwt.mvp4g2.client.eventbus.annotation.Event;
import de.gishmo.gwt.mvp4g2.client.ui.IsPresenter;
import de.gishmo.gwt.mvp4g2.processor.ProcessorConstants;
import de.gishmo.gwt.mvp4g2.processor.ProcessorException;
import de.gishmo.gwt.mvp4g2.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>The execution context manages all commands.<br>
 * Use run()-method to start execution.</p>
 */
public class EventHandlerRegristrationGenerator {

  private ProcessorUtils processorUtils;
  private EventBusUtils  eventBusUtils;

  private RoundEnvironment      roundEnvironment;
  private ProcessingEnvironment processingEnvironment;
  private TypeElement           eventBusTypeElement;
  private TypeSpec.Builder      typeSpec;

  @SuppressWarnings("unused")
  private EventHandlerRegristrationGenerator() {
  }

  private EventHandlerRegristrationGenerator(Builder builder) {
    this.roundEnvironment = builder.roundEnvironment;
    this.processingEnvironment = builder.processingEnvironment;
    this.eventBusTypeElement = builder.eventBusTypeElement;
    this.typeSpec = builder.typeSpec;

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
    throws ProcessorException {
    for (Element event : this.roundEnvironment.getElementsAnnotatedWith(Event.class)) {
      this.validateEvent(event);
    }
    this.generateLoadEventHandlerMetaDataMethod();
    this.generateAddHandlerMethod();
  }

  // TODO implement event vaidation
  private void validateEvent(Element element)
    throws ProcessorException {
    //    try {
    //      ExecutableElement executableElement = (ExecutableElement) element;
    //    } catch (Exception e) {
    //      throw new ProcessorException("@Event can only be used with a method");
    //    }
  }

  private void generateLoadEventHandlerMetaDataMethod() {
    MethodSpec.Builder loadEventHandlerMethod = MethodSpec.methodBuilder("loadEventHandlerMetaData")
                                                          .addAnnotation(Override.class)
                                                          .addModifiers(Modifier.PROTECTED);
    // List of already created EventHandler used to avoid a second create ...
    List<String> listOfEventHandlersToCreate = this.createListOfEventHandlersToCreate();
    listOfEventHandlersToCreate.forEach(eventHandlerClassName -> this.addHandlerToMetaList(loadEventHandlerMethod,
                                                                                           eventHandlerClassName));
    typeSpec.addMethod(loadEventHandlerMethod.build());
  }

  private void generateAddHandlerMethod() {
    // generate addHandlerMethod
    MethodSpec.Builder addHandlerMethod = MethodSpec.methodBuilder("addHandler")
                                                    .addAnnotation(Override.class)
                                                    .addModifiers(Modifier.PUBLIC)
                                                    .addParameter(ParameterizedTypeName.get(ClassName.get(IsPresenter.class),
                                                                                            WildcardTypeName.subtypeOf(Object.class),
                                                                                            WildcardTypeName.subtypeOf(Object.class)),
                                                                  "presenter")
                                                    .returns(ClassName.get(PresenterRegistration.class));
    // get the list of event handlers
    List<String> listOfEventHandlersToCreate = this.createListOfEventHandlersToCreate();
    listOfEventHandlersToCreate.stream()
                               .filter(this.eventBusUtils::isPresenter)
                               .forEach(eventHandlerClassName -> {
                                 String metaDataClassName = this.eventBusUtils.createEventHandlerMetaDataClassName(eventHandlerClassName);
                                 String metaDataVariableName = this.processorUtils.createFullClassName(eventHandlerClassName + ProcessorConstants.META_DATA);
                                 addHandlerMethod.addStatement("final $N $N = new $N()",
                                                               metaDataClassName,
                                                               metaDataVariableName,
                                                               metaDataClassName);
                                 addHandlerMethod.beginControlFlow("if ($N.isMultiple())",
                                                                   metaDataVariableName);
                                 this.eventBusUtils.generatePresenterBinding(addHandlerMethod,
                                                                             eventHandlerClassName,
                                                                             metaDataVariableName);
                                 addHandlerMethod.addCode("return new $T() {\n",
                                                          ClassName.get(PresenterRegistration.class));
                                 addHandlerMethod.addCode("  @$T\n",
                                                          ClassName.get(Override.class));
                                 addHandlerMethod.addCode("  public void remove() {\n");
                                 addHandlerMethod.addCode("    removePresenterHandlerMetaData($S, $N);\n",
                                                          eventHandlerClassName,
                                                          metaDataVariableName);
                                 addHandlerMethod.addCode("  }\n");
                                 addHandlerMethod.addCode("};\n");
                                 addHandlerMethod.nextControlFlow("else");
                                 addHandlerMethod.addStatement("assert false : $S",
                                                               eventHandlerClassName + ": is not annotated with @Presenter(...,multiple = true)");
                                 addHandlerMethod.endControlFlow();
                               });
    addHandlerMethod.addStatement("return null");
    typeSpec.addMethod(addHandlerMethod.build());
  }

  private void addHandlerToMetaList(MethodSpec.Builder methodToGenerate,
                                    String eventHandlerClassName) {
    // check if we deal with a presenter
    boolean isPresenter = this.eventBusUtils.isPresenter(eventHandlerClassName);
    // Name of the variable , class name
    String metaDataClassName = this.eventBusUtils.createEventHandlerMetaDataClassName(eventHandlerClassName);
    String metaDataVariableName = this.processorUtils.createFullClassName(eventHandlerClassName + ProcessorConstants.META_DATA);
    // comment
    methodToGenerate.addComment("");
    methodToGenerate.addComment("----------------------------------------------------------------------");
    methodToGenerate.addComment("");
    methodToGenerate.addComment("handle $N ($N)",
                                eventHandlerClassName,
                                isPresenter ? "Presenter" : "EventHandler");
    methodToGenerate.addComment("");
    // code ...
    methodToGenerate.addStatement("$N $N = new $N()",
                                  metaDataClassName,
                                  metaDataVariableName,
                                  metaDataClassName);
    if (isPresenter) {
      // check, that multiple is false! (We can do this not here during code generation, because we don't know it ...)
      methodToGenerate.beginControlFlow("if (!$N.isMultiple())",
                                        metaDataVariableName);
      this.eventBusUtils.generatePresenterBinding(methodToGenerate,
                                                  eventHandlerClassName,
                                                  metaDataVariableName);
      methodToGenerate.endControlFlow();
    } else {
      methodToGenerate.addStatement("super.putEventHandlerMetaData($S, $N)",
                                    eventHandlerClassName,
                                    metaDataVariableName);
      // set eventbus statement
      methodToGenerate.addStatement("$N.getEventHandler().setEventBus(this)",
                                    metaDataVariableName);
    }
  }

  private List<String> createListOfEventHandlersToCreate() {
    // get all elements annotated with Event
    List<Element> events = this.processorUtils.getMethodFromTypeElementAnnotatedWith(this.processingEnvironment,
                                                                                     this.eventBusTypeElement,
                                                                                     Event.class);
    // List of already created EventHandler used to avoid a second create ...
    List<String> listOfEventHandlersToCreate = new ArrayList<>();
    events.stream()
          .map(eventElement -> (ExecutableElement) eventElement)
          .forEach(executableElement -> {
            List<String> handlersFromAnnotation = this.eventBusUtils.getHandlerElementsAsList(executableElement,
                                                                                "handlers");
            if (handlersFromAnnotation != null) {
              for (String eventHandlerClassName : handlersFromAnnotation) {
                if (!listOfEventHandlersToCreate.contains(eventHandlerClassName)) {
                  // save the name of the event handler to create
                  listOfEventHandlersToCreate.add(eventHandlerClassName);
                }
              }
            }
            List<String> bindingsFromAnnotation = this.eventBusUtils.getHandlerElementsAsList(executableElement,
                                                                                "bind");
            if (bindingsFromAnnotation != null) {
              for (String eventHandlerClassName : bindingsFromAnnotation) {
                if (!listOfEventHandlersToCreate.contains(eventHandlerClassName)) {
                  // save the name of the event Handler to create
                  listOfEventHandlersToCreate.add(eventHandlerClassName);
                }
              }
            }
          });
    return listOfEventHandlersToCreate;
  }

  public static final class Builder {

    RoundEnvironment      roundEnvironment;
    ProcessingEnvironment processingEnvironment;
    TypeElement           eventBusTypeElement;
    TypeSpec.Builder      typeSpec;

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
     * @param processingEnvironment the processing envirement
     * @return the Builder
     */
    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
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

    public EventHandlerRegristrationGenerator build() {
      return new EventHandlerRegristrationGenerator(this);
    }
  }
}
