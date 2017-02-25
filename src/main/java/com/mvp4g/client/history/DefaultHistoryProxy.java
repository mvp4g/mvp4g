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

package com.mvp4g.client.history;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;

public class DefaultHistoryProxy
  implements HistoryProxy {

  public DefaultHistoryProxy() {
  }

  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
    return History.addValueChangeHandler(handler);
  }

  public void back() {
    History.back();
  }

  public void fireCurrentHistoryState() {
    History.fireCurrentHistoryState();
  }

  public void forward() {
    History.forward();
  }

  public String getToken() {
    return History.getToken();
  }

  public void newItem(String historyToken) {
    History.newItem(historyToken);
  }

  public void newItem(String historyToken,
                      boolean issueEvent) {
    History.newItem(historyToken,
                    issueEvent);
  }

}
