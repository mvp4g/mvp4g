/**
 * 
 */
package com.mvp4g.util.config.loader;

import java.util.Set;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.ServiceElement;

/**
 * A class responsible for loading all Services defined in the mvp4g-config.xml file.
 * 
 * @author javier
 * 
 */
public class ServicesLoader extends Mvp4gElementLoader<ServiceElement> {

	static final String[] REQUIRED_ATTRIBUTES = { "name", "class" };
	static final String[] PARENT_ATTRIBUTES = { "package" };
	static final String[] OPTIONAL_ATTRIBUTES = { "path" };

	@SuppressWarnings( "unchecked" )
	public ServicesLoader( XMLConfiguration xmlConfig ) {
		super( xmlConfig.configurationsAt( "services.service" ) );
	}

	@Override
	protected String getElementLabel() {
		return "Service";
	}

	@Override
	protected String[] getRequiredAttributeNames() {
		return REQUIRED_ATTRIBUTES;
	}

	@Override
	protected ServiceElement newElement() {
		return new ServiceElement();
	}

	@Override
	protected String[] getParentAttributeNames() {
		return PARENT_ATTRIBUTES;
	}
	
	@Override
	String[] getOptionalAttributeNames() {
		return OPTIONAL_ATTRIBUTES;
	}

	/**
	 * Optionally loads any Service elements if present in the configuration.</p>
	 * 
	 * @return a set of service elements or an empty set if no elements were found.
	 */
	@Override
	public Set<ServiceElement> loadElements() {
		return loadExistingElements();
	}
}
