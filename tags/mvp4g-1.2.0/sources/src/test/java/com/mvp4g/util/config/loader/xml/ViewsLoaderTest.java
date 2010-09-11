package com.mvp4g.util.config.loader.xml;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.config.loader.xml.ViewsLoader;

public class ViewsLoaderTest extends AbstractMvp4gElementLoaderTest<ViewElement, ViewsLoader> {

	@Override
	protected String getTagName() {
		return "view";
	}

	@Override
	protected boolean isSingleNode() {
		return false;
	}

	@Override
	protected ViewsLoader newLoader( XMLConfiguration xml ) {
		return new ViewsLoader( xml );
	}

}
