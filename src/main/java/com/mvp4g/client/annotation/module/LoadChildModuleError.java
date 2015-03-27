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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used to annotate the method of an event in your EventBus interface. It
 * indicates which event should be fired when an error occurs while loading child module code thanks
 * to GWT2 Code Splitting feature.<br>
 * <br>
 * You can only annotate methods with no parameter or with one parameter which type is compatible
 * with <code>java.lang.Throwable</code>. If the method has one parameter, the
 * <code>java.lang.Throwable</code> object returned by GWT2 code splitting feature will be passed to
 * the event.<br>
 * <br>
 * You must not have more than one <code>LoadChildModuleError</code> annotation in a class.
 *
 * @author plcoirier
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoadChildModuleError {

}
