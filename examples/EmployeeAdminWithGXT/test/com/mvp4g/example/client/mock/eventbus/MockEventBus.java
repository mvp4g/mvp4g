package com.mvp4g.example.client.mock.eventbus;

import com.mvp4g.client.event.EventBus;
import static junit.framework.Assert.*;

public class MockEventBus extends EventBus {

	private String lastDispatchedEventType = null;
	private Object lastDispatchedObject = null;

	public void dispatch( String eventType, Object form, boolean hasHistory ) {
		lastDispatchedEventType = eventType;
		lastDispatchedObject = form;
	}

	public String getLastDispatchedEventType() {
		return lastDispatchedEventType;
	}

	public Object getLastDispatchedObject() {
		return lastDispatchedObject;
	}
	
	public void assertEvent(String expectedEventType, Object expectedDispatchedObject){
		assertEquals( expectedEventType, lastDispatchedEventType );
		assertEquals( expectedDispatchedObject, lastDispatchedObject );
	}

}
