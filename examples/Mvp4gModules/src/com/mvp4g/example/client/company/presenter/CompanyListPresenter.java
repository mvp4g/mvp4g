package com.mvp4g.example.client.company.presenter;

import java.util.ArrayList;
import java.util.List;

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

	public interface CompanyListViewInterface extends LazyView {
		void setGoToCreationToken(String token);
		
		void setGoToProductsToken(String token);

		void addCompany( Widget w );

		void removeCompany( int row );

		Widget getViewWidget();

		void clearTable();

		HasValue<Boolean> isFiltered();

		void alert( String msg );
	}

	private List<CompanyBean> companies;

	private List<EventHandlerInterface<CompanyEventBus>> rows = new ArrayList<EventHandlerInterface<CompanyEventBus>>();

	@Override
	public void bindView() {
		view.setGoToCreationToken( getTokenGenerator().goToCreation() );
		view.setGoToProductsToken( getTokenGenerator().goToProduct( 0, 5 ) );

		HasValue<Boolean> isFiltered = view.isFiltered();
		isFiltered.setValue( false );
		eventBus.setFilteringEnabled( false );
		isFiltered.addValueChangeHandler( new ValueChangeHandler<Boolean>() {

			public void onValueChange( ValueChangeEvent<Boolean> event ) {
				eventBus.setFilteringEnabled( event.getValue() );
			}

		} );
	}

	public void onGoToCompany( int start, int end ) {
		view.clearTable();
		for ( EventHandlerInterface<CompanyEventBus> row : rows ) {
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
	}

	public void onBackToList() {
		eventBus.changeBody( view.getViewWidget() );
	}

	public void onCompanyDeleted( CompanyBean company ) {
		finishDeletion( company );
	}

	public void onCompanyCreated( CompanyBean company ) {
		companies.add( company );
		addCompany( company );
	}

	public void onForward() {
		eventBus.selectCompanyMenu();
	}

	public void onHasBeenThere() {
		view.alert( "Has been on Company list page" );
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
