package com.mvp4g.example.client.mock.widget;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;

public class MyMockButton implements MyButtonInterface {

	private ClickHandler clickHandler = null;

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

	public HandlerRegistration addClickHandler( ClickHandler handler ) {
		clickHandler = handler;

		return new HandlerRegistration() {
			public void removeHandler() {
			}
		};
	}

	public void fireEvent( GwtEvent<?> event ) {
	}

	public ClickHandler getClickHandler() {
		return clickHandler;
	}

}
