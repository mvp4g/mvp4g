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


/**
 * @author javier
 */
public class StartElement
  extends Mvp4gElement {

  private static final String START_ELEMENT_ID = StartElement.class.getName();

  public StartElement() {
    super("start");
  }

  @Override
  public String getUniqueIdentifierName() {
    // this element does not have a user-specified identifier: use a global label
    return START_ELEMENT_ID;
  }

  public String getPresenter() {
    return getProperty("presenter");
  }

  public void setPresenter(String presenter) {
    setProperty("presenter",
                presenter);
  }

  public String getEventType() {
    return getProperty("eventType");
  }

  public void setEventType(String eventType) {
    setProperty("eventType",
                eventType);
  }

  public boolean hasEventType() {
    return getEventType() != null;
  }

  public String getHistory() {
    return getProperty("history");
  }

  public void setHistory(String history) {
    setProperty("history",
                history);
  }

  public boolean hasHistory() {
    return Boolean.TRUE.toString().equalsIgnoreCase(getHistory());
  }

  public String getForwardEventType() {
    return getProperty("forwardEventType");
  }

  public void setForwardEventType(String forwardEventType) {
    setProperty("forwardEventType",
                forwardEventType);
  }

  public boolean hasForwardEventType() {
    return getForwardEventType() != null;
  }

  public boolean hasPresenter() {
    return (getPresenter() != null);
  }

}
