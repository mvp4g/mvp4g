package com.mvp4g.example.client.presenter;

import static com.mvp4g.example.client.Constants.DEPARTMENTS;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.EmployeeAdminWithGXTEventBus;
import com.mvp4g.example.client.UserServiceAsync;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.view.UserProfileView;
import com.mvp4g.example.client.widget.interfaces.IButton;
import com.mvp4g.example.client.widget.interfaces.IListBox;
import com.mvp4g.example.client.widget.interfaces.ITextBox;
import com.mvp4g.example.client.widget.interfaces.IWidget;

@Presenter( view = UserProfileView.class )
public class UserProfilePresenter extends BasePresenter<UserProfilePresenter.IUserProfileView, EmployeeAdminWithGXTEventBus> {
	
	public interface IUserProfileView extends IWidget {

		 IWidget getViewWidget();

		 ITextBox getFirstName();

		 ITextBox getLastName();

		 ITextBox getEmail();

		 ITextBox getUsername();

		 ITextBox getPassword();

		 ITextBox getConfirmPassword();

		 IListBox getDepartment();

		 IButton getUpdateButton();

		 IButton getCancelButton();

		 void showAddMode();

		 void showUpdateMode();

	}

	private boolean toUpdate = true;
	private boolean enabled = false;
	private UserBean user = null;
	private UserBean userCopy = new UserBean();

	private UserServiceAsync service = null;

	@Override
	public void bind() {
		IListBox list = view.getDepartment();
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
				eventBus.unselectUser();
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
		IListBox department = view.getDepartment();
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
		eventBus.changeLeftBottomWidget( view.getViewWidget() );
	}

	public void onSelectUser( UserBean user ) {
		this.user = user;
		userCopy.copy( user );

		toUpdate = true;
		fillForm();
		IButton update = view.getUpdateButton();
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
		IButton update = view.getUpdateButton();
		update.setEnabled( false );
		view.showAddMode();
		view.getCancelButton().setEnabled( true );
		toUpdate = false;
		enabled = true;

	}

	@InjectService
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
		IButton update = view.getUpdateButton();
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
				eventBus.userCreated( user );
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
				eventBus.userUpdated( user );
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
