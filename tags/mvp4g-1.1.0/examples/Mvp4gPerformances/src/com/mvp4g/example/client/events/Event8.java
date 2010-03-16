package com.mvp4g.example.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class Event8 extends GwtEvent<Event8.Event8Handler> {

	public interface Event8Handler extends EventHandler {
		void handle( Event8 event );
	}

	public static final GwtEvent.Type<Event8.Event8Handler> TYPE = new GwtEvent.Type<Event8.Event8Handler>();

	private String event = null;

	public Event8( String event ) {
		this.event = event;
	}

	public String getEvent() {
		return event;
	}

	@Override
	protected void dispatch( Event8Handler handler ) {
		handler.handle( this );
	}

	@Override
	public Type<Event8.Event8Handler> getAssociatedType() {
		return TYPE;
	}

}
