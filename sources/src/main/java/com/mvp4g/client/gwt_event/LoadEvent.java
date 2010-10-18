package com.mvp4g.client.gwt_event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a widget load event. This event should be fired when a widget is added to the DOM.
 */
public class LoadEvent extends GwtEvent<LoadHandler> {

	public static Type<LoadHandler> TYPE = new Type<LoadHandler>();

	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch( LoadHandler handler ) {
		handler.onLoad();
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<LoadHandler> getAssociatedType() {
		return TYPE;
	}

}
