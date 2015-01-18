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
package org.gwt4e.mvp4g.application.client.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO documenation

/**
 * This annotation should be used to annotate methods of interfaces that extends
 * <code>EventBus</code> in order to define event.<br/>
 * <br/>
 * The annotation has the following attributes:
 * <ul>
 * <li>loader: the load-method of the loader will be executed during application start and
 * after the mvp4g modules are created.</li>
 * <li>fireStartEvent: if true, the star-event will be generated and after the load-method is called.
 * if false, no start-event will be fired! (default is true)</li>
 * </ul>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Application {

  boolean fireStartEvent() default true;

}
