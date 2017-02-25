package com.mvp4g.client.history;

/**
 * Define an interface that allow to confirm or stop a navigation event.
 *
 * @author plcoirier
 */
public interface NavigationConfirmationInterface {

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
