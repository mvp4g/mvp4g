package com.mvp4g.example.client.presenter.display;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.ServiceAsync;
import com.mvp4g.example.client.bean.ProductBean;
import com.mvp4g.example.client.view.display.CartDisplayView;
import com.mvp4g.example.client.widget.IView;

@Presenter( view = CartDisplayView.class )
public class CartDisplayPresenter extends LazyPresenter<CartDisplayPresenter.CartDisplayViewInterface, MyEventBus> {

	public interface CartDisplayViewInterface extends LazyView, IView {

		void clear();

		void addProduct( String name, String price, String description );

	}

	@Inject
	private ServiceAsync service = null;

	public void onDisplayCart( String username ) {
		view.clear();
		if ( username != null ) {
			service.getCart( username, new AsyncCallback<List<ProductBean>>() {

				public void onFailure( Throwable caught ) {
					//do sthg						
				}

				public void onSuccess( List<ProductBean> products ) {
										
					for ( ProductBean product : products ) {
						view.addProduct( product.getName(), product.getPrice(), product.getDescription() );
					}

					eventBus.changeMainWidget( view );
				}

			} );
		}

	}	

}
