package com.mvp4g.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mvp4gWithEdgebox implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Button c = new Button( "Click me" );
		c.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {

				PingService pingService = GWT.create( PingService.class );
				pingService.ping( "Hello", new RequestCallback() {

					public void onError( Request arg0, Throwable arg1 ) {

					}

					public void onResponseReceived( Request arg0, Response resp ) {
						RootPanel.get().add( new Label( resp.getText() ) );
					}
				} );
			}
		} );
		RootPanel.get().add( c );
	}
}
