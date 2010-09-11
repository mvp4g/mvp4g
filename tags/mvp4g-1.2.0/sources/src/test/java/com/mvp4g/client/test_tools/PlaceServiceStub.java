package com.mvp4g.client.test_tools;

import static junit.framework.Assert.assertEquals;

import com.mvp4g.client.history.PlaceService;

public class PlaceServiceStub extends PlaceService {

	String lastParam = null;
	String lastEventType = null;

	public PlaceServiceStub() {
		super( new HistoryProxyStub(), PlaceService.DEFAULT_SEPARATOR, false );
	}

	@Override
	public void place( String eventType, String param ) {
		this.lastEventType = eventType;
		this.lastParam = param;
	}

	public Object getLastForm() {
		return lastParam;
	}

	public String getLastEventType() {
		return lastEventType;
	}

	public void assertEvent( String expectedEventType, Object expectedForm ) {
		assertEquals( expectedEventType, lastEventType );
		assertEquals( expectedForm, lastParam );
	}

	@Override
	protected void sendInitEvent() {
		//Nothing to do			
	}

	@Override
	protected void sendNotFoundEvent() {
		//Nothing to do
	}

}
