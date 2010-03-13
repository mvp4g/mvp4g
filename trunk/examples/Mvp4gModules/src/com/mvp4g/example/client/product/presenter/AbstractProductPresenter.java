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

public abstract class AbstractProductPresenter extends LazyPresenter<AbstractProductPresenter.ProductViewInterface, ProductEventBus> {

	protected ProductBean product = null;

	protected ProductServiceAsync service = null;

	public interface ProductViewInterface extends LazyView {
		public HasValue<String> getName();

		public HasClickHandlers getLeftButton();

		public HasClickHandlers getRightButton();

		public Widget getViewWidget();
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

}
