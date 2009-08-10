package com.mvp4g.util.config.element;

import static org.junit.Assert.*;

import org.junit.Test;

public class EventElementTest extends AbstractMvp4gElementTest<EventElement> {

	private static final String[] properties = { "eventObjectClass", "calledMethod", "type" };
	private static final String[] values = { "handlers" };

	@Test
	public void testEventParameterString() {
		assertEquals( "();", element.getEventParameterString() );
		element.setEventObjectClass( "com.lang.String" );
		assertEquals( "(form);", element.getEventParameterString() );
	}

	@Test
	public void testNullEventObjectClass() {
		assertEquals( Object.class.getName(), element.getEventObjectClass() );
	}

	@Test
	public void testToString() {
		element.setType( "type" );
		assertEquals( "[type]", element.toString() );
	}

	@Override
	protected String[] getProperties() {
		return properties;
	}

	@Override
	protected String getTag() {
		return "event";
	}

	@Override
	protected String[] getValues() {
		return values;
	}

	@Override
	protected EventElement newElement() {
		return new EventElement();
	}

	@Override
	protected String getUniqueIdentifierName() {
		return "type";
	}

}
