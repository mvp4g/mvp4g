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

/**
 * Define an interface that allow to confirm or stop a navigation event.
 *
 * @author plcoirier
 */
public interface IsNavigationConfirmation {

  /**
   * Call to stop or confirm a navigation event. To confirm an event, the event's fireEvent method
   * should be called. If you don't call this method, the event will automatically be stopped.<br>
   * <br>
   * By default, when the event is confirmed, the NavigationConfirmation is removed from the
   * application.
   *
   * @param event
   *   command representation of the event to confirm or stop
   */
  void confirm(NavigationEventCommand event);

}
