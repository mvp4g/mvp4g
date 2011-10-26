package com.mvp4g.util.config.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.dev.util.collect.HashMap;

public class SplitterElement extends SimpleMvp4gElement {

	private List<EventHandlerElement> eventHandlers = new ArrayList<EventHandlerElement>();

	private List<PresenterElement> presenters = new ArrayList<PresenterElement>();

	private Map<EventElement, EventAssociation<Integer>> events = new HashMap<EventElement, EventAssociation<Integer>>();

	private Map<EventElement, List<Integer>> binds = new HashMap<EventElement, List<Integer>>();

	public List<EventHandlerElement> getEventHandlers() {
		return eventHandlers;
	}

	public List<PresenterElement> getPresenters() {
		return presenters;
	}

	public Map<EventElement, EventAssociation<Integer>> getEvents() {
		return events;
	}

	public Map<EventElement, List<Integer>> getBinds() {
		return binds;
	}

}
