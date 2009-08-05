package com.mvp4g.example.client.presenter;

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
import com.mvp4g.example.client.presenter.view_interface.UserProfileViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyListBoxInterface;

public class UserProfilePresenter extends Presenter<UserProfileViewInterface> implements Constants {

	private boolean toUpdate = true;
	private UserBean user = null;
	private UserBean userCopy = new UserBean();

	private UserServiceAsync service = null;

	@Override
	public void bind() {
		MyListBoxInterface list = view.getDepartment();
		list.addItem( "--None Selected--" );
		for ( String dep : DEPARTMENTS ) {
			list.addItem( dep );
		}

		view.getUpdateButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {

				fillBean();
				if ( toUpdate ) {
					updateUser();
				} else {
					createUser();
				}
			}

		} );

		view.getCancelButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				init();
			}

		} );

		KeyUpHandler handler = new KeyUpHandler() {

			public void onKeyUp( KeyUpEvent event ) {
				enableUpdateButton();
			}

		};

		view.getUsername().addKeyUpHandler( handler );
		view.getPassword().addKeyUpHandler( handler );
		view.getConfirmPassword().addKeyUpHandler( handler );
		view.getDepartment().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				enableUpdateButton();
			}

		} );

		init();
	}

	public void onStart() {
		eventBus.dispatch( EventsEnum.CHANGE_LEFT_BOTTOM_WIDGET, view.getViewWidget() );
	}

	public void onSelectUser( UserBean user ) {
		this.user = user;
		userCopy.copy( user );

		toUpdate = true;
		fillForm();
		MyButtonInterface update = view.getUpdateButton();
		update.setEnabled( true );
		update.setText( "Update" );
		view.getCancelButton().setEnabled( true );
	}

	public void onCreateNewUser( UserBean user ) {
		this.user = user;
		init();
		MyButtonInterface update = view.getUpdateButton();
		update.setEnabled( false );
		update.setText( "Add User" );
		view.getCancelButton().setEnabled( true );
		toUpdate = false;

	}

	public void setUserService( UserServiceAsync service ) {
		this.service = service;
	}

	void init() {
		view.getFirstName().setValue( "" );
		view.getLastName().setValue( "" );
		view.getEmail().setValue( "" );
		view.getUsername().setValue( "" );
		view.getPassword().setValue( "" );
		view.getConfirmPassword().setValue( "" );
		view.getDepartment().setSelectedIndex( 0 );
		MyButtonInterface update = view.getUpdateButton();
		update.setEnabled( false );
		update.setText( "Update" );
		view.getCancelButton().setEnabled( false );
	}

	void fillForm() {
		view.getFirstName().setValue( user.getFirstName() );
		view.getLastName().setValue( user.getLastName() );
		view.getEmail().setValue( user.getEmail() );
		view.getUsername().setValue( user.getUsername() );
		view.getPassword().setValue( user.getPassword() );
		view.getConfirmPassword().setValue( user.getPassword() );
		view.getDepartment().setSelectedValue( user.getDepartment() );
	}

	void fillBean() {
		user.setFirstName( view.getFirstName().getValue() );
		user.setLastName( view.getLastName().getValue() );
		user.setEmail( view.getEmail().getValue() );
		user.setUsername( view.getUsername().getValue() );
		user.setPassword( view.getPassword().getValue() );
		user.setDepartment( view.getDepartment().getSelectedValue() );
	}

	void createUser() {
		service.createUser( user, new AsyncCallback<Void>() {

			public void onFailure( Throwable caught ) {
				// TODO Auto-generated method stub

			}

			public void onSuccess( Void result ) {
				init();
				eventBus.dispatch( EventsEnum.USER_CREATED, user );
			}

		} );
	}

	void updateUser() {
		service.updateUser( user, new AsyncCallback<Void>() {

			public void onFailure( Throwable caught ) {
				user = userCopy;
			}

			public void onSuccess( Void result ) {
				init();
				eventBus.dispatch( EventsEnum.USER_UPDATED, user );
			}

		} );
	}

	void enableUpdateButton() {

		String username = view.getUsername().getValue();
		int department = view.getDepartment().getSelectedIndex();
		String password = view.getPassword().getValue();
		String confirm = view.getConfirmPassword().getValue();

		boolean enabled = ( username != null ) && ( username.length() > 0 ) && ( department > 0 ) && ( password != null ) && ( password.length() > 0 )
				&& ( confirm != null ) && ( confirm.length() > 0 ) && ( confirm.equals( password ) );
		view.getUpdateButton().setEnabled( enabled );
	}

}
