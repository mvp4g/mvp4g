package com.mvp4g.util.config.loader.xml;

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.DebugElement;
import com.mvp4g.util.exception.loader.Mvp4gXmlException;

public class DebugLoader extends Mvp4gElementLoader<DebugElement> {

	static final String[] REQUIRED_ATTRIBUTES = { "enabled" };

	@SuppressWarnings( "unchecked" )
	public DebugLoader( XMLConfiguration xmlConfig ) {
		super( xmlConfig.configurationsAt( "debug" ) );
	}

	@Override
	String getElementLabel() {
		return "Debug";
	}

	@Override
	String[] getRequiredAttributeNames() {
		return REQUIRED_ATTRIBUTES;
	}

	@Override
	protected DebugElement newElement() {
		return new DebugElement();
	}

	public DebugElement loadElement() throws Mvp4gXmlException {
		Set<DebugElement> elements = super.loadElements();
		return ( elements.size() == 0 ) ? null : new ArrayList<DebugElement>( elements ).get( 0 );
	}

}
