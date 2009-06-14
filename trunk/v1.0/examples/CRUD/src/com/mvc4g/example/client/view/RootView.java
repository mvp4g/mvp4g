package com.mvc4g.example.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvc4g.example.client.presenter.view_interface.RootViewInterface;
import com.mvc4g.example.client.view.widget.LabelWithValue;

public class RootView extends Composite implements RootViewInterface{
	
	private LabelWithValue message = new LabelWithValue();
	private Panel body = new SimplePanel();
	
	public RootView(){
		VerticalPanel mainPanel = new VerticalPanel();
		message.setValue("Test");
		mainPanel.add(message);
		mainPanel.add(body);
		initWidget(mainPanel);		
	}
	
	public HasValue<String> getMessage(){
		return message;
	}
	
	public Panel getBody(){
		return body;
	}
	
	public Widget getViewWidget(){
		return this;
	}

}
