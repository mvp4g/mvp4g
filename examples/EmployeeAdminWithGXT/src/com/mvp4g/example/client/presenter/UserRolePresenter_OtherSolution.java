package com.mvp4g.example.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.EmployeeAdminWithGXTEventBus;
import com.mvp4g.example.client.UserServiceAsync;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.gxt.MyListModel;
import com.mvp4g.example.client.widget.interfaces.IWidget;

/**
 * SOLUTION 2
 * 
 * Description:
 * use GXT directly in the view interface and presenter.
 *  
 * Advantages:
 * -No extra class needed, it's easier to code.
 * -You keep GXT logic 
 * 
 * Drawbacks:
 * -You can't test presenters with JUnit or Mock libraries since GXT widgets can only be
 * tested with GWTTestCase (http://www.extjs.com/ forum/showthread.php?t=63053).
 */

//This line is commented because this presenter is not used
//I just let this presenter in the code to show another to treat GXT & MVP
//@Presenter( view = UserRoleView.class )
public class UserRolePresenter_OtherSolution extends BasePresenter<UserRolePresenter_OtherSolution.IUserRoleView_OtherSolution, EmployeeAdminWithGXTEventBus> implements Constants {

	public interface IUserRoleView_OtherSolution extends IWidget {

		public ListField<MyListModel> getSelectedRolesListBox();

		public SimpleComboBox<String> getRoleChoiceListBox();

		public Button getAddButton();

		public Button getRemoveButton();

		public void displayError( String error );

	}

	private final static String NONE_SELECTED = "--None Selected--";

	private UserBean user = null;

	private boolean enabled = false;

	@Inject
	private UserServiceAsync service = null;

	@Override
	public void bind() {
		Button add = view.getAddButton();
		add.addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
				addRole( view.getRoleChoiceListBox().getSimpleValue() );
			}

		} );

		Button remove = view.getRemoveButton();
		remove.addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
				List<MyListModel> selection = view.getSelectedRolesListBox().getSelection();
				for ( MyListModel data : selection ) {
					deleteRole( data.getText() );
				}

			}

		} );

		ListField<MyListModel> selectedRoles = view.getSelectedRolesListBox();
		selectedRoles.addListener( Events.Focus, new Listener<FieldEvent>() {

			public void handleEvent( FieldEvent be ) {
				enableRemoveButton();

			}

		} );

		SimpleComboBox<String> rolesChoices = view.getRoleChoiceListBox();
		rolesChoices.addListener( Events.Select, new Listener<FieldEvent>() {

			public void handleEvent( FieldEvent be ) {
				enabledAddButton();

			}

		} );

		rolesChoices.add( NONE_SELECTED );
		for ( String role : ROLES ) {
			rolesChoices.add( role );
		}

		disable();

	}

	public void onStart() {
		eventBus.changeRightBottomWidget( view );
	}

	public void onSelectUser( UserBean user ) {
		this.user = user;

		ListStore<MyListModel> store = view.getSelectedRolesListBox().getStore();
		store.removeAll();
		List<String> roles = user.getRoles();
		if ( roles != null ) {
			for ( String role : roles ) {
				store.add( new MyListModel( role ) );
			}
		}

		enabled = true;
		SimpleComboBox<String> rolesChoices = view.getRoleChoiceListBox();
		rolesChoices.setSimpleValue( NONE_SELECTED );
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
					view.getSelectedRolesListBox().getStore().add( new MyListModel( role ) );
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
				ListField<MyListModel> selectedRoles = view.getSelectedRolesListBox();
				selectedRoles.getStore().remove( selectedRoles.getSelection().get( 0 ) );
				view.getRemoveButton().setEnabled( false );
			}

		} );

	}

	private void enableRemoveButton() {
		if ( enabled && ( view.getSelectedRolesListBox().getSelection().size() > 0 ) ) {
			view.getRemoveButton().setEnabled( true );
		}
	}

	private void enabledAddButton() {
		Button add = view.getAddButton();
		if ( enabled ) {
			if ( view.getRoleChoiceListBox().getSelectedIndex() == 0 ) {
				add.setEnabled( false );
			} else {
				add.setEnabled( true );
			}
		}
	}

	private void disable() {
		view.getRemoveButton().setEnabled( false );
		view.getAddButton().setEnabled( false );
		view.getSelectedRolesListBox().getStore().removeAll();
		enabled = false;
		SimpleComboBox<String> rolesChoices = view.getRoleChoiceListBox();
		rolesChoices.setSimpleValue( NONE_SELECTED );
		rolesChoices.setEnabled( false );
	}

}
