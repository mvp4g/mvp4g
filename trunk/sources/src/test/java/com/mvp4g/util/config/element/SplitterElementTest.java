package com.mvp4g.util.config.element;

import static junit.framework.Assert.assertTrue;

import java.util.List;
import java.util.Map;

public class SplitterElementTest extends SimpleMvp4gElementTest {

	public void testGetters() {
		SplitterElement splitter = new SplitterElement();

		List<EventHandlerElement> eventHandlers = splitter.getEventHandlers();
		assertTrue( eventHandlers.size() == 0 );

		List<PresenterElement> presenters = splitter.getPresenters();
		assertTrue( presenters.size() == 0 );

		Map<EventElement, EventAssociation<Integer>> events = splitter.getEvents();
		assertTrue( events.size() == 0 );
	}

}
