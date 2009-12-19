package com.mvp4g.example.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mvp4g.example.client.product.ProductService;
import com.mvp4g.example.client.product.bean.ProductBean;

public class ProductServiceImpl extends RemoteServiceServlet implements
		ProductService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4546417863195659071L;

	public void deleteProduct(ProductBean product) {
		// TODO Auto-generated method stub
	}

	public List<ProductBean> getProducts() {
		List<ProductBean> companies = new ArrayList<ProductBean>();
		for(int i=0; i<10; i++){
			companies.add(new ProductBean(i, "Product " + i));
		}
		return companies;
	}

	public void updateProduct(ProductBean product) {
		// TODO Auto-generated method stub
	}

	public void createProduct(ProductBean product) {
		// TODO Auto-generated method stub		
	}

}
