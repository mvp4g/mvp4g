package com.mvp4g.client.history;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.event.EventBus;

@History
public class ClearHistory implements HistoryConverter<Void, EventBus> {

	public void convertFromToken(String eventType, String param,
			EventBus eventBus) {
		throw new RuntimeException("ClearHistory: convertFromToken should never be called");		
	}

	public String convertToToken(String eventType, Void form) {
		throw new RuntimeException("ClearHistory: convertToToken should never be called");		
	}

}
