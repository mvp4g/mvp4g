package com.mvp4g.example.client.history;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.example.client.bean.DealBean;

public class DealHistoryConverter extends AbstractHistoryConverter<DealBean> {

	@Override
	void serviceCall( String id, AsyncCallback<DealBean> callback ) {
		service.getDealDetails( id, callback );	
	}

}
