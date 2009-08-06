package com.mvp4g.example.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.mvp4g.client.presenter.Presenter;
import com.mvp4g.example.client.EventsEnum;
import com.mvp4g.example.client.UserServiceAsync;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.UserListViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyTableInterface;

public class UserListPresenter extends Presenter<UserListViewInterface> {

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
				eventBus.dispatch( EventsEnum.CREATE_NEW_USER, new UserBean() );
			}

		} );
		MyTableInterface table = view.getListTable();
		table.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				MyTableInterface table = view.getListTable();

				Cell cell = table.getCellForEvent( event );
				selectUser( cell.getRowIndex() );

			}

		} );
		table.setText( 0, 0, "Username" );
		table.setText( 0, 1, "First Name" );
		table.setText( 0, 2, "Last Name" );
		table.setText( 0, 3, "Email" );
		table.setText( 0, 4, "Department" );

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

				eventBus.dispatch( EventsEnum.CHANGE_TOP_WIDGET, view.getViewWidget() );

			}

		} );

	}

	public void onUserUpdated( UserBean user ) {
		displayUser( user, users.indexOf( user ) + 1);
	}
	
	public void onUserCreated( UserBean user ) {
		users.add( user );
		displayUser( user, users.size() );
	}
	
	public void onUnselectUser() {
		view.getListTable().unSelectRow( indexSelected );
		indexSelected = 0;		
	}

	public void setUserService( UserServiceAsync service ) {
		this.service = service;
	}

	void selectUser( int row ) {
		MyTableInterface table = view.getListTable();

		if ( row > 0 ) {

			if ( indexSelected > 0 ) {
				table.unSelectRow( indexSelected );
			}

			indexSelected = row;
			table.selectRow( indexSelected );
			eventBus.dispatch( EventsEnum.SELECT_USER, users.get( row - 1 ) );
			view.getDeleteButton().setEnabled( true );
		}
	}

	void deleteUser() {
		service.deleteUser( users.get( indexSelected - 1 ), new AsyncCallback<Void>() {

			public void onFailure( Throwable caught ) {
				// TODO Auto-generated method stub

			}

			public void onSuccess( Void result ) {
				users.remove( indexSelected - 1 );
				view.getListTable().removeRow( indexSelected );
				view.getDeleteButton().setEnabled( false );
				setVisibleConfirmDeletion( false );
				eventBus.dispatch( EventsEnum.UNSELECT_USER );
			}

		} );
	}

	void displayUser( UserBean user, int row ) {
		MyTableInterface flexTable = view.getListTable();
		flexTable.setText( row, 0, user.getUsername() );
		flexTable.setText( row, 1, user.getFirstName() );
		flexTable.setText( row, 2, user.getLastName() );
		flexTable.setText( row, 3, user.getEmail() );
		flexTable.setText( row, 4, user.getDepartment() );
	}

	void setVisibleConfirmDeletion( boolean visible ) {
		view.getConfirmText().setVisible( visible );
		view.getYesButton().setVisible( visible );
		view.getNoButton().setVisible( visible );
	}
}
