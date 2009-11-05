package com.mvp4g.example.client.presenter.display;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.bean.ProductBean;
import com.mvp4g.example.client.presenter.view_interface.display.ProductDisplayViewInterface;
import com.mvp4g.example.client.view.display.ProductDisplayView;

@Presenter( view = ProductDisplayView.class )
public class ProductDisplayPresenter extends BasePresenter<ProductDisplayViewInterface, MyEventBus> {

	public void onDisplayProduct( ProductBean product ) {
		view.getName().setText( product.getName() );
		view.getDescription().setText( product.getDescription() );
		view.getPrice().setText( product.getPrice() );
		eventBus.changeMainWidget( view.getViewWidget() );
	}

}
