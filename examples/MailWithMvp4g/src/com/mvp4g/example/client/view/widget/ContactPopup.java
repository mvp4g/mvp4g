package com.mvp4g.example.client.view.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContactPopup extends PopupPanel {
	@UiTemplate("ContactPopup.ui.xml")
	interface Binder extends UiBinder<Widget, ContactPopup> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	Element nameDiv;
	@UiField
	Element emailDiv;

	public ContactPopup(String name, String email) {
		// The popup's constructor's argument is a boolean specifying that it
		// auto-close itself when the user clicks outside of it.
		super(true);
		add(binder.createAndBindUi(this));

		nameDiv.setInnerText(name);
		emailDiv.setInnerText(email);
	}
}
