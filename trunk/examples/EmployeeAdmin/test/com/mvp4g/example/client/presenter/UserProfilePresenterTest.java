package com.mvp4g.example.client.presenter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.mock.MockGwtEvent;
import com.mvp4g.example.client.mock.eventbus.MockEventBus;
import com.mvp4g.example.client.mock.service.MockUserServiceAsync;
import com.mvp4g.example.client.mock.view.MockUserProfileView;
import com.mvp4g.example.client.mock.widget.MyMockButton;
import com.mvp4g.example.client.mock.widget.MyMockListBox;
import com.mvp4g.example.client.mock.widget.MyMockTextBox;

public class UserProfilePresenterTest implements Constants {

	UserProfilePresenter presenter = null;
	MockUserProfileView view = null;
	MockEventBus eventBus = null;
	MockUserServiceAsync service = null;
	List<UserBean> users = null;

	@Before
	public void setUp() {
		presenter = new UserProfilePresenter();
		view = new MockUserProfileView();
		eventBus = new MockEventBus();
		service = new MockUserServiceAsync( null );
		presenter.setView( view );
		presenter.setEventBus( eventBus );
		presenter.setUserService( service );
		presenter.bind();
		presenter.onStart();
	}

	@Test
	public void testBind() {
		assertInitForm();
	}

	@Test
	public void testEnabledButton() {

		presenter.onCreateNewUser( new UserBean() );

		MyMockTextBox[] textBoxes = { (MyMockTextBox)view.getUsername(), (MyMockTextBox)view.getPassword(), (MyMockTextBox)view.getConfirmPassword() };
		MyMockButton update = (MyMockButton)view.getUpdateButton();

		for ( MyMockTextBox textBox : textBoxes ) {
			fillForm( DEPARTMENTS[0] );
			textBox.getKeyUpHandler().onKeyUp( MockGwtEvent.KEY_UP_EVENT );
			assertTrue( update.isEnabled() );
			fillForm( "" );
			textBox.getKeyUpHandler().onKeyUp( MockGwtEvent.KEY_UP_EVENT );
			assertFalse( update.isEnabled() );
		}

		MyMockListBox department = (MyMockListBox)view.getDepartment();

		fillForm( DEPARTMENTS[0] );
		department.getKeyUpHandler().onKeyUp( MockGwtEvent.KEY_UP_EVENT );
		assertTrue( update.isEnabled() );
		fillForm( "" );
		department.getKeyUpHandler().onKeyUp( MockGwtEvent.KEY_UP_EVENT );
		assertFalse( update.isEnabled() );

		fillForm( DEPARTMENTS[0] );
		department.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertTrue( update.isEnabled() );
		fillForm( "" );
		department.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertFalse( update.isEnabled() );

	}

	@Test
	public void testNotEnabledButton() {
		presenter.onCreateNewUser( new UserBean() );

		MyMockButton update = (MyMockButton)view.getUpdateButton();
		MyMockListBox department = (MyMockListBox)view.getDepartment();

		fillForm( DEPARTMENTS[0] );
		view.getUsername().setValue( "" );
		department.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertFalse( update.isEnabled() );

		fillForm( DEPARTMENTS[0] );
		view.getPassword().setValue( "" );
		department.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertFalse( update.isEnabled() );

		fillForm( DEPARTMENTS[0] );
		view.getConfirmPassword().setValue( "" );
		department.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertFalse( update.isEnabled() );

		fillForm( DEPARTMENTS[0] );
		view.getPassword().setValue( "test" );
		department.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertFalse( update.isEnabled() );

		fillForm( DEPARTMENTS[0] );
		department.setSelectedIndex( 0 );
		department.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		assertFalse( update.isEnabled() );
	}

	@Test
	public void testOnStart() {
		eventBus.assertChangeLeftBottomWidget( view.getViewWidget() );
	}

	@Test
	public void testOnUnselectUser() {
		presenter.onUnselectUser();
		assertInitForm();
	}

	@Test
	public void testOnSelectAndUpdate() {
		UserBean user = createUser( DEPARTMENTS[0] );
		presenter.onSelectUser( user );
		assertUserBeanAndFormEquals( user );
		assertTrue( view.isUpdateMode() );
		MyMockButton update = (MyMockButton)view.getUpdateButton();
		assertTrue( update.isEnabled() );
		MyMockButton cancel = (MyMockButton)view.getCancelButton();
		assertTrue( cancel.isEnabled() );

		fillForm( DEPARTMENTS[1] );
		update.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		eventBus.assertUserUpdated( user );

		assertInitForm();
		assertFalse( update.isEnabled() );
		assertFalse( cancel.isEnabled() );

	}

	@Test
	public void testOnNewAndCreate() {
		UserBean user = new UserBean();
		presenter.onCreateNewUser( user );
		assertInitForm();
		MyMockButton update = (MyMockButton)view.getUpdateButton();
		assertFalse( update.isEnabled() );
		MyMockButton cancel = (MyMockButton)view.getCancelButton();
		assertTrue( cancel.isEnabled() );
		assertFalse( view.isUpdateMode() );

		fillForm( DEPARTMENTS[1] );
		update.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		eventBus.assertUserCreated( user );

		assertInitForm();
		assertFalse( update.isEnabled() );
		assertFalse( cancel.isEnabled() );

	}

	@Test
	public void testCancelButton() {
		MyMockButton cancel = (MyMockButton)view.getCancelButton();
		cancel.getClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
		eventBus.assertUnselectUser();
	}

	private void fillForm( String value ) {

		view.getDepartment().setSelectedValue( value );

		view.getUsername().setValue( value );
		view.getFirstName().setValue( value );
		view.getLastName().setValue( value );
		view.getEmail().setValue( value );
		view.getPassword().setValue( value );
		view.getConfirmPassword().setValue( value );

	}

	private void assertUserBeanAndFormEquals( UserBean user ) {
		assertEquals( view.getUsername().getValue(), user.getUsername() );
		assertEquals( view.getFirstName().getValue(), user.getFirstName() );
		assertEquals( view.getLastName().getValue(), user.getLastName() );
		assertEquals( view.getEmail().getValue(), user.getEmail() );
		assertEquals( view.getPassword().getValue(), user.getPassword() );
		assertEquals( view.getDepartment().getSelectedValue(), user.getDepartment() );
	}

	private void assertInitForm() {
		assertEquals( view.getUsername().getValue(), "" );
		assertEquals( view.getFirstName().getValue(), "" );
		assertEquals( view.getLastName().getValue(), "" );
		assertEquals( view.getEmail().getValue(), "" );
		assertEquals( view.getPassword().getValue(), "" );
		assertEquals( view.getDepartment().getSelectedIndex(), 0 );
	}

	private UserBean createUser( String value ) {
		UserBean user = new UserBean();
		user.setUsername( value );
		user.setFirstName( value );
		user.setLastName( value );
		user.setEmail( value );
		user.setPassword( value );
		user.setDepartment( value );
		return user;
	}

}
