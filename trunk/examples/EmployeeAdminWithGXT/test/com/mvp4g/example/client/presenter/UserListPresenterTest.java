package com.mvp4g.example.client.presenter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.store.ListStore;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.mock.event.MockGridEvent;
import com.mvp4g.example.client.mock.eventbus.MockEventBus;
import com.mvp4g.example.client.mock.service.MockUserServiceAsync;
import com.mvp4g.example.client.mock.view.MockUserListView;
import com.mvp4g.example.client.mock.widget.MyMockLabel;
import com.mvp4g.example.client.mock.widget.gxt.MyGXTMockButton;
import com.mvp4g.example.client.mock.widget.gxt.MyGXTMockTable;
import com.mvp4g.example.client.presenter.gxt.MyUserListModel;
import com.mvp4g.example.client.util.GWTMockUtilities;

public class UserListPresenterTest implements Constants {

	UserListPresenter presenter = null;
	MockUserListView view = null;
	MockEventBus eventBus = null;
	MockUserServiceAsync service = null;
	List users = null;

	@Before
	public void setUp() {
		GWTMockUtilities.disarm();
		presenter = new UserListPresenter();
		view = new MockUserListView();
		eventBus = new MockEventBus();
		users = createUsers();
		service = new MockUserServiceAsync( users );
		presenter.setView( view );
		presenter.setEventBus( eventBus );
		presenter.setUserService( service );
		presenter.bind();
		presenter.onStart();
	}

	@After
	public void tearUp() {
		GWTMockUtilities.restore();
	}

	@Test
	public void testBind() {
		MyGXTMockButton delete = (MyGXTMockButton)view.getDeleteButton();
		assertFalse( delete.isEnabled() );
		assertConfirmDeletionBar( false );
	}

	@Test
	public void testOnStart() {
		int nbUser = users.size();
		for ( int i = 0; i < nbUser; i++ ) {
			assertTableRow( i, (UserBean)( (BeanModel)users.get( i ) ).getBean() );
		}
		eventBus.assertChangeTopWidget( view.getViewWidget() );

	}

	@Test
	public void testOnUserUpdated() {
		UserBean user = ( (BeanModel)users.get( 0 ) ).getBean();
		fillUser( user );
		presenter.onUserUpdated( user );
		assertTableRow( 0, user );
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
		MyGXTMockButton delete = (MyGXTMockButton)view.getDeleteButton();
		delete.getListener( Events.Select ).handleEvent( new ButtonEvent( null ) );
		assertConfirmDeletionBar( true );
	}

	@Test
	public void testNewClick() {
		MyGXTMockButton newButton = (MyGXTMockButton)view.getNewButton();
		newButton.getListener( Events.Select ).handleEvent( new ButtonEvent( null ) );
		eventBus.assertCreateNewUser();
	}

	@Test
	public void testYesClick() {

		MyGXTMockTable table = (MyGXTMockTable)view.getTable();
		table.getListener( Events.RowClick ).handleEvent( new MockGridEvent<MyUserListModel>() );

		MyGXTMockButton yes = (MyGXTMockButton)view.getYesButton();
		yes.getListener( Events.Select ).handleEvent( new ButtonEvent( null ) );
		assertEquals( view.getStore().getCount(), users.size() - 1 );
	}

	@Test
	public void testNoClick() {
		MyGXTMockButton no = (MyGXTMockButton)view.getNoButton();
		no.getListener( Events.Select ).handleEvent( new ButtonEvent( null ) );
		assertConfirmDeletionBar( false );
	}

	@Test
	public void testSelectAndUnselect() {
		MyGXTMockTable table = (MyGXTMockTable)view.getTable();
		MockGridEvent<MyUserListModel> event = new MockGridEvent<MyUserListModel>();

		table.getListener( Events.RowClick ).handleEvent( event );
		assertEquals( event.getRowIndex(), table.getLastSelected() );

		presenter.onUnselectUser();
		assertEquals( -1, table.getLastSelected() );

	}

	private void assertTableRow( int row, UserBean user ) {
		ListStore<BeanModel> store = view.getStore();
		assertEquals( store.getAt( row ).getBean(), user );
	}

	private void assertConfirmDeletionBar( boolean visible ) {
		MyGXTMockButton yes = (MyGXTMockButton)view.getYesButton();
		MyGXTMockButton no = (MyGXTMockButton)view.getNoButton();
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
