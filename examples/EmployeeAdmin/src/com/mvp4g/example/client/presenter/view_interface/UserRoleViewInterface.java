package com.mvp4g.example.client.presenter.view_interface;

import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyListBoxInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;

public interface UserRoleViewInterface {
	
	public MyWidgetInterface getViewWidget();
	public MyListBoxInterface getSelectedRolesListBox();
	public MyListBoxInterface getRoleChoiceListBox();
	public MyButtonInterface getAddButton();
	public MyButtonInterface getRemoveButton();
	public void displayError(String error);

}
