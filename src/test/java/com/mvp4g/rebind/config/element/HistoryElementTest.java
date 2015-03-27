package com.mvp4g.rebind.config.element;


public class HistoryElementTest extends AbstractMvp4gElementTest<HistoryElement> {

	protected static final String[] properties = { "initEvent", "notFoundEvent", "placeServiceClass" };

	@Override
	protected String[] getProperties() {
		return properties;
	}

	@Override
	protected String getTag() {
		return "history";
	}

	@Override
	protected String getUniqueIdentifierName() {
		return HistoryElement.class.getName();
	}

	@Override
	protected HistoryElement newElement() {
		return new HistoryElement();
	}

}
