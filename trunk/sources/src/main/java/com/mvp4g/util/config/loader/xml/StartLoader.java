/**
 * 
 */
package com.mvp4g.util.config.loader.xml;

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.exception.loader.Mvp4gXmlException;

/**
 * A class responsible for loading the Start tag defined in the configuration file.
 * <p/>
 * 
 * @author javier
 * 
 */
public class StartLoader extends Mvp4gElementLoader<StartElement> {

	static final String[] REQUIRED_ATTRIBUTES = { "view" };
	static final String[] OPTIONAL_ATTRIBUTES = { "eventType", "history" };

	@SuppressWarnings( "unchecked" )
	public StartLoader( XMLConfiguration xmlConfig ) {
		super( xmlConfig.configurationsAt( "start" ) );
	}

	@Override
	protected String getElementLabel() {
		return "Start";
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
	protected StartElement newElement() {
		return new StartElement();
	}

	public StartElement loadElement() throws Mvp4gXmlException {
		Set<StartElement> elements = super.loadElements();
		return ( elements.size() == 0 ) ? null : new ArrayList<StartElement>( elements ).get( 0 );
	}
}
