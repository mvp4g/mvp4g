package com.mvp4g.example.client.presenter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.mock.MockGwtEvent;
import com.mvp4g.example.client.mock.eventbus.MockEventBus;
import com.mvp4g.example.client.mock.service.MockUserServiceAsync;
import com.mvp4g.example.client.mock.view.MockUserRoleView;
import com.mvp4g.example.client.mock.widget.MyMockButton;
import com.mvp4g.example.client.mock.widget.MyMockListBox;

public class UserRolePresenterTest {

	UserRolePresenter presenter = null;
	MockUserRoleView view = null;
	MockEventBus eventBus = null;
	MockUserServiceAsync service = null;
	List<UserBean> users = null;

	@Before
	public void setUp() {
		presenter = new UserRolePresenter();
		view = new MockUserRoleView();
		eventBus = new MockEventBus();
		service = new MockUserServiceAsync( null );
		presenter.setView( view );
		presenter.setEventBus( eventBus );
		presenter.setUserService( service );
		presenter.bindIfNeeded();
		presenter.onStart();
	}

	@Test
	public void testBind() {
		assertDisable();
	}

	@Test
	public void testOnCreateNewUser() {
		presenter.onCreateNewUser( new UserBean() );
		assertDisable();
	}

	@Test
	public void testOnSelectUser() {
		String[] roles = { "role1", "role2" };
		UserBean user = createUserBean( roles );
		presenter.onSelectUser( user );
		assertFalse( ( (MyMockButton)view.getRemoveButton() ).isEnabled() );
		assertFalse( ( (MyMockButton)view.getAddButton() ).isEnabled() );
		MyMockListBox rolesChoices = (MyMockListBox)view.getRoleChoiceListBox();
		assertEquals( rolesChoices.getSelectedIndex(), 0 );
		assertTrue( rolesChoices.isEnabled() );

		MyMockListBox selectedRoles = (MyMockListBox)view.getSelectedRolesListBox();
		List<String> items = selectedRoles.getItems();
		assertEquals( items.size(), roles.length );
		for ( String role : roles ) {
			assertTrue( items.contains( role ) );
		}

	}

	@Test
	public void testOnStart() {
		eventBus.assertChangeRightBottomWidget( view.getViewWidget() );
	}

	@Test
	public void testOnUnselectUser() {
		presenter.onUnselectUser();
		assertDisable();
	}

	@Test
	public void testOnUserUpdated() {
		presenter.onUserUpdated( new UserBean() );
		assertDisable();
	}

	@Test
	public void testRemoveRole() {
		UserBean user = createUserBean( new String[] { "role1", "role2" } );
		presenter.onSelectUser( user );

		view.getSelectedRolesListBox().setSelectedIndex( 1 );

		MyMockListBox selectedRoles = (MyMockListBox)view.getSelectedRolesListBox();

		assertEquals( 2, selectedRoles.getItems().size() );

		MyMockButton remove = (MyMockButton)view.getRemoveButton();
		remove.setEnabled( true );
		remove.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );

		assertEquals( "role1", selectedRoles.getItems().get( 0 ) );
		assertEquals( "role1", user.getRoles().get( 0 ) );
		assertTrue( remove.isEnabled() );

		remove.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );

		assertEquals( 0, selectedRoles.getItems().size() );
		assertEquals( 0, user.getRoles().size() );
		assertFalse( remove.isEnabled() );
	}

	@Test
	public void testAddRole() {
		UserBean user = new UserBean();
		presenter.onSelectUser( user );

		MyMockListBox selectedRoles = (MyMockListBox)view.getSelectedRolesListBox();

		assertEquals( 0, selectedRoles.getItems().size() );

		MyMockListBox rolesChoices = (MyMockListBox)view.getRoleChoiceListBox();
		rolesChoices.setSelectedIndex( 1 );

		MyMockButton add = (MyMockButton)view.getAddButton();
		add.setEnabled( true );
		add.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );

		assertEquals( rolesChoices.getSelectedValue(), selectedRoles.getItems().get( 0 ) );
		assertEquals( rolesChoices.getSelectedValue(), user.getRoles().get( 0 ) );
		assertEquals( view.getError(), null );

		add.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );

		assertTrue( view.getError() != null );

	}

	@Test
	public void testAddEnabled() {
		MyMockButton add = (MyMockButton)view.getAddButton();
		assertFalse( add.isEnabled() );

		MyMockListBox rolesChoices = (MyMockListBox)view.getRoleChoiceListBox();
		rolesChoices.setSelectedIndex( 1 );

		rolesChoices.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertFalse( add.isEnabled() );

		rolesChoices.getKeyUpHandler().onKeyUp( MockGwtEvent.KEY_UP_EVENT );
		assertFalse( add.isEnabled() );

		presenter.onSelectUser( new UserBean() );
		rolesChoices.setSelectedIndex( 1 );
		rolesChoices.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertTrue( add.isEnabled() );

		rolesChoices.setSelectedIndex( 0 );
		rolesChoices.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertFalse( add.isEnabled() );

		rolesChoices.setSelectedIndex( 1 );
		rolesChoices.getKeyUpHandler().onKeyUp( MockGwtEvent.KEY_UP_EVENT );
		assertTrue( add.isEnabled() );

		rolesChoices.setSelectedIndex( 0 );
		rolesChoices.getKeyUpHandler().onKeyUp( MockGwtEvent.KEY_UP_EVENT );
		assertFalse( add.isEnabled() );

	}

	@Test
	public void testRemoveEnabled() {
		MyMockListBox selectedRoles = (MyMockListBox)view.getSelectedRolesListBox();
		MyMockButton remove = (MyMockButton)view.getRemoveButton();
		assertFalse( remove.isEnabled() );

		presenter.onSelectUser( createUserBean( new String[] { "test" } ) );
		selectedRoles.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertFalse( remove.isEnabled() );
		selectedRoles.getKeyUpHandler().onKeyUp( MockGwtEvent.KEY_UP_EVENT );
		assertFalse( remove.isEnabled() );

		selectedRoles.setSelectedIndex( 0 );
		selectedRoles.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertTrue( remove.isEnabled() );

		presenter.onSelectUser( createUserBean( new String[] { "test" } ) );
		selectedRoles.setSelectedIndex( -1 );
		selectedRoles.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertFalse( remove.isEnabled() );

		presenter.onSelectUser( createUserBean( new String[] { "test" } ) );
		selectedRoles.setSelectedIndex( 0 );
		selectedRoles.getKeyUpHandler().onKeyUp( MockGwtEvent.KEY_UP_EVENT );
		assertTrue( remove.isEnabled() );

		presenter.onSelectUser( createUserBean( new String[] { "test" } ) );
		selectedRoles.setSelectedIndex( -1 );
		selectedRoles.getKeyUpHandler().onKeyUp( MockGwtEvent.KEY_UP_EVENT );
		assertFalse( remove.isEnabled() );

	}

	private void assertDisable() {
		assertFalse( ( (MyMockButton)view.getRemoveButton() ).isEnabled() );
		assertFalse( ( (MyMockButton)view.getAddButton() ).isEnabled() );
		assertEquals( ( (MyMockListBox)view.getSelectedRolesListBox() ).getItems().size(), 0 );
		MyMockListBox rolesChoices = (MyMockListBox)view.getRoleChoiceListBox();
		assertEquals( rolesChoices.getSelectedIndex(), 0 );
		assertFalse( rolesChoices.isEnabled() );
	}

	private UserBean createUserBean( String[] rolesInput ) {
		UserBean user = new UserBean();
		List<String> roles = new ArrayList<String>();
		for ( String role : rolesInput ) {
			roles.add( role );
		}

		user.setRoles( roles );
		return user;
	}

}
