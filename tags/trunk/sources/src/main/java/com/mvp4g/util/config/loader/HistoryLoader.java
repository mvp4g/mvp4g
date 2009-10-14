/**
 * 
 */
package com.mvp4g.util.config.loader;

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.HistoryElement;

/**
 * A class responsible for loading the Start tag defined in the mvp4g-config.xml file.
 * <p/>
 * 
 * @author javier
 * 
 */
public class HistoryLoader extends Mvp4gElementLoader<HistoryElement> {

	static final String[] REQUIRED_ATTRIBUTES = { "initEvent" };
	
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
	protected HistoryElement newElement() {
		return new HistoryElement();
	}
	
	/**
	 * Optionally loads History element if present in the configuration.</p>
	 * 
	 * @return a set of history elements or an empty set if no elements were found.
	 */
	@Override
	public Set<HistoryElement> loadElements() {
		return loadExistingElements();
	}

	public HistoryElement loadElement() {
		Set<HistoryElement> elements = loadElements();
		return (elements.size() == 0) ? null : new ArrayList<HistoryElement>( elements ).get( 0 );
	}
}
