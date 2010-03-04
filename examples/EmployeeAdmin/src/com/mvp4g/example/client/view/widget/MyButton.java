package com.mvp4g.example.client.view.widget;

import com.google.gwt.user.client.ui.Button;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;

public class MyButton extends Button implements MyButtonInterface {

	public MyButton() {
		//nothing to do
	}

	public MyButton( String text ) {
		super( text );
	}

}
