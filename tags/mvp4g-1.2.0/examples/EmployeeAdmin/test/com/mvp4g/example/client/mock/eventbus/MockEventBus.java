package com.mvp4g.example.client.mock.eventbus;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import com.mvp4g.client.event.BaseEventBusWithLookUp;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.example.client.EmployeeAdminEventBus;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.widget.interfaces.IWidget;

public class MockEventBus extends BaseEventBusWithLookUp implements EmployeeAdminEventBus {

	private String lastDispatchedEventType = null;
	private Object[] lastDispatchedObject = null;

	@Override
	public void dispatch( String eventType, Object... form ) {
		lastDispatchedEventType = eventType;
		lastDispatchedObject = form;
	}

	public String getLastDispatchedEventType() {
		return lastDispatchedEventType;
	}

	public Object[] getLastDispatchedObject() {
		return lastDispatchedObject;
	}

	public void assertEvent( String expectedEventType, Object[] expectedDispatchedObject ) {
		assertEquals( expectedEventType, lastDispatchedEventType );
		assertEquals( expectedDispatchedObject.length, lastDispatchedObject.length );
		for ( int i = 0; i < expectedDispatchedObject.length; i++ ) {
			assertEquals( expectedDispatchedObject[i], lastDispatchedObject[i] );
		}
	}

	private boolean changeLeftBottomWidgetEvent = false;

	public void changeLeftBottomWidget( IWidget widget ) {
		changeLeftBottomWidgetEvent = true;
		lastDispatchedObject = new Object[] { widget };
	}

	public void assertChangeLeftBottomWidget( IWidget widget ) {
		assertTrue( changeLeftBottomWidgetEvent );
		assertEquals( widget, lastDispatchedObject[0] );
		changeLeftBottomWidgetEvent = false;
	}

	private boolean changeRightBottomWidgetEvent = false;

	public void changeRightBottomWidget( IWidget widget ) {
		changeRightBottomWidgetEvent = true;
		lastDispatchedObject = new Object[] { widget };
		;
	}

	public void assertChangeRightBottomWidget( IWidget widget ) {
		assertTrue( changeRightBottomWidgetEvent );
		assertEquals( widget, lastDispatchedObject[0] );
		changeRightBottomWidgetEvent = false;
	}

	private boolean changeTopWidgetEvent = false;

	public void changeTopWidget( IWidget widget ) {
		changeTopWidgetEvent = true;
		lastDispatchedObject = new Object[] { widget };
	}

	public void assertChangeTopWidget( IWidget widget ) {
		assertTrue( changeTopWidgetEvent );
		assertEquals( widget, lastDispatchedObject[0] );
		changeTopWidgetEvent = false;
	}

	private boolean createNewUserEvent = false;

	public void createNewUser( UserBean user ) {
		createNewUserEvent = true;
		lastDispatchedObject = new Object[] { user };
	}

	public void assertCreateNewUser() {
		assertTrue( createNewUserEvent );
		UserBean u = (UserBean)lastDispatchedObject[0];
		assertNull( u.getFirstName() );
		assertNull( u.getLastName() );
		assertNull( u.getEmail() );
		assertNull( u.getUsername() );
		assertNull( u.getPassword() );
		assertNull( u.getDepartment() );
		assertNull( u.getRoles() );
		createNewUserEvent = false;
	}

	private boolean selectUserEvent = false;

	public void selectUser( UserBean user ) {
		selectUserEvent = true;
		lastDispatchedObject = new Object[] { user };
		;
	}

	public void assertSelectUser( UserBean user ) {
		assertTrue( selectUserEvent );
		assertEquals( user, lastDispatchedObject[0] );
		selectUserEvent = false;
	}

	private boolean startEvent = false;

	public void start() {
		startEvent = true;
	}

	public void assertStart() {
		assertTrue( startEvent );
		startEvent = false;
	}

	private boolean unselectUserEvent = false;

	public void unselectUser() {
		unselectUserEvent = true;
	}

	public void assertUnselectUser() {
		assertTrue( unselectUserEvent );
		unselectUserEvent = false;
	}

	private boolean userCreatedEvent = false;

	public void userCreated( UserBean user ) {
		userCreatedEvent = true;
		lastDispatchedObject = new Object[] { user };
	}

	public void assertUserCreated( UserBean user ) {
		assertTrue( userCreatedEvent );
		assertEquals( user, lastDispatchedObject[0] );
		userCreatedEvent = false;
	}

	private boolean userUpdatedEvent = false;

	public void userUpdated( UserBean user ) {
		userUpdatedEvent = true;
		lastDispatchedObject = new Object[] { user };
	}

	public void assertUserUpdated( UserBean user ) {
		assertTrue( userUpdatedEvent );
		assertEquals( user, lastDispatchedObject[0] );
		userUpdatedEvent = false;
	}

	@Override
	protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ) {
		// TODO Auto-generated method stub
		return null;
	}

}
