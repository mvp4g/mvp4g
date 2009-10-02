package com.mvp4g.example.client.view;


import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.mvp4g.example.client.presenter.view_interface.TopBarViewInterface;

public class TopBarView extends Composite implements TopBarViewInterface {
	
	private ListBox deals = new ListBox();
	private ListBox products = new ListBox();
	
	private Button showDeal = new Button("SHOW");
	private Button showProduct = new Button("SHOW");
	
	private CheckBox save = new CheckBox("Save in History");

	
	public TopBarView(){
		HorizontalPanel mainPanel = new HorizontalPanel();
		initWidget( mainPanel );
		
		save.setValue( true );
		
		mainPanel.add( new Label("Products :") );
		mainPanel.add( products );
		mainPanel.add( showProduct );
		mainPanel.add( new Label("Deals :") );
		mainPanel.add( deals );
		mainPanel.add( showDeal );
		mainPanel.add(save);
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

}
