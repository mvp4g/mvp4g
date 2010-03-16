package com.mvp4g.util.config.loader.xml;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.loader.xml.PresentersLoader;

public class PresentersLoaderTest extends AbstractMvp4gElementLoaderTest<PresenterElement, PresentersLoader> {

	@Override
	protected String getTagName() {
		return "presenter";
	}

	@Override
	protected boolean isSingleNode() {
		return false;
	}

	@Override
	protected PresentersLoader newLoader( XMLConfiguration xml ) {
		return new PresentersLoader( xml );
	}

}
