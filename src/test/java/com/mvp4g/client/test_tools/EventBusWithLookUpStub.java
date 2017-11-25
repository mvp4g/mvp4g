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

import com.google.gwt.user.client.Command;
import com.mvp4g.client.event.BaseEventBusWithLookUp;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.client.history.NavigationConfirmationInterface;
import com.mvp4g.client.history.NavigationEventCommand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class EventBusWithLookUpStub
  extends BaseEventBusWithLookUp {

  public static final String MVP4G_EXCEPTION      = "mvp4gException";
  public static final String CLASS_CAST_EXCEPTION = "classCastException";

  private String   lastDispatchedEventType = null;
  private Object[] lastDispatchedObject    = null;
  private NavigationConfirmationInterface conf;

  public void dispatch(String eventType,
                       Object... form) {
    lastDispatchedEventType = eventType;
    lastDispatchedObject = form;
    if (MVP4G_EXCEPTION.equals(eventType)) {
      Object o = new Object();
      start((Integer) o);
    } else if (CLASS_CAST_EXCEPTION.equals(eventType)) {
      start(10);
    }
  }

  private void start(final Integer i) {
    Command cmd = new Command() {

      public void execute() {
        Object                              o = new Object();
        @SuppressWarnings("unused") Integer i = (Integer) o;
      }

    };
    cmd.execute();
  }

  public String getLastDispatchedEventType() {
    return lastDispatchedEventType;
  }

  public Object[] getLastDispatchedObject() {
    return lastDispatchedObject;
  }

  public void assertEvent(String expectedEventType,
                          Object[] expectedDispatchedObject) {
    assertEquals(expectedEventType,
                 lastDispatchedEventType);
    if (expectedDispatchedObject == null) {
      assertNull(lastDispatchedObject);
    } else {
      assertTrue(expectedDispatchedObject.length == lastDispatchedObject.length);
      for (int i = 0; i < expectedDispatchedObject.length; i++) {
        assertEquals(expectedDispatchedObject[i],
                     lastDispatchedObject[i]);
      }
    }
  }

  @Override
  protected <T extends EventHandlerInterface<?>> T createHandler(Class<T> handlerClass) {
    return null;
  }

  public NavigationConfirmationInterface getLastNavigationConfirmation() {
    return conf;
  }

  public void setApplicationHistoryStored(boolean historyStored) {

  }

  public void setNavigationConfirmation(NavigationConfirmationInterface navigationConfirmation) {
    conf = navigationConfirmation;
  }

  public void confirmNavigation(NavigationEventCommand event) {

  }

}
