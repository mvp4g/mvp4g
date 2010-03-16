package com.mvp4g.example.client.presenter.view_interface;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface AccountViewInterface extends LazyView {

	public HasClickHandlers getShowCart();

	public Label getUsername();

	public Widget getViewWidget();

}
