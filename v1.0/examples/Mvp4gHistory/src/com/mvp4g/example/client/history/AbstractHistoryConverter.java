package com.mvp4g.example.client.history;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.example.client.ServiceAsync;
import com.mvp4g.example.client.bean.BasicBean;
import com.mvp4g.example.client.lib.HistoryConverter;

public abstract class AbstractHistoryConverter<T extends BasicBean> implements HistoryConverter<T> {

	protected ServiceAsync service = null;

	public void convertFromToken( final String eventType, String param, final EventBus eventBus ) {

		String[] idTab = ( param == null ) ? null : param.split( "=" );

		if ( ( idTab != null ) && ( idTab.length > 1 ) ) {

			serviceCall( idTab[1], new AsyncCallback<T>() {

				public void onFailure( Throwable caught ) {
					// TODO Auto-generated method stub

				}

				public void onSuccess( T result ) {
					eventBus.dispatch( eventType, result );
				}

			} );
		}
	}

	public String convertToToken( T form ) {
		return "id=" + form.getId();
	}

	public void setService( ServiceAsync service ) {
		this.service = service;
	}

	abstract void serviceCall( String id, AsyncCallback<T> callback );

}
