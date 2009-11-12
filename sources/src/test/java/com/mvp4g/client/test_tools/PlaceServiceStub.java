package com.mvp4g.client.test_tools;

import static junit.framework.Assert.assertEquals;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.PlaceService;

public class PlaceServiceStub<E extends EventBus> extends PlaceService<E> {

	Object lastForm = null;
	String lastEventType = null;

	public PlaceServiceStub() {
		super( new HistoryProxyStub() );
	}

	@Override
	public <T> void place( String eventType, T form ) {
		this.lastEventType = eventType;
		this.lastForm = form;
	}

	@Override
	protected void sendInitEvent() {
		// TODO Auto-generated method stub			
	}

	public Object getLastForm() {
		return lastForm;
	}

	public String getLastEventType() {
		return lastEventType;
	}

	public void assertEvent( String expectedEventType, Object expectedForm ) {
		assertEquals( expectedEventType, lastEventType );
		assertEquals( expectedForm, lastForm );
	}

}
