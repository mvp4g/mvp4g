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

package com.mvp4g.client.event;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.client.history.NavigationConfirmationInterface;
import com.mvp4g.client.history.NavigationEventCommand;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class BaseEventHandlerTest {

  private BaseEventHandler<BaseEventBus> eventHandler = null;
  private BaseEventBus bus;
  private int          bindCallCount;

  @Before
  public void setUp() {
    eventHandler = new BaseEventHandler<BaseEventBus>();
    bindCallCount = 0;
    bus = new BaseEventBus() {

      @Override
      protected <T extends EventHandlerInterface<?>> T createHandler(Class<T> handlerClass) {
        return null;
      }

      public void setApplicationHistoryStored(boolean historyStored) {

      }

      public void setNavigationConfirmation(NavigationConfirmationInterface navigationConfirmation) {

      }

      public void confirmNavigation(NavigationEventCommand event) {

      }

    };
  }

  @Test
  public void testDefaultConstructor() {
    assertNull(eventHandler.getEventBus());
  }

  @Test
  public void testSetter() {
    eventHandler.setEventBus(bus);
    assertSame(eventHandler.getEventBus(),
               bus);
  }

  @Test
  public void testActivated() {
    BaseEventHandler<EventBus> handler = new BaseEventHandler<EventBus>() {
      public void bind() {
        super.bind();
        bindCallCount++;
      }
    };

    assertTrue(handler.isActivated(false,
                                   null));
    assertTrue(bindCallCount == 1);

    assertTrue(handler.isActivated(false,
                                   null));
    assertTrue(bindCallCount == 1);

    handler.setActivated(false);

    assertFalse(handler.isActivated(false,
                                    null));
    assertTrue(bindCallCount == 1);
  }

  @Test
  public void testPassiveEvent() {
    BaseEventHandler<EventBus> handler = new BaseEventHandler<EventBus>() {
      public void bind() {
        super.bind();
        bindCallCount++;
      }
    };

    assertFalse(handler.isActivated(true,
                                    null));
    assertTrue(bindCallCount == 0);

    assertTrue(handler.isActivated(false,
                                   null));
    assertTrue(bindCallCount == 1);

    assertTrue(handler.isActivated(true,
                                   null));
    assertTrue(bindCallCount == 1);
  }

  @Test
  public void testTokeniser() {
    eventHandler.setEventBus(bus);
    BaseEventBus eventBus = eventHandler.getTokenGenerator();
    assertSame(bus,
               eventBus);
    assertTrue(bus.tokenMode);
  }

  @Test
  public void testPass() {
    EventHanderWithPass handler = new EventHanderWithPass();
    handler.setPass(true);
    assertTrue(handler.pass("foo"));
    assertTrue(handler.isActivated(false,
                                   "foo"));

    handler.setPass(false);
    assertFalse(handler.pass("foo"));
    assertFalse(handler.isActivated(false,
                                    "foo"));

  }

  public class EventHanderWithPass
    extends BaseEventHandler<EventBus> {

    private boolean pass = false;

    @Override
    protected boolean pass(String eventName,
                           Object... parameters) {
      return pass;
    }

    public void setPass(boolean pass) {
      this.pass = pass;
    }

  }

}
