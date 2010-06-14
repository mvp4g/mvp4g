package com.mvp4g.util.config.element;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class HistoryConverterElementTest extends Mvp4gWithServicesElementTest {
	
	private static final String[] properties = { "convertParams" };

	@Test
	public void testDefaultConvertParams() throws DuplicatePropertyNameException{
		HistoryConverterElement element = new HistoryConverterElement();
		assertTrue( element.isConvertParams() );
		
		element.setConvertParams( Boolean.FALSE.toString() );
		assertEquals( element.getConvertParams(), Boolean.FALSE.toString() );
		assertFalse( element.isConvertParams() );
		
	}
	
	@Override
	protected String getTag() {
		return "historyConverter";
	}

	@Override
	protected SimpleMvp4gElement newElement() {
		return new HistoryConverterElement();
	}
	
	@Override
	protected String[] getProperties() {
		return properties;
	}

}
