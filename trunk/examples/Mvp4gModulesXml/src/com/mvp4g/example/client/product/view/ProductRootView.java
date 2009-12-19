package com.mvp4g.example.client.product.view;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.product.presenter.ProductRootPresenter.ProductRootViewInterface;

public class ProductRootView extends SimplePanel implements
		ProductRootViewInterface {

	private Label message = null;
	private SimplePanel bodyContainer = null;

	public void displayText(String text) {
		message.setText(text);
	}

	public void setBody(Widget newBody) {
		bodyContainer.setWidget(newBody);
	}

	public void createView() {
		message = new Label();
		bodyContainer = new SimplePanel();

		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add(message);
		mainPanel.add(bodyContainer);
		setWidget(mainPanel);
	}

}
