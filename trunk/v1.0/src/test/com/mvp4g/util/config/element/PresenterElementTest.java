package com.mvp4g.util.config.element;

public class PresenterElementTest extends SimpleMvp4gElementTest {

	private static final String[] properties = SimpleMvp4gElementTest.addProperties( new String[] { "view" } );
	private static final String[] values = { "services" };

	@Override
	protected String[] getProperties() {
		return properties;
	}

	@Override
	protected String getTag() {
		return "presenter";
	}

	@Override
	protected String[] getValues() {
		return values;
	}

	@Override
	protected SimpleMvp4gElement newElement() {
		return new PresenterElement();
	}

}
