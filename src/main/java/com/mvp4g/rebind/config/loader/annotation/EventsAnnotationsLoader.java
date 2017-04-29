/*
 * Copyright (c) 2009 - 2017 - Pierre-Laurent Coirer, Frank Hossfeld
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
package com.mvp4g.rebind.config.loader.annotation;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.inject.client.GinModule;
import com.mvp4g.client.annotation.*;
import com.mvp4g.client.annotation.module.*;
import com.mvp4g.client.event.*;
import com.mvp4g.client.presenter.NoStartPresenter;
import com.mvp4g.rebind.config.Mvp4gConfiguration;
import com.mvp4g.rebind.config.element.*;
import com.mvp4g.rebind.exception.loader.Mvp4gAnnotationException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A class responsible for loading information contained in <code>Events</code> annotation.
 *
 * @author plcoirier
 */
public class EventsAnnotationsLoader
  extends Mvp4gAnnotationsLoader<Events> {

  /*
   * (non-Javadoc)
   *
   * @seecom.mvp4g.rebind.config.loader.annotation.Mvp4gAnnotationsLoader#
   * getMandatoryInterfaceName()
   */
  @Override
  protected String getMandatoryInterfaceName() {
    return EventBus.class.getCanonicalName();
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.rebind.config.loader.annotation.Mvp4gAnnotationsLoader#controlType
   * (com.google.gwt .core.ext.typeinfo.JClassType, com.google.gwt.core.ext.typeinfo.JClassType)
   */
  @Override
  protected void controlType(JClassType c,
                             JClassType s) {
    // do nothing, control are done later.
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.rebind.config.loader.annotation.Mvp4gAnnotationsLoader#loadElement
   * (com.google.gwt .core.ext.typeinfo.JClassType, java.lang.annotation.Annotation,
   * com.mvp4g.rebind.config.Mvp4gConfiguration)
   */
  @Override
  protected void loadElement(JClassType c,
                             Events annotation,
                             Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {

    if (annotation.module()
                  .getCanonicalName()
                  .equals(configuration.getModule()
                                       .getQualifiedSourceName())) {

      if (configuration.getEventBus() != null) {
        String err = "You can define only one event bus by Mvp4g module. Do you already have another EventBus interface for the module " +
                     annotation.module()
                               .getCanonicalName() +
                     "?";
        throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                           null,
                                           err);
      }

      if (c.isInterface() == null) {
        String err = Events.class.getSimpleName() + " annotation can only be used on interfaces.";
        throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                           null,
                                           err);
      }

      EventBusElement eventBus = buildEventBusElement(c,
                                                      configuration);

      if (eventBus != null) {
        configuration.setEventBus(eventBus);
        loadEventFilters(c,
                         annotation,
                         configuration);
        loadChildModules(c,
                         annotation,
                         configuration);
        loadStartView(c,
                      annotation,
                      configuration);
        loadEvents(c,
                   annotation,
                   configuration);
        loadDebug(c,
                  annotation,
                  configuration);
        loadGinModule(annotation,
                      configuration);
        loadHistoryProxy(annotation,
                         configuration);
        loadHistoryConfiguration(c,
                                 configuration);
      } else {
        String err = "this class must implement " + EventBus.class.getCanonicalName() + " since it is annoted with " + Events.class.getSimpleName() + ".";
        throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                           null,
                                           err);
      }
    } else {
      // save event bus type of potentiel child module
      configuration.getOthersEventBusClassMap()
                   .put(annotation.module()
                                  .getCanonicalName(),
                        c);
      ChildModules children = c.getAnnotation(ChildModules.class);
      if (children != null) {
        Map<String, ChildModuleElement> moduleParentEventBus = configuration.getModuleParentEventBusClassMap();
        ChildModuleElement childElement;
        String childClass;
        for (ChildModule child : children.value()) {
          childClass = child.moduleClass()
                            .getCanonicalName();
          if (moduleParentEventBus.containsKey(childClass)) {
            String err = "Module " + childClass + "can only have 1 parent.";
            throw new Mvp4gAnnotationException(childClass,
                                               null,
                                               err);
          } else {
            childElement = new ChildModuleElement();
            childElement.setClassName(childClass);
            childElement.setParentEventBus(c);
            childElement.setParentModuleClass(annotation.module()
                                                        .getCanonicalName());
            childElement.setAutoDisplay(Boolean.toString(child.autoDisplay()));
            moduleParentEventBus.put(childClass,
                                     childElement);
          }
        }
      }
    }
  }

  /**
   * Build event bus element according to the implemented interface.
   *
   * @param c             annoted class type
   * @param configuration configuration containing loaded elements of the application
   * @return event bus corresponding to the implemented interface (null if none of the interfaces
   * are implemented)
   */
  private EventBusElement buildEventBusElement(JClassType c,
                                               Mvp4gConfiguration configuration) {

    TypeOracle oracle = configuration.getOracle();

    EventBusElement eventBus = null;
    if (c.isAssignableTo(oracle.findType(EventBusWithLookup.class.getCanonicalName()))) {
      eventBus = new EventBusElement(c.getQualifiedSourceName(),
                                     BaseEventBusWithLookUp.class.getCanonicalName(),
                                     true);
    } else if (c.isAssignableTo(oracle.findType(EventBus.class.getCanonicalName()))) {
      eventBus = new EventBusElement(c.getQualifiedSourceName(),
                                     BaseEventBus.class.getCanonicalName(),
                                     false);
    }

    return eventBus;
  }

  private void loadEventFilters(JClassType c,
                                Events annotation,
                                Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {
    Filters filters = c.getAnnotation(Filters.class);
    if (filters != null) {
      boolean forceFilters = filters.forceFilters();
      Class<? extends EventFilter<?>>[] filterClasses = filters.filterClasses();
      if (((filterClasses == null) || (filterClasses.length == 0)) && !forceFilters) {
        String err = "Useless " +
                     Filters.class.getSimpleName() +
                     " annotation. Don't use this annotation if your module doesn't have any event filters. If you plan on adding filters when the application runs, set the forceFilters option to true.";
        throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                           null,
                                           err);
      }

      Set<EventFilterElement> filterElements = configuration.getEventFilters();
      String filterClassName = null;
      EventFilterElement filterElement = null;
      for (Class<? extends EventFilter<?>> filterClass : filterClasses) {
        filterClassName = filterClass.getCanonicalName();
        if (getElementName(filterElements,
                           filterClassName) != null) {
          String err = "Multiple definitions for event filter " + filterClassName + ".";
          throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                             null,
                                             err);
        }
        filterElement = new EventFilterElement();
        filterElement.setName(buildElementName(filterClassName,
                                               ""));
        filterElement.setClassName(filterClassName);
        addElement(filterElements,
                   filterElement,
                   c,
                   null);
      }

      EventFiltersElement filtersElement = new EventFiltersElement();
      filtersElement.setAfterHistory(Boolean.toString(filters.afterHistory()));
      filtersElement.setFilterForward(Boolean.toString(filters.filterForward()));
      filtersElement.setFilterStart(Boolean.toString(filters.filterStart()));
      filtersElement.setForceFilters(Boolean.toString(filters.forceFilters()));
      configuration.setEventFilterConfiguration(filtersElement);
    }
  }

  private void loadChildModules(JClassType c,
                                Events annotation,
                                Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {
    ChildModules childModules = c.getAnnotation(ChildModules.class);
    if (childModules != null) {
      ChildModule[] children = childModules.value();
      if ((children == null) || (children.length == 0)) {
        String err = "Useless " + ChildModules.class.getSimpleName() + " annotation. Don't use this annotation if your module doesn't have any child module.";
        throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                           null,
                                           err);
      }

      Set<ChildModuleElement> modules = configuration.getChildModules();
      String moduleClass = null;
      ChildModuleElement module = null;
      for (ChildModule child : children) {
        moduleClass = child.moduleClass()
                           .getCanonicalName();
        if (getElementName(modules,
                           moduleClass) != null) {
          String err = "You can't have two child modules describing the same module: " +
                       child.moduleClass()
                            .getCanonicalName();
          throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                             null,
                                             err);
        }
        module = new ChildModuleElement();
        module.setName(buildElementName(moduleClass,
                                        ""));
        module.setClassName(moduleClass);
        module.setAsync(Boolean.toString(child.async()));
        module.setAutoDisplay(Boolean.toString(child.autoDisplay()));

        addElement(modules,
                   module,
                   c,
                   null);

      }
    }

  }

  /**
   * Load information about the start view
   *
   * @param c             annoted class
   * @param annotation    Events annotation of the class
   * @param configuration configuration containing loaded elements of the application
   * @throws Mvp4gAnnotationException if no view with the given class and name exist
   */
  private void loadStartView(JClassType c,
                             Events annotation,
                             Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {

    Set<PresenterElement> presenters = configuration.getPresenters();
    String presenterName = annotation.startPresenterName();
    Class<?> presenterClass = annotation.startPresenter();
    boolean hasStartPresenter = !NoStartPresenter.class.equals(presenterClass);
    if (hasStartPresenter) {
      if ((presenterName != null) && (presenterName.length() > 0)) {
        boolean found = false;
        for (PresenterElement presenter : presenters) {
          if (presenterName.equals(presenter.getName())) {
            TypeOracle oracle = configuration.getOracle();
            JClassType presenterType = oracle.findType(presenter.getClassName());
            JClassType startType = oracle.findType(presenterClass.getCanonicalName());
            if (!presenterType.isAssignableTo(startType)) {
              String err = "There is no instance with name " + presenterName + " that extends " + presenterType;
              throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                                 null,
                                                 err);
            }
            found = true;
            break;
          }
        }
        if (!found) {
          String err = "There is no presenter named " + presenterName;
          throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                             null,
                                             err);
        }

      } else {
        presenterName = getElementName(presenters,
                                       presenterClass.getCanonicalName());
        if (presenterName == null) {
          String err = "There is no instance of " + presenterClass.getCanonicalName() + ". Have you forgotten to annotate it with @Presenter or @EventHander?";
          throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                             null,
                                             err);
        }
      }
    }

    StartElement element = new StartElement();
    if (hasStartPresenter) {
      element.setPresenter(presenterName);
    }
    element.setHistory(Boolean.toString(annotation.historyOnStart()));
    configuration.setStart(element);
  }

  /**
   * Load events defined by this class
   *
   * @param c             annoted class
   * @param annotation    annotation of the class
   * @param configuration configuration containing loaded elements of the application
   * @throws Mvp4gAnnotationException if events are properly described
   */
  private void loadEvents(JClassType c,
                          Events annotation,
                          Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {

    Event event = null;
    EventElement element = null;

    Set<EventElement> events = configuration.getEvents();

    JParameter[] params = null;

    JClassType eventBusWithLookupType = configuration.getOracle()
                                                     .findType(EventBusWithLookup.class.getCanonicalName());
    JClassType eventBusType = configuration.getOracle()
                                           .findType(EventBus.class.getCanonicalName());
    JClassType enclosingType = null;
    String historyName;
    Class<?> broadcast;
    for (JMethod method : c.getOverridableMethods()) {
      event = method.getAnnotation(Event.class);
      if (event == null) {
        enclosingType = method.getEnclosingType();
        if (!(eventBusType.equals(enclosingType) || (eventBusWithLookupType.equals(enclosingType)))) {
          String err = Event.class.getSimpleName() + " annotation missing.";
          throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                             method.getName(),
                                             err);
        }
        //in this case, it's a method by Mvp4g EventBus interface, no need to create an event
        continue;
      }

      params = method.getParameters();
      int nbParams = params.length;
      String[] paramClasses;
      if (nbParams > 0) {
        paramClasses = new String[nbParams];
        for (int i = 0; i < nbParams; i++) {
          paramClasses[i] = params[i].getType()
                                     .getQualifiedSourceName();
        }
      } else {
        paramClasses = null;
      }

      historyName = event.name();

      element = new EventElement();

      element.setType(method.getName());
      element.setHandlers(buildPresentersAndEventHandlers(c,
                                                          method,
                                                          event.handlers(),
                                                          event.handlerNames(),
                                                          configuration));
      element.setBinds(buildPresentersAndEventHandlers(c,
                                                       method,
                                                       event.bind(),
                                                       event.bindNames(),
                                                       configuration));
      element.setCalledMethod(event.calledMethod());
      element.setForwardToModules(buildChildModules(c,
                                                    method,
                                                    event,
                                                    configuration));
      element.setForwardToParent(Boolean.toString(event.forwardToParent()));
      element.setActivate(buildPresentersAndEventHandlers(c,
                                                          method,
                                                          event.activate(),
                                                          event.activateNames(),
                                                          configuration));
      element.setDeactivate(buildPresentersAndEventHandlers(c,
                                                            method,
                                                            event.deactivate(),
                                                            event.deactivateNames(),
                                                            configuration));
      element.setGenerate(buildPresentersAndEventHandlers(c,
                                                          method,
                                                          event.generate(),
                                                          event.generateNames(),
                                                          configuration));
      element.setNavigationEvent(Boolean.toString(event.navigationEvent()));
      element.setWithTokenGeneration(Boolean.toString(method.getReturnType()
                                                            .getQualifiedSourceName()
                                                            .equals(String.class.getName())));
      element.setPassive(Boolean.toString(event.passive()));
      broadcast = event.broadcastTo();
      if (!Event.NoBroadcast.class.equals(broadcast)) {
        element.setBroadcastTo(broadcast.getCanonicalName());
      }
      if (paramClasses != null) {
        element.setEventObjectClass(paramClasses);
      }
      if (!Event.DEFAULT_NAME.equals(historyName)) {
        element.setName(historyName);
      }

      addElement(events,
                 element,
                 c,
                 method);

      if (method.getAnnotation(Start.class) != null) {
        if (configuration.getStart()
                         .getEventType() == null) {
          configuration.getStart()
                       .setEventType(method.getName());
        } else {
          String err = "Duplicate value for Start event. It is already defined by another method.";
          throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                             method.getName(),
                                             err);
        }
      }
      if (method.getAnnotation(Forward.class) != null) {
        if (configuration.getStart()
                         .getForwardEventType() == null) {
          configuration.getStart()
                       .setForwardEventType(method.getName());
        } else {
          String err = "Duplicate value for Forward event. It is already defined by another method.";
          throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                             method.getName(),
                                             err);
        }
      }
      loadHistoryEvent(c,
                       method,
                       configuration);
      loadHistory(c,
                  method,
                  event,
                  element,
                  configuration);
      loadEventToLoadChildModuleView(c,
                                     method,
                                     configuration);
      loadChildConfig(c,
                      method,
                      configuration);

    }

  }

  private void loadDebug(JClassType c,
                         Events annotation,
                         Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {
    Debug debug = c.getAnnotation(Debug.class);

    if (debug != null) {
      Class<? extends Mvp4gLogger> loggerClass = debug.logger();
      DebugElement debugElem = new DebugElement();
      debugElem.setLogger(loggerClass.getCanonicalName());
      debugElem.setLogLevel(debug.logLevel()
                                 .name());

      configuration.setDebug(debugElem);
    }
  }

  private void loadGinModule(Events annotation,
                             Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {
    GinModuleElement ginModule = new GinModuleElement();
    Class<? extends GinModule>[] modules = annotation.ginModules();
    int modulesCount = modules.length;
    String[] modulesClassNames = new String[modules.length];
    for (int i = 0; i < modulesCount; i++) {
      modulesClassNames[i] = modules[i].getCanonicalName();
    }
    ginModule.setModules(modulesClassNames);
    ginModule.setModuleProperties(annotation.ginModuleProperties());

    configuration.setGinModule(ginModule);
  }

  private void loadHistoryProxy(Events annotation,
                                Mvp4gConfiguration configuration) {
    HistoryProxyElement historyProxy = configuration.getHistoryProxy();
    if (historyProxy == null) {
      historyProxy = new HistoryProxyElement();
      configuration.setHistoryProxy(historyProxy);
    }
    historyProxy.setHistoryProxyClass(annotation.historyProxy()
                                                .getCanonicalName());
  }

  private void loadHistoryConfiguration(JClassType c,
                                        Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {

    PlaceService historyConfig = c.getAnnotation(PlaceService.class);
    if (historyConfig != null) {
      HistoryElement history = configuration.getHistory();
      if (history == null) {
        history = new HistoryElement();
        configuration.setHistory(history);
      }

      history.setPlaceServiceClass(historyConfig.value()
                                                .getCanonicalName());
    }
  }

  /**
   * Build handler of the events. If the class name of the handler is given, try to find if an
   * instance of this class exists, otherwise throw an error.
   *
   * @param c             annoted class
   * @param method        method that defines the event
   * @param event         Event Annotation of the method
   * @param configuration configuration containing loaded elements of the application
   * @return array of handlers' names
   * @throws Mvp4gAnnotationException if no instance of a given handler class can be found
   */
  private String[] buildPresentersAndEventHandlers(JClassType c,
                                                   JMethod method,
                                                   Class<? extends EventHandlerInterface<? extends EventBus>>[] presenterAndEventHandlerClasses,
                                                   String[] presenterAndEventHandlerNames,
                                                   Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {

    Set<EventHandlerElement> presentersAndEventHandlers = new HashSet<EventHandlerElement>(configuration.getPresenters());
    presentersAndEventHandlers.addAll(configuration.getEventHandlers());
    String[] handlers = new String[presenterAndEventHandlerNames.length + presenterAndEventHandlerClasses.length];

    String handlerName = null;
    int index = 0;
    for (Class<?> handler : presenterAndEventHandlerClasses) {
      handlerName = getElementName(presentersAndEventHandlers,
                                   handler.getCanonicalName());
      if (handlerName == null) {
        String err = "No instance of " + handler.getCanonicalName() + " is defined. Have you forgotten to annotate your event handler with @Presenter or @EventHandler?";
        throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                           method.getName(),
                                           err);
      }
      handlers[index] = handlerName;
      index++;
    }

    for (String h : presenterAndEventHandlerNames) {
      handlers[index] = h;
      index++;
    }

    return handlers;
  }

  private String[] buildChildModules(JClassType c,
                                     JMethod method,
                                     Event event,
                                     Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {
    Set<ChildModuleElement> loadedChildModules = configuration.getChildModules();

    Class<?>[] childModuleClasses = event.forwardToModules();
    String[] childModules = new String[childModuleClasses.length];

    String moduleName = null;
    int index = 0;
    for (Class<?> moduleClass : childModuleClasses) {
      moduleName = getElementName(loadedChildModules,
                                  moduleClass.getCanonicalName());
      //it's not a child module, could be a siblings, in this case name == class name
      if (moduleName == null) {
        moduleName = moduleClass.getCanonicalName();
      }
      childModules[index] = moduleName;
      index++;
    }

    return childModules;
  }

  private void loadHistoryEvent(JClassType c,
                                JMethod method,
                                Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {
    if (method.getAnnotation(InitHistory.class) != null) {
      HistoryElement history = configuration.getHistory();
      if (history == null) {
        history = new HistoryElement();
        configuration.setHistory(history);
      }

      if (history.getInitEvent() == null) {
        history.setInitEvent(method.getName());
      } else {
        String err = "Duplicate value for Init History event. It is already defined by another method or in your configuration file.";
        throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                           method.getName(),
                                           err);
      }
    }
    if (method.getAnnotation(NotFoundHistory.class) != null) {
      HistoryElement history = configuration.getHistory();
      if (history == null) {
        history = new HistoryElement();
        configuration.setHistory(history);
      }

      if (history.getNotFoundEvent() == null) {
        history.setNotFoundEvent(method.getName());
      } else {
        String err = "Duplicate value for Not Found History event. It is already defined by another method or in your configuration file.";
        throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                           method.getName(),
                                           err);
      }
    }
  }

  /**
   * Build history converter of an event. If the converter class name is given, first it tries to
   * find an instance of this class, and if none is found, create one.
   *
   * @param c             annoted class
   * @param method        method that defines the event
   * @param annotation    Event annotation
   * @param element       Event element
   * @param configuration configuration containing loaded elements of the application
   * @throws Mvp4gAnnotationException
   */
  private void loadHistory(JClassType c,
                           JMethod method,
                           Event annotation,
                           EventElement element,
                           Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {
    String hcName = annotation.historyConverterName();
    Class<?> hcClass = annotation.historyConverter();
    if ((hcName != null) && (hcName.length() > 0)) {
      element.setHistory(hcName);
    } else if (!Event.NoHistoryConverter.class.equals(hcClass)) {
      String hcClassName = hcClass.getCanonicalName();
      Set<HistoryConverterElement> historyConverters = configuration.getHistoryConverters();
      hcName = getElementName(historyConverters,
                              hcClassName);
      if (hcName == null) {
        String err = "No instance of " + hcClassName + " is defined. Have you forgotten to annotate your history converter with @History?";
        throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                           method.getName(),
                                           err);
      }
      element.setHistory(hcName);
    }
  }

  private void loadEventToLoadChildModuleView(JClassType c,
                                              JMethod method,
                                              Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {
    DisplayChildModuleView annotation = method.getAnnotation(DisplayChildModuleView.class);
    if (annotation != null) {
      ChildModuleElement module = null;
      Set<ChildModuleElement> childModules = configuration.getChildModules();
      for (Class<?> moduleClass : annotation.value()) {
        module = getElement(childModules,
                            moduleClass.getCanonicalName());
        if (module == null) {
          String err = "No instance of " + moduleClass.getCanonicalName() + " is defined.  Have you forgotten to add it to @ChildModules of your event bus interface?";
          throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                             method.getName(),
                                             err);
        }
        if (module.getEventToDisplayView() == null) {
          module.setEventToDisplayView(method.getName());
        } else {
          String err = "Module " + module.getClassName() + ": you can't have two events to load this module view.";
          throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                             method.getName(),
                                             err);
        }

      }
    }
  }

  private void loadChildConfig(JClassType c,
                               JMethod method,
                               Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {
    if (method.getAnnotation(BeforeLoadChildModule.class) != null) {
      ChildModulesElement childConfig = configuration.getLoadChildConfig();
      if (childConfig == null) {
        childConfig = new ChildModulesElement();
        configuration.setLoadChildConfig(childConfig);
      }
      if (childConfig.getBeforeEvent() == null) {
        childConfig.setBeforeEvent(method.getName());
      } else {
        String err = "Duplicate value for Before Load Child event. It is already defined by another method or in your configuration file.";
        throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                           method.getName(),
                                           err);
      }
    }
    if (method.getAnnotation(AfterLoadChildModule.class) != null) {
      ChildModulesElement childConfig = configuration.getLoadChildConfig();
      if (childConfig == null) {
        childConfig = new ChildModulesElement();
        configuration.setLoadChildConfig(childConfig);
      }
      if (childConfig.getAfterEvent() == null) {
        childConfig.setAfterEvent(method.getName());
      } else {
        String err = "Duplicate value for After Load Child event. It is already defined by another method or in your configuration file.";
        throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                           method.getName(),
                                           err);
      }
    }
    if (method.getAnnotation(LoadChildModuleError.class) != null) {
      ChildModulesElement childConfig = configuration.getLoadChildConfig();
      if (childConfig == null) {
        childConfig = new ChildModulesElement();
        configuration.setLoadChildConfig(childConfig);
      }
      if (childConfig.getErrorEvent() == null) {
        childConfig.setErrorEvent(method.getName());
      } else {
        String err = "Duplicate value for Error Load Child event. It is already defined by another method or in your configuration file.";
        throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                           method.getName(),
                                           err);
      }
    }

  }

}
