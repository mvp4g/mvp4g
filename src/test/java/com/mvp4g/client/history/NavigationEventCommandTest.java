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

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.client.test_tools.EventBusWithLookUpStub;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class NavigationEventCommandTest {

  private NavigationEventCommand cmd;

  private EventBusWithLookUpStub eventBus;

  private NavigationConfirmationInterface conf;

  @Before
  public void setUp() {
    eventBus = new EventBusWithLookUpStub();
    cmd = new NavigationEventCommand(eventBus) {

      @Override
      protected void execute() {
        //nothing to do
      }
    };
    conf = new NavigationConfirmationInterface() {

      public void confirm(NavigationEventCommand event) {
        //nothing to do
      }
    };
  }

  @Test
  public void testFireEvent() {
    eventBus.setNavigationConfirmation(conf);
    cmd.fireEvent();
    assertNull(eventBus.getLastNavigationConfirmation());

    eventBus.setNavigationConfirmation(conf);
    cmd.fireEvent(true);
    assertNull(eventBus.getLastNavigationConfirmation());

    eventBus.setNavigationConfirmation(conf);
    cmd.fireEvent(false);
    assertSame(conf,
               eventBus.getLastNavigationConfirmation());
  }

}
