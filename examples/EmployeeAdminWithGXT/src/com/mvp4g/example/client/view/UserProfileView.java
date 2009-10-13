package com.mvp4g.example.client.view;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.view_interface.UserProfileViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyListBoxInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyTextBoxInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;
import com.mvp4g.example.client.view.gxt.MyGXTButton;
import com.mvp4g.example.client.view.gxt.MyGXTComboBox;
import com.mvp4g.example.client.view.gxt.MyGXTTextBox;

public class UserProfileView extends LayoutContainer implements UserProfileViewInterface, MyWidgetInterface {

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

	public MyWidgetInterface getViewWidget() {
		return this;
	}

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
