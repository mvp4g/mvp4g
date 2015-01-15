package com.mvp4g.rebind.config.element;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EventFiltersElementTest extends AbstractMvp4gElementTest<EventFiltersElement> {

	protected static final String[] properties = { "afterHistory", "filterForward", "filterStart", "forceFilters" };

	@Test
	public void testAfterHistory() {
		EventFiltersElement element = newElement();
		assertFalse( element.isAfterHistory() );
		element.setAfterHistory( "true" );
		assertTrue( element.isAfterHistory() );

		element = newElement();
		element.setAfterHistory( "false" );
		assertFalse( element.isAfterHistory() );
	}

	@Test
	public void testFilterStart() {
		EventFiltersElement element = newElement();
		assertTrue( element.isFilterStart() );
		element.setFilterStart( "false" );
		assertFalse( element.isFilterStart() );

		element = newElement();
		element.setFilterStart( "true" );
		assertTrue( element.isFilterStart() );
	}

	@Test
	public void testFilterFoward() {
		EventFiltersElement element = newElement();
		assertTrue( element.isFilterForward() );
		element.setFilterForward( "false" );
		assertFalse( element.isFilterForward() );

		element = newElement();
		element.setFilterForward( "true" );
		assertTrue( element.isFilterForward() );
	}

	@Test
	public void testForceFilters() {
		EventFiltersElement element = newElement();
		assertFalse( element.isForceFilters() );
		element.setForceFilters( "true" );
		assertTrue( element.isForceFilters() );

		element = newElement();
		element.setForceFilters( "false" );
		assertFalse( element.isForceFilters() );
	}

	@Override
	protected String[] getProperties() {
		return properties;
	}

	@Override
	protected String getTag() {
		return "eventFilters";
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
