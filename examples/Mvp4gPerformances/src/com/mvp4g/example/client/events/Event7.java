package com.mvp4g.example.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class Event7 extends GwtEvent<Event7.Event7Handler> {

	public interface Event7Handler extends EventHandler {
		void handle( Event7 event );
	}

	public static final GwtEvent.Type<Event7.Event7Handler> TYPE = new GwtEvent.Type<Event7.Event7Handler>();

	private String event = null;

	public Event7( String event ) {
		this.event = event;
	}

	public String getEvent() {
		return event;
	}

	@Override
	protected void dispatch( Event7Handler handler ) {
		handler.handle( this );
	}

	@Override
	public Type<Event7.Event7Handler> getAssociatedType() {
		return TYPE;
	}

}
