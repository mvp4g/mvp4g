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

package com.mvp4g.rebind.test_tools.annotation.events;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Forward;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.NotFoundHistory;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.rebind.test_tools.annotation.history_converters.HistoryConverterForEvent;
import com.mvp4g.rebind.test_tools.annotation.presenters.PresenterWithName;
import com.mvp4g.rebind.test_tools.annotation.presenters.SimplePresenter01;

@Events(startPresenter = PresenterWithName.class)
public interface EventBusOk
  extends EventBus {

  @NotFoundHistory
  @Event(handlerNames = "name",
         calledMethod = "treatEvent1",
         historyConverterName = "history")
  void event1(String obj);

  @Start
  @InitHistory
  @Forward
  @Event(handlers = PresenterWithName.class,
         historyConverter = HistoryConverterForEvent.class,
         navigationEvent = true,
         passive = true)
  String event2();

  @Event(activate = PresenterWithName.class,
         deactivate = PresenterWithName.class,
         generate = PresenterWithName.class,
         activateNames = "name",
         deactivateNames = "name",
         generateNames = "name")
  void event3();

  @Event(activate = { PresenterWithName.class,
                      SimplePresenter01.class },
         deactivate = { PresenterWithName.class,
                        SimplePresenter01.class },
         generate = { PresenterWithName.class,
                      SimplePresenter01.class },
         activateNames = { "name",
                           "name1" },
         deactivateNames = { "name",
                             "name1" },
         generateNames = { "name",
                           "name1" })
  void event4();

  @Event(bind = PresenterWithName.class,
         bindNames = { "name",
                       "name1" })
  void event5();

}
