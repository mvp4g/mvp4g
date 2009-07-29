package com.mvp4g.util.config.element;

public class ServiceElementTest extends SimpleMvp4gElementTest {

	@Override
	protected String getTag() {
		return "service";
	}

	@Override
	protected SimpleMvp4gElement newElement() {
		return new PresenterElement();
	}

}
