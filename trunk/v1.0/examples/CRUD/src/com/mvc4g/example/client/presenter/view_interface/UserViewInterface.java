package com.mvc4g.example.client.presenter.view_interface;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public interface UserViewInterface {
	
	public HasClickHandlers getButton();
	
	public HasValue<String> getLastName();
	
	public HasValue<String> getFirstName();
	
	public Widget getViewWidget();

}
