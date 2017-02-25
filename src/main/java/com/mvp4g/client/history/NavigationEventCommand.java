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

import com.mvp4g.client.event.EventBus;

/**
 * Command that represents a navigation event.This command is passed to the Navigation Confirmation
 * object that may allow the navigation by executing this command.
 *
 * @author plcoirier
 */
public abstract class NavigationEventCommand {

  private EventBus eventBus;

  public NavigationEventCommand(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  /**
   * Execute the command by firing the event. Equivalent to fireEvent(true).
   */
  public void fireEvent() {
    fireEvent(true);
  }

  /**
   * Execute the command by firing the event. Remove the NavigationConfirmation object before
   * firing the event if needed.
   *
   * @param removeConfirmation
   *   if true, NavigationConfirmation is removed
   */
  public void fireEvent(boolean removeConfirmation) {
    if (removeConfirmation) {
      eventBus.setNavigationConfirmation(null);
    }
    execute();
  }

  /**
   * Action to do when the event is fired.
   */
  abstract protected void execute();

}
