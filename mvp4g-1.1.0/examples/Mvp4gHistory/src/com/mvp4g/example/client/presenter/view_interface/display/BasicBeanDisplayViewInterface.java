package com.mvp4g.example.client.presenter.view_interface.display;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface BasicBeanDisplayViewInterface extends LazyView {

	public Label getName();

	public Label getDescription();

	public Widget getViewWidget();

}
