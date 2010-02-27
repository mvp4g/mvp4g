/**
 * 
 */
package com.mvp4g.util.config.loader.xml;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.ServiceElement;

/**
 * A class responsible for loading all Services defined in the configuration file.
 * 
 * @author javier
 * 
 */
public class ServicesLoader extends Mvp4gElementLoader<ServiceElement> {

	static final String[] REQUIRED_ATTRIBUTES = { "name", "class" };
	static final String[] PARENT_ATTRIBUTES = { "package" };
	static final String[] OPTIONAL_ATTRIBUTES = { "path", "generatedClassName" };

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

}
