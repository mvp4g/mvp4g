package com.mvp4g.example.client.presenter.view_interface;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public interface AccountViewInterface {
	
	public HasClickHandlers getShowCart();
	public Label getUsername();
	public Widget getViewWidget();

}
