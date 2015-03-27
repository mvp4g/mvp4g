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

import com.google.gwt.inject.client.GinModule;
import com.mvp4g.client.DefaultMvp4gGinModule;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.presenter.PresenterInterface;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation indicates that the annotated interface should be used to define the event bus of
 * the specified module. This annotation can be used only on interfaces that extends
 * <code>EventBus</code>.<br>
 * <br>
 * The annotation has the following attributes:
 * <ul>
 * <li>startPresenter: class of the presenter which view should be loaded when the module starts.</li>
 * <li>startPresenterName: you can also specify the name of the start presenter.</li>
 * <li>module: class of the module for which the annotated interface should be used to generate the
 * event bus. If no module is specified, it means that the interface should be used to generate the
 * Root Module (first module to be loaded and only module without parent).</li>
 * <li>historyOnStart: if true, the current history state will be fired when the application starts.
 * </li>
 * <li>ginModules: classes of the GIN modules the framework should use when generating presenters,
 * event handlers, history converters &amp; views. You can specify zero to severals GIN modules.</li>
 * <li>ginModuleProperties: deferred property names used to retrieve the class of the GIN modules.
 * You can use ginModuleProperties and/or ginModules.</li>
 * </ul>
 *
 * @author plcoirier
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Events {

  String startPresenterName() default "";

  Class<? extends PresenterInterface<?, ?>> startPresenter();

  Class<? extends Mvp4gModule> module() default Mvp4gModule.class;

  boolean historyOnStart() default false;

  Class<? extends GinModule>[] ginModules() default DefaultMvp4gGinModule.class;

  String[] ginModuleProperties() default {};

}
