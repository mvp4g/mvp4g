package com.mvp4g.util.config.loader;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.StartElement;

public class StartLoaderTest extends AbstractMvp4gElementLoaderTest<StartElement, StartLoader> {

	@Override
	protected String getTagName() {
		return "start";
	}

	@Override
	protected boolean isSingleNode() {
		return true;
	}

	@Override
	protected StartLoader newLoader( XMLConfiguration xml ) {
		return new StartLoader( xml );
	}

}
