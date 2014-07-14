package com.mvp4g.example.client.mock.view;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.mock.widget.MyMockLabel;
import com.mvp4g.example.client.mock.widget.gxt.MyGXTMockButton;
import com.mvp4g.example.client.mock.widget.gxt.MyGXTMockPagingToolBar;
import com.mvp4g.example.client.mock.widget.gxt.MyGXTMockTable;
import com.mvp4g.example.client.presenter.UserListPresenter.IUserListView;
import com.mvp4g.example.client.widget.interfaces.ILabel;
import com.mvp4g.example.client.widget.interfaces.IWidget;
import com.mvp4g.example.client.widget.interfaces.gxt.IGXTPagingToolBar;

public class MockUserListView implements IUserListView {

	private ILabel confirmText = new MyMockLabel();
	private MyGXTMockButton deleteButton = new MyGXTMockButton();
	private MyGXTMockButton newButton = new MyGXTMockButton();
	private MyGXTMockButton yesButton = new MyGXTMockButton();
	private MyGXTMockButton noButton = new MyGXTMockButton();
	private MyGXTMockTable table = new MyGXTMockTable();
	private MyGXTMockPagingToolBar toolBar = new MyGXTMockPagingToolBar();

	private ListStore<BeanModel> store = null;

	public ILabel getConfirmText() {
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

	public IWidget getViewWidget() {
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

	public IGXTPagingToolBar getToolBar() {
		return toolBar;
	}

	public ListStore<BeanModel> getStore() {
		return store;
	}

}
