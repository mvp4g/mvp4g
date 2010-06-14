package com.mvp4g.util.config.element;


public class GinModuleElementTest extends AbstractMvp4gElementTest<GinModuleElement> {

	protected static final String[] properties = { "className" };

	@Override
	protected String getTag() {
		return "gin";
	}

	@Override
	protected String getUniqueIdentifierName() {
		return GinModuleElement.class.getName();
	}

	@Override
	protected GinModuleElement newElement() {
		return new GinModuleElement();
	}

	@Override
	protected String[] getProperties() {
		return properties;
	}

}
