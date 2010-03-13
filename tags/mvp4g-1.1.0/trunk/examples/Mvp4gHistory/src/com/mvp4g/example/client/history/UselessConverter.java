package com.mvp4g.example.client.history;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.history.HistoryConverter;

@History
public class UselessConverter implements HistoryConverter<Object, EventBusWithLookup> {

	public void convertFromToken( String eventType, String param, EventBusWithLookup eventBus ) {
		// TODO Auto-generated method stub

	}

	public String convertToToken( String eventType, Object form ) {
		// TODO Auto-generated method stub
		return null;
	}

}
