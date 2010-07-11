package com.mvp4g.example.client.main;

import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.event.EventFilter;

public class MainEventFilter implements EventFilter<EventBusWithLookup> {

	public boolean filterEvent( String eventType, Object[] params, EventBusWithLookup eventBus ) {
		eventBus.setFilteringEnabledForNextOne( false );
		eventBus.dispatch( "displayMessage", "Main Event " + eventType + " has been filtered." );
		return false;
	}
}
