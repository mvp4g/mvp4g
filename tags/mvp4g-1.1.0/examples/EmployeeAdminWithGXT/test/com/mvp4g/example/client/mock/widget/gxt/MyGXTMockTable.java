package com.mvp4g.example.client.mock.widget.gxt;

import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTTableInterface;

public class MyGXTMockTable extends MyGXTMockWidget implements MyGXTTableInterface {

	private int lastSelected = -1;

	public void selectRow( int row ) {
		lastSelected = row;
	}

	public void unSelectRow( int row ) {
		if ( lastSelected == row ) {
			lastSelected = -1;
		}
	}

	public int getLastSelected() {
		return lastSelected;
	}

}
