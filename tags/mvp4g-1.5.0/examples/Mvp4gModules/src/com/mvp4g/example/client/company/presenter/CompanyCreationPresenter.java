package com.mvp4g.example.client.company.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.history.NavigationConfirmationInterface;
import com.mvp4g.client.history.NavigationEventCommand;
import com.mvp4g.example.client.company.bean.CompanyBean;
import com.mvp4g.example.client.company.view.CompanyCreationView;

@Presenter( view = CompanyCreationView.class )
public class CompanyCreationPresenter extends AbstractCompanyPresenter implements NavigationConfirmationInterface {

	public void onGoToCreation() {
		view.getName().setValue( "" );
		eventBus.changeBody( view );		
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
	public void onBeforeEvent() {
		eventBus.setNavigationConfirmation( this );
	}
	
	public void confirm( NavigationEventCommand event ) {
		if ( ( view.getName().getValue().length() == 0 )
				|| ( view.confirm( "Your company hasn't been created. Are you sure you want to navigate away from this page?" ) ) ) {
			event.fireEvent();
		}
	}

}
