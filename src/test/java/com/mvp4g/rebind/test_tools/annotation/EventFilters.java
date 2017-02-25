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

package com.mvp4g.rebind.test_tools.annotation;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.event.EventFilter;

public class EventFilters {

  public static class EventFilter1
    implements EventFilter<EventBus> {

    public boolean filterEvent(String eventType,
                               Object[] params,
                               EventBus eventBus) {
      return false;
    }

  }

  public static class EventFilter2
    implements EventFilter<EventBus> {

    public boolean filterEvent(String eventType,
                               Object[] params,
                               EventBus eventBus) {
      return false;
    }

  }

  public static class EventFilter3
    implements EventFilter<EventBusWithLookup> {

    public boolean filterEvent(String eventType,
                               Object[] params,
                               EventBusWithLookup eventBus) {
      return false;
    }

  }

}
