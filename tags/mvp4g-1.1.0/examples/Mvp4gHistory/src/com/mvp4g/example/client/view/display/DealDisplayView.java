package com.mvp4g.example.client.view.display;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.view_interface.display.DealDisplayViewInterface;

public class DealDisplayView extends BasicBeanDisplayView implements DealDisplayViewInterface {

	private Label code = new Label();

	@Override
	protected Widget createWidget() {
		VerticalPanel mainPanel = (VerticalPanel)super.createWidget();
		HorizontalPanel panel = new HorizontalPanel();
		panel.add( new Label( "Code" ) );
		panel.add( code );
		code.addStyleName( "price" );
		mainPanel.add( panel );
		return mainPanel;
	}

	public Label getCode() {
		return code;
	}

}
