package com.mvp4g.example.client.mock.widget.gxt;

import java.util.ArrayList;
import java.util.List;

import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTTableInterface;

public class MyGXTMockTable extends MyGXTMockWidget implements MyGXTTableInterface {

	private List<UserBean> table = new ArrayList<UserBean>();
	private int lastSelected = -1;

	public int getRowCount() {
		return table.size();
	}

	public void removeRow( int row ) {
		table.remove( row );
	}

	public void selectRow( int row ) {
		lastSelected = row;
	}

	public void unSelectRow( int row ) {
		if ( lastSelected == row ) {
			lastSelected = -1;
		}
	}

	public UserBean getRow( int row ) {
		return table.get( row );
	}

	public int getLastSelected() {
		return lastSelected;
	}

	public void addUser( UserBean user ) {
		table.add( user );
	}

	public void updateUser( UserBean user, int index ) {
		table.set( index, user );
	}

}
