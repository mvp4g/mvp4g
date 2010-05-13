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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.presenter.PresenterInterface;

/**
 * This annotation should be used to annotate methods of interfaces that extends
 * <code>EventBus</code> in order to define event.<br/>
 * <br/>
 * The annotation has the following attributes:
 * <ul>
 *<li>handlers: classes of the presenters that handle this event. You can have zero to several
 * handlers for an event.</li>
 *<li>handlerNames: instead of using their classes, you can define handlers thanks to their name
 * (in case you have given names to your handlers). Not recommended because this method tends to
 * create typo errors.</li>
 * <li>modulesToLoad: child modules that should be loaded if necessary and to which the event should
 * be forwarded. Child modules to which the event is forwarded must be one of the child modules of
 * the <code>EventBus</code> interface's module (ie one of the modules defined inside
 * <code>ChildModules</code> annotation). If an object is associated to the event, it will also be
 * forwarded. An event can be forwarded to zero to several child modules.</li>
 * <li>forwardToParent: if true, event will be forwarded to the parent module. In this case, the
 * module should have a parent.</li>
 * <li>calledMethod: name of the method that handlers should define and that will be called when the
 * event is fired. By default it's equal to "on" + event's method name.</li>
 * <li>historyConverter: class of the history converter that should be used to store the event in
 * browse history. If no history converter is specified, event won't be stored in browse history.
 * You can define only one history converter for each event.
 * <li>historyConverterName: instead of using its class, you can define the history converter thanks
 * to his name (in case you have given names to your history converter). Not recommended because
 * this method tends to create typo errors.</li>
 * 
 * @author plcoirier
 * 
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface Event {

	public static final String DEFAULT_NAME = "#%!|&";

	Class<? extends PresenterInterface<?, ? extends EventBus>>[] handlers() default {};

	String[] handlerNames() default {};

	Class<? extends Mvp4gModule>[] modulesToLoad() default {};

	boolean forwardToParent() default false;

	String calledMethod() default "";

	String historyConverterName() default "";

	Class<? extends HistoryConverter<?>> historyConverter() default NoHistoryConverter.class;

	Class<? extends PresenterInterface<?, ? extends EventBus>>[] activate() default {};

	String[] activateNames() default {};

	Class<? extends PresenterInterface<?, ? extends EventBus>>[] deactivate() default {};

	String[] deactivateNames() default {};

	String historyName() default DEFAULT_NAME;

	class NoHistoryConverter implements HistoryConverter<EventBus> {

		private NoHistoryConverter() {
			//to prevent this class to be used
		}

		public void convertFromToken( String eventType, String param, EventBus eventBus ) {
		}

		public boolean isCrawlable() {
			return false;
		}

	}

}
