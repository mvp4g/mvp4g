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
 * This annotation allows to define history configuration for the framework.<br/>
 * <br/>
 * This annotation can be used only on classes that implement <code>EventBus</code> and that are
 * annotated with <code>@Events</code>.<br/>
 * <br/>
 * The annotation has the following attributes:
 * <ul>
 * <li>paramSeparator: character that should be used to separate in the history token the event name
 * and the parameters. By default, this parameter is equal to "?". This parameter shouldn't be use
 * in parameter values.</li>
 * <li>paramSeparatorAlwaysAdded (default false): add the separator between the event name and the
 * parameters even if there is no parameter. This parameter has to be set to true if you use "/" to
 * allow the framework to differentiate this the parameter separator from the child modules
 * separator.
 * 
 * @author plcoirier
 * 
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface HistoryConfiguration {

	String paramSeparator();

	boolean paramSeparatorAlwaysAdded() default false;

}
