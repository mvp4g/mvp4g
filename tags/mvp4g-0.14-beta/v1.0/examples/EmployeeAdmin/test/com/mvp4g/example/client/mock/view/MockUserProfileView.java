package com.mvp4g.example.client.mock.view;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.mock.widget.MyMockButton;
import com.mvp4g.example.client.mock.widget.MyMockListBox;
import com.mvp4g.example.client.mock.widget.MyMockTextBox;
import com.mvp4g.example.client.presenter.view_interface.UserProfileViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyListBoxInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyTextBoxInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;

public class MockUserProfileView implements UserProfileViewInterface, MyWidgetInterface {
	
	private boolean updateMode = true;
	
	private MyMockButton cancel = new MyMockButton();
	private MyMockButton update = new MyMockButton();
	
	private MyMockTextBox confirmPassword = new MyMockTextBox();
	private MyMockListBox department = new MyMockListBox();
	private MyMockTextBox email = new MyMockTextBox();
	private MyMockTextBox firstName = new MyMockTextBox();
	private MyMockTextBox lastName = new MyMockTextBox();
	private MyMockTextBox password = new MyMockTextBox();
	private MyMockTextBox username = new MyMockTextBox();

	public MyButtonInterface getCancelButton() {
		return cancel;
	}

	public MyTextBoxInterface getConfirmPassword() {
		return confirmPassword;
	}

	public MyListBoxInterface getDepartment() {
		return department;
	}

	public MyTextBoxInterface getEmail() {
		return email;
	}

	public MyTextBoxInterface getFirstName() {
		return firstName;
	}

	public MyTextBoxInterface getLastName() {
		return lastName;
	}

	public MyTextBoxInterface getPassword() {
		return password;
	}

	public MyButtonInterface getUpdateButton() {
		return update;
	}

	public MyTextBoxInterface getUsername() {
		return username;
	}

	public MyWidgetInterface getViewWidget() {
		return this;
	}

	public Widget getMyWidget() {
		return null;
	}

	public void showAddMode() {
		updateMode = false;		
	}

	public void showUpdateMode() {
		updateMode = true;		
	}
	
	public boolean isUpdateMode(){
		return updateMode;
	}

}
