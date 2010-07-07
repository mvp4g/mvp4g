package com.mvp4g.example.client.mock.widget;

import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.mvp4g.example.client.widget.interfaces.ITextBox;

public class MyMockTextBox implements ITextBox {

	private String value = null;
	private ValueChangeHandler<String> valueChangeHandler = null;
	private KeyUpHandler keyUpHandler = null;

	public String getValue() {
		return value;
	}

	public void setValue( String value ) {
		this.value = value;
	}

	public void setValue( String value, boolean fireEvents ) {
		setValue( value );
	}

	public HandlerRegistration addValueChangeHandler( ValueChangeHandler<String> handler ) {
		valueChangeHandler = handler;

		return new HandlerRegistration() {
			public void removeHandler() {
			}
		};
	}

	public void fireEvent( GwtEvent<?> event ) {
	}

	public HandlerRegistration addKeyUpHandler( KeyUpHandler handler ) {
		keyUpHandler = handler;

		return new HandlerRegistration() {
			public void removeHandler() {
			}
		};
	}

	public ValueChangeHandler<String> getValueChangeHandler() {
		return valueChangeHandler;
	}

	public KeyUpHandler getKeyUpHandler() {
		return keyUpHandler;
	}

}
