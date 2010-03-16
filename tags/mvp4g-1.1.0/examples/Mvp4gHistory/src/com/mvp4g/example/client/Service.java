package com.mvp4g.example.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mvp4g.example.client.bean.BasicBean;
import com.mvp4g.example.client.bean.DealBean;
import com.mvp4g.example.client.bean.ProductBean;

@RemoteServiceRelativePath( "service" )
public interface Service extends RemoteService {

	public List<BasicBean> getProducts();

	public ProductBean getProductDetails( String id );

	public List<BasicBean> getDeals();

	public DealBean getDealDetails( String id );

	public List<ProductBean> getCart( String username );

}
