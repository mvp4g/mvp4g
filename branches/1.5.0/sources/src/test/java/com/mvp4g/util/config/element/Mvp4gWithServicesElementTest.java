package com.mvp4g.util.config.element;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class Mvp4gWithServicesElementTest extends SimpleMvp4gElementTest {

	@Test
	public void testSetServices() {
		Mvp4gWithServicesElement e = (Mvp4gWithServicesElement)element;
		List<InjectedElement> services = e.getInjectedServices();
		assertEquals( 0, services.size() );
		e.setValues( "services", new String[] { "service" } );
		assertEquals( 1, services.size() );
		assertEquals( "service", services.get( 0 ).getElementName() );
		assertEquals( "setService", services.get( 0 ).getSetterName() );
	}

	@Test
	public void testSetValues() {
		Mvp4gWithServicesElement e = (Mvp4gWithServicesElement)element;
		List<InjectedElement> services = e.getInjectedServices();
		String[] tab = new String[] { "service" };
		assertEquals( 0, services.size() );
		e.setValues( "test", tab );
		assertEquals( 0, services.size() );
		assertEquals( e.getValues( "test" ), tab );
	}

	@Override
	protected String getTag() {
		return "withServices";
	}

	@Override
	protected SimpleMvp4gElement newElement() {
		return new Mvp4gWithServicesElement();
	}

}
