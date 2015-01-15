package com.mvp4g.rebind.config.element;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.dev.util.collect.HashMap;

public class SplitterElement extends SimpleMvp4gElement {

	private Set<EventHandlerElement> handlers = new HashSet<EventHandlerElement>();

	private Map<EventElement, EventAssociation<String>> events = new HashMap<EventElement, EventAssociation<String>>();

	public Set<EventHandlerElement> getHandlers() {
		return handlers;
	}

	public Map<EventElement, EventAssociation<String>> getEvents() {
		return events;
	}

	public void setLoader( String loader ) {
		setProperty( "loader", loader );
	}

	public String getLoader() {
		return getProperty( "loader" );
	}

}
