package com.mvp4g.example.client.presenter;

import static com.mvp4g.example.client.Constants.ROLES;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.EmployeeAdminWithGXTEventBus;
import com.mvp4g.example.client.UserServiceAsync;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.IUserRoleView;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.IUserRoleView.IUserRolePresenter;
import com.mvp4g.example.client.view.UserRoleView;

@Presenter( view = UserRoleView.class )
public class UserRolePresenter extends BasePresenter<IUserRoleView, EmployeeAdminWithGXTEventBus> implements IUserRolePresenter {

	final static String NONE_SELECTED = "--None Selected--";

	UserBean user = null;

	boolean enabled = false;

	private UserServiceAsync service = null;

	@Override
	public void bind() {
		view.addPossibleRole( NONE_SELECTED );
		for ( String role : ROLES ) {
			view.addPossibleRole( role );
		}

		view.setPresenter( this );

		disable();
	}

	public void onStart() {
		eventBus.changeRightBottomWidget( view );
	}

	public void onSelectUser( UserBean user ) {
		this.user = user;

		view.clearUserRoles();
		List<String> roles = user.getRoles();
		if ( roles != null ) {
			for ( String role : roles ) {
				view.addUserRole( role );
			}
		}

		enabled = true;
		view.setPossibleRole( NONE_SELECTED );
		view.setPossibleRoleEnabled( true );
		view.setAddButtonEnabled( false );
		view.setRemoveButtonEnabled( false );

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
	
	public void onAddButtonClicked() {
		addRole( view.getPossibleRoleSelected() );
	}

	public void onPossibleRoleSelected() {
		enabledAddButton();
	}

	public void onRemoveButtonClicked() {
		List<String> selection = view.getSelectedRoles();
		for ( String role : selection ) {
			deleteRole( role );
		}
	}

	public void onRoleSelected() {
		enableRemoveButton();
	}
	
	@InjectService
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
					view.addUserRole( role );
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
				view.removeUserRole( role );
				view.setRemoveButtonEnabled( false );
			}

		} );

	}

	private void enableRemoveButton() {
		if ( enabled && isUserRoleSelected() ) {
			view.setRemoveButtonEnabled( true );
			;
		}
	}

	private void enabledAddButton() {
		if ( enabled ) {
			view.setAddButtonEnabled( isPossibleRoleSelected() );
		}
	}

	private void disable() {
		view.setRemoveButtonEnabled( false );
		view.setAddButtonEnabled( false );
		view.clearUserRoles();
		enabled = false;
		view.setPossibleRole( NONE_SELECTED );
		view.setPossibleRoleEnabled( false );
	}	

	private boolean isPossibleRoleSelected() {
		return !NONE_SELECTED.equals( view.getPossibleRoleSelected() );
	}

	private boolean isUserRoleSelected() {
		List<String> roles = view.getSelectedRoles();
		return ( roles != null ) && ( roles.size() > 0 );
	}

}
