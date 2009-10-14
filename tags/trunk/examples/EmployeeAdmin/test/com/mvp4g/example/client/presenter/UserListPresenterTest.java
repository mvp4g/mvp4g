package com.mvp4g.example.client.presenter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.EventsEnum;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.mock.MockGwtEvent;
import com.mvp4g.example.client.mock.eventbus.MockEventBus;
import com.mvp4g.example.client.mock.service.MockUserServiceAsync;
import com.mvp4g.example.client.mock.view.MockUserListView;
import com.mvp4g.example.client.mock.widget.MyMockButton;
import com.mvp4g.example.client.mock.widget.MyMockLabel;
import com.mvp4g.example.client.mock.widget.MyMockTable;

public class UserListPresenterTest implements Constants {

	UserListPresenter presenter = null;
	MockUserListView view = null;
	MockEventBus eventBus = null;
	MockUserServiceAsync service = null;
	List<UserBean> users = null;

	@Before
	public void setUp() {
		presenter = new UserListPresenter();
		view = new MockUserListView();
		eventBus = new MockEventBus();
		users = createUsers();
		service = new MockUserServiceAsync( users );
		presenter.setView( view );
		presenter.setEventBus( eventBus );
		presenter.setUserService( service );
		presenter.onStart();
	}

	@Test
	public void testBind() {
		MyMockButton delete = (MyMockButton)view.getDeleteButton();
		assertFalse( delete.isEnabled() );
		assertConfirmDeletionBar( false );
	}

	@Test
	public void testOnStart() {
		int nbUser = users.size();
		for ( int i = 0; i < nbUser; i++ ) {
			assertTableRow( i + 1, users.get( i ) );
		}
		eventBus.assertEvent( EventsEnum.CHANGE_TOP_WIDGET.toString(), view.getViewWidget() );

	}

	@Test
	public void testOnUserUpdated() {
		UserBean user = users.get( 0 );
		fillUser( user );
		presenter.onUserUpdated( user );
		assertTableRow( 1, user );
	}

	@Test
	public void testOnUserCreated() {
		UserBean user = new UserBean();
		fillUser( user );
		presenter.onUserCreated( user );
		assertTableRow( users.size(), user );
	}

	@Test
	public void testDeleteClick() {
		MyMockButton delete = (MyMockButton)view.getDeleteButton();
		delete.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertConfirmDeletionBar( true );
	}

	@Test
	public void testNewClick() {
		MyMockButton newButton = (MyMockButton)view.getNewButton();
		newButton.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertEquals( eventBus.getLastDispatchedEventType(), EventsEnum.CREATE_NEW_USER.toString() );
		UserBean u = (UserBean)eventBus.getLastDispatchedObject();

		assertNull( u.getFirstName() );
		assertNull( u.getLastName() );
		assertNull( u.getEmail() );
		assertNull( u.getUsername() );
		assertNull( u.getPassword() );
		assertNull( u.getDepartment() );
		assertNull( u.getRoles() );

	}

	@Test
	public void testYesClick() {

		MyMockTable table = (MyMockTable)view.getTable();
		table.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );

		int tableSize = table.getRowCount();
		int usersSize = users.size();
		MyMockButton yes = (MyMockButton)view.getYesButton();
		yes.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertEquals( tableSize - 1, table.getRowCount() );
		assertEquals( usersSize - 1, users.size() );
	}

	@Test
	public void testNoClick() {
		MyMockButton no = (MyMockButton)view.getNoButton();
		no.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertConfirmDeletionBar( false );
	}

	@Test
	public void testSelectAndUnselect() {
		MyMockTable table = (MyMockTable)view.getTable();
		ClickEvent event = MockGwtEvent.CLICK_EVENT;
		table.getClickHandler().onClick( event );
		assertEquals( table.getRowForEvent( event ), table.getLastSelected() );

		presenter.onUnselectUser();
		assertEquals( -1, table.getLastSelected() );

	}

	private void assertTableRow( int row, UserBean user ) {
		MyMockTable table = (MyMockTable)view.getTable();
		assertEquals( user.getUsername(), table.getText( row, 0 ) );
		assertEquals( user.getFirstName(), table.getText( row, 1 ) );
		assertEquals( user.getLastName(), table.getText( row, 2 ) );
		assertEquals( user.getEmail(), table.getText( row, 3 ) );
		assertEquals( user.getDepartment(), table.getText( row, 4 ) );
	}

	private void assertConfirmDeletionBar( boolean visible ) {
		MyMockButton yes = (MyMockButton)view.getYesButton();
		MyMockButton no = (MyMockButton)view.getNoButton();
		MyMockLabel text = (MyMockLabel)view.getConfirmText();
		assertEquals( visible, yes.isVisible() );
		assertEquals( visible, no.isVisible() );
		assertEquals( visible, text.isVisible() );
	}

	private void fillUser( UserBean user ) {
		user.setUsername( "username" );
		user.setFirstName( "firstName" );
		user.setLastName( "lastName" );
		user.setEmail( "email" );
		user.setDepartment( "department" );
	}

	private List<UserBean> createUsers() {
		List<UserBean> users = new ArrayList<UserBean>();

		String[] firstNames = { "Karim", "Cristiano", "Iker", "Sergio" };
		String[] lastNames = { "Benzema", "Ronaldo", "Casillas", "Ramos" };
		int NB_USERS = 4;

		UserBean user = null;
		String firstName = null;
		String lastName = null;
		String username = null;
		List<String> roles = null;

		for ( int i = 0; i < NB_USERS; i++ ) {
			user = new UserBean();

			firstName = firstNames[i % firstNames.length];
			user.setFirstName( firstName );

			lastName = lastNames[i % lastNames.length];
			user.setLastName( lastName );

			username = ( firstName.substring( 0, 1 ) + lastName ).toLowerCase();
			user.setUsername( username );

			user.setEmail( username + "@real.com" );

			user.setDepartment( DEPARTMENTS[i % DEPARTMENTS.length] );

			user.setPassword( "1234" );

			roles = new ArrayList<String>();
			roles.add( ROLES[i] );
			user.setRoles( roles );

			users.add( user );
		}
		return users;
	}

}
