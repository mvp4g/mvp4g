package com.mvp4g.util.config.element;

import com.mvp4g.client.event.EventBusWithLookup;

public class EventBusElement {

	private String interfaceClassName = null;
	private String abstractClassName = null;
	private boolean withLookUp = true;

	public EventBusElement( String interfaceClassName, String abstractClassName, boolean withLookUp ) {
		this.interfaceClassName = interfaceClassName;
		this.abstractClassName = abstractClassName;
		this.withLookUp = withLookUp;
	}

	/**
	 * @return the interfaceClassName
	 */
	public String getInterfaceClassName() {
		return interfaceClassName;
	}

	/**
	 * @return the abstractClassName
	 */
	public String getAbstractClassName() {
		return abstractClassName;
	}

	/**
	 * @return the withLookUp
	 */
	public boolean isWithLookUp() {
		return withLookUp;
	}

	public boolean isXml() {
		return EventBusWithLookup.class.getCanonicalName().equals( interfaceClassName );
	}

}
