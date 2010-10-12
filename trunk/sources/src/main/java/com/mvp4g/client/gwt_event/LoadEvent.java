package com.mvp4g.client.gwt_event;

import com.google.gwt.event.shared.GwtEvent;

public class LoadEvent extends GwtEvent<LoadHandler> {

	public static Type<LoadHandler> TYPE = new Type<LoadHandler>();

	@Override
	protected void dispatch( LoadHandler handler ) {
		handler.onLoad();
	}

	@Override
	public Type<LoadHandler> getAssociatedType() {
		return TYPE;
	}

}
