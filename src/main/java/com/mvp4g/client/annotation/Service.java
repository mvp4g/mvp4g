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
package com.mvp4g.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * You can use this annotation in the following cases:
 * <ul>
 * <li>you want to create a service with a specific name thanks to the attribute <i>name</i>.</li>
 * <li>you want to define a specific URL for the Service Entry Point thanks to the attribute
 * <i>path</i>.</li>
 * <li>you want to specify the class that should be generated when creating the service thanks to
 * GWT deferred binding feature (GWT.create) because the generated class is not a asynchronous RPC
 * class (XXXAsync.java) thanks to the attribute <i>generatedClass</i>.</li>
 * </ul>
 * There is no need to use this annotation to indicate to the framework that this class is a
 * service.<br>
 *
 * @author plcoirier
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

  String name() default "";

  String path() default "";

  Class<?> generatedClass() default Void.class;

}
