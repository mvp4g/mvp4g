/**
 * 
 */
package com.mvp4g.util.config.loader;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.PresenterElement;

/**
 * A class responsible for loading all Presenters defined in the mvp4g-config.xml file.
 * 
 * @author javier
 * 
 */
public class PresentersLoader extends Mvp4gElementLoader<PresenterElement> {

	static final String[] REQUIRED_ATTRIBUTES = { "name", "class", "view" };
	static final String[] PARENT_ATTRIBUTES = { "package" };
	static final String[] OPTIONAL_MULTI_VALUE_ATTRIBUTES = { "services" };

	@SuppressWarnings( "unchecked" )
	public PresentersLoader( XMLConfiguration xmlConfig ) {
		super( xmlConfig.configurationsAt( "presenters.presenter" ) );
	}

	@Override
	protected String getElementLabel() {
		return "Presenter";
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
	protected PresenterElement newElement() {
		return new PresenterElement();
	}

}
