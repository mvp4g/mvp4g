package com.mvp4g.example.client.presenter.view_interface;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyLabelInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTPagingToolBarInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTTableInterface;

public interface UserListViewInterface {

	public void buildWidget( ListStore<BeanModel> store );

	public MyWidgetInterface getViewWidget();

	public MyGXTTableInterface getTable();

	public MyGXTPagingToolBarInterface getToolBar();

	public MyGXTButtonInterface getDeleteButton();

	public MyGXTButtonInterface getNewButton();

	public MyGXTButtonInterface getYesButton();

	public MyGXTButtonInterface getNoButton();

	public MyLabelInterface getConfirmText();

}
