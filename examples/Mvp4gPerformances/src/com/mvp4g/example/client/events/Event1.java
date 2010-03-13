package com.mvp4g.example.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class Event1 extends GwtEvent<Event1.Event1Handler> {

	public interface Event1Handler extends EventHandler {
		void handle( Event1 event );
	}

	public static final GwtEvent.Type<Event1.Event1Handler> TYPE = new GwtEvent.Type<Event1.Event1Handler>();

	private String event = null;

	public Event1( String event ) {
		this.event = event;
	}

	public String getEvent() {
		return event;
	}

	@Override
	protected void dispatch( Event1Handler handler ) {
		handler.handle( this );
	}

	@Override
	public Type<Event1.Event1Handler> getAssociatedType() {
		return TYPE;
	}

}
