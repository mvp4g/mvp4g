package com.mvp4g.client.gwt_event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * A widget that implements this interface provides registration for {@link UnloadHandler}
 * instances.
 */
public interface HasUnloadHandlers
  extends HasHandlers {

  /**
   * Adds a {@link UnloadEvent} handler.
   *
   * @param handler
   *   the unload handler
   *
   * @return {@link HandlerRegistration} used to remove this handler
   */
  HandlerRegistration addUnloadHandler(UnloadHandler handler);

}
