package de.gishmo.gwt.mvp4g2.processor;


import com.google.auto.service.AutoService;
import de.gishmo.gwt.mvp4g2.client.application.annotation.Application;
import de.gishmo.gwt.mvp4g2.client.eventbus.annotation.*;
import de.gishmo.gwt.mvp4g2.client.history.annotation.History;
import de.gishmo.gwt.mvp4g2.client.ui.annotation.EventHandler;
import de.gishmo.gwt.mvp4g2.processor.handler.application.ApplicationAnnotationHandler;
import de.gishmo.gwt.mvp4g2.processor.handler.eventHandler.EventHandlerAnnotationHandler;
import de.gishmo.gwt.mvp4g2.processor.handler.eventHandler.PresenterAnnotationHandler;
import de.gishmo.gwt.mvp4g2.processor.handler.eventbus.EventBusAnnotationHandler;
import de.gishmo.gwt.mvp4g2.processor.handler.history.HistoryAnnotationHandler;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.LinkedHashSet;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class Processor
  extends AbstractProcessor {

  private ProcessorUtils processorUtils;

  public Processor() {
    super();
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> annotations = new LinkedHashSet<String>();
    annotations.add(Application.class.getCanonicalName());
    annotations.add(Debug.class.getCanonicalName());
    annotations.add(Event.class.getCanonicalName());
    annotations.add(EventBus.class.getCanonicalName());
    annotations.add(EventHandler.class.getCanonicalName());
    annotations.add(EventHandler.class.getCanonicalName());
    annotations.add(Filters.class.getCanonicalName());
    annotations.add(History.class.getCanonicalName());
    annotations.add(Start.class.getCanonicalName());
    return annotations;
  }
  @Override
  public boolean process(Set<? extends TypeElement> annotations,
                         RoundEnvironment roundEnv) {
    if (!roundEnv.processingOver()) {
      // setup processor ...
      setUp();
      try {
        // handling the Application annotation
        ApplicationAnnotationHandler.builder()
                                    .processingEnvironment(super.processingEnv)
                                    .roundEnvironment(roundEnv)
                                    .build()
                                    .process();
        // handling the History annotation
        HistoryAnnotationHandler.builder()
                                .processingEnvironment(super.processingEnv)
                                .roundEnvironment(roundEnv)
                                .build()
                                .process();
        // handling the eventBus annotation
        EventBusAnnotationHandler.builder()
                                 .processingEnvironment(super.processingEnv)
                                 .roundEnvironment(roundEnv)
                                 .build()
                                 .process();
        // handling the eventBus annotation
        EventHandlerAnnotationHandler.builder()
                                     .processingEnvironment(super.processingEnv)
                                     .roundEnvironment(roundEnv)
                                     .build()
                                     .process();
        // handling the Presenter annotation
        PresenterAnnotationHandler.builder()
                                  .processingEnvironment(super.processingEnv)
                                  .roundEnvironment(roundEnv)
                                  .build()
                                  .process();


      } catch (Exception e) {
        this.processorUtils.createErrorMessage(e.getMessage());
        throw new RuntimeException(e);
      }
      return true;
    }
    return false;
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(super.processingEnv)
                                        .build();
  }
}
