package com.mvp4g.example.client.view.display;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mvp4g.example.client.presenter.view_interface.display.BasicBeanDisplayViewInterface;

public class BasicBeanDisplayView extends Composite implements BasicBeanDisplayViewInterface {
	
	private VerticalPanel mainPanel = new VerticalPanel();
	
	private Label name = new Label();
	private Label description = new Label();
	
	public BasicBeanDisplayView(){
		initWidget( mainPanel );
		setStyleName( "display" );
		name.addStyleName( "name" );
		description.addStyleName( "description" );
		mainPanel.add( name );
		mainPanel.add( description );		
	}

	public Label getDescription() {
		return description;
	}

	public Label getName() {
		return name;
	}
	
	protected VerticalPanel getMainPanel(){
		return mainPanel;
	}
	

}
