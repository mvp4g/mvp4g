package com.mvp4g.example.client.main.historyConverter;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.history.XmlHistoryConverter;

@History
public class NoParamHistoryConverter implements XmlHistoryConverter<Void> {

	public String convertToToken(String eventType, Void form) {
		//no param to store in url
		return null;
	}

	public void convertFromToken(String eventType, String param, EventBusWithLookup eventBus) {
		eventBus.dispatch(eventType);		
	}

}
