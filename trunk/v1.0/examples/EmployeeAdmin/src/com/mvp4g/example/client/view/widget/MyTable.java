package com.mvp4g.example.client.view.widget;

import com.google.gwt.user.client.ui.FlexTable;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyTableInterface;

public class MyTable extends FlexTable implements MyTableInterface {

	public void selectRow( int row ) {
		getRowFormatter().addStyleName( row, "selected" );

	}

	public void unSelectRow( int row ) {
		getRowFormatter().removeStyleName( row, "selected" );
	}

}
