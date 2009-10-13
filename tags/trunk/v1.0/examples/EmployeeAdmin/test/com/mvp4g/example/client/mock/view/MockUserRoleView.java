package com.mvp4g.example.client.mock.view;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.mock.widget.MyMockButton;
import com.mvp4g.example.client.mock.widget.MyMockListBox;
import com.mvp4g.example.client.presenter.view_interface.UserRoleViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyListBoxInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;

public class MockUserRoleView implements UserRoleViewInterface, MyWidgetInterface {

	private String error = null;
	private MyMockButton add = new MyMockButton();
	private MyMockButton remove = new MyMockButton();
	private MyMockListBox choices = new MyMockListBox();
	private MyMockListBox selectedRoles = new MyMockListBox();

	public void displayError( String error ) {
		this.error = error;
	}

	public MyButtonInterface getAddButton() {
		return add;
	}

	public MyButtonInterface getRemoveButton() {
		return remove;
	}

	public MyListBoxInterface getRoleChoiceListBox() {
		return choices;
	}

	public MyListBoxInterface getSelectedRolesListBox() {
		return selectedRoles;
	}

	public MyWidgetInterface getViewWidget() {
		return this;
	}

	public Widget getMyWidget() {
		return null;
	}
	
	public String getError(){
		return error;
	}

}
