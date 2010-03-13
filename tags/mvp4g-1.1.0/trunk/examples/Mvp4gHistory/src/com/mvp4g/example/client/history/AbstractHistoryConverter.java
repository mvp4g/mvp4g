package com.mvp4g.example.client.history;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.ServiceAsync;
import com.mvp4g.example.client.bean.BasicBean;

public abstract class AbstractHistoryConverter<T extends BasicBean> implements HistoryConverter<T, MyEventBus> {

	protected ServiceAsync service = null;

	public void convertFromToken( final String eventType, String param, final MyEventBus eventBus ) {

		String[] idTab = ( param == null ) ? null : param.split( "=" );

		if ( ( idTab != null ) && ( idTab.length > 1 ) ) {

			serviceCall( idTab[1], new AsyncCallback<T>() {

				public void onFailure( Throwable caught ) {
					// TODO Auto-generated method stub

				}

				public void onSuccess( T result ) {
					sendEvent( eventBus, result );
				}

			} );
		}
	}

	public String convertToToken( String eventType, T form ) {
		return "id=" + form.getId();
	}

	@InjectService
	public void setService( ServiceAsync service ) {
		this.service = service;
	}

	abstract void serviceCall( String id, AsyncCallback<T> callback );

	abstract void sendEvent( MyEventBus eventBus, T result );

}
