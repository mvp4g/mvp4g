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

import com.mvp4g.client.Mvp4gEventPasser;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.history.NavigationConfirmationInterface;
import com.mvp4g.client.history.NavigationEventCommand;

public class Mvp4gModuleStub
  implements Mvp4gModule {

  public final String TOKEN = "token";

  private EventBus eventBus;
  private String   eventType;
  private boolean  tokenOnly, historyNotFound, historyInit;
  private Object           form;
  private Mvp4gEventPasser passer;

  public Mvp4gModuleStub(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  public void createAndStartModule() {

  }

  public void onForward() {
    // TODO Auto-generated method stub

  }

  public Object getStartView() {
    return null;
  }

  public EventBus getEventBus() {
    return eventBus;
  }

  public void addConverter(String token,
                           HistoryConverter<?> hc) {

  }

  public String place(String token,
                      String form,
                      boolean tokenOnly) {
    this.eventType = token;
    this.form = form;
    this.tokenOnly = tokenOnly;
    return TOKEN;
  }

  public void dispatchHistoryEvent(String eventType,
                                   Mvp4gEventPasser passer) {
    this.eventType = eventType;
    this.passer = passer;
  }

  public void clearHistory() {
    this.eventType = null;
    this.form = null;
  }

  public void setParentModule(Mvp4gModule parentModule) {
    // TODO Auto-generated method stub

  }

  public void loadChildModule(String childModuleClassName,
                              String eventName,
                              boolean passive,
                              Mvp4gEventPasser passer) {
    // TODO Auto-generated method stub

  }

  public void sendInitEvent() {
    historyInit = true;
  }

  public void sendNotFoundEvent() {
    historyNotFound = true;
  }

  /**
   * @return the eventType
   */
  public String getEventType() {
    return eventType;
  }

  /**
   * @return the passer
   */
  public Mvp4gEventPasser getPasser() {
    return passer;
  }

  /**
   * @return the form
   */
  public Object getForm() {
    return form;
  }

  /**
   * @return the tokenOnly
   */
  public Object getTokenOnly() {
    return tokenOnly;
  }

  public void addConverter(String eventType,
                           String historyName,
                           HistoryConverter<?> hc) {
    // TODO Auto-generated method stub

  }

  public void confirmEvent(NavigationEventCommand event) {
    // TODO Auto-generated method stub

  }

  public void setNavigationConfirmation(NavigationConfirmationInterface navigationConfirmation) {
    // TODO Auto-generated method stub

  }

  public boolean isHistoryNotFound() {
    return historyNotFound;
  }

  public boolean isHistoryInit() {
    return historyInit;
  }

}
