package com.mvp4g.example.client.company.view;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.company.presenter.CompanyRootPresenter.CompanyRootViewInterface;

public class CompanyRootView extends SimplePanel implements
		CompanyRootViewInterface {

	private Label message = null;
	private SimplePanel bodyContainer = null;

	public void displayText(String text) {
		message.setText(text);
		message.setVisible(text.length() > 0);
	}

	public void setBody(Widget newBody) {
		bodyContainer.setWidget(newBody);
	}

	public void createView() {
		message = new Label();
		message.setStyleName("messageBar");
		bodyContainer = new SimplePanel();
		
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add(message);
		mainPanel.add(bodyContainer);
		setWidget(mainPanel);
	}

}
