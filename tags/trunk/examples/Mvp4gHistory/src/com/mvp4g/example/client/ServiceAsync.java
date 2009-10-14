package com.mvp4g.example.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.example.client.bean.BasicBean;
import com.mvp4g.example.client.bean.DealBean;
import com.mvp4g.example.client.bean.ProductBean;

public interface ServiceAsync {

	void getProducts( AsyncCallback<List<BasicBean>> callback );

	void getDealDetails( String id, AsyncCallback<DealBean> callback );

	void getDeals( AsyncCallback<List<BasicBean>> callback );

	void getProductDetails(String id,  AsyncCallback<ProductBean> callback );

	void getCart( String username, AsyncCallback<List<ProductBean>> callback );

}
