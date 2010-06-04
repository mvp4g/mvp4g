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
 * This annotation may be used to define an event filter for the framework.<br/>
 * <br/>
 * This annotation can be used only on classes that implement <code>EventBus</code>
 * or <code>EventBusWithLookup</code>.
 * 
 * @author Nick Hebner
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface Filters {

	Class<? extends EventFilter<?>>[] filterClasses();
	
	boolean afterHistory() default false;

}