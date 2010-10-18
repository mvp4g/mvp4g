package com.mvp4g.client.gwt_event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * A widget that implements this interface provides registration for {@link LoadHandler} instances.
 */
public interface HasLoadHandlers extends HasHandlers {

	/**
	 * Adds a {@link LoadEvent} handler.
	 * 
	 * @param handler
	 *            the load handler
	 * @return {@link HandlerRegistration} used to remove this handler
	 */
	HandlerRegistration addLoadHandler( LoadHandler handler );

}
