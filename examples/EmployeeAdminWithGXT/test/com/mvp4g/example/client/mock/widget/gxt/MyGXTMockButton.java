package com.mvp4g.example.client.mock.widget.gxt;

import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTButtonInterface;

public class MyGXTMockButton extends MyGXTMockWidget implements MyGXTButtonInterface {

	private boolean enabled = true;
	private boolean visible = true;
	private String text = null;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled( boolean enabled ) {
		this.enabled = enabled;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible( boolean visible ) {
		this.visible = visible;
	}

	public String getText() {
		return text;
	}

	public void setText( String text ) {
		this.text = text;
	}

}
