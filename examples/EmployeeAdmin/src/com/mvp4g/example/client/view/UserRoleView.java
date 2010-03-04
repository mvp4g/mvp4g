package com.mvp4g.example.client.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.view_interface.UserRoleViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyListBoxInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;
import com.mvp4g.example.client.view.widget.MyButton;
import com.mvp4g.example.client.view.widget.MyListBox;

public class UserRoleView extends Composite implements UserRoleViewInterface, MyWidgetInterface {

	private MyListBox selectedRoles = new MyListBox();
	private MyListBox rolesChoices = new MyListBox();

	private MyButton add = new MyButton( "Add" );
	private MyButton remove = new MyButton( "Remove" );

	public UserRoleView() {

		selectedRoles.setVisibleItemCount( 10 );
		selectedRoles.setWidth( "100%" );

		CaptionPanel cp = new CaptionPanel( "User Roles" );

		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing( 3 );
		hp.add( rolesChoices );
		hp.add( add );
		hp.add( remove );

		VerticalPanel vp = new VerticalPanel();
		vp.setWidth( "100%" );
		vp.setSpacing( 2 );
		vp.add( selectedRoles );
		vp.add( hp );

		cp.add( vp );

		initWidget( cp );
	}

	public MyWidgetInterface getViewWidget() {
		return this;
	}

	public void displayError( String error ) {
		Window.alert( error );
	}

	public MyButtonInterface getAddButton() {
		return add;
	}

	public MyButtonInterface getRemoveButton() {
		return remove;
	}

	public MyListBoxInterface getRoleChoiceListBox() {
		return rolesChoices;
	}

	public MyListBoxInterface getSelectedRolesListBox() {
		return selectedRoles;
	}

	public Widget getMyWidget() {
		return this;
	}

}
