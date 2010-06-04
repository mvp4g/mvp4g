package com.mvp4g.example.client.company.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.client.presenter.LazyXmlPresenter;
import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.company.bean.CompanyBean;

public class CompanyListPresenter extends LazyXmlPresenter<CompanyListPresenter.CompanyListViewInterface> {

	private List<CompanyBean> companies = null;
	
	private List<EventHandlerInterface<EventBusWithLookup>> rows = new ArrayList<EventHandlerInterface<EventBusWithLookup>>();

	public interface CompanyListViewInterface extends LazyView {
		public HasClickHandlers getCreateButton();

		public void addCompany( Widget w );

		public void removeCompany( int row );

		public Widget getViewWidget();

		public void clearTable();
		
		public HasValue<Boolean> isFiltered();
	}

	@Override
	public void bindView() {
		view.getCreateButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.dispatch( "goToCreation()");
			}

		} );
		HasValue<Boolean> isFiltered = view.isFiltered();
		isFiltered.setValue( false );
		eventBus.setFilteringEnabled( false );
		isFiltered.addValueChangeHandler( new ValueChangeHandler<Boolean>() {
			
			public void onValueChange( ValueChangeEvent<Boolean> event ) {
				eventBus.setFilteringEnabled( event.getValue() );
			}
			
		});
	}

	public void onGoToCompany( int start, int end ) {
		view.clearTable();
		for(EventHandlerInterface<EventBusWithLookup> row : rows){
			eventBus.removeHandler( row );
		}
		rows.clear();
		eventBus.dispatch( "getCompanyList", start, end );
	}

	public void onCompanyListRetrieved( List<CompanyBean> companies ) {
		this.companies = companies;
		for ( int i = 0; i < companies.size(); i++ ) {
			addCompany( companies.get( i ) );
		}
		eventBus.dispatch( "changeBody", view.getViewWidget() );
	}

	public void onGoToList() {
		eventBus.dispatch( "changeBody", view.getViewWidget() );
	}

	public void onCompanyDeleted( CompanyBean company ) {
		eventBus.dispatch( "changeBody", view.getViewWidget() );
		finishDeletion( company );
	}

	public void onCompanyCreated( CompanyBean company ) {
		companies.add( company );
		addCompany( company );
	}

	private void addCompany( CompanyBean company ) {
		CompanyRowPresenter presenter = eventBus.addHandler( CompanyRowPresenter.class );
		presenter.setCompany( company );
		view.addCompany( presenter.getView().getViewWidget() );
		rows.add( presenter );
	}

	private void finishDeletion( CompanyBean company ) {
		int row = companies.indexOf( company );
		EventHandlerInterface<EventBusWithLookup> handler = rows.remove( row );
		eventBus.removeHandler( handler );
		companies.remove( row );
		view.removeCompany( row );
		eventBus.dispatch( "displayMessage", "Deletion Succeeded" );
	}

}
