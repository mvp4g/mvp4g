package com.mvp4g.example.client.view.gxt;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTTableInterface;

public class MyGXTTable extends Grid<BeanModel> implements MyGXTTableInterface {

	public MyGXTTable( ListStore<BeanModel> store, ColumnModel cm ) {
		super( store, cm );
	}

	public void selectRow( int row ) {
		getSelectionModel().select( row, false );
	}

	public void unSelectRow( int row ) {
		getSelectionModel().deselect( row );
	}

}
