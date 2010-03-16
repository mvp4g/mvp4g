package com.mvp4g.example.client.view.gxt;

import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;

public class MyGXTPagingToolBar extends PagingToolBar implements com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTPagingToolBarInterface {

	public MyGXTPagingToolBar( int pageSize ) {
		super( pageSize );
	}

}
