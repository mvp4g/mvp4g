/**
 * 
 */
package com.mvp4g.util.config.loader.xml;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.EventElement;

/**
 * A class responsible for loading all Events defined in the configuration file.
 * 
 * @author javier
 * 
 */
public class EventsLoader extends Mvp4gElementLoader<EventElement> {

	static final String[] REQUIRED_ATTRIBUTES = { "type" };
	static final String[] OPTIONAL_ATTRIBUTES = { "calledMethod", "eventObjectClass", "history", "forwardToParent" };
	static final String[] OPTIONAL_MULTI_VALUE_ATTRIBUTES = { "handlers", "modulesToLoad" };

	@SuppressWarnings( "unchecked" )
	public EventsLoader( XMLConfiguration xmlConfig ) {
		super( xmlConfig.configurationsAt( "events.event" ) );
	}

	@Override
	protected String getElementLabel() {
		return "Event";
	}

	@Override
	protected String[] getRequiredAttributeNames() {
		return REQUIRED_ATTRIBUTES;
	}

	@Override
	protected String[] getOptionalAttributeNames() {
		return OPTIONAL_ATTRIBUTES;
	}

	@Override
	protected String[] getOptionalMultiValueAttributeNames() {
		return OPTIONAL_MULTI_VALUE_ATTRIBUTES;
	}

	@Override
	protected EventElement newElement() {
		return new EventElement();
	}

}
