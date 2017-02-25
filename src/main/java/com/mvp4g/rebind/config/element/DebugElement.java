/*
 * Copyright 2009 Pierre-Laurent Coirier
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
package com.mvp4g.rebind.config.element;

import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.DefaultMvp4gLogger;

public class DebugElement
  extends Mvp4gElement {

  private static final String DEBUG_ELEMENT_ID = DebugElement.class.getName();

  public DebugElement() {
    super("debug");
  }

  @Override
  public String getUniqueIdentifierName() {
    return DEBUG_ELEMENT_ID;
  }

  public String getLogLevel() {
    String logLevel = getProperty("logLevel");
    return (logLevel == null) ?
           LogLevel.SIMPLE.name() :
           logLevel;
  }

  public void setLogLevel(String logLevel) {
    setProperty("logLevel",
                logLevel);
  }

  public String getLogger() {
    String logger = getProperty("logger");
    return (logger == null) ?
           DefaultMvp4gLogger.class.getCanonicalName() :
           logger;
  }

  public void setLogger(String logger) {
    setProperty("logger",
                logger);
  }

}
