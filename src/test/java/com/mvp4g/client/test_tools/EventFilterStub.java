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

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventFilter;

public class EventFilterStub
  implements EventFilter<EventBus> {

  private boolean filter;

  public boolean filterEvent(String eventType,
                             Object[] params,
                             EventBus eventBus) {
    return filter;
  }

  /**
   * @return the filter
   */
  public boolean isFilter() {
    return filter;
  }

  /**
   * @param filter
   *   the filter to set
   */
  public void setFilter(boolean filter) {
    this.filter = filter;
  }

}
