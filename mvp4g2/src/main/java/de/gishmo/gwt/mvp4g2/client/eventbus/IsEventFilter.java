package de.gishmo.gwt.mvp4g2.client.eventbus;

/**
 * This interface can be implemented in order to receive events all events from an EventBus.<br>
 * <br>
 * Classes that implement this interface are associated with an EventBus class by adding the<br>
 * filter class literal to the filterClasses list in the EventBus' @Events annotation<br>
 *
 * @author Nick Hebner
 */
public interface IsEventFilter<E extends IsEventBus> {

  /**
   * Filter an event
   *
   * @param eventBus
   *   event bus used to fire the event
   * @param eventName
   *   name of the event to filter
   * @param params
   *   objects sent with the event
   *
   * @return false if event should be stopped, true otherwise
   */
  boolean filterEvent(E eventBus,
                      String eventName,
                      Object... params);

}
