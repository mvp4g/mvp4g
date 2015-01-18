package com.mvp4g.rebind.test_tools.annotation.views;

import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.rebind.test_tools.annotation.presenters.SimplePresenter01;

public class SimpleInjectedView extends SimpleView implements ReverseViewInterface<SimplePresenter01> {

	SimplePresenter01 presenter;

	public SimplePresenter01 getPresenter() {
		return presenter;
	}

	public void setPresenter(SimplePresenter01 presenter) {
		this.presenter = presenter;
	}
}
