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

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.event.EventBus;

/**
 * This particular history converter will clear browse history from GWT token whenever an event to
 * which it is associated is fired.
 *
 * @author plcoirier
 */
@History
public class ClearHistory
  implements HistoryConverter<EventBus> {

  public void convertFromToken(String historyName,
                               String param,
                               EventBus eventBus) {
    throw new RuntimeException("ClearHistory: convertFromToken should never be called");
  }

  public boolean isCrawlable() {
    return false;
  }

}
