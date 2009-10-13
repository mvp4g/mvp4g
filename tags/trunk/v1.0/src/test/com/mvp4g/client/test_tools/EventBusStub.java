package com.mvp4g.client.test_tools;

import com.mvp4g.client.event.EventBus;
import static junit.framework.Assert.*;

public class EventBusStub extends EventBus {

	private String lastDispatchedEventType = null;
	private Object lastDispatchedObject = null;
	private boolean lastStoreInHistory = false;

	public void dispatch( String eventType, Object form, boolean storeInHistory ) {
		lastDispatchedEventType = eventType;
		lastDispatchedObject = form;
		lastStoreInHistory = storeInHistory;
	}

	public String getLastDispatchedEventType() {
		return lastDispatchedEventType;
	}

	public Object getLastDispatchedObject() {
		return lastDispatchedObject;
	}

	public boolean isLastStoreInHistory() {
		return lastStoreInHistory;
	}

	public void assertEvent( String expectedEventType, Object expectedDispatchedObject, boolean expectedStoreInHistory ) {
		assertEquals( expectedEventType, lastDispatchedEventType );
		assertEquals( expectedDispatchedObject, lastDispatchedObject );
		assertEquals( expectedStoreInHistory, lastStoreInHistory );
	}

}
