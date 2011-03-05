package com.mvp4g.util.config.element;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mvp4g.client.annotation.History.HistoryConverterType;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class HistoryConverterElementTest extends Mvp4gWithServicesElementTest {
	
	private static final String[] properties = { "type" };

	@Test
	public void testDefaultConvertParams() throws DuplicatePropertyNameException{
		HistoryConverterElement element = new HistoryConverterElement();
		assertEquals( element.getType(), HistoryConverterType.DEFAULT.name() );
		
		element.setType( HistoryConverterType.DEFAULT.name() );
		assertEquals( element.getType(), HistoryConverterType.DEFAULT.name() );
		
		element = new HistoryConverterElement();
		element.setType( HistoryConverterType.SIMPLE.name() );
		assertEquals( element.getType(), HistoryConverterType.SIMPLE.name() );
		
		element = new HistoryConverterElement();
		element.setType( HistoryConverterType.NONE.name() );
		assertEquals( element.getType(), HistoryConverterType.NONE.name() );		
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
