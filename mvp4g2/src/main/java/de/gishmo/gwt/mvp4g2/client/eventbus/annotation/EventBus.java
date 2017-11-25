/*
 * Copyright (C) 2016 Frank Hossfeld <frank.hossfeld@googlemail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package de.gishmo.gwt.mvp4g2.client.eventbus.annotation;

import de.gishmo.gwt.mvp4g2.client.ui.IsShell;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>This annotation is used to annotate an interface in mvp4g and mark it as mvp4g eventbus.
 * <br><br>
 * The annotation has the following attributes:
 * <ul>
 * <li>shell: the shell of the application. The shell will be added to the viewport.</li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBus {

  Class<? extends IsShell> shell();

  boolean historyOnStart() default false;

//
//  String startPresenterName() default "";
//
//  Class<? extends PresenterInterface<?, ?>> startPresenter();
//
//  Class<? extends Mvp4gModule> module() default Mvp4gModule.class;
//
//  Class<? extends HistoryProxy> historyProxy() default DefaultHistoryProxy.class;
//
//  Class<? extends GinModule>[] ginModules() default DefaultMvp4gGinModule.class;
//
//  String[] ginModuleProperties() default {};

}
