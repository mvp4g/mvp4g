package com.mvp4g.util.config.element;

public class ViewElementTest extends SimpleMvp4gElementTest {

	@Override
	protected String getTag() {
		return "view";
	}

	@Override
	protected SimpleMvp4gElement newElement() {
		return new ViewElement();
	}

}
