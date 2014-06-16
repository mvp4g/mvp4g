/*
 * Copyright 2010 Pierre-Laurent Coirier
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
package com.mvp4g.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.ext.*;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JRealClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.mvp4g.client.Mvp4gEventPasser;
import com.mvp4g.client.Mvp4gException;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.Mvp4gRunAsync;
import com.mvp4g.client.annotation.*;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventFilter;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.history.NavigationConfirmationInterface;
import com.mvp4g.client.history.NavigationEventCommand;
import com.mvp4g.client.presenter.PresenterInterface;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.EventBusElement;
import com.mvp4g.util.config.element.Mvp4gElement;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class uses to create the implementation class of Mvp4gStarter
 *
 * @author plcoirier
 */
public class Mvp4gGenerator
  extends IncrementalGenerator {

  /*
   * A version id. Increment this as needed, when structural changes are made to
   * the generated output, specifically with respect to it's effect on the
   * caching and reuse of previous generator results. Previously cached
   * generator results will be invalidated automatically if they were generated
   * by a version of this generator with a different version id.
   */
  private static final long GENERATOR_VERSION_ID = 1L;


  @Override
  public RebindResult generateIncrementally(TreeLogger logger,
                                            GeneratorContext generatorContext,
                                            String moduleClass)
    throws UnableToCompleteException {

    TypeOracle typeOracle = generatorContext.getTypeOracle();
    assert (typeOracle != null);

    JClassType module = typeOracle.findType(moduleClass);
    if (module == null) {
      logger.log(TreeLogger.ERROR,
                 "Unable to find metadata for module class '" + moduleClass + "'");
      throw new UnableToCompleteException();
    }

    if (module.isInterface() == null) {
      logger.log(TreeLogger.ERROR,
                 "'" + moduleClass + "' is not a interface");
      throw new UnableToCompleteException();
    }

    TreeLogger moduleLogger = logger.branch(TreeLogger.DEBUG,
                                            "Generating mvp4g configuration for module class '" + module.getQualifiedSourceName() + "'",
                                            null);

    return create(moduleLogger,
                  generatorContext,
                  moduleClass);
  }


  @Override
  public long getVersionId() {
    return GENERATOR_VERSION_ID;
  }


  private RebindResult create(TreeLogger logger,
                              GeneratorContext context,
                              String moduleName)
    throws UnableToCompleteException {

    Date start = new Date();

    String generatedClassQualifiedName;

    try {
      TypeOracle typeOracle = context.getTypeOracle();

      JClassType module = typeOracle.findType(moduleName);
      if (module == null) {
        logger.log(TreeLogger.ERROR,
                   "Unable to find metadata for type '" + moduleName + "'",
                   null);
        throw new UnableToCompleteException();
      }

      @SuppressWarnings("unchecked")
      Map<Class<? extends Annotation>, List<JClassType>> scanResult = AnnotationScanner.scan(logger,
                                                                                             typeOracle,
                                                                                             new Class[]{Presenter.class,
                                                                                               History.class,
                                                                                               Events.class,
                                                                                               Service.class,
                                                                                               EventHandler.class}
      );

      Mvp4gConfiguration configuration = new Mvp4gConfiguration(logger,
                                                                context);

      String suffix = "Impl" + configuration.load(module,
                                                  scanResult);

      generatedClassQualifiedName = module.getParameterizedQualifiedSourceName() + suffix;

      String packageName = module.getPackage().getName();
      String originalClassName = module.getSimpleSourceName();
      String generatedClassName = originalClassName + suffix;

      // check weather there is a usual version or not.
      if (checkAlreadyGenerated(logger,
                                context,
                                configuration)) {
        // Log
        logger.log(TreeLogger.INFO,
                   "Reuse already generated files",
                   null);
        // stop generating
        return new RebindResult(RebindMode.USE_EXISTING,
                                packageName + "." + generatedClassName);
      }

      // Log
      logger.log(TreeLogger.INFO,
                 "Start generate files ... ",
                 null);

      // No, there is non. Create a new one.
      SourceWriter sourceWriter = getSourceWriter(logger,
                                                  context,
                                                  module,
                                                  packageName,
                                                  generatedClassName);

      if (sourceWriter != null) {
        logger.log(TreeLogger.INFO,
                   "Generating source for " + generatedClassQualifiedName + " ",
                   null);
        Mvp4gConfigurationFileWriter writer = new Mvp4gConfigurationFileWriter(sourceWriter,
                                                                               configuration);
        writer.writeConf();
        sourceWriter.commit(logger);
      } else {
        // don't expect this to occur, but could happen if an instance was
        // recently generated but not yet committed
        new RebindResult(RebindMode.USE_EXISTING,
                         generatedClassQualifiedName);
      }

      Date end = new Date();

      logger.log(TreeLogger.INFO,
                 "Mvp4g Compilation: " + (end.getTime() - start.getTime()) + "ms.");

      return new RebindResult(RebindMode.USE_ALL_NEW_WITH_NO_CACHING,
                              packageName + "." + generatedClassName);
    } catch (InvalidMvp4gConfigurationException e) {
      logger.log(TreeLogger.ERROR,
                 e.getMessage(),
                 e);
      throw new UnableToCompleteException();
    }
  }

  private SourceWriter getSourceWriter(TreeLogger logger,
                                       GeneratorContext context,
                                       JClassType originalType,
                                       String packageName,
                                       String generatedClassName)
    throws UnableToCompleteException {

    logger.log(TreeLogger.INFO,
               "Generating writer for " + packageName + "." + generatedClassName,
               null);

    PrintWriter printWriter = context.tryCreate(logger,
                                                packageName,
                                                generatedClassName);
    if (printWriter == null) {
      return null;
    }

    ClassSourceFileComposerFactory classFactory = new ClassSourceFileComposerFactory(packageName,
                                                                                     generatedClassName);

    classFactory.addImplementedInterface(originalType.getName());
    String[] classesToImport = getClassesToImport();
    for (String classToImport : classesToImport) {
      classFactory.addImport(classToImport);
    }

    return classFactory.createSourceWriter(context,
                                           printWriter);
  }

  String[] getClassesToImport() {
    return new String[]{com.mvp4g.client.history.PlaceService.class.getName(), GWT.class.getName(), com.google.gwt.user.client.History.class.getName(),
      ServiceDefTarget.class.getName(), PresenterInterface.class.getName(), EventBus.class.getName(), Mvp4gException.class.getName(),
      HistoryConverter.class.getName(), Mvp4gEventPasser.class.getName(), Mvp4gModule.class.getName(), GinModules.class.getName(),
      Ginjector.class.getName(), BaseEventBus.class.getName(), EventFilter.class.getName(), EventHandlerInterface.class.getName(),
      List.class.getName(), NavigationEventCommand.class.getName(), NavigationConfirmationInterface.class.getName(),
      RunAsyncCallback.class.getName(), Mvp4gRunAsync.class.getName(), Command.class.getName()};
  }

  private boolean checkAlreadyGenerated(TreeLogger logger,
                                        GeneratorContext ctx,
                                        Mvp4gConfiguration configuration) {

    CachedGeneratorResult lastRebindResult = ctx.getCachedGeneratorResult();

    if (lastRebindResult == null || !ctx.isGeneratorResultCachingEnabled()) {
      return false;
    }

    // Check whether all files are up to date
    // EventBus
    if (!this.checkEventBus(logger,
                            ctx)) {
      return false;
    }
    // Module
    if (!this.checkModule(logger,
                          ctx,
                          configuration.getModule())) {
      return false;
    }
    // ChildModuleElement
    if (!this.checkSet(logger,
                       ctx,
                       configuration.getChildModules())) {
      return false;
    }
    // everything ok!  -> use the old one ...
    return true;
  }

  private boolean checkSet(TreeLogger logger,
                           GeneratorContext ctx,
                           Set<? extends Mvp4gElement> setOfElements) {

    long lastTimeGenerated = ctx.getCachedGeneratorResult().getTimeGenerated();

    for (Mvp4gElement element : setOfElements) {
      JClassType sourceType = ctx.getTypeOracle().findType(element.getProperty("class"));
      if (sourceType == null) {
        logger.log(TreeLogger.TRACE,
                   "Found previously dependent type that's no longer present: " + element.getProperty("class"));
        return false;
      }
      assert sourceType instanceof JRealClassType;
      JRealClassType realClass = (JRealClassType) sourceType;
      if (realClass == null ||
        realClass.getLastModifiedTime() > lastTimeGenerated) {
        return false;
      }
    }
    return true;
  }

  private boolean checkEventBus(TreeLogger logger,
                                GeneratorContext ctx) {

    long lastTimeGenerated = ctx.getCachedGeneratorResult().getTimeGenerated();

    JClassType sourceType = ctx.getTypeOracle().findType(EventBusElement.class.getName());
    if (sourceType == null) {
      logger.log(TreeLogger.TRACE,
                 "Found previously dependent type that's no longer present: " + EventBusElement.class.getName());
      return false;
    }
    assert sourceType instanceof JRealClassType;
    JRealClassType realClass = (JRealClassType) sourceType;
    if (realClass == null ||
      realClass.getLastModifiedTime() > lastTimeGenerated) {
      return false;
    }

    return true;
  }

  private boolean checkModule(TreeLogger logger,
                              GeneratorContext ctx,
                              JClassType module) {

    long lastTimeGenerated = ctx.getCachedGeneratorResult().getTimeGenerated();

    if (module == null) {
      logger.log(TreeLogger.TRACE,
                 "Found previously dependent type that's no longer present: " + Mvp4gModule.class.getName());
      return false;
    }
    assert module instanceof JRealClassType;
    JRealClassType realClass = (JRealClassType) module;
    if (realClass == null ||
      realClass.getLastModifiedTime() > lastTimeGenerated) {
      return false;
    }

    return true;
  }
}
