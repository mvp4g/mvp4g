package com.mvp4g.example.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.mvp4g.example.client.presenter.view_interface.LoginViewInterface;

public class LoginView extends Composite implements LoginViewInterface {

	private TextBox username = new TextBox();
	private Button login = new Button( "Login" );
	
	public LoginView(){
		HorizontalPanel mainPanel = new HorizontalPanel();
		initWidget( mainPanel );
		
		mainPanel.add( username );
		mainPanel.add( login );
		
	}

	public HasClickHandlers getLoginButton() {
		return login;
	}

	public HasText getUserName() {
		return username;
	}

}
