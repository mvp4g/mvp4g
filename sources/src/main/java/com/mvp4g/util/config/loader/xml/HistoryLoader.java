/**
 * 
 */
package com.mvp4g.util.config.loader.xml;

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.HistoryElement;
import com.mvp4g.util.exception.loader.Mvp4gXmlException;

/**
 * A class responsible for loading the History tag defined in the configuration file.
 * <p/>
 * 
 * @author plcoirier
 * 
 */
public class HistoryLoader extends Mvp4gElementLoader<HistoryElement> {

	static final String[] REQUIRED_ATTRIBUTES = {};
	static final String[] OPTIONAL_ATTRIBUTES = { "initEvent", "notFoundEvent" };

	@SuppressWarnings( "unchecked" )
	public HistoryLoader( XMLConfiguration xmlConfig ) {
		super( xmlConfig.configurationsAt( "history" ) );
	}

	@Override
	protected String getElementLabel() {
		return "History";
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
	protected HistoryElement newElement() {
		return new HistoryElement();
	}

	public HistoryElement loadElement() throws Mvp4gXmlException {
		Set<HistoryElement> elements = loadElements();
		return ( elements.size() == 0 ) ? null : new ArrayList<HistoryElement>( elements ).get( 0 );
	}
}
