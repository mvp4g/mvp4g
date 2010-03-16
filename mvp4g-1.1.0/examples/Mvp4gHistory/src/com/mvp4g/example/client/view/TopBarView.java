package com.mvp4g.example.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.view_interface.TopBarViewInterface;

public class TopBarView extends BaseView implements TopBarViewInterface {

	private ListBox deals = new ListBox();
	private ListBox products = new ListBox();

	private Button showDeal = new Button( "SHOW" );
	private Button showProduct = new Button( "SHOW" );

	private CheckBox save = new CheckBox( "Save in History" );

	@Override
	protected Widget createWidget() {
		HorizontalPanel mainPanel = new HorizontalPanel();

		save.setValue( true );

		mainPanel.setVerticalAlignment( HasVerticalAlignment.ALIGN_MIDDLE );
		mainPanel.add( new Label( "Products :" ) );
		mainPanel.add( products );
		mainPanel.add( showProduct );
		mainPanel.add( new Label( "Deals :" ) );
		mainPanel.add( deals );
		mainPanel.add( showDeal );
		mainPanel.add( save );

		mainPanel.setStyleName( "bar" );

		return mainPanel;
	}

	public ListBox getDealList() {
		return deals;
	}

	public ListBox getProductList() {
		return products;
	}

	public HasClickHandlers getShowDealButton() {
		return showDeal;
	}

	public HasClickHandlers getShowProductButton() {
		return showProduct;
	}

	public HasValue<Boolean> getSave() {
		return save;
	}

	public Widget getViewWidget() {
		return this;
	}

}
