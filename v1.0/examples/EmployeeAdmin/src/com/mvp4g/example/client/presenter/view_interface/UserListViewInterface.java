package com.mvp4g.example.client.presenter.view_interface;

import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyLabelInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyTableInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;

public interface UserListViewInterface {
	
	public MyWidgetInterface getViewWidget();
	public MyTableInterface getListTable();
	public MyButtonInterface getDeleteButton();
	public MyButtonInterface getNewButton();
	public MyButtonInterface getYesButton();
	public MyButtonInterface getNoButton();
	public MyLabelInterface getConfirmText();

}
