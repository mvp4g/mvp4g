package com.mvp4g.example.client.widget.impl;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.mvp4g.example.client.widget.interfaces.gxt.IGXTTable;

public class MyGXTTable extends Grid<BeanModel> implements IGXTTable {

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
