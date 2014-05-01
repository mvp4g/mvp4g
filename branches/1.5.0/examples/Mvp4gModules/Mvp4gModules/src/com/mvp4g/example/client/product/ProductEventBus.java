package com.mvp4g.example.client.product;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.NoStartPresenter;
import com.mvp4g.example.client.company.CompanyModule;
import com.mvp4g.example.client.product.bean.ProductBean;
import com.mvp4g.example.client.product.presenter.ProductCreationPresenter;
import com.mvp4g.example.client.product.presenter.ProductDisplayPresenter;
import com.mvp4g.example.client.product.presenter.ProductEditPresenter;
import com.mvp4g.example.client.product.presenter.ProductListPresenter;
import com.mvp4g.example.client.util.HasBeenThereHandler;

@Events( startPresenter = NoStartPresenter.class, module = ProductModule.class )
public interface ProductEventBus extends EventBus {

	/* Navigation events */
	@Event( handlers = ProductCreationPresenter.class, navigationEvent = true )
	void goToCreation();

	@Event( handlers = ProductListPresenter.class, navigationEvent = true )
	void backToList();

	@Event( handlers = ProductEditPresenter.class, navigationEvent = true )
	void goToEdit( ProductBean product );

	@Event( handlers = ProductDisplayPresenter.class, navigationEvent = true )
	void goToDisplay( ProductBean product );

	@Event( handlers = ProductListPresenter.class, navigationEvent = true )
	void goToProduct( Integer start, Integer end );

	/* Business events */
	@Event( forwardToParent = true )
	void displayMessage( String message );

	@Event( forwardToParent = true )
	void changeBody( IsWidget body );

	@Event( forwardToParent = true )
	void selectProductMenu();

	@Event( handlers = ProductListPresenter.class )
	void productCreated( ProductBean product );

	@Event( handlers = ProductListPresenter.class )
	void productDeleted( ProductBean product );

	@Event( broadcastTo = HasBeenThereHandler.class, passive = true )
	void hasBeenThere();

	@Event( handlers = ProductListPresenter.class )
	void goToProduct2( String[] indexes );
	
	@Event( broadcastTo = HasBeenThereHandler.class, passive = true )
	void broadcastInfo(String[] info);
	
	@Event(forwardToModules = CompanyModule.class)
	void goToCompanyFromProduct(String info);
	
	@Event( broadcastTo = Mvp4gModule.class )
	void broadcastInfoFromProduct(String info);
	
	@Event( broadcastTo = Mvp4gModule.class, passive = true )
	void broadcastInfoFromProductPassive(String info);

}
