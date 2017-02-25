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
package com.mvp4g.rebind;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.SourceWriter;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.Mvp4gRunAsync;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.History.HistoryConverterType;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.history.ClearHistory;
import com.mvp4g.client.history.PlaceService;
import com.mvp4g.rebind.config.Mvp4gConfiguration;
import com.mvp4g.rebind.config.element.ChildModuleElement;
import com.mvp4g.rebind.config.element.ChildModulesElement;
import com.mvp4g.rebind.config.element.DebugElement;
import com.mvp4g.rebind.config.element.EventAssociation;
import com.mvp4g.rebind.config.element.EventBusElement;
import com.mvp4g.rebind.config.element.EventElement;
import com.mvp4g.rebind.config.element.EventFilterElement;
import com.mvp4g.rebind.config.element.EventFiltersElement;
import com.mvp4g.rebind.config.element.EventHandlerElement;
import com.mvp4g.rebind.config.element.HistoryConverterElement;
import com.mvp4g.rebind.config.element.HistoryElement;
import com.mvp4g.rebind.config.element.InjectedElement;
import com.mvp4g.rebind.config.element.LoaderElement;
import com.mvp4g.rebind.config.element.Mvp4gElement;
import com.mvp4g.rebind.config.element.PresenterElement;
import com.mvp4g.rebind.config.element.ServiceElement;
import com.mvp4g.rebind.config.element.SplitterElement;
import com.mvp4g.rebind.config.element.StartElement;
import com.mvp4g.rebind.config.element.ViewElement;
import com.mvp4g.rebind.exception.InvalidMvp4gConfigurationException;

/**
 * @author plcoirier
 */
public class Mvp4gConfigurationFileWriter {

  private SourceWriter sourceWriter = null;

  private Mvp4gConfiguration configuration = null;

  public Mvp4gConfigurationFileWriter(SourceWriter sourceWriter,
                                      Mvp4gConfiguration configuration) {
    this.sourceWriter = sourceWriter;
    this.configuration = configuration;
  }

  public void writeConf() {

    sourceWriter.indent();

    sourceWriter.println();

    writeEventBusClass();

    sourceWriter.println();

    writeGinInjector();

    sourceWriter.println();

    writeSplitterClasses();

    sourceWriter.println("private Object startView = null;");
    sourceWriter.println("private PresenterInterface startPresenter = null;");
    sourceWriter.println("protected AbstractEventBus eventBus = null;");
    sourceWriter.print("protected ");
    sourceWriter.print(getGinjectorClassName());
    sourceWriter.println(" injector = null;");
    DebugElement debug = configuration.getDebug();
    if (debug != null) {
      sourceWriter.print("protected ");
      sourceWriter.print(debug.getLogger());
      sourceWriter.println(" logger;");
    }
    sourceWriter.print("protected ");
    sourceWriter.print(configuration.getModule()
                                    .getQualifiedSourceName());
    sourceWriter.println(" itself = this;");

    writeLoaders(false);

    writeParentEventBus();

    writeChildModules();

    writeHistoryConnection();

    sourceWriter.println();

    writeForwardEvent();

    sourceWriter.println();

    sourceWriter.println("public void createAndStartModule(){");
    sourceWriter.indent();
//    sourceWriter.println(" GWT.log(\"mvp4g version ==> mvp4g-1.5.1-SNAPSHOT\");");
    if (configuration.getHistory() != null) {
      sourceWriter.print("HistoryProxyProvider.INSTANCE.set(new ");
      sourceWriter.print(configuration.getHistory()
                                      .getHistoryProxyClass());
      sourceWriter.println("());");
    } else {
      sourceWriter.println("HistoryProxyProvider.INSTANCE.set(new DefaultHistoryProxy());");
    }

    String injectorClassName = getGinjectorClassName();
    sourceWriter.print("injector = GWT.create( ");
    sourceWriter.print(injectorClassName);
    sourceWriter.println(".class );");

    sourceWriter.println();

    sourceWriter.print("createModule();");
    sourceWriter.print("startModule();");

    sourceWriter.outdent();
    sourceWriter.println("}");

    writeCreateModule();

    writeStartMNodule();

    writeGetters();

  }

  private void writeCreateModule() {
    sourceWriter.println("public void createModule(){");
    sourceWriter.indent();

    writeLoaders(true);

    writeViews();

    sourceWriter.println();

    writeLogger();

    sourceWriter.println();

    writeServices();

    sourceWriter.println();

    writeHistory();

    sourceWriter.println();

    writePresenters();

    sourceWriter.println();

    writeEventHandlers();

    sourceWriter.println();

    writeEventBus();

    sourceWriter.println();

    injectEventBus();

    sourceWriter.println();

    writeEventFilters();

    sourceWriter.println();

    sourceWriter.outdent();

    StartElement start = configuration.getStart();
    if (start.hasPresenter()) {
      String startPresenter = start.getPresenter();
      PresenterElement presenter = getElement(startPresenter,
                                              configuration.getPresenters());
      if (presenter.isMultiple()) {
        sourceWriter.print("this.startPresenter = eventBus.addHandler(");
        sourceWriter.print(presenter.getClassName());
        sourceWriter.println(".class);");
      } else {
        sourceWriter.print("this.startPresenter = ");
        sourceWriter.print(startPresenter);
        sourceWriter.println(";");
      }
      sourceWriter.println("this.startView = startPresenter.getView();");
    }

    sourceWriter.println("}");

  }

  private void writeStartMNodule() {
    sourceWriter.println("public void startModule(){");
    sourceWriter.indent();

    writeStartEvent();

    sourceWriter.outdent();
    sourceWriter.println("}");

  }

  private void writeGetters() {
    sourceWriter.println("public Object getStartView(){");
    sourceWriter.indent();

    if (configuration.getStart()
                     .hasPresenter()) {
      sourceWriter.println("if (startPresenter != null) {");
      sourceWriter.indent();
      sourceWriter.println("startPresenter.setActivated(true);");
      sourceWriter.println("startPresenter.isActivated(false, null);");
      sourceWriter.outdent();
      sourceWriter.print("}");
      sourceWriter.println("return startView;");
    } else {
      sourceWriter.println("throw new Mvp4gException(\"getStartView shouldn't be called since this module has no start view.\");");
    }
    sourceWriter.outdent();
    sourceWriter.println("}");
    sourceWriter.println();
    sourceWriter.println("public EventBus getEventBus(){");
    sourceWriter.indent();
    sourceWriter.println("return eventBus;");
    sourceWriter.outdent();
    sourceWriter.println("}");
  }

  private void writeParentEventBus() {

    if (!configuration.isRootModule()) {
      String parentEventBusClass = configuration.getParentEventBus()
                                                .getQualifiedSourceName();
      sourceWriter.print("private ");
      sourceWriter.print(Mvp4gModule.class.getCanonicalName());
      sourceWriter.println(" parentModule = null;");
      sourceWriter.print("private ");
      sourceWriter.print(parentEventBusClass);
      sourceWriter.println(" parentEventBus = null;");
      sourceWriter.print("public void setParentModule(");
      sourceWriter.print(Mvp4gModule.class.getCanonicalName());
      sourceWriter.println(" module){");
      sourceWriter.indent();
      sourceWriter.println("parentModule = module;");
      sourceWriter.print("parentEventBus = (");
      sourceWriter.print(parentEventBusClass);
      sourceWriter.println(") module.getEventBus();");
      sourceWriter.outdent();
      sourceWriter.println("}");
    } else {
      // only root module can have a placeService instance
      sourceWriter.println("private PlaceService placeService = null;");
      sourceWriter.print("public void setParentModule(");
      sourceWriter.print(Mvp4gModule.class.getCanonicalName());
      sourceWriter.println(" module){}");
    }
  }

  private void writeChildModules() {

    Set<ChildModuleElement> children = configuration.getChildModules();

    boolean hasChildren = (children.size() > 0);
    if (hasChildren) {

      sourceWriter.println("public java.util.Map<String, Mvp4gModule> modules = new java.util.HashMap<String, Mvp4gModule>();");
      sourceWriter.println();

      String            moduleClassName = null;
      EventElement      event           = null;
      Set<EventElement> events          = configuration.getEvents();

      ChildModulesElement loadConfig = configuration.getLoadChildConfig();
      String              errorEvent, beforeEvent, afterEvent;
      boolean             isError, isBefore, isAfter;

      if (loadConfig == null) {
        errorEvent = null;
        beforeEvent = null;
        afterEvent = null;
        isError = false;
        isBefore = false;
        isAfter = false;
      } else {
        errorEvent = loadConfig.getErrorEvent();
        beforeEvent = loadConfig.getBeforeEvent();
        afterEvent = loadConfig.getAfterEvent();
        isError = (errorEvent != null) && (errorEvent.length() > 0);
        isBefore = (beforeEvent != null) && (beforeEvent.length() > 0);
        isAfter = (afterEvent != null) && (afterEvent.length() > 0);
      }

      String formError = null;
      if (isError) {
        String[] params = getElement(errorEvent,
                                     configuration.getEvents()).getEventObjectClass();
        if ((params != null) && (params.length > 0)) {
          formError = "reason";
        }
      }
      boolean isAsync         = true;
      boolean isAsyncEnabled  = configuration.isAsyncEnabled();
      String  suffix          = configuration.getSuffix();
      boolean hasMultipleImpl = (suffix != null) && (suffix.length() > 0);
      String  asyncImpl       = null;
      String  asyncCallback   = null;
      String  loaderName      = null;
      boolean hasLoader;
      for (ChildModuleElement module : children) {
        isAsync = module.isAsync() && isAsyncEnabled;

        loaderName = module.getLoader();
        hasLoader = (loaderName != null);

        if (hasMultipleImpl && isAsync) {
          asyncCallback = module.getName() + "RunAsyncCallback" + suffix;
          sourceWriter.print("interface ");
          sourceWriter.print(asyncCallback);
          sourceWriter.print(" extends ");
          sourceWriter.print(RunAsyncCallback.class.getName());
          sourceWriter.println(" {}");
          asyncImpl = module.getName() + "RunAsync" + suffix;
          sourceWriter.print("interface ");
          sourceWriter.print(asyncImpl);
          sourceWriter.print(" extends ");
          sourceWriter.print(Mvp4gRunAsync.class.getName());
          sourceWriter.print("<");
          sourceWriter.print(asyncCallback);
          sourceWriter.println("> {}");
        }

        moduleClassName = module.getClassName();
        sourceWriter.print("private void load");
        sourceWriter.print(module.getName());
        sourceWriter.println("(final String eventName, final Mvp4gEventPasser passer){");
        sourceWriter.indent();

        if (hasLoader) {
          sourceWriter.println("final Object[] params = (passer == null) ? null : passer.getEventObjects();");
          sourceWriter.print(loaderName);
          sourceWriter.println(".preLoad( eventBus, eventName, params, new Command(){");
          sourceWriter.indent();
          sourceWriter.println("public void execute() {");
          sourceWriter.indent();
        }

        if (isAsync) {
          if (isBefore) {
            writeDispatchEvent(beforeEvent,
                               null);
          }
          if (hasMultipleImpl) {
            sourceWriter.print("((");
            sourceWriter.print(Mvp4gRunAsync.class.getName());
            sourceWriter.print(") GWT.create(");
            sourceWriter.print(asyncImpl);
            sourceWriter.print(".class )).load( new ");
            sourceWriter.print(asyncCallback);
          } else {
            sourceWriter.print("GWT.runAsync(new RunAsyncCallback");
          }
          sourceWriter.print("() {");
          sourceWriter.indent();
          sourceWriter.println("public void onSuccess() {");
          sourceWriter.indent();
          if (isAfter) {
            writeDispatchEvent(afterEvent,
                               null);
          }
          if (hasLoader) {
            sourceWriter.print(loaderName);
            sourceWriter.println(".onSuccess(eventBus, eventName, params );");
          }
        }
        sourceWriter.print(moduleClassName);
        sourceWriter.print(" newModule = (");
        sourceWriter.print(moduleClassName);
        sourceWriter.print(") modules.get(\"");
        sourceWriter.print(moduleClassName);
        sourceWriter.println("\");");
        sourceWriter.println("if(newModule == null){");
        sourceWriter.indent();
        sourceWriter.print("newModule = GWT.create(");
        sourceWriter.print(moduleClassName);
        sourceWriter.println(".class);");
        sourceWriter.print("modules.put(\"");
        sourceWriter.print(moduleClassName);
        sourceWriter.println("\", newModule);");
        sourceWriter.println("newModule.setParentModule(itself);");
        sourceWriter.println("newModule.createAndStartModule();");
        sourceWriter.outdent();
        sourceWriter.println("}");

        sourceWriter.println("newModule.onForward();");

        if (module.isAutoDisplay()) {
          event = getElement(module.getEventToDisplayView(),
                             events);
          writeDispatchEvent(event.getType(),
                             "(" + event.getEventObjectClass()[0] + ") newModule.getStartView()");
        }

        sourceWriter.println("if(passer != null) passer.pass(newModule);");
        if (isAsync) {
          sourceWriter.outdent();
          sourceWriter.println("}");
          sourceWriter.println("public void onFailure(Throwable reason) {");
          if (isAfter) {
            writeDispatchEvent(afterEvent,
                               null);
          }
          if (isError) {
            sourceWriter.indent();
            writeDispatchEvent(errorEvent,
                               formError);
            sourceWriter.outdent();
          }
          if (hasLoader) {
            sourceWriter.print(loaderName);
            sourceWriter.println(".onFailure( eventBus, eventName, params, reason );");
          }
          sourceWriter.println("}");
          sourceWriter.outdent();
          sourceWriter.println("});");
        }

        if (hasLoader) {
          sourceWriter.outdent();
          sourceWriter.println("}");
          sourceWriter.outdent();
          sourceWriter.println("});");
        }

        sourceWriter.outdent();
        sourceWriter.println("}");
      }
    }
    sourceWriter.println("public void loadChildModule(String childModuleClassName, String eventName, boolean passive, Mvp4gEventPasser passer){");
    sourceWriter.indent();
    if (hasChildren) {
      sourceWriter.println("if (passive){");
      sourceWriter.indent();
      sourceWriter.println("Mvp4gModule childModule = modules.get(childModuleClassName);");
      sourceWriter.println("if((childModule != null) && (passer != null)){");
      sourceWriter.indent();
      sourceWriter.println("passer.pass(childModule);");
      sourceWriter.outdent();
      sourceWriter.println("}");
      sourceWriter.outdent();
      sourceWriter.println("}");

      String childModuleClassName, childModuleName;
      for (ChildModuleElement childModule : configuration.getChildModules()) {
        childModuleName = childModule.getName();
        childModuleClassName = childModule.getClassName();
        sourceWriter.print("else if(\"");
        sourceWriter.print(childModuleClassName);
        sourceWriter.println("\".equals(childModuleClassName)){");
        sourceWriter.indent();
        sourceWriter.print("load");
        sourceWriter.print(childModuleName);
        sourceWriter.println("(eventName, passer);");
        sourceWriter.outdent();
        sourceWriter.println("}");
      }
      sourceWriter.println("else {");
      sourceWriter.indent();
      sourceWriter.println("throw new Mvp4gException( \"ChildModule \" + childModuleClassName + \" not found. Is this module a sibling module?\" );");
      sourceWriter.outdent();
      sourceWriter.println("}");
    }
    sourceWriter.outdent();
    sourceWriter.println("}");
  }

  private void writeEventBusClass() {

    EventBusElement eventBus = configuration.getEventBus();

    sourceWriter.print("private abstract class AbstractEventBus extends ");
    sourceWriter.print(eventBus.getAbstractClassName());
    sourceWriter.print(" implements ");
    sourceWriter.print(eventBus.getInterfaceClassName());
    sourceWriter.println("{}");
  }

  private void writeGinInjector() {
    sourceWriter.print("@GinModules({");
    List<String> modules = configuration.getGinModule()
                                        .getModules();
    int modulesCount = modules.size() - 1;
    for (int i = 0; i < modulesCount; i++) {
      sourceWriter.print(modules.get(i));
      sourceWriter.print(".class,");
    }
    sourceWriter.print(modules.get(modulesCount));
    sourceWriter.println(".class})");

    String moduleName = configuration.getModule()
                                     .getQualifiedSourceName()
                                     .replace(".",
                                              "_");
    sourceWriter.print("public interface ");
    sourceWriter.print(moduleName);
    sourceWriter.println("Ginjector extends Ginjector {");
    sourceWriter.indent();
    for (PresenterElement presenter : configuration.getPresenters()) {
      sourceWriter.print(presenter.getClassName());
      sourceWriter.print(" get");
      sourceWriter.print(presenter.getName());
      sourceWriter.println("();");
    }
    for (EventHandlerElement eventHandler : configuration.getEventHandlers()) {
      sourceWriter.print(eventHandler.getClassName());
      sourceWriter.print(" get");
      sourceWriter.print(eventHandler.getName());
      sourceWriter.println("();");
    }
    for (ViewElement view : configuration.getViews()) {
      sourceWriter.print(view.getClassName());
      sourceWriter.print(" get");
      sourceWriter.print(view.getName());
      sourceWriter.println("();");
    }
    for (HistoryConverterElement history : configuration.getHistoryConverters()) {
      sourceWriter.print(history.getClassName());
      sourceWriter.print(" get");
      sourceWriter.print(history.getName());
      sourceWriter.println("();");
    }
    for (EventFilterElement filter : configuration.getEventFilters()) {
      sourceWriter.print(filter.getClassName());
      sourceWriter.print(" get");
      sourceWriter.print(filter.getName());
      sourceWriter.println("();");
    }
    for (LoaderElement loader : configuration.getLoaders()) {
      sourceWriter.print(loader.getClassName());
      sourceWriter.print(" get");
      sourceWriter.print(loader.getName());
      sourceWriter.println("();");
    }
    DebugElement debug = configuration.getDebug();
    if (debug != null) {
      String loggerClass = debug.getLogger();
      sourceWriter.print(loggerClass);
      sourceWriter.print(" get");
      sourceWriter.print(loggerClass.replace(".",
                                             "_"));
      sourceWriter.println("();");
    }
    if (configuration.isRootModule()) {
      HistoryElement history = configuration.getHistory();
      String placeServiceClass = (history == null) ?
                                 null :
                                 history.getPlaceServiceClass();
      if (placeServiceClass == null) {
        placeServiceClass = PlaceService.class.getCanonicalName();
      }
      sourceWriter.print(placeServiceClass);
      sourceWriter.print(" get");
      sourceWriter.print(placeServiceClass.replace(".",
                                                   "_"));
      sourceWriter.println("();");
    }
    sourceWriter.outdent();
    sourceWriter.print("}");

  }

  /**
   * Write the history converters included in the configuration file.
   * <br>
   * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
   */
  private void writeHistory() {
    if (configuration.isRootModule()) {
      HistoryElement history = configuration.getHistory();
      String placeServiceClass = (history == null) ?
                                 null :
                                 history.getPlaceServiceClass();
      if (placeServiceClass == null) {
        placeServiceClass = PlaceService.class.getCanonicalName();
      }
      sourceWriter.print("placeService = injector.get");
      sourceWriter.print(placeServiceClass.replace(".",
                                                   "_"));
      sourceWriter.println("();");
    }

    String name = null;

    for (HistoryConverterElement converter : configuration.getHistoryConverters()) {
      name = converter.getName();
      createInstance(name,
                     converter.getClassName(),
                     true);
      injectServices(name,
                     converter.getInjectedServices());
    }
  }

  /**
   * Write the views included in the configuration file.
   * <br>
   * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
   */
  private void writeViews() {

    for (ViewElement view : configuration.getViews()) {
      if (view.isInstantiateAtStart()) {
        createInstance(view.getName(),
                       view.getClassName(),
                       true);
      }
    }

  }

  /**
   * Write the presenters included in the configuration file.
   * <br>
   * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
   */
  private void writePresenters() {

    String name, view;
    String className = null;

    for (PresenterElement presenter : configuration.getPresenters()) {
      if (!presenter.isMultiple() && !presenter.isAsync()) {
        name = presenter.getName();
        className = presenter.getClassName();
        view = presenter.getView();

        createInstance(name,
                       className,
                       true);

        sourceWriter.print(name);
        sourceWriter.println(".setView(" + view + ");");
        if (presenter.hasInverseView()) {
          sourceWriter.print(view);
          sourceWriter.println(".setPresenter(" + name + ");");
        }

        injectServices(name,
                       presenter.getInjectedServices());
      }
    }

  }

  /**
   * Write the eventHandlers included in the configuration file.
   * <br>
   * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
   */
  private void writeEventHandlers() {

    String name      = null;
    String className = null;

    for (EventHandlerElement eventHandler : configuration.getEventHandlers()) {
      if (!eventHandler.isMultiple() && !eventHandler.isAsync()) {
        name = eventHandler.getName();
        className = eventHandler.getClassName();

        createInstance(name,
                       className,
                       true);

        injectServices(name,
                       eventHandler.getInjectedServices());
      }
    }
  }

  /**
   * Write the presenters included in the configuration file.
   * <br>
   * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
   */
  private void injectEventBus() {

    for (PresenterElement presenter : configuration.getPresenters()) {
      if (!presenter.isMultiple() && !presenter.isAsync()) {
        sourceWriter.print(presenter.getName());
        sourceWriter.println(".setEventBus(eventBus);");
      }
    }

    for (EventHandlerElement eventHandler : configuration.getEventHandlers()) {
      if (!eventHandler.isMultiple() && !eventHandler.isAsync()) {
        sourceWriter.print(eventHandler.getName());
        sourceWriter.println(".setEventBus(eventBus);");
      }
    }

    if (configuration.isRootModule()) {
      sourceWriter.print("placeService.setModule(itself);");
    }

  }

  /**
   * Write the logger included in the configuration file.
   * <br>
   * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
   */
  private void writeLogger() {
    DebugElement debug = configuration.getDebug();
    if (debug != null) {
      sourceWriter.print("logger = injector.get");
      sourceWriter.print(debug.getLogger()
                              .replace(".",
                                       "_"));
      sourceWriter.println("();");
    }
  }

  /**
   * Write the services included in the configuration file.
   * <br>
   * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
   */
  private void writeServices() {

    String name = null;

    for (ServiceElement service : configuration.getServices()) {
      name = service.getName();

      sourceWriter.print("final ");
      sourceWriter.print(service.getGeneratedClassName());
      sourceWriter.print(" ");
      sourceWriter.print(name);
      sourceWriter.print(" = GWT.create(");
      sourceWriter.print(service.getClassName());
      sourceWriter.println(".class);");

      if (service.hasPath()) {
        sourceWriter.print("((ServiceDefTarget) ");
        sourceWriter.print(name);
        sourceWriter.print(").setServiceEntryPoint(\"");
        sourceWriter.print(service.getPath());
        sourceWriter.print("\");");
      }
    }
  }

  /**
   * Write the events included in the configuration file
   * <br>
   * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
   *
   * @throws UnableToCompleteException
   *   thrown if the events tag aren't correct.
   * @throws InvalidMvp4gConfigurationException
   */
  private void writeEventBus() {

    EventBusElement eventBus = configuration.getEventBus();

    sourceWriter.println("eventBus = new AbstractEventBus(){");
    sourceWriter.indent();

    writeMultipleConstructor();

    List<EventElement> eventsWithHistory = new ArrayList<EventElement>();

    Set<EventHandlerElement> eventHandlers = new HashSet<EventHandlerElement>(configuration.getPresenters());
    eventHandlers.addAll(configuration.getEventHandlers());

    String[]                objectClasses  = null;
    String                  type, history, param;
    List<String>            activate, deactivate, handlers, binds, generates;
    boolean                 hasLog         = (configuration.getDebug() != null);
    Set<EventFilterElement> filters        = configuration.getEventFilters();
    EventFiltersElement     filtersElement = configuration.getEventFilterConfiguration();
    boolean filterAfterHistory = (filtersElement == null) ?
                                 false :
                                 filtersElement.isAfterHistory();
    boolean hasFilter = (filters != null) && (filters.size() > 0) || ((filtersElement != null) && (filtersElement.isForceFilters()));
    boolean isNavigationEvent, isWithTokenGeneration;
    for (EventElement event : configuration.getEvents()) {
      type = event.getType();
      objectClasses = event.getEventObjectClass();

      isNavigationEvent = event.isNavigationEvent();

      handlers = event.getHandlers();
      binds = event.getBinds();
      history = event.getHistory();
      activate = event.getActivate();
      deactivate = event.getDeactivate();
      generates = event.getGenerate();
      isWithTokenGeneration = event.isWithTokenGeneration();

      sourceWriter.print("public ");
      sourceWriter.print((isWithTokenGeneration) ?
                         "String " :
                         "void ");
      sourceWriter.print(type);
      sourceWriter.print("(");
      if ((objectClasses == null) || (objectClasses.length == 0)) {
        param = null;
      } else {
        int           nbParams     = objectClasses.length;
        StringBuilder paramBuilder = new StringBuilder(50 * nbParams);
        int           i;
        for (i = 0; i < (nbParams - 1); i++) {
          if (isNavigationEvent) {
            sourceWriter.print("final ");
          }
          sourceWriter.print(objectClasses[i]);
          sourceWriter.print(" attr");
          sourceWriter.print(Integer.toString(i));
          sourceWriter.print(",");

          paramBuilder.append("attr");
          paramBuilder.append(i);
          paramBuilder.append(",");
        }

        if (isNavigationEvent) {
          sourceWriter.print("final ");
        }
        sourceWriter.print(objectClasses[i]);
        sourceWriter.print(" attr");
        sourceWriter.print(Integer.toString(i));

        paramBuilder.append("attr");
        paramBuilder.append(i);

        param = paramBuilder.toString();

      }
      sourceWriter.println("){");

      sourceWriter.indent();

      if (isWithTokenGeneration) {
        sourceWriter.println("if(tokenMode){");
        sourceWriter.indent();
        if (event.isTokenGenerationFromParent()) {
          sourceWriter.println("tokenMode=false;");
          sourceWriter.print("((");
          sourceWriter.print(BaseEventBus.class.getName());
          sourceWriter.println(") parentEventBus).tokenMode = true;");
          sourceWriter.print("return ");
          writeParentEvent(event,
                           param);
        } else {
          sourceWriter.print("return ");
          writeEventHistoryConvertion(event,
                                      getElement(history,
                                                 configuration.getHistoryConverters()),
                                      param,
                                      true);
        }
        sourceWriter.outdent();
        sourceWriter.println("} else {");
        sourceWriter.indent();
      }

      if (isNavigationEvent) {
        if (hasLog) {
          writeLog("Asking for user confirmation: ",
                   type,
                   objectClasses);
        }
        sourceWriter.println("confirmNavigation(new NavigationEventCommand(this){");
        sourceWriter.indent();
        sourceWriter.println("public void execute(){");
        sourceWriter.indent();
      }

      if (hasLog) {
        sourceWriter.println("int startLogDepth = BaseEventBus.logDepth;");
        sourceWriter.println("try {");
        sourceWriter.indent();
        sourceWriter.println("++BaseEventBus.logDepth;");
        writeLog("",
                 type,
                 objectClasses);
        sourceWriter.println("++BaseEventBus.logDepth;");
      }

      if (!filterAfterHistory) {
        writeEventFilter(hasFilter,
                         event,
                         param);
      }

      if (history != null) {

        HistoryConverterElement historyConverterElement = getElement(history,
                                                                     configuration.getHistoryConverters());
        if (ClearHistory.class.getCanonicalName()
                              .equals(historyConverterElement.getClassName())) {
          sourceWriter.println("clearHistory(itself);");
        } else {
          writeEventHistoryConvertion(event,
                                      historyConverterElement,
                                      param,
                                      false);
          eventsWithHistory.add(event);
        }
      }

      if (filterAfterHistory) {
        writeEventFilter(hasFilter,
                         event,
                         param);
      }

      if ((activate != null) && (activate.size() > 0)) {
        writeActivation(activate,
                        eventHandlers,
                        true,
                        true);
      }
      if ((deactivate != null) && (deactivate.size() > 0)) {
        writeActivation(deactivate,
                        eventHandlers,
                        false,
                        true);
      }

      writeLoadChildModule(event,
                           param);
      writeLoadSiblingModule(event,
                             param);
      writeLoadSplitters(event,
                         param);
      writeParentEvent(event,
                       param);

      writeEventAction(event,
                       binds,
                       handlers,
                       generates,
                       eventHandlers,
                       param,
                       true);

      if (hasLog) {
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.println("finally {");
        sourceWriter.indent();
        sourceWriter.println("BaseEventBus.logDepth = startLogDepth;");
        sourceWriter.outdent();
        sourceWriter.println("}");
      }

      if (isNavigationEvent) {
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.outdent();
        sourceWriter.println("});");
      }

      if (isWithTokenGeneration) {
        sourceWriter.outdent();
        sourceWriter.println("return null;");
        sourceWriter.println("}");
      }

      sourceWriter.outdent();
      sourceWriter.println("}");

    }

    if (eventBus.isWithLookUp()) {
      writeEventLookUp();
    }

    sourceWriter.println("public void setNavigationConfirmation( NavigationConfirmationInterface navigationConfirmation ) {");
    sourceWriter.indent();
    if (!configuration.isRootModule()) {
      sourceWriter.println("parentEventBus.setNavigationConfirmation(navigationConfirmation);");
    } else {
      sourceWriter.println("placeService.setNavigationConfirmation(navigationConfirmation);");
    }
    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.println("public void confirmNavigation(NavigationEventCommand event){");
    sourceWriter.indent();
    if (!configuration.isRootModule()) {
      sourceWriter.println("parentEventBus.confirmNavigation(event);");
    } else {
      sourceWriter.println("placeService.confirmEvent(event);");
    }
    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.println("public void setApplicationHistoryStored( boolean historyStored ){");
    sourceWriter.indent();
    if (!configuration.isRootModule()) {
      sourceWriter.println("parentEventBus.setApplicationHistoryStored(historyStored);");
    } else {
      sourceWriter.println("placeService.setEnabled(historyStored);");
    }
    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.println("};");

    for (EventElement event : eventsWithHistory) {
      sourceWriter.print("addConverter( \"");
      sourceWriter.print(event.getName());
      sourceWriter.print("\",");
      sourceWriter.print(event.getHistory());
      sourceWriter.print(");");
    }
  }

  private void writeEventAction(EventElement event,
                                List<String> binds,
                                List<String> handlers,
                                List<String> generates,
                                Set<EventHandlerElement> eventHandlers,
                                String param,
                                boolean fromEventBus) {
    EventHandlerElement eventHandler;
    String              type         = event.getType();
    String              name         = event.getName();
    String              calledMethod = event.getCalledMethod();
    boolean             isPassive    = event.isPassive();
    // write bind annotations
    if (binds != null) {

      for (String bind : binds) {
        eventHandler = getElement(bind,
                                  eventHandlers); // get handler from set of all handlers by its name

        if (!eventHandler.isMultiple()) {
          // passive events not allowed for binds
          writeBindHandling(bind,
                            type,
                            name,
                            param);
        } else {
          writeMultipleActionBegin(eventHandler,
                                   "",
                                   fromEventBus);
          writeBindHandling("handler",
                            type,
                            name,
                            param); // handler contains bind for cycle
          writeMultipleActionEnd();
        }
      }
    }

    if (handlers != null) {

      for (String handler : handlers) {
        eventHandler = getElement(handler,
                                  eventHandlers);
        if (!eventHandler.isMultiple()) {
          writeEventHandling(handler,
                             type,
                             name,
                             calledMethod,
                             param,
                             isPassive);
        } else {
          writeMultipleActionBegin(eventHandler,
                                   "",
                                   fromEventBus);
          writeEventHandling("handler",
                             type,
                             name,
                             calledMethod,
                             param,
                             isPassive);
          writeMultipleActionEnd();
        }
      }

    }

    if (generates != null) {
      for (String generate : generates) {
        eventHandler = getElement(generate,
                                  eventHandlers);
        if (eventHandler instanceof PresenterElement) {
          createPresenter((PresenterElement) eventHandler,
                          true);
        } else {
          createEventHandler(eventHandler,
                             true);
        }
        sourceWriter.print("eventBus.finishAddHandler(");
        sourceWriter.print(eventHandler.getName());
        sourceWriter.print(",");
        sourceWriter.print(eventHandler.getClassName());
        sourceWriter.println(".class, true);");
        writeEventHandling(generate,
                           type,
                           name,
                           calledMethod,
                           param,
                           isPassive);
      }
    }
  }

  private void writeEventFilters() {
    String filterName;
    for (EventFilterElement filter : configuration.getEventFilters()) {
      filterName = filter.getName();
      createInstance(filterName,
                     filter.getClassName(),
                     true);
      sourceWriter.print("eventBus.addEventFilter(");
      sourceWriter.print(filterName);
      sourceWriter.print(");");
    }
  }

  private void writeEventHistoryConvertion(EventElement event,
                                           HistoryConverterElement historyConverterElement,
                                           String param,
                                           boolean onlyTokens) {
    sourceWriter.print("place( itself, \"");
    sourceWriter.print(event.getName());
    sourceWriter.print("\",");
    HistoryConverterType type = com.mvp4g.client.annotation.History.HistoryConverterType.valueOf(historyConverterElement.getType());
    switch (type) {
      case DEFAULT:
        sourceWriter.print(historyConverterElement.getName());
        sourceWriter.print(".");
        sourceWriter.print(event.getCalledMethod());
        sourceWriter.print("(");
        if (param != null) {
          sourceWriter.print(param);
        }
        sourceWriter.print(")");
        break;
      case SIMPLE:
        sourceWriter.print(historyConverterElement.getName());
        sourceWriter.print(".convertToToken(\"");
        sourceWriter.print(event.getName());
        sourceWriter.print("\"");
        if (param != null) {
          sourceWriter.print(",");
          sourceWriter.print(param);
        }
        sourceWriter.print(")");
        break;
      default:
        sourceWriter.print("null");
        break;
    }
    sourceWriter.print(",");
    sourceWriter.print(Boolean.toString(onlyTokens));
    sourceWriter.println(");");

  }

  private void writeMultipleActionBegin(EventHandlerElement eventHandler,
                                        String varSubName,
                                        boolean fromEventBus) {
    String className   = eventHandler.getClassName();
    String elementName = eventHandler.getName() + varSubName;
    sourceWriter.print("List<");
    sourceWriter.print(className);
    sourceWriter.print("> handlers");
    sourceWriter.print(elementName);
    sourceWriter.print(" = ");
    if (!fromEventBus) {
      sourceWriter.print("eventBus.");
    }
    sourceWriter.print("getHandlers(");
    sourceWriter.print(className);
    sourceWriter.println(".class);");
    sourceWriter.print("if(handlers");
    sourceWriter.print(elementName);
    sourceWriter.println("!= null){");
    sourceWriter.indent();
    sourceWriter.print(className);
    sourceWriter.println(" handler;");
    sourceWriter.print("int handlerCount = handlers");
    sourceWriter.print(elementName);
    sourceWriter.println(".size();");
    sourceWriter.println("for(int i=0; i<handlerCount; i++){");
    sourceWriter.indent();
    sourceWriter.print("handler = handlers");
    sourceWriter.print(elementName);
    sourceWriter.println(".get(i);");
  }

  private void writeMultipleActionEnd() {
    sourceWriter.outdent();
    sourceWriter.println("}");
    sourceWriter.outdent();
    sourceWriter.println("}");
  }

  private void writeActivation(List<String> activateList,
                               Set<EventHandlerElement> handlers,
                               boolean activate,
                               boolean fromEventBus) {
    String activateStr = ".setActivated(" + Boolean.toString(activate) + ");";
    String varSubName = (activate) ?
                        "act" :
                        "de";
    EventHandlerElement handler;
    for (String handlerName : activateList) {
      handler = getElement(handlerName,
                           handlers);
      if (handler.isMultiple()) {
        writeMultipleActionBegin(handler,
                                 varSubName,
                                 fromEventBus);
        sourceWriter.print("handler");
        sourceWriter.println(activateStr);
        writeMultipleActionEnd();
      } else {
        sourceWriter.print(handlerName);
        sourceWriter.println(activateStr);
      }
    }
  }

  private void writeEventHandling(String handler,
                                  String type,
                                  String name,
                                  String calledMethod,
                                  String param,
                                  boolean passive) {
    sourceWriter.print("if (");
    sourceWriter.print(handler);
    sourceWriter.print(".isActivated(");
    sourceWriter.print(Boolean.toString(passive));
    sourceWriter.print(", \"");
    sourceWriter.print(name);
    sourceWriter.print("\"");
    if (param != null) {
      sourceWriter.print(", new Object[]{");
      sourceWriter.print(param);
      sourceWriter.print("}");
    }
    sourceWriter.println(")){");
    sourceWriter.indent();

    writeDetailedLog(handler,
                     type,
                     false);

    sourceWriter.print(handler);
    sourceWriter.print(".");
    sourceWriter.print(calledMethod);
    sourceWriter.print("(");
    if (param != null) {
      sourceWriter.print(param);
    }
    sourceWriter.println(");");

    sourceWriter.outdent();
    sourceWriter.println("}");
  }

  /**
   * Only bind type needed to make it binded.
   *
   * @param bind
   * @param type
   */
  private void writeBindHandling(String bind,
                                 String type,
                                 String name,
                                 String param) {
    sourceWriter.print(bind);
    sourceWriter.print(".isActivated(");
    sourceWriter.print("false"); // passive events not allowed for binds
    sourceWriter.print(", \"");
    sourceWriter.print(name);
    sourceWriter.print("\"");
    if (param != null) {
      sourceWriter.print(", new Object[]{");
      sourceWriter.print(param);
      sourceWriter.print("}");
    }
    sourceWriter.println(");");

    writeDetailedLog(bind,
                     type,
                     true);
  }

  private void writeEventFilter(boolean hasFilter,
                                EventElement event,
                                String parentParam) {
    if (hasFilter) {
      sourceWriter.indent();
      sourceWriter.print("if (!filterEvent(\"");
      sourceWriter.print(event.getName());
      sourceWriter.print("\"");
      if (parentParam != null) {
        sourceWriter.print(", new Object[]{");
        sourceWriter.print(parentParam);
        sourceWriter.print("}");
      }
      sourceWriter.println(")){");

      sourceWriter.indent();
      writeEventFiltersLog(event.getType());
      sourceWriter.print("return");
      if (event.isWithTokenGeneration() && !event.isNavigationEvent()) {
        sourceWriter.print(" null");
      }
      sourceWriter.println(";");
      sourceWriter.outdent();
      sourceWriter.println("}");
    }
  }

  private void writeEventLookUp() {

    sourceWriter.println("public void dispatch( String eventType, Object... data ){");
    sourceWriter.indent();

    sourceWriter.println("try{");
    sourceWriter.indent();

    String[] objectClasses = null;
    String   param         = null;

    for (EventElement event : configuration.getEvents()) {

      objectClasses = event.getEventObjectClass();

      if ((objectClasses == null) || (objectClasses.length == 0)) {
        param = "();";
      } else {
        int           nbParams     = objectClasses.length;
        StringBuilder paramBuilder = new StringBuilder(50 * nbParams);
        int           i;
        for (i = 0; i < (nbParams - 1); i++) {
          paramBuilder.append("(");
          paramBuilder.append(getAssociatedClass(objectClasses[i]));
          paramBuilder.append(") data[");
          paramBuilder.append(Integer.toString(i));
          paramBuilder.append("],");
        }

        paramBuilder.append("(");
        paramBuilder.append(getAssociatedClass(objectClasses[i]));
        paramBuilder.append(") data[");
        paramBuilder.append(Integer.toString(i));
        paramBuilder.append("]");

        param = "(" + paramBuilder.toString() + ");";

      }

      sourceWriter.print("if ( \"");
      sourceWriter.print(event.getName());
      sourceWriter.println("\".equals( eventType ) ){");

      sourceWriter.indent();
      sourceWriter.print(event.getType());
      sourceWriter.println(param);
      sourceWriter.outdent();
      sourceWriter.print("} else ");

    }

    sourceWriter.println("{");
    sourceWriter.indent();
    sourceWriter.println("throw new Mvp4gException( \"Event \" + eventType + \" doesn't exist. Have you forgotten to add it to your Mvp4g configuration file?\" );");
    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.outdent();
    sourceWriter.println("} catch ( ClassCastException e ) {");
    sourceWriter.indent();
    sourceWriter.println("handleClassCastException( e, eventType );");
    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.outdent();
    sourceWriter.println("}");
    sourceWriter.outdent();
  }

  /**
   * Write the start event tag included in the configuration file
   * <br>
   * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
   *
   * @throws UnableToCompleteException
   *   thrown if the start event tag isn't correct.
   */
  private void writeStartEvent() {

    StartElement start = configuration.getStart();
    // TODO
    //    // Start view
    //    if (start.hasPresenter()) {
    //      String startPresenter = start.getPresenter();
    //      PresenterElement presenter = getElement(startPresenter,
    //                                              configuration.getPresenters());
    //      if (presenter.isMultiple()) {
    //        sourceWriter.print("this.startPresenter = eventBus.addHandler(");
    //        sourceWriter.print(presenter.getClassName());
    //        sourceWriter.println(".class);");
    //      } else {
    //        sourceWriter.print("this.startPresenter = ");
    //        sourceWriter.print(startPresenter);
    //        sourceWriter.println(";");
    //      }
    //      sourceWriter.println("this.startView = startPresenter.getView();");
    //    }

    if (start.hasEventType()) {
      EventFiltersElement filterConf = configuration.getEventFilterConfiguration();
      if ((filterConf != null) && (!filterConf.isFilterStart())) {
        sourceWriter.println("eventBus.setFilteringEnabledForNextOne(false);");
      }
      writeDispatchEvent(start.getEventType(),
                         null);
    }

    if (start.hasHistory()) {
      sourceWriter.println("HistoryProxyProvider.INSTANCE.get().fireCurrentHistoryState();");
    }

  }

  private void writeForwardEvent() {

    sourceWriter.println("public void onForward(){");
    sourceWriter.indent();

    StartElement start = configuration.getStart();

    if (start.hasForwardEventType()) {
      EventFiltersElement filterConf = configuration.getEventFilterConfiguration();
      if ((filterConf != null) && (!filterConf.isFilterForward())) {
        sourceWriter.println("eventBus.setFilteringEnabledForNextOne(false);");
      }

      writeDispatchEvent(start.getForwardEventType(),
                         null);
    }

    sourceWriter.outdent();
    sourceWriter.println("}");

  }

  /**
   * Write the lines to create a new instance of an element
   *
   * @param elementName
   *   name of the element to create
   * @param className
   *   class name of the element to create
   */
  private void createInstance(String elementName,
                              String className,
                              boolean isFinal) {
    if (isFinal) {
      sourceWriter.print("final ");
    }
    sourceWriter.print(className);
    sourceWriter.print(" ");
    sourceWriter.print(elementName);
    sourceWriter.print(" = injector.get");
    sourceWriter.print(elementName);
    sourceWriter.println("();");
  }

  private void createPresenter(PresenterElement presenter,
                               boolean withInstanceName) {
    String elementName = presenter.getName();
    if (withInstanceName) {
      sourceWriter.print(presenter.getClassName());
      sourceWriter.print(" ");
      sourceWriter.print(elementName);
      sourceWriter.print(" = ");
    }
    sourceWriter.print("BaseEventBus.setPresenter(");
    sourceWriter.print(Boolean.toString(presenter.hasInverseView()));
    sourceWriter.print(", injector.get");
    sourceWriter.print(elementName);
    sourceWriter.print("(), injector.get");
    sourceWriter.print(presenter.getView());
    sourceWriter.println("(), eventBus);");
    injectServices(elementName,
                   presenter.getInjectedServices());
  }

  private void createEventHandler(EventHandlerElement eventHandler,
                                  boolean withInstanceName) {
    String elementName = eventHandler.getName();
    if (withInstanceName) {
      sourceWriter.print(eventHandler.getClassName());
      sourceWriter.print(" ");
      sourceWriter.print(elementName);
      sourceWriter.print(" = ");
    }
    sourceWriter.print("BaseEventBus.setEventHandler(injector.get");
    sourceWriter.print(elementName);
    sourceWriter.println("(), eventBus);");
    injectServices(elementName,
                   eventHandler.getInjectedServices());
  }

  /**
   * Write the lines to inject services into an element
   *
   * @param elementName
   *   name of the element where services need to be injected
   * @param injectedServices
   *   name of the services to inject
   */
  private void injectServices(String elementName,
                              List<InjectedElement> injectedServices) {
    for (InjectedElement service : injectedServices) {
      sourceWriter.print(elementName);
      sourceWriter.println("." + service.getSetterName() + "(" + service.getElementName() + ");");
    }
  }

  private void writeMultipleConstructor() {
    sourceWriter.println("protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ){");
    sourceWriter.indent();
    for (PresenterElement presenter : configuration.getPresenters()) {
      if (presenter.isMultiple() && !presenter.isAsync()) {
        sourceWriter.print("if (");
        sourceWriter.print(presenter.getClassName());
        sourceWriter.println(".class.equals(handlerClass)){");
        sourceWriter.indent();
        sourceWriter.print("return (T) ");
        createPresenter(presenter,
                        false);
        sourceWriter.outdent();
        sourceWriter.println("}");
      }
    }
    for (EventHandlerElement eventHandler : configuration.getEventHandlers()) {
      if (eventHandler.isMultiple()) {
        sourceWriter.print("if (");
        sourceWriter.print(eventHandler.getClassName());
        sourceWriter.println(".class.equals(handlerClass)){");
        sourceWriter.indent();
        sourceWriter.print("return (T) ");
        createEventHandler(eventHandler,
                           false);
        sourceWriter.outdent();
        sourceWriter.println("}");
      }
    }
    sourceWriter.outdent();
    sourceWriter.println("return null;");
    sourceWriter.println("}");
  }

  private void writeParentEvent(EventElement event,
                                String form) {
    if (event.hasForwardToParent()) {
      sourceWriter.print("parentEventBus.");
      sourceWriter.print(event.getType());
      sourceWriter.print("(");
      if ((form != null) && (form.length() > 0)) {
        sourceWriter.print(form);
      }
      sourceWriter.println(");");
    }
  }

  private void writeLoadChildModule(EventElement event,
                                    String param) {

    boolean passive = event.isPassive();

    ChildModuleElement      module             = null;
    Set<ChildModuleElement> modules            = configuration.getChildModules();
    String[]                eventObjectClasses = null;
    String                  eventObject        = null;
    List<String>            modulesToLoad      = event.getForwardToModules();
    if (modulesToLoad != null) {
      if (passive) {
        sourceWriter.println("Mvp4gModule module;");
      }
      for (String moduleName : modulesToLoad) {
        module = getElement(moduleName,
                            modules);
        eventObjectClasses = event.getEventObjectClass();

        JClassType eventBusType = configuration.getOthersEventBusClassMap()
                                               .get(module.getClassName());
        String eventBusClass = eventBusType.getQualifiedSourceName();

        if (passive) {
          eventObject = param;
          sourceWriter.print("module = modules.get(\"");
          sourceWriter.print(module.getClassName());
          sourceWriter.println("\");");
          sourceWriter.println("if(module != null){");
        } else {
          if ((eventObjectClasses == null) || (eventObjectClasses.length == 0)) {
            eventObject = null;
          } else {
            int           nbParam            = eventObjectClasses.length;
            StringBuilder eventObjectBuilder = new StringBuilder(nbParam * 70);

            int i;
            for (i = 0; i < (nbParam - 1); i++) {
              eventObjectBuilder.append("(");
              eventObjectBuilder.append(getAssociatedClass(eventObjectClasses[i]));
              eventObjectBuilder.append(") eventObjects[");
              eventObjectBuilder.append(i);
              eventObjectBuilder.append("],");
            }
            eventObjectBuilder.append("(");
            eventObjectBuilder.append(getAssociatedClass(eventObjectClasses[i]));
            eventObjectBuilder.append(") eventObjects[");
            eventObjectBuilder.append(i);
            eventObjectBuilder.append("]");
            eventObject = eventObjectBuilder.toString();
          }
          sourceWriter.print("load");
          sourceWriter.print(module.getName());
          sourceWriter.print("(\"");
          sourceWriter.print(event.getName());
          sourceWriter.print("\", new Mvp4gEventPasser(");
          if (param != null) {
            sourceWriter.print("new Object[]{");
            sourceWriter.print(param);
            sourceWriter.print("}");
          }
          sourceWriter.println("){");
          sourceWriter.indent();
          sourceWriter.println("public void pass(Mvp4gModule module){");

        }

        sourceWriter.indent();
        sourceWriter.print(eventBusClass);
        sourceWriter.print(" eventBus = (");
        sourceWriter.print(eventBusClass);
        sourceWriter.println(") module.getEventBus();");
        writeDispatchEvent(event.getType(),
                           eventObject);
        sourceWriter.outdent();
        sourceWriter.println("}");
        if (!passive) {
          sourceWriter.outdent();
          sourceWriter.println("});");
        }

      }
    }

  }

  private void writeLoadSiblingModule(EventElement event,
                                      String param) {

    String passive = Boolean.toString(event.isPassive());

    List<String> siblingsToLoad = event.getSiblingsToLoad();
    if ((siblingsToLoad != null) && (siblingsToLoad.size() > 0)) {

      String[] eventObjectClasses = event.getEventObjectClass();
      String   eventObject;
      if ((eventObjectClasses == null) || (eventObjectClasses.length == 0)) {
        eventObject = null;
      } else {
        int           nbParam            = eventObjectClasses.length;
        StringBuilder eventObjectBuilder = new StringBuilder(nbParam * 70);

        int i;
        for (i = 0; i < (nbParam - 1); i++) {
          eventObjectBuilder.append("(");
          eventObjectBuilder.append(getAssociatedClass(eventObjectClasses[i]));
          eventObjectBuilder.append(") eventObjects[");
          eventObjectBuilder.append(i);
          eventObjectBuilder.append("],");
        }
        eventObjectBuilder.append("(");
        eventObjectBuilder.append(getAssociatedClass(eventObjectClasses[i]));
        eventObjectBuilder.append(") eventObjects[");
        eventObjectBuilder.append(i);
        eventObjectBuilder.append("]");
        eventObject = eventObjectBuilder.toString();
      }
      String eventBusClass;
      for (String moduleClassName : siblingsToLoad) {
        eventObjectClasses = event.getEventObjectClass();

        eventBusClass = configuration.getOthersEventBusClassMap()
                                     .get(moduleClassName)
                                     .getQualifiedSourceName();

        sourceWriter.print("parentModule.loadChildModule(\"");
        sourceWriter.print(moduleClassName);
        sourceWriter.print("\", \"");
        sourceWriter.print(event.getName());
        sourceWriter.print("\", ");
        sourceWriter.print(passive);
        sourceWriter.print(", new Mvp4gEventPasser(");
        if (param != null) {
          sourceWriter.print("new Object[]{");
          sourceWriter.print(param);
          sourceWriter.print("}");
        }
        sourceWriter.println("){");
        sourceWriter.indent();
        sourceWriter.println("public void pass(Mvp4gModule module){");

        sourceWriter.indent();
        sourceWriter.print(eventBusClass);
        sourceWriter.print(" eventBus = (");
        sourceWriter.print(eventBusClass);
        sourceWriter.print(") module.getEventBus();");
        writeDispatchEvent(event.getType(),
                           eventObject);
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.outdent();
        sourceWriter.println("});");
      }
    }

  }

  private void writeLoadSplitters(EventElement event,
                                  String form) {
    List<String> splitters = event.getSplitters();
    if (splitters != null) {
      boolean  done        = false;
      String[] eventObjectClasses;
      String   eventObject = null;
      boolean  toLoad;
      for (String splitter : splitters) {
        // If the event is passive but we need to generate multiple handlers, we have to load it.
        toLoad = !event.isPassive() ||
                 (getElement(splitter,
                             configuration.getSplitters()).getEvents()
                                                          .get(event)
                                                          .getGenerate()
                                                          .size() > 0);

        if (!done && toLoad) {
          eventObjectClasses = event.getEventObjectClass();
          if ((eventObjectClasses == null) || (eventObjectClasses.length == 0)) {
            eventObject = null;
          } else {
            int           nbParam            = eventObjectClasses.length;
            StringBuilder eventObjectBuilder = new StringBuilder(nbParam * 70);

            int i;
            for (i = 0; i < (nbParam - 1); i++) {
              eventObjectBuilder.append("(");
              eventObjectBuilder.append(getAssociatedClass(eventObjectClasses[i]));
              eventObjectBuilder.append(") eventObjects[");
              eventObjectBuilder.append(i);
              eventObjectBuilder.append("],");
            }
            eventObjectBuilder.append("(");
            eventObjectBuilder.append(getAssociatedClass(eventObjectClasses[i]));
            eventObjectBuilder.append(") eventObjects[");
            eventObjectBuilder.append(i);
            eventObjectBuilder.append("]");
            eventObject = eventObjectBuilder.toString();
          }
          done = true;
        }

        if (toLoad) {
          sourceWriter.print("load");
          sourceWriter.print(splitter);
          sourceWriter.print("(\"");
          sourceWriter.print(event.getName());
          sourceWriter.print("\", new Mvp4gEventPasser(");
          if (form != null) {
            sourceWriter.print("new Object[]{");
            sourceWriter.print(form);
            sourceWriter.print("}");
          }
          sourceWriter.println("){");
          sourceWriter.indent();
          sourceWriter.println("public void pass(Mvp4gModule module){");
        } else {
          sourceWriter.print("if (");
          sourceWriter.print(splitter);
          sourceWriter.println(" != null ){");
        }
        sourceWriter.indent();
        sourceWriter.print(splitter);
        sourceWriter.print(".");
        sourceWriter.print(event.getType());
        sourceWriter.print("(");
        if (toLoad) {
          if (eventObject != null) {
            sourceWriter.print(eventObject);
          }
        } else {
          if ((form != null) && (form.length() > 0)) {
            sourceWriter.print(form);
          }
        }
        sourceWriter.println(");");
        sourceWriter.outdent();
        sourceWriter.println("}");
        if (toLoad) {
          sourceWriter.outdent();
          sourceWriter.println("});");
        }
      }
    }
  }

  private void writeSplitterClasses() {
    String[]                                    objectClasses;
    StringBuilder                               paramBuilder = null;
    StringBuilder                               constructorBuilder;
    Map<EventElement, EventAssociation<String>> events;
    int                                         nbParams;
    EventAssociation<String>                    eventAssociation;
    Set<EventHandlerElement>                    eventHandlers;
    String                                      splitterName, splitterClassName, handlerName, handlerClassName, constructor, loaderName;
    boolean                                     hasLoader;

    List<String> activate, deactivate;

    ChildModulesElement loadConfig = configuration.getLoadChildConfig();
    String              errorEvent, beforeEvent, afterEvent;
    boolean             isError, isBefore, isAfter;

    if (loadConfig == null) {
      errorEvent = null;
      beforeEvent = null;
      afterEvent = null;
      isError = false;
      isBefore = false;
      isAfter = false;
    } else {
      errorEvent = loadConfig.getErrorEvent();
      beforeEvent = loadConfig.getBeforeEvent();
      afterEvent = loadConfig.getAfterEvent();
      isError = (errorEvent != null) && (errorEvent.length() > 0);
      isBefore = (beforeEvent != null) && (beforeEvent.length() > 0);
      isAfter = (afterEvent != null) && (afterEvent.length() > 0);
    }
    String formError = null;
    if (isError) {
      String[] params = getElement(errorEvent,
                                   configuration.getEvents()).getEventObjectClass();
      if ((params != null) && (params.length > 0)) {
        formError = "reason";
      }
    }

    String  suffix          = configuration.getSuffix();
    boolean hasMultipleImpl = (suffix != null) && (suffix.length() > 0);

    Set<SplitterElement> splitters = configuration.getSplitters();
    String               asyncImpl = null, asyncMultipleCallback = null;
    for (SplitterElement splitter : splitters) {
      eventHandlers = splitter.getHandlers();
      //multipleHandlers = splitter.getMultipleHandlers();
      splitterName = splitter.getName();
      splitterClassName = splitter.getClassName();
      loaderName = splitter.getLoader();
      hasLoader = (loaderName != null);

      if (hasMultipleImpl) {
        asyncMultipleCallback = splitterClassName + "MultipleRunAsyncCallback" + suffix;
        sourceWriter.print("interface ");
        sourceWriter.print(asyncMultipleCallback);
        sourceWriter.print(" extends ");
        sourceWriter.print(RunAsyncCallback.class.getName());
        sourceWriter.println(" {}");

        asyncImpl = splitterClassName + "RunAsyncImpl" + suffix;
        sourceWriter.print("interface ");
        sourceWriter.print(asyncImpl);
        sourceWriter.print(" extends ");
        sourceWriter.print(Mvp4gRunAsync.class.getName());
        sourceWriter.print("<");
        sourceWriter.print(asyncMultipleCallback);
        sourceWriter.println("> {}");
      }

      sourceWriter.print("private ");
      sourceWriter.print(splitterClassName);
      sourceWriter.print(" ");
      sourceWriter.print(splitterName);
      sourceWriter.println(";");

      sourceWriter.print("private void load");
      sourceWriter.print(splitterName);
      sourceWriter.println("(final String eventName, final Mvp4gEventPasser passer) {");
      sourceWriter.indent();
      if (hasLoader) {
        sourceWriter.println("final Object[] params = (passer == null) ? null : passer.getEventObjects();");
        sourceWriter.print(loaderName);
        sourceWriter.println(".preLoad( eventBus, eventName, params, new Command(){");
        sourceWriter.indent();
        sourceWriter.println("public void execute() {");
        sourceWriter.indent();
      }

      if (isBefore) {
        writeDispatchEvent(beforeEvent,
                           null);
      }

      if (hasMultipleImpl) {
        sourceWriter.print("((");
        sourceWriter.print(asyncImpl);
        sourceWriter.print(") GWT.create(");
        sourceWriter.print(asyncImpl);
        sourceWriter.print(".class)).load(new ");
        sourceWriter.print(asyncMultipleCallback);
      } else {
        sourceWriter.print("GWT.runAsync(new ");
        sourceWriter.print(RunAsyncCallback.class.getSimpleName());
      }
      sourceWriter.println("(){ ");
      sourceWriter.indent();

      sourceWriter.println("public void onSuccess() { ");
      sourceWriter.indent();
      if (isAfter) {
        writeDispatchEvent(afterEvent,
                           null);
      }
      if (hasLoader) {
        sourceWriter.print(loaderName);
        sourceWriter.println(".onSuccess(eventBus, eventName, params );");
      }
      sourceWriter.print("if (");
      sourceWriter.print(splitterName);
      sourceWriter.println(" == null) {");
      sourceWriter.indent();
      sourceWriter.print(splitterName);
      sourceWriter.print(" = new ");
      sourceWriter.print(splitterClassName);
      sourceWriter.println("();");
      sourceWriter.outdent();
      sourceWriter.println("}");
      sourceWriter.println("passer.pass(null);");
      sourceWriter.outdent();
      sourceWriter.println("}");
      sourceWriter.println("public void onFailure( Throwable reason ) {");
      sourceWriter.indent();
      if (isAfter) {
        writeDispatchEvent(afterEvent,
                           null);
      }
      if (isError) {
        sourceWriter.indent();
        writeDispatchEvent(errorEvent,
                           formError);
        sourceWriter.outdent();
      }
      if (hasLoader) {
        sourceWriter.print(loaderName);
        sourceWriter.println(".onFailure( eventBus, eventName, params, reason );");
      }
      sourceWriter.outdent();
      sourceWriter.println("}");

      sourceWriter.outdent();
      sourceWriter.println("});");
      if (hasLoader) {
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.outdent();
        sourceWriter.println("});");
      }
      sourceWriter.outdent();
      sourceWriter.println("}");

      sourceWriter.print("public class ");
      sourceWriter.print(splitterClassName);
      sourceWriter.println(" { ");
      sourceWriter.indent();

      constructorBuilder = new StringBuilder(eventHandlers.size() * 100);
      for (EventHandlerElement eventHandler : eventHandlers) {
        handlerName = eventHandler.getName();
        handlerClassName = eventHandler.getClassName();
        if (!eventHandler.isMultiple()) {
          sourceWriter.print("private ");
          sourceWriter.print(handlerClassName);
          sourceWriter.print(" ");
          sourceWriter.print(handlerName);
          sourceWriter.println(";");
          constructorBuilder.append(handlerName)
                            .append(" = BaseEventBus.");
          boolean isPresenter = eventHandler instanceof PresenterElement;
          if (isPresenter) {
            PresenterElement presenter = (PresenterElement) eventHandler;
            constructorBuilder.append("setPresenter( ");
            constructorBuilder.append(Boolean.toString(presenter.hasInverseView()));
            constructorBuilder.append(", injector.get");
            constructorBuilder.append(handlerName);
            constructorBuilder.append("(), injector.get");
            constructorBuilder.append(presenter.getView());
          } else {
            constructorBuilder.append("setEventHandler( injector.get");
            constructorBuilder.append(handlerName);
          }
          constructorBuilder.append("(), eventBus);")
                            .append('\n');
          for (InjectedElement service : eventHandler.getInjectedServices()) {
            sourceWriter.print(eventHandler.getName());
            sourceWriter.println("." + service.getSetterName() + "(" + service.getElementName() + ");");
          }
        }
      }

      constructor = constructorBuilder.toString();
      if (constructor.length() > 0) {
        sourceWriter.print("public ");
        sourceWriter.print(splitterClassName);
        sourceWriter.println("(){");
        sourceWriter.print(constructor);
        sourceWriter.println("}");
      }

      events = splitter.getEvents();
      for (EventElement event : events.keySet()) {
        eventAssociation = events.get(event);
        activate = eventAssociation.getActivated();
        deactivate = eventAssociation.getDeactivated();

        sourceWriter.print("public void ");
        sourceWriter.print(event.getType());
        sourceWriter.print("(");
        objectClasses = event.getEventObjectClass();
        if ((objectClasses != null) && ((nbParams = objectClasses.length) > 0)) {
          paramBuilder = new StringBuilder(20 * nbParams);
          int i;
          for (i = 0; i < (nbParams - 1); i++) {
            sourceWriter.print(objectClasses[i]);
            sourceWriter.print(" attr");
            sourceWriter.print(Integer.toString(i));
            sourceWriter.print(",");
            paramBuilder.append("attr");
            paramBuilder.append(i);
            paramBuilder.append(",");
          }
          sourceWriter.print(objectClasses[i]);
          sourceWriter.print(" attr");
          sourceWriter.print(Integer.toString(i));
          paramBuilder.append("attr");
          paramBuilder.append(i);

        } else {
          paramBuilder = null;
        }
        sourceWriter.println("){");
        sourceWriter.indent();

        if ((activate != null) && (activate.size() > 0)) {
          writeActivation(activate,
                          eventHandlers,
                          true,
                          false);
        }
        if ((deactivate != null) && (deactivate.size() > 0)) {
          writeActivation(deactivate,
                          eventHandlers,
                          false,
                          false);
        }

        writeEventAction(event,
                         eventAssociation.getBinds(),
                         eventAssociation.getHandlers(),
                         eventAssociation.getGenerate(),
                         eventHandlers,
                         (paramBuilder == null) ?
                         null :
                         paramBuilder.toString(),
                         false);

        sourceWriter.outdent();
        sourceWriter.println("}");

      }

      sourceWriter.outdent();
      sourceWriter.println("}");
    }
  }

  private void writeLoaders(boolean forInstantion) {
    Set<LoaderElement> loaders = configuration.getLoaders();
    for (LoaderElement loader : loaders) {
      if (!forInstantion) {
        sourceWriter.print(loader.getClassName());
        sourceWriter.print(" ");
      }
      sourceWriter.print(loader.getName());
      if (forInstantion) {
        sourceWriter.print(" = ");
        sourceWriter.print("injector.get");
        sourceWriter.print(loader.getName());
        sourceWriter.print("()");
      }
      sourceWriter.println(";");
    }
  }

  /**
   * Retrieve an element exists in a set thanks to its unique identifier
   *
   * @param <T>
   *   type of the elements in the set
   * @param elementName
   *   value of the unique identifier of the element to find
   * @param elements
   *   set of elemets
   *
   * @return found element
   */
  private <T extends Mvp4gElement> T getElement(String elementName,
                                                Set<T> elements) {
    T eFound = null;
    for (T element : elements) {
      if (element.getUniqueIdentifier()
                 .equals(elementName)) {
        eFound = element;
        break;
      }
    }

    return eFound;
  }

  private void writeDispatchEvent(String eventType,
                                  String form) {
    sourceWriter.print("eventBus.");
    sourceWriter.print(eventType);
    sourceWriter.print("(");
    if ((form != null) && (form.length() > 0)) {
      sourceWriter.print(form);
    }
    sourceWriter.println(");");
  }

  private void writeHistoryConnection() {
    sourceWriter.println("public void addConverter(String historyName, HistoryConverter<?> hc){");
    sourceWriter.indent();
    if (!configuration.isRootModule()) {
      String historyName = configuration.getHistoryName();
      if (historyName != null) {
        sourceWriter.print("parentModule.addConverter(\"");
        sourceWriter.print(historyName);
        sourceWriter.print(PlaceService.MODULE_SEPARATOR);
        sourceWriter.println("\" + historyName, hc);");
      }
    } else {
      sourceWriter.println("placeService.addConverter(historyName, hc);");
    }
    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.println("public void clearHistory(){");
    sourceWriter.indent();
    if (!configuration.isRootModule()) {
      String historyName = configuration.getHistoryName();
      if (historyName != null) {
        sourceWriter.println("parentModule.clearHistory();");
      }
    } else {
      sourceWriter.println("placeService.clearHistory();");
    }
    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.println("public String place(String token, String form, boolean onlyToken){");
    sourceWriter.indent();
    if (!configuration.isRootModule()) {
      String historyName = configuration.getHistoryName();
      if (historyName != null) {
        sourceWriter.print("return parentModule.place(\"");
        sourceWriter.print(historyName);
        sourceWriter.print(PlaceService.MODULE_SEPARATOR);
        sourceWriter.println("\" + token, form, onlyToken );");
      } else {
        sourceWriter.println("throw new Mvp4gException(\"This method shouldn't be called. There is no history support for this module.\");");
      }
    } else {
      sourceWriter.println("return placeService.place( token, form, onlyToken );");
    }
    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.println("public void dispatchHistoryEvent(String eventType, final Mvp4gEventPasser passer){");
    sourceWriter.indent();
    sourceWriter.println("int index = eventType.indexOf(PlaceService.MODULE_SEPARATOR);");
    sourceWriter.println("if(index > -1){");
    sourceWriter.indent();
    sourceWriter.println("String moduleHistoryName = eventType.substring(0, index);");
    sourceWriter.println("String nextToken = eventType.substring(index + 1);");
    sourceWriter.println("Mvp4gEventPasser nextPasser = new Mvp4gEventPasser(nextToken) {");
    sourceWriter.indent();
    sourceWriter.println("public void pass(Mvp4gModule module) {");
    sourceWriter.indent();
    sourceWriter.println("module.dispatchHistoryEvent((String) eventObjects[0], passer);");
    sourceWriter.outdent();
    sourceWriter.println("}");
    sourceWriter.outdent();
    sourceWriter.println("};");

    String historyName;
    for (ChildModuleElement child : configuration.getChildModules()) {
      historyName = child.getHistoryName();
      if ((historyName != null) && (historyName.length() > 0)) {
        sourceWriter.print("if(\"");
        sourceWriter.print(historyName);
        sourceWriter.println("\".equals(moduleHistoryName)){");
        sourceWriter.indent();
        sourceWriter.print("load");
        sourceWriter.print(child.getName());
        sourceWriter.println("(null, nextPasser);");
        sourceWriter.println("return;");
        sourceWriter.outdent();
        sourceWriter.println("}");
      }
    }

    sourceWriter.println("passer.setEventObject(false);");
    sourceWriter.println("passer.pass(this);");

    sourceWriter.outdent();
    sourceWriter.println("}else{");
    sourceWriter.indent();
    sourceWriter.println("passer.pass(this);");
    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.outdent();
    sourceWriter.println("}");

    HistoryElement history                 = configuration.getHistory();
    boolean        hasHistoryConfiguration = configuration.isRootModule() && (history != null);
    sourceWriter.println("public void sendInitEvent(){");
    sourceWriter.indent();
    if (hasHistoryConfiguration) {
      sourceWriter.print("eventBus.");
      sourceWriter.print(history.getInitEvent());
      sourceWriter.println("();");
    }
    sourceWriter.outdent();
    sourceWriter.println("}");
    sourceWriter.outdent();
    sourceWriter.indent();
    sourceWriter.println("public void sendNotFoundEvent(){");
    sourceWriter.indent();
    if (hasHistoryConfiguration) {
      String event = history.getNotFoundEvent();
      if (event == null) {
        event = history.getInitEvent();
      }
      sourceWriter.print("eventBus.");
      sourceWriter.print(event);
      sourceWriter.println("();");
    }
    sourceWriter.outdent();
    sourceWriter.println("}");
  }

  private void writeLog(String beforeText,
                        String type,
                        String[] objectClasses) {
    DebugElement debug = configuration.getDebug();
    if (debug != null) {
      sourceWriter.print("logger.log(\"");
      sourceWriter.print(beforeText);
      sourceWriter.print("Module: ");
      sourceWriter.print(configuration.getModule()
                                      .getSimpleSourceName());
      sourceWriter.print(" || event: ");
      sourceWriter.print(type);
      int nbClasses = (objectClasses == null) ?
                      0 :
                      objectClasses.length;
      if (nbClasses > 0) {
        sourceWriter.print(" || param(s): \" + attr0");
        for (int i = 1; i < nbClasses; i++) {
          sourceWriter.print("+ \", \" + attr");
          sourceWriter.print(Integer.toString(i));
        }

      } else {
        sourceWriter.print("\"");
      }
      sourceWriter.println(", BaseEventBus.logDepth);");
    }
  }

  private void writeDetailedLog(String handler,
                                String eventType,
                                boolean isBind) {
    DebugElement debug = configuration.getDebug();

    if (debug != null &&
        debug.getLogLevel()
             .equals(LogLevel.DETAILED.name())) {
      sourceWriter.print("logger.log(");
      sourceWriter.print(handler);
      if (isBind) {
        sourceWriter.print(".toString() + \" binds ");
      } else {
        sourceWriter.print(".toString() + \" handles ");
      }
      sourceWriter.print(eventType);
      sourceWriter.println("\", BaseEventBus.logDepth);");
    }
  }

  private void writeEventFiltersLog(String type) {
    DebugElement debug = configuration.getDebug();

    if (debug != null &&
        debug.getLogLevel()
             .equals(LogLevel.DETAILED.name())) {
      sourceWriter.print("logger.log(\"event ");
      sourceWriter.print(type);
      sourceWriter.println(" didn't pass filter(s)\", BaseEventBus.logDepth);");
    }
  }

  private String getAssociatedClass(String param) {
    String paramClass;
    if ("boolean".equals(param)) {
      paramClass = "Boolean";
    } else if ("byte".equals(param)) {
      paramClass = "Byte";
    } else if ("char".equals(param)) {
      paramClass = "Character";
    } else if ("double".equals(param)) {
      paramClass = "Double";
    } else if ("float".equals(param)) {
      paramClass = "Float";
    } else if ("int".equals(param)) {
      paramClass = "Integer";
    } else if ("long".equals(param)) {
      paramClass = "Long";
    } else if ("short".equals(param)) {
      paramClass = "Short";
    } else if ("void".equals(param)) {
      paramClass = "Void";
    } else {
      paramClass = param;
    }
    return paramClass;
  }

  private String getGinjectorClassName() {
    return configuration.getModule()
                        .getQualifiedSourceName()
                        .replace(".",
                                 "_") + "Ginjector";
  }
}
