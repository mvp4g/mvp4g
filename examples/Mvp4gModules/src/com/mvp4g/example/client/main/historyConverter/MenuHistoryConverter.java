package com.mvp4g.example.client.main.historyConverter;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.example.client.main.MainEventBus;

@History
public class MenuHistoryConverter implements HistoryConverter<MainEventBus> {

	public String onGoToCompany(int start, int end){
		return convertIndexes( start, end );
	}

	public String onGoToProduct(Integer start, Integer end){
		return convertIndexes( start, end );
	}

	private String convertIndexes(int start, int end){
		return "start=" + start + "&end=" + end;
	}

	public void convertFromToken( String eventType, String param, MainEventBus eventBus ) {
		String[] paramTab= param.split( "&" );
		int start = Integer.parseInt( paramTab[0].split( "=" )[1]);
		int end = Integer.parseInt( paramTab[1].split( "=" )[1]);
		eventBus.dispatch( eventType, start, end );
	}

}
