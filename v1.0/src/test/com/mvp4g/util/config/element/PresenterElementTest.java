package com.mvp4g.util.config.element;

public class PresenterElementTest extends Mvp4gWithServicesElementTest {

	private static final String[] properties = SimpleMvp4gElementTest.addProperties( new String[] { "view" } );

	@Override
	protected String[] getProperties() {
		return properties;
	}

	@Override
	protected String getTag() {
		return "presenter";
	}

	@Override
	protected SimpleMvp4gElement newElement() {
		return new PresenterElement();
	}

}
