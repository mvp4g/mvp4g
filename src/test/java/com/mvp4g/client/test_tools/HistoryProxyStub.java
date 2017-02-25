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

package com.mvp4g.client.test_tools;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.mvp4g.client.history.HistoryProxy;

public class HistoryProxyStub
  implements HistoryProxy {

  private ValueChangeHandler<String> handler    = null;
  private boolean                    issueEvent = false;
  private String                     token      = null;

  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
    this.handler = handler;
    return null;
  }

  public void back() {
    //not needed
  }

  public void fireCurrentHistoryState() {
    //not needed
  }

  public void forward() {
    //not needed
  }

  public String getToken() {
    return token;
  }

  public void newItem(String historyToken) {
    //not needed
  }

  public void newItem(String token,
                      boolean issueEvent) {
    this.token = token;
    this.issueEvent = issueEvent;
  }

  public ValueChangeHandler<String> getHandler() {
    return handler;
  }

  public boolean isIssueEvent() {
    return issueEvent;
  }

}
