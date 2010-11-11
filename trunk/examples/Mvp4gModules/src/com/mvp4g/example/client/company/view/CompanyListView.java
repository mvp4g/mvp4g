package com.mvp4g.example.client.company.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.company.presenter.CompanyListPresenter.CompanyListViewInterface;

public class CompanyListView extends Composite implements CompanyListViewInterface {

	private Image createButton = null;
	private VerticalPanel table = null;
	private CheckBox filter = null;

	public HasClickHandlers getCreateButton() {
		return createButton;
	}

	public void removeCompany( int row ) {
		table.remove( row );
	}

	public void clearTable() {
		table.clear();
	}

	public void createView() {
		table = new VerticalPanel();
		createButton = new Image( "images/add.png" );
		filter = new CheckBox( "Filter Company EventBus events" );

		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add( table );
		mainPanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_RIGHT );
		mainPanel.add( createButton );
		mainPanel.add( filter );

		initWidget( mainPanel );
	}

	public Widget getViewWidget() {
		return this;
	}

	public void addCompany( Widget w ) {
		table.add( w );
	}

	public HasValue<Boolean> isFiltered() {
		return filter;
	}

	public void alert( String msg ) {
		Window.alert( msg );
	}

}
