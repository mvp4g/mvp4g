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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.mvp4g.client.event.EventFilter;

/**
 * This annotation may be used to set event filters for the framework.<br/>
 * <br/>
 * This annotation can be used only on classes that implement <code>EventBus</code> and that are
 * annotated with <code>@Events</code>.<br/>
 * <br/>
 * The annotation has the following attributes:
 * <ul>
 * <li>filterClasses: classes of the filter to use.</li>
 * <li>afterHistory (by default, false): if set to true, events will be filtered after the history
 * conversion. In this case, even if an event is stopped, it will still appear in the browser
 * history.</li>
 * <li>filterStart (by default, true): if set to true, the start event fired when the application
 * starts will be filtered.</li>
 * <li>filterForward (by default, true): if set to true, the forward event fired when the module
 * receives an event forwarded from its parent will be filtered.</li>
 * <li>forceFilters (by default, false): if you want to add filters dynamically and you have no
 * static filters (ie you haven't set any filter thanks to the attribute filterClasses), you will
 * need to set this attribute to true. Otherwise, when there is no static filter, Mvp4g optimizes
 * the code and removes the filtering methods (so filter(s) added dynamically wouldn't work as
 * filtering feature would have been removed).
 * </ul>
 * 
 * @author Nick Hebner
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface Filters {

	Class<? extends EventFilter<?>>[] filterClasses();

	boolean afterHistory() default false;

	boolean filterStart() default true;

	boolean filterForward() default true;

	boolean forceFilters() default false;

}
