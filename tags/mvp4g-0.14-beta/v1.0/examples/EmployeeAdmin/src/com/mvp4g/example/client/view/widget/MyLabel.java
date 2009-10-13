package com.mvp4g.example.client.view.widget;

import com.google.gwt.user.client.ui.Label;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyLabelInterface;

public class MyLabel extends Label implements MyLabelInterface {
	
	public MyLabel(String text){
		super(text);
	}

}
