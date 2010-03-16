package com.mvp4g.example.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class Event2 extends GwtEvent<Event2.Event2Handler> {

	public interface Event2Handler extends EventHandler {
		void handle( Event2 event );
	}

	public static final GwtEvent.Type<Event2.Event2Handler> TYPE = new GwtEvent.Type<Event2.Event2Handler>();

	private String event = null;

	public Event2( String event ) {
		this.event = event;
	}

	public String getEvent() {
		return event;
	}

	@Override
	protected void dispatch( Event2Handler handler ) {
		handler.handle( this );
	}

	@Override
	public Type<Event2.Event2Handler> getAssociatedType() {
		return TYPE;
	}

}
