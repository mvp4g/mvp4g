package com.mvp4g.example.client.history;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;

public class HistoryConverter3 implements HistoryConverter<EventBus> {

	@Override
	public void convertFromToken( String eventType, String param, EventBus eventBus ) {

	}

	@Override
	public boolean isCrawlable() {
		return false;
	}

}
