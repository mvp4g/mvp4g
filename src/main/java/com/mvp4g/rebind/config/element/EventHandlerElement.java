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
package com.mvp4g.rebind.config.element;

/**
 * An Mvp4g EventHandler configuration element.<br>
 *
 * @author Dan Persa
 */
public class EventHandlerElement
  extends Mvp4gWithServicesElement {

  public EventHandlerElement() {
    super("eventHandler");
  }

  public EventHandlerElement(String tagName) {
    super(tagName);
  }

  public boolean isMultiple() {
    return Boolean.TRUE.toString()
                       .equalsIgnoreCase(getMultiple());
  }

  public String getMultiple() {
    return getProperty("multiple");
  }

  public void setMultiple(String multiple) {
    setProperty("multiple",
                multiple);
  }

  public boolean isAsync() {
    return (getAsync() != null);
  }

  public String getAsync() {
    return getProperty("async");
  }

  public void setAsync(String async) {
    setProperty("async",
                async);
  }
}
