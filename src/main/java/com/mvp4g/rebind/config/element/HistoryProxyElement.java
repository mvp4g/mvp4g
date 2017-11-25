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
 * An Mvp4g History Proxy configuration element.<br>
 * <br>
 * A <i>historyProxy</i> element is composed of one attributes:
 * <ol>
 * <li><i>historyProxy</i>: this element is optional. IN case it is not set, the
 * DefaultHistoryProxy is used.</li>
 * </ol>
 * <br>
 * This element is optional because not all GWT applications have to manage history.
 *
 * @author plcoirier
 */
public class HistoryProxyElement
  extends Mvp4gElement {

  private static final String HISTORY_ELEMENT_ID = HistoryProxyElement.class.getName();

  public HistoryProxyElement() {
    super("historyProxy");
  }

  @Override
  public String getUniqueIdentifierName() {
    // this element does not have a user-specified identifier: use a global label
    return HISTORY_ELEMENT_ID;
  }

  public String getHistoryProxyClass() {
    return getProperty("historyProxyClass");
  }

  public void setHistoryProxyClass(String historyProxyClass) {
    setProperty("historyProxyClass",
                historyProxyClass);
  }
}
