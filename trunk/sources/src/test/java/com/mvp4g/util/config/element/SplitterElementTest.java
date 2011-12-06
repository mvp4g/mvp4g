package com.mvp4g.util.config.element;

import static junit.framework.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

public class SplitterElementTest extends SimpleMvp4gElementTest {

	public void testGetters() {
		SplitterElement splitter = new SplitterElement();

		Set<EventHandlerElement> eventHandlers = splitter.getHandlers();
		assertTrue( eventHandlers.size() == 0 );

		Map<EventElement, EventAssociation<String>> events = splitter.getEvents();
		assertTrue( events.size() == 0 );
	}

}
