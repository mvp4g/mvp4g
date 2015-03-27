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
   * @param removeConfirmation if true, NavigationConfirmation is removed
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
