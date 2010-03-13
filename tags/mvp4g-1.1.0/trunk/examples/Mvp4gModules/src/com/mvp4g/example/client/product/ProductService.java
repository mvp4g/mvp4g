package com.mvp4g.example.client.product;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mvp4g.example.client.product.bean.ProductBean;

@RemoteServiceRelativePath( "product" )
public interface ProductService extends RemoteService {

	public List<ProductBean> getProducts();

	public void createProduct( ProductBean product );

	public void deleteProduct( ProductBean product );

	public void updateProduct( ProductBean product );

}
