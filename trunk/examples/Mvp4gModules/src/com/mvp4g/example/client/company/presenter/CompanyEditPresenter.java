package com.mvp4g.example.client.company.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.example.client.company.bean.CompanyBean;
import com.mvp4g.example.client.company.view.CompanyEditView;

@Presenter( view = CompanyEditView.class )
public class CompanyEditPresenter extends AbstractCompanyPresenter {

	public void onGoToEdit( CompanyBean company ) {
		this.company = company;
		fillView();
		eventBus.changeBody( view.getViewWidget() );
	}

	@Override
	protected void clickOnLeftButton( ClickEvent event ) {
		fillBean();
		service.updateCompany( company, new AsyncCallback<Void>() {

			public void onSuccess( Void result ) {
				eventBus.goToDisplay( company );
				eventBus.displayMessage( "Update Succeeded" );
			}

			public void onFailure( Throwable caught ) {
				eventBus.displayMessage( "Update Failed" );
			}
		} );
	}

	@Override
	protected void clickOnRightButton( ClickEvent event ) {
		clear();
	}

}
