package com.mvp4g.example.client.mock.widget;

import com.mvp4g.example.client.widget.interfaces.ILabel;

public class MyMockLabel implements ILabel {

	private boolean visible = true;

	public boolean isVisible() {
		return visible;
	}

	public void setVisible( boolean visible ) {
		this.visible = visible;
	}

}
