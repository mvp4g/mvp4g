package com.mvp4g.example.client.history;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.annotation.History.HistoryConverterType;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;

@History( type = HistoryConverterType.AUTO )
public class HistoryConverter5 implements HistoryConverter<EventBus> {
	
	String convertToToken( String eventName, String s ) {
		return null;
	}
	
	public void convertToToken( String eventName, int i ) {
		
	}

	public String convertToToken( String eventName ) {
		return null;
	}
	
	public String convertToToken( String eventName, int i, String s ) {
		return null;
	}

	@Override
	public void convertFromToken( String eventType, String param, EventBus eventBus ) {

	}

	@Override
	public boolean isCrawlable() {
		return false;
	}

}
