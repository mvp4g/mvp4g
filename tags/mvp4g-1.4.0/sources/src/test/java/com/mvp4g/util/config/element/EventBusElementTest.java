package com.mvp4g.util.config.element;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

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
