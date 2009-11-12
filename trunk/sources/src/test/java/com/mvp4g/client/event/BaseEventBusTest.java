package com.mvp4g.client.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.client.test_tools.PlaceServiceStub;

public class BaseEventBusTest {

	private BaseEventBus bus = null;
	private PlaceServiceStub<BaseEventBus> placeService = null;

	@Before
	public void setUp() {
		bus = new BaseEventBus();
		placeService = new PlaceServiceStub<BaseEventBus>();
	}

	@Test
	public void testSetAndGetHistoryStored() {
		assertTrue( bus.isHistoryStored() );
		bus.setHistoryStored( false );
		assertFalse( bus.isHistoryStored() );
		bus.setHistoryStored( true );
		assertTrue( bus.isHistoryStored() );
	}

	@Test
	public void testSetAndGetHistoryStoredForNextOne() {
		bus.setHistoryStored( false );
		bus.setHistoryStoredForNextOne( true );
		assertTrue( bus.isHistoryStored() );
		bus.place( placeService, "", "" );
		assertFalse( bus.isHistoryStored() );

		bus.setHistoryStored( true );
		bus.setHistoryStoredForNextOne( false );
		assertFalse( bus.isHistoryStored() );
		bus.place( placeService, "", "" );
		assertTrue( bus.isHistoryStored() );

		bus.setHistoryStored( true );
		bus.setHistoryStoredForNextOne( true );
		assertTrue( bus.isHistoryStored() );
		bus.place( placeService, "", "" );
		assertTrue( bus.isHistoryStored() );

		bus.setHistoryStored( false );
		bus.setHistoryStoredForNextOne( false );
		assertFalse( bus.isHistoryStored() );
		bus.place( placeService, "", "" );
		assertFalse( bus.isHistoryStored() );

	}
	
	@Test
	public void testPlace(){
		
		placeService.assertEvent( null, null );
		
		String eventType = "eventType";
		String form = "form";
		
		bus.setHistoryStored( false );
		bus.place( placeService, eventType, form );
		placeService.assertEvent( null, null );
		
		bus.setHistoryStored( true );
		bus.place( placeService, eventType, form );
		placeService.assertEvent( eventType, form );
	}

}
