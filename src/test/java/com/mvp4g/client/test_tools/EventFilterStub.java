package com.mvp4g.client.test_tools;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventFilter;

public class EventFilterStub implements EventFilter<EventBus> {

	private boolean filter;

	public boolean filterEvent( String eventType, Object[] params, EventBus eventBus ) {
		return filter;
	}

	/**
	 * @return the filter
	 */
	public boolean isFilter() {
		return filter;
	}

	/**
	 * @param filter
	 *            the filter to set
	 */
	public void setFilter( boolean filter ) {
		this.filter = filter;
	}

}
