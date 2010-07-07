package com.mvp4g.example.client.mock.view;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.mock.widget.MyMockButton;
import com.mvp4g.example.client.mock.widget.MyMockListBox;
import com.mvp4g.example.client.mock.widget.MyMockTextBox;
import com.mvp4g.example.client.presenter.UserProfilePresenter.IUserProfileView;
import com.mvp4g.example.client.widget.interfaces.IButton;
import com.mvp4g.example.client.widget.interfaces.IListBox;
import com.mvp4g.example.client.widget.interfaces.ITextBox;
import com.mvp4g.example.client.widget.interfaces.IWidget;

public class MockUserProfileView implements IUserProfileView {

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

	public IButton getCancelButton() {
		return cancel;
	}

	public ITextBox getConfirmPassword() {
		return confirmPassword;
	}

	public IListBox getDepartment() {
		return department;
	}

	public ITextBox getEmail() {
		return email;
	}

	public ITextBox getFirstName() {
		return firstName;
	}

	public ITextBox getLastName() {
		return lastName;
	}

	public ITextBox getPassword() {
		return password;
	}

	public IButton getUpdateButton() {
		return update;
	}

	public ITextBox getUsername() {
		return username;
	}

	public IWidget getViewWidget() {
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

	public boolean isUpdateMode() {
		return updateMode;
	}

}
