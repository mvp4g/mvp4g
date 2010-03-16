package com.mvp4g.util.config.loader.xml;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.ChildModuleElement;

public class ChildModuleLoaderTest extends AbstractMvp4gElementLoaderTest<ChildModuleElement, ChildModuleLoader> {

	@Override
	protected String getTagName() {
		return "childModule";
	}

	@Override
	protected boolean isSingleNode() {
		return false;
	}

	@Override
	protected ChildModuleLoader newLoader( XMLConfiguration xml ) {
		return new ChildModuleLoader( xml );
	}

}
