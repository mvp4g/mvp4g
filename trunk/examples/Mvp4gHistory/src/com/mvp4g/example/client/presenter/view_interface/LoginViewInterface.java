package com.mvp4g.example.client.presenter.view_interface;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;

public interface LoginViewInterface {
	
	public HasText getUserName();
	public HasClickHandlers getLoginButton();

}
