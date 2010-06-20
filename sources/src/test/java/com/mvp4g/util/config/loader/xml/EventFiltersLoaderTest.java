package com.mvp4g.util.config.loader.xml;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.EventFilterElement;

public class EventFiltersLoaderTest extends AbstractMvp4gElementLoaderTest<EventFilterElement, EventFiltersLoader> {

	@Override
	protected String getTagName() {
		return "filter";
	}

	@Override
	protected boolean isSingleNode() {
		return false;
	}

	@Override
	protected EventFiltersLoader newLoader( XMLConfiguration xml ) {
		return new EventFiltersLoader( xml );
	}

}
