package com.mvp4g.rebind.config.element;

import static org.junit.Assert.*;

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
