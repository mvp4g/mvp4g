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
import com.mvp4g.example.client.util.HasBeenThereHandler;

@Events( startView = ProductListView.class, module = ProductModule.class )
public interface ProductEventBus extends EventBus {

	/* Navigation events */
	@Event( handlers = ProductCreationPresenter.class, navigationEvent = true )
	void goToCreation();

	@Event( handlers = ProductListPresenter.class, navigationEvent = true )
	void backToList();

	@Event( handlers = ProductEditPresenter.class, navigationEvent = true )
	void goToEdit( ProductBean product );

	@Event( handlers = ProductDisplayPresenter.class, historyConverter = ProductHistoryConverter.class, navigationEvent = true )
	void goToDisplay( ProductBean product );

	@Event( handlers = ProductListPresenter.class, navigationEvent = true )
	void goToProduct( Integer start, Integer end );

	/* Business events */
	@Event( forwardToParent = true )
	void displayMessage( String message );

	@Event( forwardToParent = true )
	void changeBody( Widget body );

	@Event( forwardToParent = true )
	void selectProductMenu();

	@Event( handlers = ProductListPresenter.class )
	void productCreated( ProductBean product );

	@Event( handlers = ProductListPresenter.class )
	void productDeleted( ProductBean product );

	@Event( broadcastTo = HasBeenThereHandler.class, passive = true )
	void hasBeenThere(boolean fakeParameter);

}
