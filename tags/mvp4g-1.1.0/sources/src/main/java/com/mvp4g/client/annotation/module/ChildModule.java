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
package com.mvp4g.client.annotation.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.mvp4g.client.Mvp4gModule;

/**
 * This annotation can be used as an attribute of the <code>ChildModules</code> annotation to add a
 * child module to the parent module.<br/>
 * <br/>
 * The following attributes can be defined:<br/>
 * <ul>
 * <li>moduleClass: class of the child module</li>
 * <li>async: if true, GWT2 code splitting feature will be used to load the code of the child
 * module. If you activate this feature but you use an older version of GWT (like GWT 1.7), it will
 * automatically be set to false.</li>
 * <li>autoDisplay: if true, the start view of the child module will be automatically displayed
 * inside the parent module. In this case, you need to set an event to use to display this view
 * thanks to the <code>DisplayChildModuleView</code> annotation.
 * </ul>
 * 
 * @author plcoirier
 * 
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface ChildModule {

	Class<? extends Mvp4gModule> moduleClass();

	boolean async() default true;

	boolean autoDisplay() default true;

}
