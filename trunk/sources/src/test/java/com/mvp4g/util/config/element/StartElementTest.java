package com.mvp4g.util.config.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class StartElementTest extends AbstractMvp4gElementTest<StartElement> {

	protected static final String[] properties = { "eventType", "presenter", "history", "forwardEventType" };

	@Test
	public void testHasEventType() throws DuplicatePropertyNameException {
		assertFalse( element.hasEventType() );
		element.setEventType( "test" );
		assertTrue( element.hasEventType() );
	}

	@Test
	public void testHasHistory() throws DuplicatePropertyNameException {
		assertFalse( element.hasHistory() );
		element.setHistory( "true" );
		assertTrue( element.hasHistory() );
	}

	@Test
	public void testHasHistoryUpper() throws DuplicatePropertyNameException {
		assertFalse( element.hasHistory() );
		element.setHistory( "TRUE" );
		assertTrue( element.hasHistory() );
	}

	@Test
	public void testHasHistoryFalse() throws DuplicatePropertyNameException {
		assertFalse( element.hasHistory() );
		element.setHistory( "false" );
		assertFalse( element.hasHistory() );
	}

	@Test
	public void testHasHistoryAny() throws DuplicatePropertyNameException {
		assertFalse( element.hasHistory() );
		element.setHistory( "laksjd123" );
		assertFalse( element.hasHistory() );
	}
	
	@Test
	public void testHasForward() throws DuplicatePropertyNameException{
		assertFalse( element.hasForwardEventType() );
		String test = "test";
		element.setForwardEventType( test );
		assertEquals( test, element.getForwardEventType() );
		assertTrue( element.hasForwardEventType() );
	}
	
	@Test
	public void testHasStartView() throws DuplicatePropertyNameException {
		assertFalse( element.hasPresenter() );
		
		element.setPresenter( "presenter" );
		assertTrue( element.hasPresenter() );
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
