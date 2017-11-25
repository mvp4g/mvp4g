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

package com.mvp4g.client.presenter;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.client.history.NavigationConfirmationInterface;
import com.mvp4g.client.history.NavigationEventCommand;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class PresenterTest {

  private BasePresenter<Object, EventBus> presenter = null;

  @Before
  public void setUp() {
    presenter = new BasePresenter<Object, EventBus>();
  }

  @Test
  public void testDefaultConstructor() {
    assertNull(presenter.getView());
    assertNull(presenter.getEventBus());
  }

  @Test
  public void testSetter() {
    String view = "View";
    BaseEventBus bus = new BaseEventBus() {

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
    presenter.setEventBus(bus);
    presenter.setView(view);
    assertSame(presenter.getView(),
               view);
    assertSame(presenter.getEventBus(),
               bus);
  }

}
