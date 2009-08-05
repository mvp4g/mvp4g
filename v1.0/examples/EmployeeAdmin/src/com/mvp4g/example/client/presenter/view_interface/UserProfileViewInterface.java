package com.mvp4g.example.client.presenter.view_interface;

import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyListBoxInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyTextBoxInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;

public interface UserProfileViewInterface {
	
	public MyWidgetInterface getViewWidget();
	public MyTextBoxInterface getFirstName();
	public MyTextBoxInterface getLastName();
	public MyTextBoxInterface getEmail();
	public MyTextBoxInterface getUsername();
	public MyTextBoxInterface getPassword();
	public MyTextBoxInterface getConfirmPassword();
	public MyListBoxInterface getDepartment();
	public MyButtonInterface getUpdateButton();
	public MyButtonInterface getCancelButton();

}
