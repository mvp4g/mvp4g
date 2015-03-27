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
package com.mvp4g.client.annotation;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.client.history.HistoryConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation should be used to annotate methods of interfaces that extends
 * <code>EventBus</code> in order to define event.<br>
 * <br>
 * The annotation has the following attributes:
 * <ul>
 * <li>handlers: classes of the handlers of this event. You can have zero to several handlers for an
 * event.</li>
 * <li>handlerNames: instead of using their classes, you can define handlers thanks to their name
 * (in case you have given names to your handlers). Not recommended because this method tends to
 * create typo errors.</li>
 * <li> bind: classes that need to be binded when this event occurs. You can have zero to several classes
 * for an event. </li>
 * <li> bindNames: instead of using their classes, you can define binds thanks to their name. Not recommended.
 * <li>modulesToLoad: child modules that should be loaded if necessary and to which the event should
 * be forwarded. Child modules to which the event is forwarded must be one of the child modules of
 * the <code>EventBus</code> interface's module (ie one of the modules defined inside
 * <code>ChildModules</code> annotation). If object(s) are associated to the event, they will also
 * be forwarded. An event can be forwarded to zero to several child modules.</li>
 * <li>forwardToParent: if true, event will be forwarded to the parent module. In this case, the
 * module must have a parent.</li>
 * <li>calledMethod: name of the method that handlers should define and that will be called when the
 * event is fired. By default it's equal to "on" + event's method name.</li>
 * <li>historyConverter: class of the history converter that should be used to store the event in
 * browse history. If no history converter is specified, event won't be stored in browse history.
 * You can define only one history converter for each event.
 * <li>historyConverterName: instead of using its class, you can define the history converter thanks
 * to his name (in case you have given names to your history converter). Not recommended because
 * this method tends to create typo errors.</li>
 * <li>historyName: name of the event that should be stored in the history token. By default, this
 * name is equal to the name of the event's method.
 * <li>activate: classes of handlers that should be activated with this event. You can activate zero
 * to several handlers. Handlers to activate don't have to handle the event.</li>
 * <li>activateNames: instead of using their classes, you can activate handlers thanks to their name
 * (in case you have given names to your handlers). Not recommended because this method tends to
 * create typo errors.</li>
 * <li>deactivate: classes of handlers that should be deactivated with this event. You can activate
 * zero to several handlers. Handlers to deactivate must not handle the event.</li>
 * <li>deactivateNames: instead of using their classes, you can activate handlers thanks to their
 * name (in case you have given names to your handlers). Not recommended because this method tends
 * to create typo errors.</li>
 * <li>navigationEvent: indicates that when this event is fired, a navigation control is done to
 * verify the event can be fired. Usually a navigation event is an event that will change the
 * displayed screen.</li>
 * <li>passive: when an event is fired, it will build any handlers not built yet and/or load any
 * child modules not loaded yet expect if the event is passive.</li>
 * </ul>
 *
 * @author plcoirier
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Event {

  //default name that developers are unlikely to enter to know when method name should be used
  public static final String DEFAULT_NAME = "#%!|&";

  Class<? extends EventHandlerInterface<? extends EventBus>>[] handlers() default {};

  String[] handlerNames() default {};

  Class<? extends EventHandlerInterface<? extends EventBus>>[] bind() default {};

  String[] bindNames() default {};

  Class<? extends Mvp4gModule>[] forwardToModules() default {};

  boolean forwardToParent() default false;

  String calledMethod() default "";

  String historyConverterName() default "";

  Class<? extends HistoryConverter<?>> historyConverter() default NoHistoryConverter.class;

  Class<? extends EventHandlerInterface<? extends EventBus>>[] activate() default {};

  String[] activateNames() default {};

  Class<? extends EventHandlerInterface<? extends EventBus>>[] deactivate() default {};

  String[] deactivateNames() default {};

  String name() default DEFAULT_NAME;

  boolean navigationEvent() default false;

  boolean passive() default false;

  Class<?> broadcastTo() default NoBroadcast.class;

  Class<? extends EventHandlerInterface<? extends EventBus>>[] generate() default {};

  String[] generateNames() default {};

  class NoHistoryConverter
    implements HistoryConverter<EventBus> {

    private NoHistoryConverter() {
      //to prevent this class to be used
    }

    public void convertFromToken(String historyName,
                                 String param,
                                 EventBus eventBus) {
    }

    public boolean isCrawlable() {
      return false;
    }

  }

  class NoBroadcast {
  }

}
