package com.mvp4g.util.config.loader;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.EventElement;

public class EventsLoaderTest extends AbstractMvp4gElementLoaderTest<EventElement, EventsLoader> {

	@Override
	protected String getTagName() {
		return "event";
	}

	@Override
	protected EventsLoader newLoader( XMLConfiguration xml ) {
		return new EventsLoader( xml );
	}

	@Override
	protected boolean isSingleNode() {
		return false;
	}

}
