package com.mvp4g.example.client.history;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.history.HistoryConverter;

@History
public class HistoryConverter4 implements HistoryConverter<EventBusWithLookup> {

	@Override
	public void convertFromToken( String eventType, String param, EventBusWithLookup eventBus ) {

	}

	@Override
	public boolean isCrawlable() {
		return false;
	}

}
