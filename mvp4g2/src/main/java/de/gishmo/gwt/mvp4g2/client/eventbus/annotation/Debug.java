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
package de.gishmo.gwt.mvp4g2.client.eventbus.annotation;

import de.gishmo.gwt.mvp4g2.client.eventbus.internal.DefaultMvp4g2Logger;
import de.gishmo.gwt.mvp4g2.client.eventbus.Mvp4g2Logger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation should be used to activate the logs. It has the following attributes:
 * <ul>
 * <li>logLevel: level of the logs. If the level is set to simple, only the fired events will be
 * displayed in the log, otherwise fired events and handlers of these events will be logged.</li>
 * <li>logger: class of the logger to use.</li>
 * </ul>
 * <br>
 * <br>
 * This annotation should be used only on interfaces that extend <code>EventBus</code>.
 *
 * @author plcoirier
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Debug {

  LogLevel logLevel() default LogLevel.SIMPLE;

  Class<? extends Mvp4g2Logger> logger() default DefaultMvp4g2Logger.class;

  enum LogLevel {
    SIMPLE,
    DETAILED
  }
}
