package com.mvp4g.example.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.LoginPresenter.LoginViewInterface;

public class LoginView extends BaseView implements LoginViewInterface {

	private TextBox username = new TextBox();
	private Button login = new Button( "Login" );

	@Override
	protected Widget createWidget() {
		HorizontalPanel mainPanel = new HorizontalPanel();
		mainPanel.setStyleName( "bar" );

		mainPanel.setVerticalAlignment( HasVerticalAlignment.ALIGN_MIDDLE );
		mainPanel.add( username );
		mainPanel.add( login );

		return mainPanel;

	}

	public HasClickHandlers getLoginButton() {
		return login;
	}

	public HasValue<String> getUserName() {
		return username;
	}

	public Widget getViewWidget() {
		return this;
	}

}
