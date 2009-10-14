/**
 * 
 */
package com.mvp4g.util.config.loader;

import java.util.Set;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.HistoryConverterElement;

/**
 * A class responsible for loading all History Converters defined in the mvp4g-config.xml file.
 * 
 * @author plcoirier
 * 
 */
public class HistoryConverterLoader extends Mvp4gElementLoader<HistoryConverterElement> {

	static final String[] REQUIRED_ATTRIBUTES = { "name", "class" };
	static final String[] PARENT_ATTRIBUTES = { "package" };
	static final String[] OPTIONAL_MULTI_VALUE_ATTRIBUTES = { "services" };

	@SuppressWarnings( "unchecked" )
	public HistoryConverterLoader( XMLConfiguration xmlConfig ) {
		super( xmlConfig.configurationsAt( "history.converter" ) );
	}
	
	/**
	 * Optionally loads any History Converters elements if present in the configuration.</p>
	 * 
	 * @return a set of history converter elements or an empty set if no elements were found.
	 */
	@Override
	public Set<HistoryConverterElement> loadElements() {
		return loadExistingElements();
	}

	@Override
	protected String getElementLabel() {
		return "History Converter";
	}

	@Override
	protected String[] getRequiredAttributeNames() {
		return REQUIRED_ATTRIBUTES;
	}

	@Override
	protected String[] getParentAttributeNames() {
		return PARENT_ATTRIBUTES;
	}

	@Override
	protected String[] getOptionalMultiValueAttributeNames() {
		return OPTIONAL_MULTI_VALUE_ATTRIBUTES;
	}

	@Override
	protected HistoryConverterElement newElement() {
		return new HistoryConverterElement();
	}

}
