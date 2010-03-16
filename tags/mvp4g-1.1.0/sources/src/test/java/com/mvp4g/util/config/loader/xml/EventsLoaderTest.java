package com.mvp4g.util.config.loader.xml;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.loader.xml.EventsLoader;

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
