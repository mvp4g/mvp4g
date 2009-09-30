package com.mvp4g.client.history;

import com.mvp4g.client.event.EventBus;

public interface HistoryConverter<T> {

	public String convertToToken(T form);
	public void convertFromToken(String eventType, String param, EventBus eventBus);
	
}
