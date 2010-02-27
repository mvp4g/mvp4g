/**
 * 
 */
package com.mvp4g.util.config.loader.xml;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.ChildModuleElement;

/**
 * A class responsible for loading all Services defined in the configuration file.
 * 
 * @author javier
 * 
 */
public class ChildModuleLoader extends Mvp4gElementLoader<ChildModuleElement> {

	static final String[] REQUIRED_ATTRIBUTES = { "name", "class" };
	static final String[] PARENT_ATTRIBUTES = { "package" };
	static final String[] OPTIONAL_ATTRIBUTES = { "async", "autoLoad", "eventToDisplayView" };

	@SuppressWarnings( "unchecked" )
	public ChildModuleLoader( XMLConfiguration xmlConfig ) {
		super( xmlConfig.configurationsAt( "childModules.childModule" ) );
	}

	@Override
	protected String getElementLabel() {
		return "ChildModule";
	}

	@Override
	protected String[] getRequiredAttributeNames() {
		return REQUIRED_ATTRIBUTES;
	}

	@Override
	protected ChildModuleElement newElement() {
		return new ChildModuleElement();
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
