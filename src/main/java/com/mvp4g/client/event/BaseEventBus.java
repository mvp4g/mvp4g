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
package com.mvp4g.client.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mvp4g.client.Mvp4gException;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.history.HistoryProxy;
import com.mvp4g.client.history.HistoryProxyProvider;
import com.mvp4g.client.presenter.PresenterInterface;
import com.mvp4g.client.view.ReverseViewInterface;

/**
 * Base implementation of the event bus. It should only be used by the framework.
 *
 * @author plcoirier
 */
public abstract class BaseEventBus
  implements EventBus {

  public static int                                           logDepth                         = -1;
  public        boolean                                       tokenMode                        = false;
  private       boolean                                       historyStored                    = true;
  private       boolean                                       changeHistoryStoredForNextOne    = false;
  private       boolean                                       filteringEnabled                 = true;
  private       boolean                                       changeFilteringEnabledForNextOne = false;
  private       Map<Class<?>, List<EventHandlerInterface<?>>> handlersMap                      = new HashMap<Class<?>, List<EventHandlerInterface<?>>>();

  private List<EventFilter<?>> filters = new ArrayList<EventFilter<? extends EventBus>>();

  @SuppressWarnings("unchecked")
  public static <V, E extends EventBus, P extends PresenterInterface<? super V, ? super E>> P setPresenter(boolean reverseView,
                                                                                                           P presenter,
                                                                                                           V view,
                                                                                                           E eventBus) {
    setEventHandler(presenter,
                    eventBus);
    presenter.setView(view);
    if (reverseView) {
      ((ReverseViewInterface<P>) view).setPresenter(presenter);
    }
    return presenter;
  }

  public static <E extends EventBus, H extends EventHandlerInterface<? super E>> H setEventHandler(H eventHandler,
                                                                                                   E eventBus) {
    eventHandler.setEventBus(eventBus);
    return eventHandler;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventBus#setHistoryStoredForNextOne(boolean)
   */
  public void setHistoryStoredForNextOne(boolean historyStored) {
    if (historyStored != this.historyStored) {
      changeHistoryStoredForNextOne = true;
      this.historyStored = historyStored;
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventBus#isHistoryStored()
   */
  public boolean isHistoryStored() {
    return historyStored;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventBus#setHistoryStored(boolean)
   */
  public void setHistoryStored(boolean historyStored) {
    this.historyStored = historyStored;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventBus#setFilterEnabledForNextOne(boolean)
   */
  public void setFilteringEnabledForNextOne(boolean filteringEnabled) {
    if (filteringEnabled != this.filteringEnabled) {
      changeFilteringEnabledForNextOne = true;
      this.filteringEnabled = filteringEnabled;
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventBus#isFilterEnabled(boolean)
   */
  public boolean isFilteringEnabled() {
    return filteringEnabled;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventBus#setFilterEnabled(boolean)
   */
  public void setFilteringEnabled(boolean filteringEnabled) {
    this.filteringEnabled = filteringEnabled;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventBus#addHandler(java.lang.Class, boolean)
   */
  public <E extends EventBus, T extends EventHandlerInterface<E>> T addHandler(Class<T> handlerClass)
    throws Mvp4gException {
    return addHandler(handlerClass,
                      true);
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventBus#addHandler(java.lang.Class, boolean)
   */
  public <E extends EventBus, T extends EventHandlerInterface<E>> T addHandler(Class<T> handlerClass,
                                                                               boolean bind)
    throws Mvp4gException {
    T handler = createHandler(handlerClass);
    if (handler == null) {
      throw new Mvp4gException("Handler with type " +
                               handlerClass.getName() +
                               " couldn't be created by the Mvp4g. Have you forgotten to set multiple attribute to true for this handler or are you trying to create an handler that belongs to another module (another type of event bus injected in this handler) or have you set a splitter for this handler?");
    }
    finishAddHandler(handler,
                     handlerClass,
                     bind);
    return handler;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventBus#removeHandler(com.mvp4g.client.event.
   * EventHandlerInterface)
   */
  public <T extends EventHandlerInterface<?>> void removeHandler(T handler) {
    List<EventHandlerInterface<?>> handlers = handlersMap.get(handler.getClass());
    if (handlers != null) {
      handlers.remove(handler);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventBus#addEventFilter(com.mvp4g.client.event .EventFilter)
   */
  public void addEventFilter(EventFilter<? extends EventBus> filter) {
    filters.add(filter);
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventBus#removeEventFilter(com.mvp4g.client.event .EventFilter)
   */
  public void removeEventFilter(EventFilter<? extends EventBus> filter) {
    filters.remove(filter);
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventBus#getHistory()
   */
  public HistoryProxy getHistory() {
    return HistoryProxyProvider.INSTANCE.get();
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventBus#setTokenGenerationModeForNextEvent()
   */
  public void setTokenGenerationModeForNextEvent() {
    tokenMode = true;
  }

  /**
   * Create a new instance of the given handler class.
   *
   * @param <T>
   *   type of the handler
   * @param handlerClass
   *   class of the handler
   *
   * @return new instance created
   */
  abstract protected <T extends EventHandlerInterface<?>> T createHandler(Class<T> handlerClass);

  /**
   * Utility method to finish adding a handler by adding it to the map and by binding it if
   * needed. It should only be used by the framework.
   *
   * @param <E>
   *   description @see EventBus
   * @param <T>
   *   description @see EventHandlerInterface
   * @param handler
   *   New instance to add.
   * @param handlerClass
   *   class of the handler to add.
   * @param bind
   *   if true, bind the handler at creation, otherwise do nothing.
   */
  public <E extends EventBus, T extends EventHandlerInterface<E>> void finishAddHandler(T handler,
                                                                                        Class<T> handlerClass,
                                                                                        boolean bind) {
    if (bind) {
      handler.isActivated(false,
                          null);
    }
    List<EventHandlerInterface<?>> handlers = handlersMap.get(handlerClass);
    if (handlers == null) {
      handlers = new ArrayList<EventHandlerInterface<?>>();
      handlersMap.put(handlerClass,
                      handlers);
    }
    handlers.add(handler);
  }

  /**
   * Interact with place service when needed thanks to the module
   *
   * @param module
   *   module that knows the place service
   * @param type
   *   type of the event to store
   * @param form
   *   object of the event to store
   * @param onlyToken
   *   if true, only the token will be generated and browser history won't change
   *
   * @return the generated token
   */
  protected String place(Mvp4gModule module,
                         String type,
                         String form,
                         boolean onlyToken) {
    String token;
    if (tokenMode) {
      tokenMode = false;
      token = module.place(type,
                           form,
                           onlyToken);
    } else {
      token = (historyStored) ?
              module.place(type,
                           form,
                           onlyToken) :
              null;
      resetHistoryStored();
    }
    return token;
  }

  /**
   * Change history stored flag value if needed
   */
  private void resetHistoryStored() {
    if (changeHistoryStoredForNextOne) {
      historyStored = !historyStored;
      changeHistoryStoredForNextOne = false;
    }
  }

  /**
   * Interact with place service to clear history when needed thanks to the module
   *
   * @param module
   *   module that knows the place service
   */
  protected void clearHistory(Mvp4gModule module) {
    if (historyStored) {
      module.clearHistory();
    }
    resetHistoryStored();
  }

  /**
   * If filtering is enabled, executes event filters associated with this event bus.
   *
   * @param eventName
   *   event's name
   * @param params
   *   event parameters for this event
   *
   * @return false if event should be stopped, true otherwise
   */
  protected boolean filterEvent(String eventName,
                                Object... params) {
    boolean ret = true;
    if (filteringEnabled) {
      ret = doFilterEvent(eventName,
                          params);
    }
    if (changeFilteringEnabledForNextOne) {
      filteringEnabled = !filteringEnabled;
      changeFilteringEnabledForNextOne = false;
    }
    return ret;
  }

  /**
   * Performs the actual filtering by calling each associated event filter in turn. If any event
   * filter returns false, then the event will be canceled.
   *
   * @param eventName
   *   event's name
   * @param params
   *   event parameters for this event
   *
   * @return false if event should be stopped, true otherwise
   */
  @SuppressWarnings("unchecked")
  private boolean doFilterEvent(String eventName,
                                Object[] params) {
    int                                       filterCount = filters.size();
    @SuppressWarnings("rawtypes") EventFilter filter;
    for (int i = 0; i < filterCount; i++) {
      filter = filters.get(i);
      if (!filter.filterEvent(eventName,
                              params,
                              this)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the list of handlers with the given class
   *
   * @param <T>
   *   type of the handlers
   * @param handlerClass
   *   class of the handlers
   *
   * @return list of handlers
   */
  @SuppressWarnings("unchecked")
  public <T extends EventHandlerInterface<?>> List<T> getHandlers(Class<T> handlerClass) {
    List<T> list = (List<T>) handlersMap.get(handlerClass);
    return (list == null) ?
           null :
           new ArrayList<T>(list);
  }
}
