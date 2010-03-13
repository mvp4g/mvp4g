package com.mvp4g.example.client.view.gxt;

import com.extjs.gxt.ui.client.widget.button.Button;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTButtonInterface;

public class MySimpleGXTButton extends Button implements MyGXTButtonInterface {

	public MySimpleGXTButton( String text ) {
		super( text );
	}
}
