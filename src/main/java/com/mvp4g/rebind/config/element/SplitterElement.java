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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.dev.util.collect.HashMap;

public class SplitterElement
  extends SimpleMvp4gElement {

  private Set<EventHandlerElement> handlers = new HashSet<EventHandlerElement>();

  private Map<EventElement, EventAssociation<String>> events = new HashMap<EventElement, EventAssociation<String>>();

  public Set<EventHandlerElement> getHandlers() {
    return handlers;
  }

  public Map<EventElement, EventAssociation<String>> getEvents() {
    return events;
  }

  public String getLoader() {
    return getProperty("loader");
  }

  public void setLoader(String loader) {
    setProperty("loader",
                loader);
  }

}
