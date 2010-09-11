package com.mvp4g.example.client.company.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.presenter.LazyXmlPresenter;
import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.company.CompanyServiceAsync;
import com.mvp4g.example.client.company.bean.CompanyBean;

public abstract class AbstractCompanyPresenter extends LazyXmlPresenter<AbstractCompanyPresenter.CompanyViewInterface> {

	protected CompanyBean company = null;

	protected CompanyServiceAsync service = null;

	public interface CompanyViewInterface extends LazyView {
		public HasValue<String> getName();

		public HasClickHandlers getLeftButton();

		public HasClickHandlers getRightButton();

		public HasClickHandlers getSelectNameButton();

		public Widget getViewWidget();

		public void alert( String message );
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
		view.getSelectNameButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.dispatch( "displayNameSelector" );
			}
		} );

	}

	@InjectService
	public void setService( CompanyServiceAsync service ) {
		this.service = service;
	}

	protected void fillView() {
		view.getName().setValue( company.getName() );
	}

	protected void fillBean() {
		company.setName( view.getName().getValue() );
	}

	protected void clear() {
		view.getName().setValue( "" );
	}

	abstract protected void clickOnLeftButton( ClickEvent event );

	abstract protected void clickOnRightButton( ClickEvent event );

}
