package com.mvp4g.example.client.view;

import com.google.gwt.user.client.ui.LazyPanel;
import com.mvp4g.client.view.LazyView;

public abstract class BaseView extends LazyPanel implements LazyView {

	public void createView() {
		ensureWidget();
	}

}
