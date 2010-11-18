package com.mvp4g.example.client.product.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.product.ProductEventBus;
import com.mvp4g.example.client.product.ProductServiceAsync;
import com.mvp4g.example.client.product.bean.ProductBean;
import com.mvp4g.example.client.util.HasBeenThereHandler;

public abstract class AbstractProductPresenter extends LazyPresenter<AbstractProductPresenter.ProductViewInterface, ProductEventBus> implements HasBeenThereHandler {

	protected ProductBean product = null;

	protected ProductServiceAsync service = null;

	public interface ProductViewInterface extends LazyView {
		HasValue<String> getName();

		HasClickHandlers getLeftButton();

		HasClickHandlers getRightButton();

		Widget getViewWidget();

		void alert( String message );
	}

	public void bindView() {
		view.getLeftButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				clickOnLeftButton( event );
			}
		} );

		view.getRightButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				clickOnRightButton( event );
			}
		} );

	}

	public void onHasBeenThere(boolean fakeParameter) {
		view.alert( "Has been on " + getPageName() );
	}

	@InjectService
	public void setService( ProductServiceAsync service ) {
		this.service = service;
	}

	protected void fillView() {
		view.getName().setValue( product.getName() );
	}

	protected void fillBean() {
		product.setName( view.getName().getValue() );
	}

	protected void clear() {
		view.getName().setValue( "" );
	}

	abstract protected void clickOnLeftButton( ClickEvent event );

	abstract protected void clickOnRightButton( ClickEvent event );

	abstract protected String getPageName();

}
