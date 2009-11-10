package com.mvp4g.example.client.mock.eventbus;

import static junit.framework.Assert.assertEquals;

import com.mvp4g.client.event.BaseEventBusWithLookUp;

public class MockEventBus extends BaseEventBusWithLookUp {

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
	
	public void assertEvent(String expectedEventType, Object expectedDispatchedObject){
		assertEquals( expectedEventType, lastDispatchedEventType );
		assertEquals( expectedDispatchedObject, lastDispatchedObject );
	}

}
