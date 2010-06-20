package com.mvp4g.util.config.loader.xml;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.EventHandlerElement;

public class EventHandlersLoaderTest extends AbstractMvp4gElementWithServicesLoaderTest<EventHandlerElement, EventHandlersLoader> {

	@Override
	protected String getTagName() {
		return "eventHandler";
	}

	@Override
	protected boolean isSingleNode() {
		return false;
	}

	@Override
	protected EventHandlersLoader newLoader( XMLConfiguration xml ) {
		return new EventHandlersLoader( xml );
	}

}
