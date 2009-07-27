/**
 * 
 */
package com.mvp4g.util.config.loader;

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.StartElement;

/**
 * A class responsible for loading the Start tag defined in the mvp4g-config.xml file.<p/>
 * 
 * @author javier
 *
 */
public class StartLoader extends Mvp4gElementLoader<StartElement> {

	static final String[] REQUIRED_ATTRIBUTES = { "view" };
	static final String[] OPTIONAL_ATTRIBUTES = { "eventType" };

	
	@SuppressWarnings("unchecked")
	public StartLoader(XMLConfiguration xmlConfig) {
		super(xmlConfig.configurationsAt("start"));
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

	public StartElement loadElement() {
		Set<StartElement> elements = super.loadElements();
		return new ArrayList<StartElement>( elements ).get(0);
	}
}
