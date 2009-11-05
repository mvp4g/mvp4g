package com.mvp4g.example.client.presenter.view_interface;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public interface TopBarViewInterface {
	
	public ListBox getProductList();
	public HasClickHandlers getShowProductButton();
	
	public ListBox getDealList();
	public HasClickHandlers getShowDealButton();
	public HasValue<Boolean> getSave();
	public Widget getViewWidget();
	

}
