package com.mvp4g.example.client.view;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.view_interface.UserListViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyLabelInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTPagingToolBarInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTTableInterface;
import com.mvp4g.example.client.view.gxt.MyGXTPagingToolBar;
import com.mvp4g.example.client.view.gxt.MyGXTTable;
import com.mvp4g.example.client.view.gxt.MyLabel;
import com.mvp4g.example.client.view.gxt.MySimpleGXTButton;

public class UserListView extends ContentPanel implements UserListViewInterface, MyWidgetInterface {

	private MySimpleGXTButton delete = new MySimpleGXTButton( "Delete" );
	private MySimpleGXTButton newButton = new MySimpleGXTButton( "New" );
	private MySimpleGXTButton yes = new MySimpleGXTButton( "Yes" );
	private MySimpleGXTButton no = new MySimpleGXTButton( "No" );
	private MyLabel confirmText = new MyLabel( "Are you Sure?" );

	private MyGXTTable userList = null;
	private MyGXTPagingToolBar toolBar = new MyGXTPagingToolBar( 4 );

	public UserListView() {

		setBodyBorder( false );
		setHeading( "Users" );
		setButtonAlign( HorizontalAlignment.CENTER );

		HorizontalPanel buttons = new HorizontalPanel();
		buttons.setSpacing( 4 );
		buttons.add( delete );
		buttons.add( newButton );
		buttons.add( confirmText );
		buttons.add( yes );
		buttons.add( no );

		add( buttons );

		setBottomComponent( toolBar );

	}

	public void buildWidget( ListStore<BeanModel> store ) {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		configs.add( new ColumnConfig( "username", "Username", 150 ) );
		configs.add( new ColumnConfig( "firstName", "First Name", 150 ) );
		configs.add( new ColumnConfig( "lastName", "Last Name", 150 ) );
		configs.add( new ColumnConfig( "email", "Email", 150 ) );
		configs.add( new ColumnConfig( "department", "Department", 150 ) );

		ColumnModel cm = new ColumnModel( configs );

		userList = new MyGXTTable( store, cm );
		userList.setBorders( true );
		userList.setAutoHeight( true );
		userList.setStripeRows( true );

		add( userList );

	}

	public Widget getMyWidget() {
		return this;
	}

	public MySimpleGXTButton getDeleteButton() {
		return delete;
	}

	public MyGXTTableInterface getTable() {
		return userList;
	}

	public MySimpleGXTButton getNewButton() {
		return newButton;
	}

	public MyLabelInterface getConfirmText() {
		return confirmText;
	}

	public MySimpleGXTButton getNoButton() {
		return no;
	}

	public MySimpleGXTButton getYesButton() {
		return yes;
	}

	public MyWidgetInterface getViewWidget() {
		return this;
	}

	public MyGXTPagingToolBarInterface getToolBar() {
		return toolBar;
	}

}
