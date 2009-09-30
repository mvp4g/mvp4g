package com.mvp4g.example.client.history;

import com.google.gwt.user.client.Cookies;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.EventsEnum;

public class ShowCartConverter implements HistoryConverter<Object> {

	public void convertFromToken( String eventType, String param, EventBus eventBus ) {
		String username = Cookies.getCookie( Constants.USERNAME );
		if(username == null){
			eventBus.dispatch( EventsEnum.DISPLAY_MESSAGE, "Please login to display your cart." );
		}
		else{
			eventBus.dispatch( eventType, username );
		}
	}

	public String convertToToken( Object form ) {
		return null;
	}

}
