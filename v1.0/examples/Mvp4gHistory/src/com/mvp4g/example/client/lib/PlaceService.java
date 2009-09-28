package com.mvp4g.example.client.lib;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.mvp4g.client.event.EventBus;

public class PlaceService implements ValueChangeHandler<String> {

	private static final String FIRST = "?";
	private static final String FIRST_RE = "\\" + FIRST;

	private EventBus eventBus = null;
	private String initEvent = null;

	private Map<String, HistoryConverter> converters = new HashMap<String, HistoryConverter>();

	public PlaceService( EventBus eventBus ) {
		this.eventBus = eventBus;
		History.addValueChangeHandler( this );
	}

	public void onValueChange( ValueChangeEvent<String> event ) {
		String token = event.getValue();
		if ( ( token != null ) && ( token.length() > 0 ) ) {
			String[] tokenTab = token.split( FIRST_RE );
			String eventType = tokenTab[0];
			String param = ( tokenTab.length > 1 ) ? tokenTab[1] : null;
			converters.get( eventType ).convertFromToken( eventType, param, eventBus );
		}
		else{
			eventBus.dispatch( initEvent );
		}
	}

	public void place( String eventType, Object form ) {
		String param = converters.get( eventType ).convertToToken( form );
		String token = ( ( param == null ) || ( param.length() == 0 ) ) ? eventType : ( eventType + FIRST + param );
		History.newItem( token, false );
	}

	public void addConverter( String eventType, HistoryConverter converter ) {
		converters.put( eventType, converter );
	}
	
	public void setInitEvent(String initEvent){
		this.initEvent = initEvent;
	}

}
