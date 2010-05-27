package com.mvp4g.example.client.company;

import com.google.gwt.core.client.GWT;
import com.mvp4g.client.event.EventFilter;

public class CompanyEventFilter implements EventFilter {

	@Override
	public boolean filterEvent( String eventType, Object[] params )	{
		GWT.log( "CompanyEventFilter: " + eventType );
		return true;
	}
}
