package com.mvp4g.example.client.view.gxt;

import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;

public class MyGXTButton extends Button implements MyButtonInterface {

	public MyGXTButton() {

	}

	public MyGXTButton( String text ) {
		super( text );
	}

	public HandlerRegistration addClickHandler( ClickHandler handler ) {
		return addHandler( handler, ClickEvent.getType() );
	}

}
