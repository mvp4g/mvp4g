package com.mvp4g.example.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.view_interface.IRootViewInterface;

public class RootView extends Composite implements IRootViewInterface {

	private Label message = new Label();
	private SimplePanel body = new SimplePanel();

	public RootView() {
		VerticalPanel mainPanel = new VerticalPanel();
		message.setStyleName( "message" );
		mainPanel.add( message );
		mainPanel.add( body );
		initWidget( mainPanel );
	}

	public HasText getMessage() {
		return message;
	}

	public void setBody( Widget body ) {
		this.body.setWidget( body );
	}

	public Widget getViewWidget() {
		return this;
	}

}
