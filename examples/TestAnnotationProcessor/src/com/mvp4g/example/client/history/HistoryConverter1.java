package com.mvp4g.example.client.history;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;

@History
public class HistoryConverter1 implements HistoryConverter<EventBus> {

	public String onEventHistoryOk() {
		return null;
	}
	
	public String onEventHistoryOkWithParams(String param){
		return null;
	}
	
	public String onEeventHistoryWrongParams(){
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
