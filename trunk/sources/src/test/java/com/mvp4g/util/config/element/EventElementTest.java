package com.mvp4g.util.config.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class EventElementTest extends AbstractMvp4gElementTest<EventElement> {

	private static final String[] properties = { "calledMethod", "type", "history", "forwardToParent", "historyName" };
	private static final String[] values = { "handlers", "modulesToLoad", "eventObjectClass" };

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

	@Test
	public void testActivateSetterGetter() throws DuplicatePropertyNameException {
		EventElement element = newElement();
		assertNull( element.getActivate() );
		assertNull( element.getValues( "activate" ) );
		String[] test = { "test1", "test2" };
		element.setActivate( test );
		List<String> activate = element.getActivate();
		assertTrue( test.length == activate.size() );
		for ( int i = 0; i < test.length; i++ ) {
			assertSame( test[i], activate.get( i ) );
		}
		assertNull( element.getValues( "activate" ) );

		element = newElement();
		element.setValues( "activate", test );
		activate = element.getActivate();
		assertTrue( test.length == activate.size() );
		for ( int i = 0; i < test.length; i++ ) {
			assertSame( test[i], activate.get( i ) );
		}
		assertNull( element.getValues( "activate" ) );

		try {
			element.setActivate( test );
			fail();
		} catch ( DuplicatePropertyNameException e ) {

		}

		try {
			element.setValues( "activate", test );
			fail();
		} catch ( DuplicatePropertyNameException e ) {

		}
	}

	@Test
	public void testDeactivateSetterGetter() throws DuplicatePropertyNameException {
		assertNull( element.getDeactivate() );
		assertNull( element.getValues( "deactivate" ) );
		String[] test = { "test1", "test2" };
		element.setDeactivate( test );
		List<String> deactivate = element.getDeactivate();
		assertTrue( test.length == deactivate.size() );
		for ( int i = 0; i < test.length; i++ ) {
			assertSame( test[i], deactivate.get( i ) );
		}
		assertNull( element.getValues( "deactivate" ) );

		element = newElement();
		element.setValues( "deactivate", test );
		deactivate = element.getDeactivate();
		assertTrue( test.length == deactivate.size() );
		for ( int i = 0; i < test.length; i++ ) {
			assertSame( test[i], deactivate.get( i ) );
		}
		assertNull( element.getValues( "deactivate" ) );

		try {
			element.setDeactivate( test );
			fail();
		} catch ( DuplicatePropertyNameException e ) {

		}

		try {
			element.setValues( "deactivate", test );
			fail();
		} catch ( DuplicatePropertyNameException e ) {

		}
	}

	@Test
	public void testDefaultHistoryName() throws DuplicatePropertyNameException {
		String test = "test";
		element.setType( test );
		assertEquals( test, element.getHistoryName() );
	}

	@Test
	public void testNavigationEventTrue() throws DuplicatePropertyNameException {
		String test = "true";
		element.setNavigationEvent( test );
		assertEquals( test, element.getNavigationEvent() );
		assertTrue( element.isNavigationEvent() );
	}

	@Test
	public void testNavigationEventFalse() throws DuplicatePropertyNameException {
		String test = "false";
		element.setNavigationEvent( test );
		assertEquals( test, element.getNavigationEvent() );
		assertFalse( element.isNavigationEvent() );
	}

	@Test
	public void testWithTokenGenerationTrue() throws DuplicatePropertyNameException {
		String test = "true";
		element.setWithTokenGeneration( test );
		assertEquals( test, element.getWithTokenGeneration() );
		assertTrue( element.isWithTokenGeneration() );
	}

	@Test
	public void testWithTokenGenerationFalse() throws DuplicatePropertyNameException {
		String test = "false";
		element.setWithTokenGeneration( test );
		assertEquals( test, element.getWithTokenGeneration() );
		assertFalse( element.isWithTokenGeneration() );
	}

	@Test
	public void testTokenGenerationFromParentTrue() throws DuplicatePropertyNameException {
		String test = "true";
		element.setTokenGenerationFromParent( test );
		assertEquals( test, element.getTokenGenerationFromParent() );
		assertTrue( element.isTokenGenerationFromParent() );
	}

	@Test
	public void testTokenGenerationFromParentFalse() throws DuplicatePropertyNameException {
		String test = "false";
		element.setTokenGenerationFromParent( test );
		assertEquals( test, element.getTokenGenerationFromParent() );
		assertFalse( element.isTokenGenerationFromParent() );
	}

	@Test
	public void testPassiveTrue() throws DuplicatePropertyNameException {
		String test = "true";
		element.setPassive( test );
		assertEquals( test, element.getPassive() );
		assertTrue( element.isPassive() );
	}

	@Test
	public void testPassiveFalse() throws DuplicatePropertyNameException {
		String test = "false";
		element.setPassive( test );
		assertEquals( test, element.getPassive() );
		assertFalse( element.isPassive() );
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
