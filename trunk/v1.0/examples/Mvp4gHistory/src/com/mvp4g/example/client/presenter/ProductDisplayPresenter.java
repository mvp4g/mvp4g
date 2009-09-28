package com.mvp4g.example.client.presenter;

import com.mvp4g.client.presenter.Presenter;
import com.mvp4g.example.client.EventsEnum;
import com.mvp4g.example.client.bean.ProductBean;
import com.mvp4g.example.client.presenter.view_interface.display.ProductDisplayViewInterface;

public class ProductDisplayPresenter extends Presenter<ProductDisplayViewInterface> {
	
	public void onDisplayProduct(ProductBean product){
		view.getName().setText(product.getName());
		view.getDescription().setText( product.getDescription() );
		view.getPrice().setText( product.getPrice() );
		eventBus.dispatch( EventsEnum.CHANGE_MAIN_WIDGET, view );
	}

}
