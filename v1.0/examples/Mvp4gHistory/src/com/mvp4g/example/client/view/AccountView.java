package com.mvp4g.example.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.mvp4g.example.client.presenter.view_interface.AccountViewInterface;

public class AccountView extends Composite implements AccountViewInterface {
	
	private Button showCart = new Button("Show Cart");
	private Label username = new Label();
	
	public AccountView(){
		HorizontalPanel mainPanel = new HorizontalPanel();
		mainPanel.add( new HTML("Welcome&nbsp;" ));
		mainPanel.add( username );
		mainPanel.add( showCart );
		
		initWidget( mainPanel );
		
	}

	public HasClickHandlers getShowCart() {
		return showCart;
	}

	public Label getUsername() {
		return username;
	}

}
