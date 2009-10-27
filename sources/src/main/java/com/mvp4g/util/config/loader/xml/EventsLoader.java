/**
 * 
 */
package com.mvp4g.util.config.loader.xml;

import java.util.Set;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;

/**
 * A class responsible for loading all Events defined in the mvp4g-config.xml file.
 * 
 * @author javier
 * 
 */
public class EventsLoader extends Mvp4gElementLoader<EventElement> {

	static final String[] REQUIRED_ATTRIBUTES = { "type" };
	static final String[] OPTIONAL_ATTRIBUTES = { "calledMethod", "eventObjectClass", "history" };
	static final String[] MULTI_VALUE_ATTRIBUTES = { "handlers" };

	@SuppressWarnings( "unchecked" )
	public EventsLoader( XMLConfiguration xmlConfig ) {
		super( xmlConfig.configurationsAt( "events.event" ) );
	}
	
	/**
	 * Optionally loads History element if present in the configuration.</p>
	 * 
	 * @return a set of history elements or an empty set if no elements were found.
	 */
	@Override
	public Set<EventElement> loadElements() {
		checkForNonEmptyElements();
		
		return loadExistingElements();
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
	protected String[] getMultiValueAttributeNames() {
		return MULTI_VALUE_ATTRIBUTES;
	}

	@Override
	protected EventElement newElement() {
		return new EventElement();
	}
	
	private void checkForNonEmptyElements() throws InvalidMvp4gConfigurationException {
		if ( elements.isEmpty() ) {
			String err = "No " + getElementLabel() + " elements found in configuration file.";
			throw new InvalidMvp4gConfigurationException( err );
		}
	}

}
