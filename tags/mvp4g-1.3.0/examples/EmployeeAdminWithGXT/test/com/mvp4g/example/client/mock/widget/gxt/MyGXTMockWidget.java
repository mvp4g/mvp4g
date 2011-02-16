package com.mvp4g.example.client.mock.widget.gxt;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Listener;

public class MyGXTMockWidget {

	@SuppressWarnings( "unchecked" )
	Map<EventType, Listener> listeners = new HashMap<EventType, Listener>();

	public void addListener( EventType eventType, Listener<? extends BaseEvent> listener ) {
		listeners.put( eventType, listener );
	}

	@SuppressWarnings( "unchecked" )
	public Listener getListener( EventType eventType ) {
		return listeners.get( eventType );
	}

}
