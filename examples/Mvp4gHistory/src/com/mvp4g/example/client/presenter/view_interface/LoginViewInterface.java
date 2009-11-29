package com.mvp4g.example.client.presenter.view_interface;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface LoginViewInterface extends LazyView {
	
	public HasText getUserName();
	public HasClickHandlers getLoginButton();
	public Widget getViewWidget();

}
