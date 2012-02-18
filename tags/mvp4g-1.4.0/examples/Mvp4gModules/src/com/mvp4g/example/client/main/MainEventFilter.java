package com.mvp4g.example.client.main;

import com.mvp4g.client.event.EventFilter;

public class MainEventFilter implements EventFilter<MainEventBus> {

	public boolean filterEvent( String eventType, Object[] params, MainEventBus eventBus )	{
		eventBus.setFilteringEnabledForNextOne( false );
		eventBus.displayMessage( "Main Event " + eventType + " has been filtered." );
		return false;
	}
}
