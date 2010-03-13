package com.mvp4g.util.config.loader.xml;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.ServiceElement;

public class ServicesLoaderTest extends AbstractMvp4gElementLoaderTest<ServiceElement, ServicesLoader> {

	@Override
	protected String getTagName() {
		return "service";
	}

	@Override
	protected boolean isSingleNode() {
		return false;
	}

	@Override
	protected ServicesLoader newLoader( XMLConfiguration xml ) {
		return new ServicesLoader( xml );
	}

}
