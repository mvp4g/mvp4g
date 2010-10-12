package com.mvp4g.client.gwt_event;

import com.google.gwt.event.shared.GwtEvent;

public class UnloadEvent extends GwtEvent<UnloadHandler> {

	public static Type<UnloadHandler> TYPE = new Type<UnloadHandler>();

	@Override
	protected void dispatch( UnloadHandler handler ) {
		handler.onUnload();		
	}

	@Override
	public Type<UnloadHandler> getAssociatedType() {
		return TYPE;
	}

}
