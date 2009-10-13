package com.mvp4g.util.config.loader;

import static org.junit.Assert.assertEquals;

import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Test;

import com.mvp4g.util.config.element.HistoryConverterElement;

public class HistoryConvertersLoaderTest extends AbstractMvp4gElementLoaderTest<HistoryConverterElement, HistoryConverterLoader> {

	@Test
	public void testEmptyElement() {
		assertEquals( 0, basicLoader.loadElements().size() );
	}

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
