package com.mvp4g.example.client.company.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.history.NavigationConfirmationInterface;
import com.mvp4g.example.client.company.bean.CompanyBean;
import com.mvp4g.example.client.company.view.CompanyCreationView;

@Presenter( view = CompanyCreationView.class )
public class CompanyCreationPresenter extends AbstractCompanyPresenter {

	private NavigationConfirmationInterface navConf = new NavigationConfirmationInterface() {

		public void confirm( Command event ) {
			if ( ( view.getName().getValue().length() == 0 )
					|| ( view.confirm( "Are you sure you want to navigate away from this page? Your company hasn't been created." ) ) ) {
				event.execute();
			}
		}
	};

	public void onGoToCreation() {
		eventBus.changeBody( view.getViewWidget() );
	}

	public void onNameSelected( String name ) {
		view.getName().setValue( name );
		view.alert( "Name changed on create page." );
	}

	@Override
	protected void clickOnLeftButton( ClickEvent event ) {
		company = new CompanyBean();
		fillBean();
		
		service.createCompany( company, new AsyncCallback<Void>() {

			public void onSuccess( Void result ) {
				eventBus.setNavigationConfirmation( null );
				eventBus.companyCreated( company );
				eventBus.goToDisplay( company );
				eventBus.displayMessage( "Creation Succeeded" );
				setActivated( false );
			}

			public void onFailure( Throwable caught ) {
				eventBus.displayMessage( "Creation Failed" );
			}
		} );
	}

	@Override
	protected void clickOnRightButton( ClickEvent event ) {
		clear();
	}

	@Override
	public void onLoad() {
		eventBus.setNavigationConfirmation( navConf );
	}

	@Override
	public void onUnload() {
		eventBus.setNavigationConfirmation( null );
	}

}
