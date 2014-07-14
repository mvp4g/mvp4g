package com.mvp4g.example.client.view;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.UserListPresenter.IUserListView;
import com.mvp4g.example.client.widget.impl.MyButton;
import com.mvp4g.example.client.widget.impl.MyLabel;
import com.mvp4g.example.client.widget.impl.MyTable;
import com.mvp4g.example.client.widget.interfaces.IButton;
import com.mvp4g.example.client.widget.interfaces.ILabel;
import com.mvp4g.example.client.widget.interfaces.ITable;
import com.mvp4g.example.client.widget.interfaces.IWidget;

public class UserListView extends Composite implements IUserListView {

	private MyButton delete = new MyButton( "Delete" );
	private MyButton newButton = new MyButton( "New" );
	private MyButton yes = new MyButton( "Yes" );
	private MyButton no = new MyButton( "No" );
	private MyLabel confirmText = new MyLabel( "Are you Sure?" );

	private MyTable userList = new MyTable();

	public UserListView() {

		CaptionPanel cpPanel = new CaptionPanel( "Users" );

		HorizontalPanel buttons = new HorizontalPanel();
		buttons.setSpacing( 4 );
		buttons.add( delete );
		buttons.add( newButton );
		buttons.add( confirmText );
		buttons.add( yes );
		buttons.add( no );

		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add( userList );
		mainPanel.add( buttons );
		mainPanel.setStyleName( "users" );

		userList.setStyleName( "userList" );
		userList.setCellPadding( 0 );
		userList.setCellSpacing( 0 );
		userList.getRowFormatter().setStyleName( 0, "header" );
		userList.setText( 0, 0, "Username" );
		userList.setText( 0, 1, "First Name" );
		userList.setText( 0, 2, "Last Name" );
		userList.setText( 0, 3, "Email" );
		userList.setText( 0, 4, "Department" );

		cpPanel.add( mainPanel );

		initWidget( cpPanel );
	}

	public Widget getMyWidget() {
		return this;
	}

	public IButton getDeleteButton() {
		return delete;
	}

	public ITable getTable() {
		return userList;
	}

	public IButton getNewButton() {
		return newButton;
	}

	public ILabel getConfirmText() {
		return confirmText;
	}

	public IButton getNoButton() {
		return no;
	}

	public IButton getYesButton() {
		return yes;
	}

	public IWidget getViewWidget() {
		return this;
	}

}
