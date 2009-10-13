package com.mvp4g.example.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class Event6 extends GwtEvent<Event6.Event6Handler> {

	public interface Event6Handler extends EventHandler {
		void handle( Event6 event );
	}

	public static final GwtEvent.Type<Event6.Event6Handler> TYPE = new GwtEvent.Type<Event6.Event6Handler>();

	private String event = null;

	public Event6( String event ) {
		this.event = event;
	}

	public String getEvent() {
		return event;
	}

	@Override
	protected void dispatch( Event6Handler handler ) {
		handler.handle( this );
	}

	@Override
	public Type<Event6.Event6Handler> getAssociatedType() {
		return TYPE;
	}

}
