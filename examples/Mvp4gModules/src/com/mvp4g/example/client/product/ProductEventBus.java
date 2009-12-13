package com.mvp4g.example.client.product;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.example.client.product.bean.ProductBean;
import com.mvp4g.example.client.product.presenter.ProductCreationPresenter;
import com.mvp4g.example.client.product.presenter.ProductDisplayPresenter;
import com.mvp4g.example.client.product.presenter.ProductEditPresenter;
import com.mvp4g.example.client.product.presenter.ProductListPresenter;
import com.mvp4g.example.client.product.presenter.ProductRootPresenter;
import com.mvp4g.example.client.product.view.ProductRootView;

@Events(startView=ProductRootView.class, module=ProductModule.class)
public interface ProductEventBus extends EventBus {
	
	@Event(handlers=ProductCreationPresenter.class)
	public void goToCreation();
	
	@Event(handlers=ProductListPresenter.class)
	public void goToList();
	
	@Event(handlers=ProductEditPresenter.class)
	public void goToEdit(ProductBean product);
	
	@Event(handlers=ProductDisplayPresenter.class)
	public void goToDisplay(ProductBean product);
	
	@Event(handlers=ProductListPresenter.class)
	public void goToProduct();
	
	@Event(handlers=ProductRootPresenter.class)
	public void displayMessage(String message);

	@Event(handlers=ProductRootPresenter.class)
	public void changeBody(Widget body);
	
	@Event(handlers={ProductListPresenter.class, ProductDisplayPresenter.class})
	public void productCreated(ProductBean product);
	
	@Event(handlers=ProductListPresenter.class)
	public void productDeleted(ProductBean product);
	

}
