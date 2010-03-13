package com.mvp4g.example.client.history;

import com.google.gwt.user.client.Cookies;
import com.mvp4g.client.annotation.History;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.MyEventBus;

@History
public class ShowCartConverter implements HistoryConverter<Object, MyEventBus> {

	public void convertFromToken( String eventType, String param, MyEventBus eventBus ) {
		String username = Cookies.getCookie( Constants.USERNAME );
		if ( username == null ) {
			eventBus.displayMessage( "Please login to display your cart." );
		} else {
			eventBus.displayCart( username );
		}
	}

	public String convertToToken( String eventType, Object form ) {
		return null;
	}

}
