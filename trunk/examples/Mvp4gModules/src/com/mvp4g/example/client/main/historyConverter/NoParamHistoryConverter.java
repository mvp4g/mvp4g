package com.mvp4g.example.client.main.historyConverter;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.example.client.main.MainEventBus;

@History
public class NoParamHistoryConverter implements HistoryConverter<Void, MainEventBus> {

	public String convertToToken(String eventType, Void form) {
		//no param to store in url
		return null;
	}

	public void convertFromToken(String eventType, String param, MainEventBus eventBus) {
		eventBus.dispatch(eventType);		
	}

}
