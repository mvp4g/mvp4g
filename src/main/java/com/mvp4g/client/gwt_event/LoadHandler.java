package com.mvp4g.client.gwt_event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler for {@link LoadEvent} events.
 */
public interface LoadHandler
  extends EventHandler {

  /**
   * Called when a load event is fired.
   */
  void onLoad();

}
