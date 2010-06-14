package com.mvp4g.util.config.element;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class EventFiltersElementTest extends AbstractMvp4gElementTest<EventFiltersElement> {

	protected static final String[] properties = { "afterHistory", "filterForward", "filterStart" };

	@Test
	public void testAfterHistory() throws DuplicatePropertyNameException {
		EventFiltersElement element = newElement();
		assertFalse( element.isAfterHistory() );
		element.setAfterHistory( "true" );
		assertTrue( element.isAfterHistory() );

		element = newElement();
		element.setAfterHistory( "false" );
		assertFalse( element.isAfterHistory() );
	}

	@Test
	public void testFilterStart() throws DuplicatePropertyNameException {
		EventFiltersElement element = newElement();
		assertTrue( element.isFilterStart() );
		element.setFilterStart( "false" );
		assertFalse( element.isFilterStart() );

		element = newElement();
		element.setFilterStart( "true" );
		assertTrue( element.isFilterStart() );
	}

	@Test
	public void testFilterFoward() throws DuplicatePropertyNameException {
		EventFiltersElement element = newElement();
		assertTrue( element.isFilterForward() );
		element.setFilterForward( "false" );
		assertFalse( element.isFilterForward() );

		element = newElement();
		element.setFilterForward( "true" );
		assertTrue( element.isFilterForward() );
	}

	@Override
	protected String[] getProperties() {
		return properties;
	}

	@Override
	protected String getTag() {
		return "event_filters";
	}

	@Override
	protected String getUniqueIdentifierName() {
		return EventFiltersElement.class.getName();
	}

	@Override
	protected EventFiltersElement newElement() {
		return new EventFiltersElement();
	}

}
