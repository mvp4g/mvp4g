package com.mvp4g.example.client.presenter.display;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.ServiceAsync;
import com.mvp4g.example.client.bean.ProductBean;
import com.mvp4g.example.client.presenter.view_interface.display.CartDisplayViewInterface;
import com.mvp4g.example.client.view.display.CartDisplayView;

@Presenter( view = CartDisplayView.class )
public class CartDisplayPresenter extends LazyPresenter<CartDisplayViewInterface, MyEventBus> {

	private ServiceAsync service = null;
	
	public void onDisplayCart( String username ) {
		view.clear();
		if ( username != null ) {
			service.getCart( username, new AsyncCallback<List<ProductBean>>() {

				public void onFailure( Throwable caught ) {
					//do sthg						
				}

				public void onSuccess( List<ProductBean> products ) {
					int size = products.size();
					for ( int i = 0; i < size; i++ ) {
						view.addProduct( products.get( i ) );
					}

					eventBus.changeMainWidget( view.getViewWidget() );
				}

			} );
		}

	}

	@InjectService
	public void setService( ServiceAsync service ) {
		this.service = service;
	}

}
