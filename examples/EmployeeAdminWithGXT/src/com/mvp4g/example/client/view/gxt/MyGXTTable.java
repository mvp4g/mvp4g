package com.mvp4g.example.client.view.gxt;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.event.dom.client.ClickEvent;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.gxt.MyUserListModel;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTTableInterface;

public class MyGXTTable extends Grid<MyUserListModel> implements MyGXTTableInterface{

	public MyGXTTable(ListStore<MyUserListModel> store, ColumnModel cm){
		super( store, cm );
	}
	
	
	public int getRowCount() {
		return store.getCount();
	}

	public int getRowForEvent( ClickEvent event ) {
		return 0;
	}

	public void removeRow( int row ) {
		store.remove(store.getAt( row ));		
	}

	public void selectRow( int row ) {
		getSelectionModel().select( row, false );
	}

	public void addUser( UserBean user ) {
		store.add( new MyUserListModel(user) );
	}

	public void unSelectRow( int row ) {
		getSelectionModel().deselect( row );		
	}


	public void updateUser( UserBean user , int index) {
		MyUserListModel model = store.getAt( index );
		model.updateData( user );	
		store.update( model );
	}
}
