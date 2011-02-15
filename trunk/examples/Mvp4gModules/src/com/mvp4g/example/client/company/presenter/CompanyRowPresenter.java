package com.mvp4g.example.client.company.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.company.CompanyEventBus;
import com.mvp4g.example.client.company.CompanyServiceAsync;
import com.mvp4g.example.client.company.bean.CompanyBean;
import com.mvp4g.example.client.company.view.CompanyRowView;

@Presenter( view = CompanyRowView.class, multiple = true )
public class CompanyRowPresenter extends BasePresenter<CompanyRowPresenter.ICompanyRowView, CompanyEventBus> {

	public interface ICompanyRowView {

		Widget getViewWidget();

		HasClickHandlers getDisplay();

		HasClickHandlers getEdit();

		HasClickHandlers getQuickEdit();

		HasClickHandlers getDelete();

		void setName( String name );

		void alert( String message );

	}

	private CompanyBean company;
	
	@Inject
	private CompanyServiceAsync service;
	
	private boolean calledQuickEdit = false;

	@Override
	public void bind() {

		view.getDisplay().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.goToDisplay( company );
			}
		} );
		view.getEdit().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.goToEdit( company );
			}
		} );
		view.getDelete().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				deleteCompany( company );
			}
		} );
		view.getQuickEdit().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				calledQuickEdit = true;
				eventBus.displayNameSelector();
			}
		} );
	}

	public void setCompany( CompanyBean company ) {
		this.company = company;
		view.setName( company.getName() );
	}

	private void deleteCompany( final CompanyBean company ) {
		service.deleteCompany( company, new AsyncCallback<Void>() {

			public void onFailure( Throwable caught ) {

			}

			public void onSuccess( Void result ) {
				eventBus.companyDeleted( company );
			}
		} );
	}

	public void onCompanyUpdated( CompanyBean newBean ) {
		if ( newBean.equals( company ) ) {
			company = newBean;
			view.setName( company.getName() );			
		}
	}

	public void onNameSelected( String name ) {
		if ( calledQuickEdit ) {
			company.setName( name );
			view.setName( name );
			view.alert( "Name changed with quick edit." );
			calledQuickEdit = false;
		}
	}

}
