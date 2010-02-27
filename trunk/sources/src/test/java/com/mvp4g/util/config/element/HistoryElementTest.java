package com.mvp4g.util.config.element;

import static org.junit.Assert.*;
import org.junit.Test;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class HistoryElementTest extends AbstractMvp4gElementTest<HistoryElement> {

	protected static final String[] properties = { "initEvent", "notFoundEvent" };

	@Test
	public void testGetNotFoundEventWhenNotSet() throws DuplicatePropertyNameException {
		String initEvent = "initEvent";
		element.setInitEvent( initEvent );
		assertEquals( initEvent, element.getNotFoundEvent() );
	}

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
