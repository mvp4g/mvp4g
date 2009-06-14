package com.mvc4g.example.client.view.widget;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;

public class LabelWithValue extends Label implements HasValue<String> {

	public String getValue() {
		return getText();
	}

	public void setValue(String value) {
		setText(value);
		
	}

	public void setValue(String value, boolean fireEvents) {
		// TODO Auto-generated method stub
		
	}

	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		// TODO Auto-generated method stub
		return null;
	}

}
