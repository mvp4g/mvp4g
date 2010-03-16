package com.mvp4g.util.config.element;

import static junit.framework.Assert.*;

import org.junit.Test;

public class InjectedElementTest {

	@Test
	public void test() {
		String name = "test";
		String setter = "setTest";
		InjectedElement e = new InjectedElement( name, setter );
		assertEquals( name, e.getElementName() );
		assertEquals( setter, e.getSetterName() );
	}

}
