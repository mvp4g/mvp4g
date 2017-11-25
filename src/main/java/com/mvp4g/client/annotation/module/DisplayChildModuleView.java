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
package com.mvp4g.client.annotation.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mvp4g.client.Mvp4gModule;

/**
 * This annotation can be used to annotate the method of an event in your EventBus interface. It
 * indicates which event should be fired when a child module is done loading and its start view
 * needs to be displayed.<br>
 * <br>
 * The event is fired with the child module start view so the event's method should have one
 * parameter which type is compatible with the module start view.<br>
 * <br>
 * The same event can be used to display one to several child module start view. To set the child
 * modules the event should handle, you need to add their class of the annotation attribute.
 *
 * @author plcoirier
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DisplayChildModuleView {

  public Class<? extends Mvp4gModule>[] value();

}
