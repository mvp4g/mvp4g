package com.mvp4g.util.config.element;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class EventElementTest extends AbstractMvp4gElementTest<EventElement> {

	private static final String[] properties = { "eventObjectClass", "calledMethod", "type", "history", "forwardToParent" };
	private static final String[] values = { "handlers", "modulesToLoad" };

	@Test
	public void testGetCalledMethod() throws DuplicatePropertyNameException {
		element.setType( "display" );
		assertEquals( "onDisplay", element.getCalledMethod() );
		element.setCalledMethod( "onDisplayCalled" );
		assertEquals( "onDisplayCalled", element.getCalledMethod() );
	}

	@Test
	public void testGetCalledMethodOneCharacter() throws DuplicatePropertyNameException {
		element.setType( "o" );
		assertEquals( "onO", element.getCalledMethod() );
	}

	@Test
	public void testHasHistory() throws DuplicatePropertyNameException {
		assertFalse( element.hasHistory() );
		element.setHistory( "converter" );
		assertTrue( element.hasHistory() );
	}

	@Test
	public void testToString() throws DuplicatePropertyNameException {
		element.setType( "type" );
		assertEquals( "[type]", element.toString() );
	}

	@Test
	public void testIsAsyncPath() throws DuplicatePropertyNameException {
		EventElement childModuleElement = new EventElement();
		assertFalse( childModuleElement.hasForwardToParent() );
		childModuleElement.setForwardToParent( "true" );
		assertTrue( childModuleElement.hasForwardToParent() );

		childModuleElement = new EventElement();
		childModuleElement.setForwardToParent( "false" );
		assertFalse( childModuleElement.hasForwardToParent() );

		childModuleElement = new EventElement();
		childModuleElement.setForwardToParent( "123" );
		assertFalse( childModuleElement.hasForwardToParent() );
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
