package com.mvp4g.example.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class Event10 extends GwtEvent<Event10.Event10Handler> {

	public interface Event10Handler extends EventHandler {
		void handle( Event10 event );
	}
	
	public static final GwtEvent.Type<Event10.Event10Handler> TYPE = new GwtEvent.Type<Event10.Event10Handler>();

	private String event = null;

	public Event10( String event ) {
		this.event = event;
	}

	public String getEvent() {
		return event;
	}

	@Override
	protected void dispatch( Event10Handler handler ) {
		handler.handle( this );
	}

	@Override
	public Type<Event10.Event10Handler> getAssociatedType() {
		return TYPE;
	}

}
