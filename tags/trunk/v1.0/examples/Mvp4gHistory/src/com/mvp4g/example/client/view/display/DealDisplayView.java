package com.mvp4g.example.client.view.display;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.mvp4g.example.client.presenter.view_interface.display.DealDisplayViewInterface;

public class DealDisplayView extends BasicBeanDisplayView implements DealDisplayViewInterface {
	
	private Label code = new Label();
	
	public DealDisplayView(){
		HorizontalPanel panel = new HorizontalPanel();
		panel.add( new Label("Code") );
		panel.add( code );
		
		code.addStyleName( "price" );
		
		getMainPanel().add( panel );
	}

	public Label getCode() {
		return code;
	}

}
