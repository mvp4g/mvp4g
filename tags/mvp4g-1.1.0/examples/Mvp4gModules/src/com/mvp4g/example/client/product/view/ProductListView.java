package com.mvp4g.example.client.product.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.product.presenter.ProductListPresenter.ProductListViewInterface;

public class ProductListView extends SimplePanel implements ProductListViewInterface {

	private Image createButton = null;
	private FlexTable table = null;

	public HasClickHandlers[] addProduct( String product, int row ) {

		Image l1 = new Image( "images/display.png" );
		Image l2 = new Image( "images/edit.png" );
		Image l3 = new Image( "images/delete.png" );

		HasClickHandlers[] handlers = new HasClickHandlers[] { l1, l2, l3 };

		table.setText( row + 1, 0, product );
		table.setWidget( row + 1, 1, l1 );
		table.setWidget( row + 1, 2, l2 );
		table.setWidget( row + 1, 3, l3 );

		return handlers;
	}

	public HasClickHandlers getCreateButton() {
		return createButton;
	}

	public void removeProduct( int row ) {
		table.removeRow( row + 1 );
	}

	public void updateProduct( String product, int row ) {
		table.setText( row + 1, 0, product );
	}

	public void createView() {
		table = new FlexTable();
		createButton = new Image( "images/add.png" );

		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add( table );
		mainPanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_RIGHT );
		mainPanel.add( createButton );

		setWidget( mainPanel );
	}

	public Widget getViewWidget() {
		return this;
	}

}
