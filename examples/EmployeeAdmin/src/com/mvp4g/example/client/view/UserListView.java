package com.mvp4g.example.client.view;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.view_interface.UserListViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyLabelInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyTableInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;
import com.mvp4g.example.client.view.widget.MyButton;
import com.mvp4g.example.client.view.widget.MyLabel;
import com.mvp4g.example.client.view.widget.MyTable;

public class UserListView extends Composite implements UserListViewInterface, MyWidgetInterface {

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

	public MyButtonInterface getDeleteButton() {
		return delete;
	}

	public MyTableInterface getTable() {
		return userList;
	}

	public MyButtonInterface getNewButton() {
		return newButton;
	}

	public MyLabelInterface getConfirmText() {
		return confirmText;
	}

	public MyButtonInterface getNoButton() {
		return no;
	}

	public MyButtonInterface getYesButton() {
		return yes;
	}

	public MyWidgetInterface getViewWidget() {
		return this;
	}

}
