package com.mvp4g.rebind.config.element;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

public class SplitterElementTest extends SimpleMvp4gElementTest {

	private static final String[] properties = SimpleMvp4gElementTest.addProperties( new String[] { "loader" } );

	@Override
	protected String[] getProperties() {
		return properties;
	}

	public void testGetters() {
		SplitterElement splitter = new SplitterElement();

		Set<EventHandlerElement> eventHandlers = splitter.getHandlers();
		assertTrue( eventHandlers.size() == 0 );

		Map<EventElement, EventAssociation<String>> events = splitter.getEvents();
		assertTrue( events.size() == 0 );
	}
	
	@Override
	protected SimpleMvp4gElement newElement() {
		return new SplitterElement();
	}

}
