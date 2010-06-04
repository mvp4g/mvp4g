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
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.company.CompanyEventBus;
import com.mvp4g.example.client.company.bean.CompanyBean;
import com.mvp4g.example.client.company.view.CompanyListView;

@Presenter( view = CompanyListView.class )
public class CompanyListPresenter extends LazyPresenter<CompanyListPresenter.CompanyListViewInterface, CompanyEventBus> {

	private List<CompanyBean> companies = null;
	
	private List<EventHandlerInterface<CompanyEventBus>> rows = new ArrayList<EventHandlerInterface<CompanyEventBus>>();

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
				eventBus.goToCreation();
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
		for(EventHandlerInterface<CompanyEventBus> row : rows){
			eventBus.removeHandler( row );
		}
		rows.clear();
		eventBus.getCompanyList( start, end );
	}

	public void onCompanyListRetrieved( List<CompanyBean> companies ) {
		this.companies = companies;
		for ( int i = 0; i < companies.size(); i++ ) {
			addCompany( companies.get( i ) );
		}
		eventBus.changeBody( view.getViewWidget() );
	}

	public void onGoToList() {
		eventBus.changeBody( view.getViewWidget() );
	}

	public void onCompanyDeleted( CompanyBean company ) {
		eventBus.changeBody( view.getViewWidget() );
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
		EventHandlerInterface<CompanyEventBus> handler = rows.remove( row );
		eventBus.removeHandler( handler );
		companies.remove( row );
		view.removeCompany( row );
		eventBus.displayMessage( "Deletion Succeeded" );
	}

}
