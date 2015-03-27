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

/**
 * This annotation is used on methods to indicate that this method must be used to inject a service.<br>
 * <br>
 * This annotation must be used only in classes that have a <code>Presenter</code> or
 * <code>History</code> annotation.<br>
 * <br>
 * When a service needs to be injected, the framework will search an instance of a service with the
 * same class. If it doesn't found one, it will create one.<br>
 * This means that only one instance of each service class will be created.<br>
 * <br>
 * You can also specify to inject a particular instance of a service thanks to its name with the
 * attribute <i>serviceName</i>.<br>
 *
 * @author plcoirier
 * @deprecated As of release 1.2.0, shoud use GIN injection
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InjectService {

  String serviceName() default "";

}
