package com.mvp4g.example.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.AccountPresenter.AccountViewInterface;

public class AccountView extends BaseView implements AccountViewInterface {

	private Button showCart = new Button( "Show Cart" );
	private Label username = new Label();

	@Override
	protected Widget createWidget() {
		HorizontalPanel mainPanel = new HorizontalPanel();
		mainPanel.setVerticalAlignment( HasVerticalAlignment.ALIGN_MIDDLE );
		mainPanel.add( new Label( "Welcome" ) );
		mainPanel.add( username );
		mainPanel.add( showCart );

		mainPanel.setStyleName( "bar" );
		username.addStyleName( "username" );
		return mainPanel;
	}

	public HasClickHandlers getShowCart() {
		return showCart;
	}

	public Widget getViewWidget() {
		return this;
	}

	public void setUsername( String username ) {
		this.username.setText( username );
	}

}
