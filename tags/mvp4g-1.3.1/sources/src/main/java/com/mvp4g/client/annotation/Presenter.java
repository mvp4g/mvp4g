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

/**
 * This annotation allows to define a presenter for the framework.<br/>
 * <br/>
 * To define the presenter, you need to indicate the class of the view to inject in the presenter
 * thanks to the attribute <i>view</i>. The framework will automatically create one instance of the
 * view for each presenter.<br/>
 * <br/>
 * You can define the names of the presenter and/or the presenter view thanks to the optional
 * attribute <i>name</i> and <i>viewName</i>. If you don't give a name, the framework will generate
 * one.<br/>
 * It is recommended to affect a name only if needed.<br/>
 * <br/>
 * You can also activate the multiple feature. This feature allows you to create several instance of
 * the same handler.
 * 
 * @author plcoirier
 * 
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface Presenter {

	String name() default "";

	Class<?> view();

	String viewName() default "";

	boolean multiple() default false;

}
