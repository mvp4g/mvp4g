package com.mvp4g.example.client.presenter.view_interface;

import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyLabelInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTTableInterface;

public interface UserListViewInterface {
	
	public MyWidgetInterface getViewWidget();
	public MyGXTTableInterface getTable();
	public MyGXTButtonInterface getDeleteButton();
	public MyGXTButtonInterface getNewButton();
	public MyGXTButtonInterface getYesButton();
	public MyGXTButtonInterface getNoButton();
	public MyLabelInterface getConfirmText();

}
