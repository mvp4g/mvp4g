package com.mvp4g.util.test_tools.annotation;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;

@History
public class OneHistory implements HistoryConverter<EventBus> {

	public void convertFromToken( String eventType, String param, EventBus eventBus ) {
		// TODO Auto-generated method stub
		
	}

	public boolean isCrawlable() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
