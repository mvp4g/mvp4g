package com.mvp4g.example.client.mock.widget;

import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyLabelInterface;

public class MyMockLabel implements MyLabelInterface {

	private boolean visible = true;

	public boolean isVisible() {
		return visible;
	}

	public void setVisible( boolean visible ) {
		this.visible = visible;
	}

}
