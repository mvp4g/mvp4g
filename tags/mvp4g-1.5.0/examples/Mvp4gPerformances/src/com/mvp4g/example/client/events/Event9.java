package com.mvp4g.example.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class Event9 extends GwtEvent<Event9.Event9Handler> {

	public interface Event9Handler extends EventHandler {
		void handle( Event9 event );
	}

	public static final GwtEvent.Type<Event9.Event9Handler> TYPE = new GwtEvent.Type<Event9.Event9Handler>();

	private String event = null;

	public Event9( String event ) {
		this.event = event;
	}

	public String getEvent() {
		return event;
	}

	@Override
	protected void dispatch( Event9Handler handler ) {
		handler.handle( this );
	}

	@Override
	public Type<Event9.Event9Handler> getAssociatedType() {
		return TYPE;
	}

}
