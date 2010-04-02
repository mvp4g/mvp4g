package com.mvp4g.example.client.main.historyConverter;

import com.google.inject.Inject;
import com.mvp4g.client.annotation.History;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.example.client.main.MainEventBus;
import com.mvp4g.example.client.util.token.TokenGenerator;

@History
public class MenuHistoryConverter implements HistoryConverter<MainEventBus> {

	@Inject
	private TokenGenerator tokenGenerator;

	public String onGoToCompany( int start, int end ) {
		return convertIndexes( start, end );
	}

	public String onGoToProduct( Integer start, Integer end ) {
		return convertIndexes( start, end );
	}

	private String convertIndexes( int start, int end ) {
		return tokenGenerator.convertToToken( "start", Integer.toString( start ) ) + "&"
				+ tokenGenerator.convertToToken( "end", Integer.toString( end ) );
	}

	public void convertFromToken( String eventType, String param, MainEventBus eventBus ) {
		String[] paramTab = param.split( "&" );
		int start = Integer.parseInt( tokenGenerator.convertFromToken( paramTab[0] ) );
		int end = Integer.parseInt( tokenGenerator.convertFromToken( paramTab[1] ) );
		eventBus.dispatch( eventType, start, end );
	}

}
