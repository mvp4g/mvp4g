package com.mvp4g.client.gwt_event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a widget unload event. This event should be fired when a widget is removed from the DOM.
 */
public class UnloadEvent
  extends GwtEvent<UnloadHandler> {

  public static Type<UnloadHandler> TYPE = new Type<UnloadHandler>();

  /*
   * (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
   */
  @Override
  protected void dispatch(UnloadHandler handler) {
    handler.onUnload();
  }

  /*
   * (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<UnloadHandler> getAssociatedType() {
    return TYPE;
  }

}
