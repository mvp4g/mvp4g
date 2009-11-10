package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.presenter.XmlPresenter;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.EventsEnum;
import com.mvp4g.example.client.UserServiceAsync;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.UserProfileViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyListBoxInterface;

public class UserProfilePresenter extends XmlPresenter<UserProfileViewInterface> implements Constants {

	private boolean toUpdate = true;
	private boolean enabled = false;
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
				eventBus.dispatch( EventsEnum.UNSELECT_USER );
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
		MyListBoxInterface department = view.getDepartment();
		department.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				enableUpdateButton();
			}

		} );
		department.addKeyUpHandler( new KeyUpHandler() {

			public void onKeyUp( KeyUpEvent event ) {
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
		view.showUpdateMode();
		view.getCancelButton().setEnabled( true );
		enabled = true;
	}

	public void onUnselectUser() {
		user = null;
		init();
	}

	public void onCreateNewUser( UserBean user ) {
		this.user = user;
		init();
		MyButtonInterface update = view.getUpdateButton();
		update.setEnabled( false );
		view.showAddMode();
		view.getCancelButton().setEnabled( true );
		toUpdate = false;
		enabled = true;

	}

	public void setUserService( UserServiceAsync service ) {
		this.service = service;
	}

	private void init() {
		view.getFirstName().setValue( "" );
		view.getLastName().setValue( "" );
		view.getEmail().setValue( "" );
		view.getUsername().setValue( "" );
		view.getPassword().setValue( "" );
		view.getConfirmPassword().setValue( "" );
		view.getDepartment().setSelectedIndex( 0 );
		MyButtonInterface update = view.getUpdateButton();
		update.setEnabled( false );
		view.showUpdateMode();
		view.getCancelButton().setEnabled( false );
		enabled = false;
	}

	private void fillForm() {
		view.getFirstName().setValue( user.getFirstName() );
		view.getLastName().setValue( user.getLastName() );
		view.getEmail().setValue( user.getEmail() );
		view.getUsername().setValue( user.getUsername() );
		view.getPassword().setValue( user.getPassword() );
		view.getConfirmPassword().setValue( user.getPassword() );
		view.getDepartment().setSelectedValue( user.getDepartment() );
	}

	private void fillBean() {
		user.setFirstName( view.getFirstName().getValue() );
		user.setLastName( view.getLastName().getValue() );
		user.setEmail( view.getEmail().getValue() );
		user.setUsername( view.getUsername().getValue() );
		user.setPassword( view.getPassword().getValue() );
		user.setDepartment( view.getDepartment().getSelectedValue() );
	}

	private void createUser() {
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

	private void updateUser() {
		service.updateUser( user, new AsyncCallback<Void>() {

			public void onFailure( Throwable caught ) {
				user.copy( userCopy );
			}

			public void onSuccess( Void result ) {
				init();
				eventBus.dispatch( EventsEnum.USER_UPDATED, user );
			}

		} );
	}

	private void enableUpdateButton() {

		String username = view.getUsername().getValue();
		int department = view.getDepartment().getSelectedIndex();
		String password = view.getPassword().getValue();
		String confirm = view.getConfirmPassword().getValue();

		boolean enabled = this.enabled && ( username != null ) && ( username.length() > 0 ) && ( department > 0 ) && ( password != null )
				&& ( password.length() > 0 ) && ( confirm != null ) && ( confirm.length() > 0 ) && ( confirm.equals( password ) );
		view.getUpdateButton().setEnabled( enabled );
	}

}
