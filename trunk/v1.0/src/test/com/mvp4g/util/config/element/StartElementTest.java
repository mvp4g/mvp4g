package com.mvp4g.util.config.element;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StartElementTest extends AbstractMvp4gElementTest<StartElement> {

	protected static final String[] properties = { "eventType", "view" };

	@Test
	public void testHasEventType() {
		assertFalse( element.hasEventType() );
		element.setEventType( "test" );
		assertTrue( element.hasEventType() );
	}

	@Override
	protected String[] getProperties() {
		return properties;
	}

	@Override
	protected String getTag() {
		return "start";
	}

	@Override
	protected String getUniqueIdentifierName() {
		return StartElement.class.getName();
	}

	@Override
	protected StartElement newElement() {
		return new StartElement();
	}

}
