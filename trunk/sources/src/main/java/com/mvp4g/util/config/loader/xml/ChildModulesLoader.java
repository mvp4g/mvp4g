/**
 * 
 */
package com.mvp4g.util.config.loader.xml;

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.ChildModulesElement;
import com.mvp4g.util.exception.loader.Mvp4gXmlException;

/**
 * A class responsible for loading the Start tag defined in the configuration file.
 * <p/>
 * 
 * @author javier
 * 
 */
public class ChildModulesLoader extends Mvp4gElementLoader<ChildModulesElement> {

	static final String[] REQUIRED_ATTRIBUTES = {};
	static final String[] OPTIONAL_ATTRIBUTES = { "errorEvent", "beforeEvent", "afterEvent" };

	@SuppressWarnings( "unchecked" )
	public ChildModulesLoader( XMLConfiguration xmlConfig ) {
		super( xmlConfig.configurationsAt( "childModules" ) );
	}

	@Override
	protected String getElementLabel() {
		return "ChildModules";
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
	protected ChildModulesElement newElement() {
		return new ChildModulesElement();
	}

	public ChildModulesElement loadElement() throws Mvp4gXmlException {
		Set<ChildModulesElement> elements = super.loadElements();
		return (elements.size() == 0) ? null : new ArrayList<ChildModulesElement>( elements ).get( 0 );
	}
}
