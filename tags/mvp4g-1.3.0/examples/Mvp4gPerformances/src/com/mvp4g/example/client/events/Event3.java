package com.mvp4g.example.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class Event3 extends GwtEvent<Event3.Event3Handler> {

	public interface Event3Handler extends EventHandler {
		void handle( Event3 event );
	}

	public static final GwtEvent.Type<Event3.Event3Handler> TYPE = new GwtEvent.Type<Event3.Event3Handler>();

	private String event = null;

	public Event3( String event ) {
		this.event = event;
	}

	public String getEvent() {
		return event;
	}

	@Override
	protected void dispatch( Event3Handler handler ) {
		handler.handle( this );
	}

	@Override
	public Type<Event3.Event3Handler> getAssociatedType() {
		return TYPE;
	}

}
