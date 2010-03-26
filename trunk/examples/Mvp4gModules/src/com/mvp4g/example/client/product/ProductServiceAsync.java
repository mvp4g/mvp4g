package com.mvp4g.example.client.product;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.example.client.product.bean.ProductBean;

public interface ProductServiceAsync {

	void getProducts( int start, int end, AsyncCallback<List<ProductBean>> async );

	void deleteProduct( ProductBean product, AsyncCallback<Void> callback );

	void updateProduct( ProductBean product, AsyncCallback<Void> callback );

	void createProduct( ProductBean product, AsyncCallback<Void> callback );
}
