package com.mvp4g.example.client.company;

import com.google.gwt.core.client.GWT;
import com.mvp4g.client.event.EventFilter;

public class CompanyEventFilter implements EventFilter<CompanyEventBus> {

	public boolean filterEvent( String eventType, Object[] params, CompanyEventBus eventBus )	{
		GWT.log( "CompanyEventFilter: " + eventType );
		return true;
	}
}
