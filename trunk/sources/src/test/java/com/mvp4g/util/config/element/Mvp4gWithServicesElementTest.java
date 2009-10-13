package com.mvp4g.util.config.element;

public class Mvp4gWithServicesElementTest extends SimpleMvp4gElementTest {
	
	private static final String[] values = { "services" };
	
	@Override
	protected String[] getValues() {
		return values;
	}
	
	@Override
	protected String getTag() {
		return "withServices";
	}
	
	@Override
	protected SimpleMvp4gElement newElement() {
		return new Mvp4gWithServicesElement();
	}

}
