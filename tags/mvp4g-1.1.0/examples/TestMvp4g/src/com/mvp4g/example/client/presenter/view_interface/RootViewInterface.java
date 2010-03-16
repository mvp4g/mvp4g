package com.mvp4g.example.client.presenter.view_interface;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface RootViewInterface {

	public HasValue<String> getMessage();

	public Panel getBody();

	public Widget getViewWidget();

}
