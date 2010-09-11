package com.mvp4g.example.client.widget.impl;

import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.mvp4g.example.client.widget.interfaces.IButton;

public class MyGXTButton extends Button implements IButton {

	public MyGXTButton() {

	}

	public MyGXTButton( String text ) {
		super( text );
	}

	public HandlerRegistration addClickHandler( ClickHandler handler ) {
		return addHandler( handler, ClickEvent.getType() );
	}

}
