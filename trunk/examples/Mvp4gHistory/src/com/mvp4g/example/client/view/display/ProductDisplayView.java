package com.mvp4g.example.client.view.display;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.view_interface.display.ProductDisplayViewInterface;

public class ProductDisplayView extends BasicBeanDisplayView implements ProductDisplayViewInterface {
	
	private Label price = new Label();
	
	@Override
	protected Widget createWidget() {
		VerticalPanel mainPanel = (VerticalPanel) super.createWidget();
		HorizontalPanel panel = new HorizontalPanel();
		panel.add( new Label("Price") );
		panel.add( price );		
		price.addStyleName( "price" );		
		mainPanel.add( panel );
		return mainPanel;
	}

	public Label getPrice() {
		return price;
	}

}
