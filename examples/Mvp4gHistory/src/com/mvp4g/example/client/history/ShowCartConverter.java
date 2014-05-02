package com.mvp4g.example.client.history;

import com.google.gwt.user.client.Cookies;
import com.mvp4g.client.annotation.History;
import com.mvp4g.client.annotation.History.HistoryConverterType;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.MyEventBus;

@History( type = HistoryConverterType.NONE )
public class ShowCartConverter implements HistoryConverter<MyEventBus> {

	public void convertFromToken( String eventType, String param, MyEventBus eventBus ) {
		String username = Cookies.getCookie( Constants.USERNAME );
		if ( username == null ) {
			eventBus.displayMessage( "Please login to display your cart." );
		} else {
			eventBus.displayCart( username );
		}
	}

	public boolean isCrawlable() {
		return false;
	}

}
