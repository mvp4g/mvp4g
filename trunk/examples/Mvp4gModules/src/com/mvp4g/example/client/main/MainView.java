package com.mvp4g.example.client.main;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite implements
		MainPresenter.MainViewInterface {

	private Label c = new Label("Company");
	private Label p = new Label("Product");
	private SimplePanel bodyContainer = new SimplePanel();
	private PopupPanel wait = new PopupPanel();
	private Label message = new Label();

	public MainView() {
		message.setStyleName("messageBar");
		message.setVisible(false);
		
		TabBar bar = new TabBar();
		bar.addTab(c);
		bar.addTab(p);

		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add(bar);
		mainPanel.add(message);
		mainPanel.add(bodyContainer);
		wait.add(new Label("Wait"));

		initWidget(mainPanel);

		bodyContainer.setWidget(new Label("Click on one of the tab to start."));
	}

	public HasClickHandlers getCompanyMenu() {
		return c;
	}

	public HasClickHandlers getProductMenu() {
		return p;
	}

	public void setBody(Widget newBody) {
		bodyContainer.setWidget(newBody);
	}

	public void displayErrorMessage(String error) {
		Window.alert("Error: " + error);
	}

	public void setWaitVisible(boolean visible) {
		if (visible) {
			wait.setPopupPosition(bodyContainer.getAbsoluteLeft(),
					bodyContainer.getAbsoluteTop());
			wait.setPixelSize(bodyContainer.getOffsetWidth(), bodyContainer
					.getOffsetHeight());
			wait.show();
		} else {
			wait.hide();
		}
	}

	public void displayText(String text) {
		message.setText(text);
		message.setVisible(text.length() > 0);
	}

}
