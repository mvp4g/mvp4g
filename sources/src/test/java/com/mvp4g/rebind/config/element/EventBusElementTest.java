package com.mvp4g.rebind.config.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class EventBusElementTest {

	@Test
	public void testConstructor() {
		String interfaceName = "test";
		String abstractClassName = "setTest";
		EventBusElement e = new EventBusElement( interfaceName, abstractClassName, false );
		assertEquals( interfaceName, e.getInterfaceClassName() );
		assertEquals( abstractClassName, e.getAbstractClassName() );
		assertFalse( e.isWithLookUp() );
	}

}
