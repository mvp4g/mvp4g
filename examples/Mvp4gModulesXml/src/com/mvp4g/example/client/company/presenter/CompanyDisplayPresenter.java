package com.mvp4g.example.client.company.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.example.client.company.bean.CompanyBean;
import com.mvp4g.example.client.company.view.CompanyDisplayView;

@Presenter( view = CompanyDisplayView.class )
public class CompanyDisplayPresenter extends AbstractCompanyPresenter {

	public void onGoToDisplay( CompanyBean company ) {
		displayCompany( company );
		eventBus.dispatch( "selectCompanyMenu" );
	}

	public void onCompanyCreated( CompanyBean company ) {
		displayCompany( company );
	}

	public void onNameSelected( String name ) {
		view.alert( "Selected a name on the display page does nothing, sorry." );
	}

	@Override
	protected void clickOnLeftButton( ClickEvent event ) {
		eventBus.dispatch( "goToEdit", company );
	}

	@Override
	protected void clickOnRightButton( ClickEvent event ) {
		fillBean();
		service.deleteCompany( company, new AsyncCallback<Void>() {

			public void onSuccess( Void result ) {
				eventBus.dispatch( "companyDeleted", company );
			}

			public void onFailure( Throwable caught ) {
				eventBus.dispatch( "displayMessage", "Deletion Failed" );
			}
		} );
	}

	private void displayCompany( CompanyBean company ) {
		this.company = company;
		fillView();
		eventBus.dispatch( "changeBody", view.getViewWidget() );
	}

}
