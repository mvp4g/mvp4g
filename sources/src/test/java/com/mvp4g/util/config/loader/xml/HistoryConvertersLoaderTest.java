package com.mvp4g.util.config.loader.xml;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.HistoryConverterElement;

public class HistoryConvertersLoaderTest extends AbstractMvp4gElementWithServicesLoaderTest<HistoryConverterElement, HistoryConverterLoader> {

	@Override
	protected String getTagName() {
		return "converter";
	}

	@Override
	protected HistoryConverterLoader newLoader( XMLConfiguration xml ) {
		return new HistoryConverterLoader( xml );
	}

	@Override
	protected boolean isSingleNode() {
		return false;
	}

	@Override
	protected String getParentName() {
		return "history";
	}

}
