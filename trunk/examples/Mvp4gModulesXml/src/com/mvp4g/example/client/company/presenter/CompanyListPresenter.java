package com.mvp4g.example.client.company.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyXmlPresenter;
import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.company.CompanyServiceAsync;
import com.mvp4g.example.client.company.bean.CompanyBean;
import com.mvp4g.example.client.company.view.CompanyListView;

@Presenter( view = CompanyListView.class )
public class CompanyListPresenter extends LazyXmlPresenter<CompanyListPresenter.CompanyListViewInterface> {

	private CompanyServiceAsync service = null;
	private List<CompanyBean> companies = null;

	public interface CompanyListViewInterface extends LazyView {
		public HasClickHandlers getCreateButton();

		public HasClickHandlers[] addCompany( String name, int row );

		public void removeCompany( int row );

		public void updateCompany( String name, int row );

		public Widget getViewWidget();
		
		public void clearTable();
	}

	@Override
	public void bindView() {
		view.getCreateButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.dispatch( "goToCreation" );
			}

		} );
	}

	public void onGoToCompany(int start, int end) {
		service.getCompanies(start, end, new AsyncCallback<List<CompanyBean>>() {

			public void onSuccess( List<CompanyBean> result ) {
				companies = result;
				for ( int i = 0; i < result.size(); i++ ) {
					addCompany( result.get( i ), i );
				}
				eventBus.dispatch( "changeBody", view.getViewWidget() );
			}

			public void onFailure( Throwable caught ) {

			}
		} );
		view.clearTable();
		eventBus.dispatch( "selectCompanyMenu" );
	}

	public void onGoToList() {
		eventBus.dispatch( "changeBody", view.getViewWidget() );
	}

	public void onCompanyDeleted( CompanyBean company ) {
		eventBus.dispatch( "changeBody", view.getViewWidget() );
		finishDeletion( company );
	}

	public void onCompanyCreated( CompanyBean company ) {
		int row = companies.size();
		companies.add( company );
		view.addCompany( company.getName(), row );
	}

	@InjectService
	public void setService( CompanyServiceAsync service ) {
		this.service = service;
	}

	private void addCompany( final CompanyBean company, int row ) {
		HasClickHandlers[] buttons = view.addCompany( company.getName(), row );
		buttons[0].addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.dispatch( "goToDisplay", company );
			}
		} );
		buttons[1].addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.dispatch( "goToEdit", company );
			}
		} );
		buttons[2].addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				deleteCompany( company );
			}
		} );
	}

	private void deleteCompany( final CompanyBean company ) {
		service.deleteCompany( company, new AsyncCallback<Void>() {

			public void onFailure( Throwable caught ) {

			}

			public void onSuccess( Void result ) {
				finishDeletion( company );
			}
		} );
	}

	private void finishDeletion( CompanyBean company ) {
		int row = companies.indexOf( company );
		companies.remove( row );
		view.removeCompany( row );
		eventBus.dispatch( "displayMessage", "Deletion Succeeded" );
	}

}
