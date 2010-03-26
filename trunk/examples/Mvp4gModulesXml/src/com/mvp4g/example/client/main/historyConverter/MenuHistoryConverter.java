package com.mvp4g.example.client.main.historyConverter;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.history.XmlHistoryConverter;

@History
public class MenuHistoryConverter implements XmlHistoryConverter {
	
	public String onGoToCompany(int start, int end){
		return convertIndexes( start, end );
	}

	public String onGoToProduct(int start, int end){
		return convertIndexes( start, end );
	}

	private String convertIndexes(int start, int end){
		return "start=" + start + "&end=" + end;
	}

	public void convertFromToken( String eventType, String param, EventBusWithLookup eventBus ) {
		String[] paramTab= param.split( "&" );
		int start = Integer.parseInt( paramTab[0].split( "=" )[1]);
		int end = Integer.parseInt( paramTab[1].split( "=" )[1]);
		eventBus.dispatch( eventType, start, end );
	}

}
