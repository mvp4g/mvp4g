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

import com.mvp4g.client.Mvp4gSplitter;

/**
 * This annotation allows to define an event handler for the framework.<br/>
 * <br/>
 * You can define the name of the event handler thanks to the optional attribute <i>name</i>. If you
 * don't give a name, the framework will generate one.<br/>
 * It is recommended to affect a name only if needed.<br/>
 * <br/>
 * You can activate the multiple feature to create several instance of the same handler. <br/>
 * <br/>
 * You can also use the async attribute to create a fragment for this handler (or for a group of handlers).
 * 
 * 
 * @since 27.04.2010
 * @author Dan Persa
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface EventHandler {

	String name() default "";

	boolean multiple() default false;

	Class<? extends Mvp4gSplitter> async() default NotAsync.class;

}
