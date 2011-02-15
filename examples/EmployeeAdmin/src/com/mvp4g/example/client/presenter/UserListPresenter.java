package com.mvp4g.example.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.EmployeeAdminEventBus;
import com.mvp4g.example.client.UserServiceAsync;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.view.UserListView;
import com.mvp4g.example.client.widget.interfaces.IButton;
import com.mvp4g.example.client.widget.interfaces.ILabel;
import com.mvp4g.example.client.widget.interfaces.ITable;
import com.mvp4g.example.client.widget.interfaces.IWidget;

//This presenter illustrate how you can build your presenter in order to test it
//with a mock library (in this case EasyMock)
@Presenter( view = UserListView.class )
public class UserListPresenter extends BasePresenter<UserListPresenter.IUserListView, EmployeeAdminEventBus> {

	public interface IUserListView extends IWidget {

		ITable getTable();

		IButton getDeleteButton();

		IButton getNewButton();

		IButton getYesButton();

		IButton getNoButton();

		ILabel getConfirmText();

	}

	protected int indexSelected;
	protected List<UserBean> users;

	@Inject
	private UserServiceAsync service;

	@Override
	public void bind() {

		//create inner classes for ClickHandler in order to ease testing with EasyMock

		IButton delete = view.getDeleteButton();
		delete.addClickHandler( new DeleteClickHandler() );

		view.getNewButton().addClickHandler( new NewClickHandler() );

		ITable table = view.getTable();
		table.addClickHandler( new TableClickHandler() );

		view.getYesButton().addClickHandler( new YesClickHandler() );
		view.getNoButton().addClickHandler( new NoClickHandler() );
	}

	public void onStart() {
		service.getUsers( new AsyncCallback<List<UserBean>>() {

			public void onFailure( Throwable caught ) {
				// TODO Auto-generated method stub

			}

			public void onSuccess( List<UserBean> result ) {
				setUsers( result );
				eventBus.changeTopWidget( view );
			}

		} );
		view.getDeleteButton().setEnabled( false );
		setVisibleConfirmDeletion( false );

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

	public void setUserService( UserServiceAsync service ) {
		this.service = service;
	}

	void setUsers( List<UserBean> users ) {
		this.users = users;
		int nbUsers = users.size();
		for ( int i = 0; i < nbUsers; i++ ) {
			displayUser( users.get( i ), i + 1 );
		}
	}

	void selectUser( int row ) {
		ITable table = view.getTable();

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

	void deleteUser() {
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

	void displayUser( UserBean user, int row ) {
		ITable table = view.getTable();
		table.setText( row, 0, user.getUsername() );
		table.setText( row, 1, user.getFirstName() );
		table.setText( row, 2, user.getLastName() );
		table.setText( row, 3, user.getEmail() );
		table.setText( row, 4, user.getDepartment() );
	}

	void setVisibleConfirmDeletion( boolean visible ) {
		view.getConfirmText().setVisible( visible );
		view.getYesButton().setVisible( visible );
		view.getNoButton().setVisible( visible );
	}

	class YesClickHandler implements ClickHandler {

		public void onClick( ClickEvent event ) {
			deleteUser();
		}

	}

	class NoClickHandler implements ClickHandler {

		public void onClick( ClickEvent event ) {
			setVisibleConfirmDeletion( false );
		}

	}

	class NewClickHandler implements ClickHandler {

		public void onClick( ClickEvent event ) {
			eventBus.createNewUser( new UserBean() );
		}

	}

	class DeleteClickHandler implements ClickHandler {

		public void onClick( ClickEvent event ) {
			setVisibleConfirmDeletion( true );
		}

	}

	class TableClickHandler implements ClickHandler {

		public void onClick( ClickEvent event ) {
			ITable table = view.getTable();
			selectUser( table.getRowForEvent( event ) );
		}

	}
}
