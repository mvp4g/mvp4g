package com.mvp4g.example.client.view;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.presenter.gxt.MyListModel;
import com.mvp4g.example.client.presenter.view_interface.UserRoleViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;

public class UserRoleView extends LayoutContainer implements UserRoleViewInterface, MyWidgetInterface, Constants {

	private ListField<MyListModel> selectedRoles = new ListField<MyListModel>();
	private SimpleComboBox<String> rolesChoices = new SimpleComboBox<String>();

	private Button add = new Button( "Add" );
	private Button remove = new Button( "Remove" );

	public UserRoleView() {

		selectedRoles.setWidth( "100%" );

		FormPanel form = new FormPanel();
		form.setStyleName( "userRoles" );
		form.setHeading( "User Roles" );
		selectedRoles.setHideLabel( true );
		rolesChoices.setHideLabel( true );
		selectedRoles.setStore( new ListStore<MyListModel>() );
		form.add( selectedRoles );
		form.add( rolesChoices );
		form.addButton( add );
		form.addButton( remove );

		form.setButtonAlign( HorizontalAlignment.CENTER );

		add( form );
	}

	public MyWidgetInterface getViewWidget() {
		return this;
	}

	public void displayError( String error ) {
		Window.alert( error );
	}

	public Button getAddButton() {
		return add;
	}

	public Button getRemoveButton() {
		return remove;
	}

	public SimpleComboBox<String> getRoleChoiceListBox() {
		return rolesChoices;
	}

	public ListField<MyListModel> getSelectedRolesListBox() {
		return selectedRoles;
	}

	public Widget getMyWidget() {
		return this;
	}

}
