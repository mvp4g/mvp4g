package com.mvp4g.example.client.view;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.UserProfilePresenter.IUserProfileView;
import com.mvp4g.example.client.widget.impl.MyGXTButton;
import com.mvp4g.example.client.widget.impl.MyGXTComboBox;
import com.mvp4g.example.client.widget.impl.MyGXTTextBox;
import com.mvp4g.example.client.widget.interfaces.IButton;
import com.mvp4g.example.client.widget.interfaces.IListBox;
import com.mvp4g.example.client.widget.interfaces.ITextBox;
import com.mvp4g.example.client.widget.interfaces.IWidget;

public class UserProfileView extends LayoutContainer implements IUserProfileView {

	private MyGXTTextBox firstName = new MyGXTTextBox( "First Name" );
	private MyGXTTextBox lastName = new MyGXTTextBox( "Last Name" );
	private MyGXTTextBox email = new MyGXTTextBox( "Email" );
	private MyGXTTextBox username = new MyGXTTextBox( "Username *" );
	private MyGXTTextBox password = new MyGXTTextBox( "Password *" );
	private MyGXTTextBox confirmPassword = new MyGXTTextBox( "Confirm Password *" );
	private MyGXTComboBox department = new MyGXTComboBox( "Department" );

	private MyGXTButton update = new MyGXTButton();
	private MyGXTButton cancel = new MyGXTButton( "Cancel" );

	public UserProfileView() {

		FormPanel form = new FormPanel();
		form.setStyleName( "userProfile" );
		form.setHeading( "User Profile" );

		password.setPassword( true );
		confirmPassword.setPassword( true );

		form.add( firstName );
		form.add( lastName );
		form.add( email );
		form.add( username );
		form.add( password );
		form.add( confirmPassword );
		form.add( department );

		form.addButton( update );
		form.addButton( cancel );
		form.setButtonAlign( HorizontalAlignment.CENTER );

		add( form );
	}

	public IWidget getViewWidget() {
		return this;
	}

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

	public Widget getMyWidget() {
		return this;
	}

	public void showAddMode() {
		update.setText( "Add User" );
	}

	public void showUpdateMode() {
		update.setText( "Update" );
	}

}
