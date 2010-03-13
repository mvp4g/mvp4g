package com.mvp4g.example.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.EmployeeAdminEventBus;
import com.mvp4g.example.client.UserServiceAsync;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.UserListViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyTableInterface;
import com.mvp4g.example.client.view.UserListView;

@Presenter( view = UserListView.class )
public class UserListPresenter extends BasePresenter<UserListViewInterface, EmployeeAdminEventBus> {

	protected int indexSelected = 0;
	protected List<UserBean> users = null;

	private UserServiceAsync service = null;

	@Override
	public void bind() {
		MyButtonInterface delete = view.getDeleteButton();
		delete.setEnabled( false );
		delete.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				setVisibleConfirmDeletion( true );
			}

		} );
		view.getNewButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.createNewUser( new UserBean() );
			}

		} );
		MyTableInterface table = view.getTable();
		table.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				MyTableInterface table = view.getTable();

				selectUser( table.getRowForEvent( event ) );

			}

		} );

		view.getYesButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				deleteUser();
			}

		} );
		view.getNoButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				setVisibleConfirmDeletion( false );
			}

		} );
		setVisibleConfirmDeletion( false );

	}

	public void onStart() {
		service.getUsers( new AsyncCallback<List<UserBean>>() {

			public void onFailure( Throwable caught ) {
				// TODO Auto-generated method stub

			}

			public void onSuccess( List<UserBean> result ) {
				users = result;
				int nbUsers = result.size();
				for ( int i = 0; i < nbUsers; i++ ) {
					displayUser( users.get( i ), i + 1 );
				}

				eventBus.changeTopWidget( view.getViewWidget() );

			}

		} );

	}

	public void onUserUpdated( UserBean user ) {
		displayUser( user, users.indexOf( user ) + 1 );
	}

	public void onUserCreated( UserBean user ) {
		users.add( user );
		displayUser( user, users.size() );
	}

	public void onUnselectUser() {
		view.getTable().unSelectRow( indexSelected );
		indexSelected = 0;
	}

	@InjectService
	public void setUserService( UserServiceAsync service ) {
		this.service = service;
	}

	private void selectUser( int row ) {
		MyTableInterface table = view.getTable();

		if ( row > 0 ) {

			if ( indexSelected > 0 ) {
				table.unSelectRow( indexSelected );
			}

			indexSelected = row;
			table.selectRow( indexSelected );
			eventBus.selectUser( users.get( row - 1 ) );
			view.getDeleteButton().setEnabled( true );
		}
	}

	private void deleteUser() {
		service.deleteUser( users.get( indexSelected - 1 ), new AsyncCallback<Void>() {

			public void onFailure( Throwable caught ) {
				// TODO Auto-generated method stub

			}

			public void onSuccess( Void result ) {
				users.remove( indexSelected - 1 );
				view.getTable().removeRow( indexSelected );
				view.getDeleteButton().setEnabled( false );
				setVisibleConfirmDeletion( false );
				eventBus.unselectUser();
			}

		} );
	}

	private void displayUser( UserBean user, int row ) {
		MyTableInterface table = view.getTable();
		table.setText( row, 0, user.getUsername() );
		table.setText( row, 1, user.getFirstName() );
		table.setText( row, 2, user.getLastName() );
		table.setText( row, 3, user.getEmail() );
		table.setText( row, 4, user.getDepartment() );
	}

	private void setVisibleConfirmDeletion( boolean visible ) {
		view.getConfirmText().setVisible( visible );
		view.getYesButton().setVisible( visible );
		view.getNoButton().setVisible( visible );
	}
}
