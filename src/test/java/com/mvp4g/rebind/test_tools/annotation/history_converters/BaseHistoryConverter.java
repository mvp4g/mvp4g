package com.mvp4g.rebind.test_tools.annotation.history_converters;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;

public class BaseHistoryConverter implements HistoryConverter<EventBus> {

	public void convertFromToken( String eventType, String param, EventBus eventBus ) {
	}

	public boolean isCrawlable() {
		return false;
	}

}
