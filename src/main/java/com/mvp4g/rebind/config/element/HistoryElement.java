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
 * An Mvp4g History configuration element.<br>
 * <br>
 * A <i>history</i> element is composed of two attributes:
 * <ol>
 * <li><i>package</i>: this element is optional and used by the child tag</li>
 * <li><i>initEvent</i>: a required attribute that represents the event to send when token fired by
 * history is empty (for example when you go back to the first page).</li>
 * </ol>
 * <br>
 * This element is optional because not all GWT applications have to manage history.
 *
 * @author plcoirier
 */
public class HistoryElement
  extends Mvp4gElement {

  private static final String HISTORY_ELEMENT_ID = HistoryElement.class.getName();

  public HistoryElement() {
    super("history");
  }

  @Override
  public String getUniqueIdentifierName() {
    // this element does not have a user-specified identifier: use a global label
    return HISTORY_ELEMENT_ID;
  }

  public String getPlaceServiceClass() {
    return getProperty("placeServiceClass");
  }

  public void setPlaceServiceClass(String placeServiceClass) {
    setProperty("placeServiceClass",
                placeServiceClass);
  }

  public String getInitEvent() {
    return getProperty("initEvent");
  }

  public void setInitEvent(String initEvent) {
    setProperty("initEvent",
                initEvent);
  }

  public String getNotFoundEvent() {
    return getProperty("notFoundEvent");
  }

  public void setNotFoundEvent(String notFoundEvent) {
    setProperty("notFoundEvent",
                notFoundEvent);
  }

  public String getHistoryProxyClass() {
    return getProperty("historyProxyClass");
  }

  public void setHistoryProxyClass(String historyProxyClass) {
    setProperty("historyProxyClass",
                historyProxyClass);
  }
}
