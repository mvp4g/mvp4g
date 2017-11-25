package de.gishmo.gwt.mvp4g2.client.ui.internal;

import de.gishmo.gwt.mvp4g2.client.ui.IsEventHandler;

/**
 * type of the eventBus
 */
public abstract class EventHandlerMetaData<P extends IsEventHandler<?>>
  extends HandlerMetaData {

  private P eventHandler;

  public EventHandlerMetaData(String canonicalName,
                              EventHandlerMetaData.Kind kind,
                              P eventHandler) {
    super(canonicalName,
          kind);
    this.eventHandler = eventHandler;
  }

  public P getEventHandler() {
    return eventHandler;
  }
}
