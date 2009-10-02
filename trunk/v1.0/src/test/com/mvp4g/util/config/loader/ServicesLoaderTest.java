package com.mvp4g.util.config.loader;

import static org.junit.Assert.*;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Test;

import com.mvp4g.util.config.element.ServiceElement;

public class ServicesLoaderTest extends AbstractMvp4gElementLoaderTest<ServiceElement, ServicesLoader> {

	@Test
	public void testEmptyElement() {
		assertEquals( 0, basicLoader.loadElements().size() );		
	}

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
