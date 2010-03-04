package com.mvp4g.example.client.mock.view;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.mock.widget.MyMockButton;
import com.mvp4g.example.client.mock.widget.MyMockLabel;
import com.mvp4g.example.client.mock.widget.MyMockTable;
import com.mvp4g.example.client.presenter.view_interface.UserListViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyLabelInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyTableInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;

public class MockUserListView implements UserListViewInterface, MyWidgetInterface {

	private MyLabelInterface confirmText = new MyMockLabel();
	private MyButtonInterface deleteButton = new MyMockButton();
	private MyButtonInterface newButton = new MyMockButton();
	private MyButtonInterface yesButton = new MyMockButton();
	private MyButtonInterface noButton = new MyMockButton();
	private MyTableInterface table = new MyMockTable();

	public MyLabelInterface getConfirmText() {
		return confirmText;
	}

	public MyButtonInterface getDeleteButton() {
		return deleteButton;
	}

	public MyButtonInterface getNewButton() {
		return newButton;
	}

	public MyButtonInterface getNoButton() {
		return noButton;
	}

	public MyTableInterface getTable() {
		return table;
	}

	public MyWidgetInterface getViewWidget() {
		return this;
	}

	public MyButtonInterface getYesButton() {
		return yesButton;
	}

	public Widget getMyWidget() {
		return null;
	}

}
