package com.mvp4g.example.client.presenter.display;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.bean.ProductBean;
import com.mvp4g.example.client.presenter.view_interface.display.BasicBeanDisplayViewInterface;
import com.mvp4g.example.client.view.display.ProductDisplayView;

@Presenter( view = ProductDisplayView.class )
public class ProductDisplayPresenter extends LazyPresenter<ProductDisplayPresenter.ProductDisplayViewInterface, MyEventBus> {

	public interface ProductDisplayViewInterface extends BasicBeanDisplayViewInterface {

		void setPrice( String price );

	}

	public void onDisplayProduct( ProductBean product ) {
		view.setName( product.getName() );
		view.setDescription( product.getDescription() );
		view.setPrice( product.getPrice() );
		eventBus.changeMainWidget( view );
	}

}
