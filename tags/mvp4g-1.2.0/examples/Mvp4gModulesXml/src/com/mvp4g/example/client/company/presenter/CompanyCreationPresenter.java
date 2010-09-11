package com.mvp4g.example.client.company.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.example.client.company.bean.CompanyBean;
import com.mvp4g.example.client.company.view.CompanyCreationView;

@Presenter( view = CompanyCreationView.class )
public class CompanyCreationPresenter extends AbstractCompanyPresenter {

	public void onGoToCreation() {
		eventBus.dispatch( "changeBody", view.getViewWidget() );		
	}

	@Override
	protected void clickOnLeftButton( ClickEvent event ) {
		company = new CompanyBean();
		fillBean();
		service.createCompany( company, new AsyncCallback<Void>() {

			public void onSuccess( Void result ) {
				eventBus.dispatch( "companyCreated", company );
				eventBus.dispatch( "displayMessage", "Creation Succeeded" );
				setActivated( false );
			}

			public void onFailure( Throwable caught ) {
				eventBus.dispatch( "displayMessage", "Creation Failed" );
			}
		} );
	}

	public void onNameSelected( String name ) {
		view.getName().setValue( name );
		view.alert( "Name changed on create page." );
	}

	@Override
	protected void clickOnRightButton( ClickEvent event ) {
		clear();
	}

}
