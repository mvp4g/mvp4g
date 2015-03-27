package com.mvp4g.client.gwt_event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler for {@link UnloadEvent} events.
 */
public interface UnloadHandler
  extends EventHandler {

  /**
   * Called when a load event is fired.
   */
  public void onUnload();

}
