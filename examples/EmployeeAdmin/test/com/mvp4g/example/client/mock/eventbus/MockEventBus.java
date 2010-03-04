package com.mvp4g.example.client.mock.eventbus;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import com.mvp4g.client.event.BaseEventBusWithLookUp;
import com.mvp4g.example.client.EmployeeAdminEventBus;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;

public class MockEventBus extends BaseEventBusWithLookUp implements EmployeeAdminEventBus {

	private String lastDispatchedEventType = null;
	private Object lastDispatchedObject = null;

	public void dispatch( String eventType, Object form ) {
		lastDispatchedEventType = eventType;
		lastDispatchedObject = form;
	}

	public String getLastDispatchedEventType() {
		return lastDispatchedEventType;
	}

	public Object getLastDispatchedObject() {
		return lastDispatchedObject;
	}

	public void assertEvent( String expectedEventType, Object expectedDispatchedObject ) {
		assertEquals( expectedEventType, lastDispatchedEventType );
		assertEquals( expectedDispatchedObject, lastDispatchedObject );
	}

	private boolean changeLeftBottomWidgetEvent = false;

	public void changeLeftBottomWidget( MyWidgetInterface widget ) {
		changeLeftBottomWidgetEvent = true;
		lastDispatchedObject = widget;
	}

	public void assertChangeLeftBottomWidget( MyWidgetInterface widget ) {
		assertTrue( changeLeftBottomWidgetEvent );
		assertEquals( widget, lastDispatchedObject );
		changeLeftBottomWidgetEvent = false;
	}

	private boolean changeRightBottomWidgetEvent = false;

	public void changeRightBottomWidget( MyWidgetInterface widget ) {
		changeRightBottomWidgetEvent = true;
		lastDispatchedObject = widget;
	}

	public void assertChangeRightBottomWidget( MyWidgetInterface widget ) {
		assertTrue( changeRightBottomWidgetEvent );
		assertEquals( widget, lastDispatchedObject );
		changeRightBottomWidgetEvent = false;
	}

	private boolean changeTopWidgetEvent = false;

	public void changeTopWidget( MyWidgetInterface widget ) {
		changeTopWidgetEvent = true;
		lastDispatchedObject = widget;
	}

	public void assertChangeTopWidget( MyWidgetInterface widget ) {
		assertTrue( changeTopWidgetEvent );
		assertEquals( widget, lastDispatchedObject );
		changeTopWidgetEvent = false;
	}

	private boolean createNewUserEvent = false;

	public void createNewUser( UserBean user ) {
		createNewUserEvent = true;
		lastDispatchedObject = user;
	}

	public void assertCreateNewUser() {
		assertTrue( createNewUserEvent );
		UserBean u = (UserBean)lastDispatchedObject;
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
		lastDispatchedObject = user;
	}

	public void assertSelectUser( UserBean user ) {
		assertTrue( selectUserEvent );
		assertEquals( user, lastDispatchedObject );
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
		lastDispatchedObject = user;
	}

	public void assertUserCreated( UserBean user ) {
		assertTrue( userCreatedEvent );
		assertEquals( user, lastDispatchedObject );
		userCreatedEvent = false;
	}

	private boolean userUpdatedEvent = false;

	public void userUpdated( UserBean user ) {
		userUpdatedEvent = true;
		lastDispatchedObject = user;
	}

	public void assertUserUpdated( UserBean user ) {
		assertTrue( userUpdatedEvent );
		assertEquals( user, lastDispatchedObject );
		userUpdatedEvent = false;
	}

}
