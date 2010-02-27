package com.mvp4g.client.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.client.test_tools.Mvp4gModuleStub;

public class BaseEventBusTest {

	private BaseEventBus bus = null;
	private Mvp4gModuleStub module;

	@Before
	public void setUp() {
		bus = new BaseEventBus();
		module = new Mvp4gModuleStub( bus );
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
		bus.place( module, "", "" );
		assertFalse( bus.isHistoryStored() );

		bus.setHistoryStored( true );
		bus.setHistoryStoredForNextOne( false );
		assertFalse( bus.isHistoryStored() );
		bus.place( module, "", "" );
		assertTrue( bus.isHistoryStored() );

		bus.setHistoryStored( true );
		bus.setHistoryStoredForNextOne( true );
		assertTrue( bus.isHistoryStored() );
		bus.place( module, "", "" );
		assertTrue( bus.isHistoryStored() );

		bus.setHistoryStored( false );
		bus.setHistoryStoredForNextOne( false );
		assertFalse( bus.isHistoryStored() );
		bus.place( module, "", "" );
		assertFalse( bus.isHistoryStored() );

	}

	@Test
	public void testPlace() {

		String eventType = "eventType";
		String form = "form";

		bus.setHistoryStored( false );
		bus.place( module, eventType, form );

		bus.setHistoryStored( true );
		bus.place( module, eventType, form );

		assertEquals( module.getEventType(), eventType );
		assertEquals( module.getForm(), form );

	}

}
