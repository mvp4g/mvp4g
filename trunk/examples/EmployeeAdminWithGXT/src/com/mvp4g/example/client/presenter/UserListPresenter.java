package com.mvp4g.example.client.presenter;

import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.presenter.Presenter;
import com.mvp4g.example.client.EventsEnum;
import com.mvp4g.example.client.UserServiceAsync;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.UserListViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTTableInterface;

public class UserListPresenter extends Presenter<UserListViewInterface> {

	protected int indexSelected = 0;
	protected List<UserBean> users = null;

	private UserServiceAsync service = null;

	@SuppressWarnings( "unchecked" )
	@Override
	public void bind() {
		MyGXTButtonInterface delete = view.getDeleteButton();
		delete.setEnabled( false );
		delete.addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
				setVisibleConfirmDeletion( true );
			}

		} );
		view.getNewButton().addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
				eventBus.dispatch( EventsEnum.CREATE_NEW_USER, new UserBean() );
			}

		} );
		MyGXTTableInterface table = view.getTable();
		table.addListener( Events.RowClick, new Listener<GridEvent>() {

			public void handleEvent( GridEvent be ) {

				selectUser( be.getRowIndex() );

			}

		} );

		view.getYesButton().addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
				deleteUser();
			}

		} );
		view.getNoButton().addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
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
				MyGXTTableInterface table = view.getTable();
				for ( int i = 0; i < nbUsers; i++ ) {
					table.addUser( users.get( i ) );
				}

				eventBus.dispatch( EventsEnum.CHANGE_TOP_WIDGET, view.getViewWidget() );

			}

		} );

	}

	public void onUserUpdated( UserBean user ) {
		view.getTable().updateUser( user, users.indexOf( user ) );		
	}

	public void onUserCreated( UserBean user ) {
		users.add( user );
		view.getTable().addUser( user );
	}

	public void onUnselectUser() {
		view.getTable().unSelectRow( indexSelected );
		indexSelected = 0;
	}

	public void setUserService( UserServiceAsync service ) {
		this.service = service;
	}

	private void selectUser( int row ) {
		MyGXTTableInterface table = view.getTable();

		if ( indexSelected > 0 ) {
			table.unSelectRow( indexSelected );
		}

		indexSelected = row;
		table.selectRow( indexSelected );
		eventBus.dispatch( EventsEnum.SELECT_USER, users.get( row ) );
		view.getDeleteButton().setEnabled( true );

	}

	private void deleteUser() {
		service.deleteUser( users.get( indexSelected ), new AsyncCallback<Void>() {

			public void onFailure( Throwable caught ) {
				// TODO Auto-generated method stub

			}

			public void onSuccess( Void result ) {
				users.remove( indexSelected );
				view.getTable().removeRow( indexSelected );
				view.getDeleteButton().setEnabled( false );
				setVisibleConfirmDeletion( false );
				eventBus.dispatch( EventsEnum.UNSELECT_USER );
			}

		} );
	}

	private void setVisibleConfirmDeletion( boolean visible ) {
		view.getConfirmText().setVisible( visible );
		view.getYesButton().setVisible( visible );
		view.getNoButton().setVisible( visible );
	}
}
