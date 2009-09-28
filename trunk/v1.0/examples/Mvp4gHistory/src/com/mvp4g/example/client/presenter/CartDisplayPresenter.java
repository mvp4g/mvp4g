package com.mvp4g.example.client.presenter;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.presenter.Presenter;
import com.mvp4g.example.client.EventsEnum;
import com.mvp4g.example.client.ServiceAsync;
import com.mvp4g.example.client.bean.ProductBean;
import com.mvp4g.example.client.presenter.view_interface.display.CartDisplayViewInterface;

public class CartDisplayPresenter extends Presenter<CartDisplayViewInterface> {

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

					eventBus.dispatch( EventsEnum.CHANGE_MAIN_WIDGET, view );
				}

			} );
		}

	}
	
	public void setService(ServiceAsync service){
		this.service = service;
	}

}
