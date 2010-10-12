package com.mvp4g.example.client.product;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.example.client.product.bean.ProductBean;
import com.mvp4g.example.client.product.historyConverter.ProductHistoryConverter;
import com.mvp4g.example.client.product.presenter.ProductCreationPresenter;
import com.mvp4g.example.client.product.presenter.ProductDisplayPresenter;
import com.mvp4g.example.client.product.presenter.ProductEditPresenter;
import com.mvp4g.example.client.product.presenter.ProductListPresenter;
import com.mvp4g.example.client.product.view.ProductListView;

@Events( startView = ProductListView.class, module = ProductModule.class )
public interface ProductEventBus extends EventBus {

	/* Navigation events */
	@Event( handlers = ProductCreationPresenter.class, navigationEvent = true )
	public void goToCreation();

	@Event( handlers = ProductListPresenter.class, navigationEvent = true )
	public void backToList();

	@Event( handlers = ProductEditPresenter.class, navigationEvent = true )
	public void goToEdit( ProductBean product );

	@Event( handlers = ProductDisplayPresenter.class, historyConverter = ProductHistoryConverter.class, navigationEvent = true )
	public void goToDisplay( ProductBean product );

	@Event( handlers = ProductListPresenter.class, navigationEvent = true )
	public void goToProduct( Integer start, Integer end );

	/* Business events */
	@Event( forwardToParent = true )
	public void displayMessage( String message );

	@Event( forwardToParent = true )
	public void changeBody( Widget body );

	@Event( forwardToParent = true )
	public void selectProductMenu();

	@Event( handlers = ProductListPresenter.class )
	public void productCreated( ProductBean product );

	@Event( handlers = ProductListPresenter.class )
	public void productDeleted( ProductBean product );

}
