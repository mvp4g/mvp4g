package com.mvp4g.util.test_tools.annotation.views;

import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter;

public class SimpleInjectedView extends SimpleView implements ReverseViewInterface<SimplePresenter> {

	public SimplePresenter getPresenter() {
		return null;
	}

	public void setPresenter( SimplePresenter presenter ) {

	}
}
