package com.mvp4g.example.client.history;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.example.client.bean.ProductBean;

public class ProductHistoryConverter extends AbstractHistoryConverter<ProductBean> {

	@Override
	void serviceCall( String id, AsyncCallback<ProductBean> callback ) {
		service.getProductDetails( id, callback );	
	}

}
