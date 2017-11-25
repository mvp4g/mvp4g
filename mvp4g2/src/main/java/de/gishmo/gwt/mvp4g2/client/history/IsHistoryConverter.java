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
package de.gishmo.gwt.mvp4g2.client.history;

import de.gishmo.gwt.mvp4g2.client.eventbus.IsEventBus;

/**
 * Interface that defines methods to convert a token from/to an event.<br>
 *
 * @param <E>
 *   type of the event bus where the event is defined.
 *
 * @author plcoirier
 */
public interface IsHistoryConverter<E extends IsEventBus> {

  /**
   * Convert a token to event's object and trigger the event bus.<br>
   * <br>
   * Specific information can also be retrieved (from a cookie or server for example).
   *  @param historyName
   *   event's name
   * @param param
   *   string that was stored in the token, used to retrieve event's object (can be null
   *   if no information was stored in the URI)
   * @param eventBus
   */
  void convertFromToken(String historyName,
                               String param,
                               E eventBus);

  /**
   * Return true if the token generated should be crawlable
   *
   * @return true if crawlable
   */
   boolean isCrawlable();

}
