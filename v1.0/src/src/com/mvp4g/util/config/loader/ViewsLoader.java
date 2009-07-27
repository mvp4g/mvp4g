/**
 * 
 */
package com.mvp4g.util.config.loader;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.ViewElement;


/**
 * A class responsible for loading all Views defined in the mvp4g-config.xml file.
 *  
 * @author javier
 *
 */
public class ViewsLoader extends Mvp4gElementLoader<ViewElement> {

	static final String[] REQUIRED_ATTRIBUTES = { "name", "class" };
	static final String[] PARENT_ATTRIBUTES = { "package" };
	
	@SuppressWarnings("unchecked")
	public ViewsLoader(XMLConfiguration xmlConfig) {
		super(xmlConfig.configurationsAt("views.view"));
	}

	@Override
	protected String getElementLabel() {
		return "View";
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
	protected ViewElement newElement() {
		return new ViewElement();
	}
}
