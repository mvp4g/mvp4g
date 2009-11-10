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
	 * @param interfaceClassName
	 *            the interfaceClassName to set
	 */
	public void setInterfaceClassName( String interfaceClassName ) {
		this.interfaceClassName = interfaceClassName;
	}

	/**
	 * @return the abstractClassName
	 */
	public String getAbstractClassName() {
		return abstractClassName;
	}

	/**
	 * @param abstractClassName
	 *            the abstractClassName to set
	 */
	public void setAbstractClassName( String abstractClassName ) {
		this.abstractClassName = abstractClassName;
	}

	/**
	 * @return the withLookUp
	 */
	public boolean isWithLookUp() {
		return withLookUp;
	}

	/**
	 * @param withLookUp
	 *            the withLookUp to set
	 */
	public void setWithLookUp( boolean withLookUp ) {
		this.withLookUp = withLookUp;
	}

	public boolean isXml() {
		return EventBusWithLookup.class.getName().equals( interfaceClassName );
	}

}
