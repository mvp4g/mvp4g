package com.mvp4g.util.config.element;

import static junit.framework.Assert.*;

import org.junit.Test;

import com.mvp4g.client.event.EventBusWithLookup;

public class EventBusElementTest {

	@Test
	public void testConstructor() {
		String interfaceName = "test";
		String abstractClassName = "setTest";
		EventBusElement e = new EventBusElement( interfaceName, abstractClassName, false );
		assertEquals( interfaceName, e.getInterfaceClassName() );
		assertEquals( abstractClassName, e.getAbstractClassName() );
		assertFalse( e.isWithLookUp() );
		assertFalse( e.isXml() );
	}

	@Test
	public void testIsXml() {
		EventBusElement e = new EventBusElement( EventBusWithLookup.class.getName(), "name", false );
		assertTrue( e.isXml() );
	}

}
