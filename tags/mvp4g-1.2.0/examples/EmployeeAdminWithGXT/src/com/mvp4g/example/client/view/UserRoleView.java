package com.mvp4g.example.client.view;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.gxt.MyListModel;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.IUserRoleView;
import com.mvp4g.example.client.widget.interfaces.IWidget;

public class UserRoleView extends LayoutContainer implements IUserRoleView {

	private IUserRolePresenter presenter;

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
		rolesChoices.setTriggerAction(TriggerAction.ALL); 
		
		selectedRoles.setStore( new ListStore<MyListModel>() );
		form.add( selectedRoles );
		form.add( rolesChoices );
		form.addButton( add );
		form.addButton( remove );

		form.setButtonAlign( HorizontalAlignment.CENTER );

		add( form );
	}

	public IWidget getViewWidget() {
		return this;
	}

	public void displayError( String error ) {
		Window.alert( error );
	}

	public void addPossibleRole( String role ) {
		rolesChoices.add( role );
	}

	public void addUserRole( String role ) {
		selectedRoles.getStore().add( new MyListModel( role ) );
	}

	public void clearUserRoles() {
		selectedRoles.getStore().removeAll();
	}

	public String getPossibleRoleSelected() {
		return rolesChoices.getSimpleValue();
	}

	public List<String> getSelectedRoles() {
		List<String> roles = new ArrayList<String>();
		for ( MyListModel role : selectedRoles.getSelection() ) {
			roles.add( role.getText() );
		}
		return roles;
	}

	public void removeUserRole( String role ) {
		selectedRoles.getStore().remove( new MyListModel( role ) );
	}

	public void setAddButtonEnabled( boolean enabled ) {
		add.setEnabled( enabled );
	}

	public void setPossibleRole( String role ) {
		rolesChoices.setSimpleValue( role );
	}

	public void setPossibleRoleEnabled( boolean enabled ) {
		rolesChoices.setEnabled( enabled );
	}

	public void setPresenter( IUserRolePresenter p ) {
		this.presenter = p;

		//bind the view
		add.addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
				presenter.onAddButtonClicked();
			}

		} );

		remove.addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
				presenter.onRemoveButtonClicked();

			}

		} );

		selectedRoles.addSelectionChangedListener(new SelectionChangedListener<MyListModel>(){

			@Override
			public void selectionChanged( SelectionChangedEvent<MyListModel> se ) {
				presenter.onRoleSelected();				
			}
			
		});

		rolesChoices.addListener( Events.Select, new Listener<FieldEvent>() {

			public void handleEvent( FieldEvent be ) {
				presenter.onPossibleRoleSelected();
			}

		} );
	}

	public void setRemoveButtonEnabled( boolean enabled ) {
		remove.setEnabled( enabled );
	}

	public Widget getMyWidget() {
		return this;
	}

}
