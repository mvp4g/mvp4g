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
 * This annotation allows to define an history converter for the framework.<br/>
 * <br/>
 * You can define the name of the history converter thanks to the optional attribute <i>name</i>. If
 * you don't give a name, the framework will generate one.<br/>
 * It is recommended to affect a name only if needed.<br/>
 * <br/>
 * This annotation also has a convertParams attribute. By default, an history converter must define
 * an handling method for each event it converts. If you set this attribute to false, you won't have
 * to define these methods.<br/>
 * <br/>
 * This annotation can be used only on classes that implements <code>HistoryConverter</code>.
 * 
 * @author plcoirier
 * 
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface History {

	String name() default "";

	boolean convertParams() default true;

}
