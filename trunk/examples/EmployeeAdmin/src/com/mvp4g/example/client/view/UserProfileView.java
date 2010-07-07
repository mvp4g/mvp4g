package com.mvp4g.example.client.view;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.UserProfilePresenter.IUserProfileView;
import com.mvp4g.example.client.widget.impl.MyButton;
import com.mvp4g.example.client.widget.impl.MyListBox;
import com.mvp4g.example.client.widget.impl.MyPasswordTextBox;
import com.mvp4g.example.client.widget.impl.MyTextBox;
import com.mvp4g.example.client.widget.interfaces.IButton;
import com.mvp4g.example.client.widget.interfaces.IListBox;
import com.mvp4g.example.client.widget.interfaces.ITextBox;
import com.mvp4g.example.client.widget.interfaces.IWidget;

public class UserProfileView extends Composite implements IUserProfileView {

	private MyTextBox firstName = new MyTextBox();
	private MyTextBox lastName = new MyTextBox();
	private MyTextBox email = new MyTextBox();
	private MyTextBox username = new MyTextBox();
	private MyPasswordTextBox password = new MyPasswordTextBox();
	private MyPasswordTextBox confirmPassword = new MyPasswordTextBox();
	private MyListBox department = new MyListBox();

	private MyButton update = new MyButton();
	private MyButton cancel = new MyButton( "Cancel" );

	public UserProfileView() {
		CaptionPanel cp = new CaptionPanel( "User Profile" );

		Grid g = new Grid( 8, 2 );

		g.setText( 0, 0, "First Name" );
		g.setText( 1, 0, "Last Name" );
		g.setText( 2, 0, "Email" );
		g.setText( 3, 0, "Username *" );
		g.setText( 4, 0, "Password *" );
		g.setText( 5, 0, "Confirm Password *" );
		g.setText( 6, 0, "Department *" );

		g.setWidget( 0, 1, firstName );
		g.setWidget( 1, 1, lastName );
		g.setWidget( 2, 1, email );
		g.setWidget( 3, 1, username );
		g.setWidget( 4, 1, password );
		g.setWidget( 5, 1, confirmPassword );
		g.setWidget( 6, 1, department );

		g.setWidget( 7, 0, update );
		g.setWidget( 7, 1, cancel );

		cp.add( g );

		initWidget( cp );
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
