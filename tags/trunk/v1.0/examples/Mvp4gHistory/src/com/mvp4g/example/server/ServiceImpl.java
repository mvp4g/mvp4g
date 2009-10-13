package com.mvp4g.example.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mvp4g.example.client.Service;
import com.mvp4g.example.client.bean.BasicBean;
import com.mvp4g.example.client.bean.DealBean;
import com.mvp4g.example.client.bean.ProductBean;

public class ServiceImpl extends RemoteServiceServlet implements Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5112807154674392059L;
	
	public Map<String, ProductBean> products = new HashMap<String, ProductBean>();
	public Map<String, DealBean> deals = new HashMap<String, DealBean>();
	
	public ServiceImpl(){
		
		ProductBean p1 = new ProductBean("p1", "Right Soccer Shoe", "Perfect to score with your right foot, to go with the Left Soccer Shoe", "$100" );
		ProductBean p2 = new ProductBean("p2", "Left Soccer Shoe", "Perfect to score with your left foot, to go with the Right Soccer Shoe", "$100" );
		ProductBean p3 = new ProductBean("p3", "Soccer Ball", "Ball programmed to go directly in the goal", "$150" );
		
		products.put(p1.getId(), p1);
		products.put(p2.getId(), p2);
		products.put(p3.getId(), p3);
		
		DealBean d1 = new DealBean("d1", "Buy one, get one free", "Buy one right shoe, get the left shoe free!!!", "241");
		DealBean d2 = new DealBean("d2", "Free Delivery", "Free delivery on order of $500 or +", "500++");
		DealBean d3 = new DealBean("d3", "Buy one, get 1/2 free", "Buy one soccer goal, get another half free!!!", "1/2");
		
		deals.put( d1.getId(), d1 );
		deals.put( d2.getId(), d2 );
		deals.put( d3.getId(), d3 );
		
	}

	public DealBean getDealDetails( String id ) {
		return deals.get( id );
	}

	public List<BasicBean> getDeals() {
		return convertToList( deals );
	}

	public ProductBean getProductDetails( String id ) {
		return products.get( id );
	}

	public List<BasicBean> getProducts() {
		return convertToList( products );
	}

	private <T extends BasicBean> List<BasicBean> convertToList( Map<String, T> map ) {
		List<BasicBean> list = new ArrayList<BasicBean>();
		Iterator<String> it = map.keySet().iterator();

		BasicBean bean = null;
		String key = null;
		while ( it.hasNext() ) {
			key = it.next();
			bean = new BasicBean();
			bean.setId( key );
			bean.setName( map.get( key ).getName() );
			list.add( bean );
		}

		return list;

	}

	public List<ProductBean> getCart( String username ) {
		List<ProductBean> productList = new ArrayList<ProductBean>();
		productList.add( products.get( "p1" ) );
		productList.add( products.get( "p2" ) );
		return productList;
	}

}
