package com.mvp4g.util.config.element;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class HistoryElementTest extends AbstractMvp4gElementTest<HistoryElement> {

	protected static final String[] properties = { "initEvent", "notFoundEvent", "paramSeparator", "paramSeparatorAlwaysAdded" };

	@Test
	public void testGetNotFoundEventWhenNotSet() throws DuplicatePropertyNameException {
		String initEvent = "initEvent";
		element.setInitEvent( initEvent );
		assertEquals( initEvent, element.getNotFoundEvent() );
	}

	@Test
	public void testMultiple() throws DuplicatePropertyNameException {
		HistoryElement element = new HistoryElement();
		assertFalse( element.isParamSeparatorAlwaysAdded() );
		element.setParamSeparatorAlwaysAdded( "true" );
		assertTrue( element.isParamSeparatorAlwaysAdded() );

		element = new HistoryElement();
		element.setParamSeparatorAlwaysAdded( "false" );
		assertFalse( element.isParamSeparatorAlwaysAdded() );
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
