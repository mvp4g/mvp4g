package com.mvp4g.example.client.mock.view;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.mock.widget.MyMockLabel;
import com.mvp4g.example.client.mock.widget.gxt.MyGXTMockButton;
import com.mvp4g.example.client.mock.widget.gxt.MyGXTMockPagingToolBar;
import com.mvp4g.example.client.mock.widget.gxt.MyGXTMockTable;
import com.mvp4g.example.client.presenter.view_interface.UserListViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyLabelInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTPagingToolBarInterface;

public class MockUserListView implements UserListViewInterface, MyWidgetInterface {

	private MyLabelInterface confirmText = new MyMockLabel();
	private MyGXTMockButton deleteButton = new MyGXTMockButton();
	private MyGXTMockButton newButton = new MyGXTMockButton();
	private MyGXTMockButton yesButton = new MyGXTMockButton();
	private MyGXTMockButton noButton = new MyGXTMockButton();
	private MyGXTMockTable table = new MyGXTMockTable();
	private MyGXTMockPagingToolBar toolBar = new MyGXTMockPagingToolBar();
	
	private ListStore<BeanModel> store = null;	

	public MyLabelInterface getConfirmText() {
		return confirmText;
	}

	public MyGXTMockButton getDeleteButton() {
		return deleteButton;
	}

	public MyGXTMockButton getNewButton() {
		return newButton;
	}

	public MyGXTMockButton getNoButton() {
		return noButton;
	}

	public MyGXTMockTable getTable() {
		return table;
	}

	public MyWidgetInterface getViewWidget() {
		return this;
	}

	public MyGXTMockButton getYesButton() {
		return yesButton;
	}

	public Widget getMyWidget() {
		return null;
	}

	public void buildWidget( ListStore<BeanModel> store ) {
		this.store = store;
	}

	public MyGXTPagingToolBarInterface getToolBar() {
		return toolBar;
	}
	
	public ListStore<BeanModel> getStore(){
		return store;
	}

}
