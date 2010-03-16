package com.mvp4g.util.config.element;

public class HistoryConverterElementTest extends Mvp4gWithServicesElementTest {

	@Override
	protected String getTag() {
		return "historyConverter";
	}

	@Override
	protected SimpleMvp4gElement newElement() {
		return new HistoryConverterElement();
	}

}
