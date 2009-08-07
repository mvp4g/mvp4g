package com.mvp4g.example.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.presenter.Presenter;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.EventsEnum;
import com.mvp4g.example.client.UserServiceAsync;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.UserRoleViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyListBoxInterface;

public class UserRolePresenter extends Presenter<UserRoleViewInterface> implements Constants {

	private UserBean user = null;

	private boolean enabled = false;

	private UserServiceAsync service = null;

	@Override
	public void bind() {
		MyButtonInterface add = view.getAddButton();
		add.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				addRole( view.getRoleChoiceListBox().getSelectedValue() );
			}

		} );

		MyButtonInterface remove = view.getRemoveButton();
		remove.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				deleteRole( view.getSelectedRolesListBox().getSelectedValue() );
			}

		} );

		MyListBoxInterface selectedRoles = view.getSelectedRolesListBox();
		selectedRoles.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				enableRemoveButton();
			}

		} );
		selectedRoles.addKeyUpHandler( new KeyUpHandler(){

			public void onKeyUp( KeyUpEvent event ) {
				enableRemoveButton();
			}
			
		});

		MyListBoxInterface rolesChoices = view.getRoleChoiceListBox();
		rolesChoices.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				enabledAddButton();
			}

		} );
		rolesChoices.addKeyUpHandler( new KeyUpHandler() {

			public void onKeyUp( KeyUpEvent event ) {
				enabledAddButton();
			}

		} );
		rolesChoices.addItem( "--None Selected--" );
		for ( String role : ROLES ) {
			rolesChoices.addItem( role );
		}

		disable();

	}

	public void onStart() {
		eventBus.dispatch( EventsEnum.CHANGE_RIGHT_BOTTOM_WIDGET, view.getViewWidget() );
	}

	public void onSelectUser( UserBean user ) {
		this.user = user;
		MyListBoxInterface selectedRoles = view.getSelectedRolesListBox();
		selectedRoles.clear();
		List<String> roles = user.getRoles();
		if ( roles != null ) {
			for ( String role : roles ) {
				selectedRoles.addItem( role );
			}
		}
		enabled = true;
		MyListBoxInterface rolesChoices = view.getRoleChoiceListBox();
		rolesChoices.setSelectedIndex( 0 );
		rolesChoices.setEnabled( true );
		view.getRemoveButton().setEnabled( false );
		view.getAddButton().setEnabled( false );
	}

	public void onUserUpdated( UserBean user ) {
		user = null;
		disable();
	}

	public void onCreateNewUser( UserBean user ) {
		this.user = user;
		disable();
	}

	public void onUnselectUser() {
		user = null;
		disable();
	}

	public void setUserService( UserServiceAsync service ) {
		this.service = service;
	}

	private void addRole( final String role ) {

		List<String> roles = user.getRoles();
		if ( roles == null ) {
			roles = new ArrayList<String>();
			user.setRoles( roles );
		}
		if ( roles.contains( role ) ) {
			view.displayError( "Role already exists for this user" );
		} else {
			roles.add( role );
			service.updateUser( user, new AsyncCallback<Void>() {

				public void onFailure( Throwable caught ) {
					user.getRoles().remove( role );
				}

				public void onSuccess( Void result ) {
					view.getSelectedRolesListBox().addItem( role );
				}

			} );
		}

	}

	private void deleteRole( final String role ) {

		List<String> roles = user.getRoles();
		roles.remove( role );
		service.updateUser( user, new AsyncCallback<Void>() {

			public void onFailure( Throwable caught ) {
				user.getRoles().add( role );

			}

			public void onSuccess( Void result ) {
				MyListBoxInterface selectedRoles = view.getSelectedRolesListBox();
				selectedRoles.removeItem( role );
				if ( selectedRoles.getSelectedValue() == null ) {
					view.getRemoveButton().setEnabled( false );
				}
			}

		} );

	}

	private void enableRemoveButton() {
		if ( enabled && ( view.getSelectedRolesListBox().getSelectedValue() != null ) ) {
			view.getRemoveButton().setEnabled( true );
		}
	}

	private void enabledAddButton() {
		if ( enabled ) {
			if ( view.getRoleChoiceListBox().getSelectedIndex() == 0 ) {
				view.getAddButton().setEnabled( false );
			} else {
				view.getAddButton().setEnabled( true );
			}
		}
	}

	private void disable() {
		view.getRemoveButton().setEnabled( false );
		view.getAddButton().setEnabled( false );
		view.getSelectedRolesListBox().clear();
		enabled = false;
		MyListBoxInterface rolesChoices = view.getRoleChoiceListBox();
		rolesChoices.setSelectedIndex( 0 );
		rolesChoices.setEnabled( false );
	}

}
