package com.mvp4g.rebind.config.element;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EventHandlerElementTest extends Mvp4gWithServicesElementTest {

	private static final String[] properties = SimpleMvp4gElementTest.addProperties( new String[] { "multiple", "async" } );

	@Override
	protected String[] getProperties() {
		return properties;
	}

	@Override
	protected String getTag() {
		return "eventHandler";
	}

	@Override
	protected SimpleMvp4gElement newElement() {
		return new EventHandlerElement();
	}

	@Test
	public void testMultiple() {
		EventHandlerElement element = new EventHandlerElement();
		assertFalse( element.isMultiple() );
		element.setMultiple( "true" );
		assertTrue( element.isMultiple() );

		element = new EventHandlerElement();
		element.setMultiple( "false" );
		assertFalse( element.isMultiple() );
	}

	@Test
	public void testAsync() {
		EventHandlerElement element = new EventHandlerElement();
		assertFalse( element.isAsync() );
		element.setAsync( "async" );
		assertTrue( element.isAsync() );
	}

}
