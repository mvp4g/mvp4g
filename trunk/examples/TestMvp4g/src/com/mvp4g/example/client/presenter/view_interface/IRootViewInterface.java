package com.mvp4g.example.client.presenter.view_interface;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public interface IRootViewInterface {

	HasText getMessage();

	void setBody( Widget body );

	Widget getViewWidget();

}
