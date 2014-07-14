package com.mvp4g.example.client.presenter.view_interface;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public interface IUserViewInterface {

	HasClickHandlers getButton();

	HasText getId();

	HasText getLastName();

	HasText getFirstName();

	Widget getViewWidget();

}
