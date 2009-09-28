package com.mvp4g.example.client.view;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.mvp4g.example.client.presenter.view_interface.display.ProductDisplayViewInterface;

public class ProductDisplayView extends BasicBeanDisplayView implements ProductDisplayViewInterface {
	
	private Label price = new Label();
	
	public ProductDisplayView(){
		HorizontalPanel panel = new HorizontalPanel();
		panel.add( new Label("Price") );
		panel.add( price );
	}

	public Label getPrice() {
		return price;
	}

}
