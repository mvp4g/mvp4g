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

import com.google.gwt.core.ext.typeinfo.JClassType;

public class ChildModuleElement
  extends SimpleMvp4gElement {

  JClassType parentEventBus;

  public ChildModuleElement() {
    super("childModule");
  }

  public String getEventToDisplayView() {
    return getProperty("eventToDisplayView");
  }

  public void setEventToDisplayView(String eventToDisplayView) {
    setProperty("eventToDisplayView",
                eventToDisplayView);
  }

  public boolean isAsync() {
    return Boolean.TRUE.toString()
                       .equalsIgnoreCase(getAsync());
  }

  public String getAsync() {
    String async = getProperty("async");
    // By default it's true
    return (async == null) ?
           "true" :
           getProperty("async");
  }

  public void setAsync(String async) {
    setProperty("async",
                async);
  }

  public boolean isAutoDisplay() {
    return Boolean.TRUE.toString()
                       .equalsIgnoreCase(getAutoDisplay());
  }

  public String getAutoDisplay() {
    String autoDisplay = getProperty("autoDisplay");
    // By default it's true
    return (autoDisplay == null) ?
           "true" :
           getProperty("autoDisplay");
  }

  public void setAutoDisplay(String autoDisplay) {
    setProperty("autoDisplay",
                autoDisplay);
  }

  public String getHistoryName() {
    return getProperty("historyName");
  }

  public void setHistoryName(String historyName) {
    setProperty("historyName",
                historyName);
  }

  public JClassType getParentEventBus() {
    return parentEventBus;
  }

  public void setParentEventBus(JClassType parentEventBus) {
    this.parentEventBus = parentEventBus;
  }

  public String getParentModuleClass() {
    return getProperty("parentModuleClass");
  }

  public void setParentModuleClass(String parentModuleClass) {
    setProperty("parentModuleClass",
                parentModuleClass);
  }

  public String getLoader() {
    return getProperty("loader");
  }

  public void setLoader(String loader) {
    setProperty("loader",
                loader);
  }

}
