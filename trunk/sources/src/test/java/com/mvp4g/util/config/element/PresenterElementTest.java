package com.mvp4g.util.config.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PresenterElementTest extends Mvp4gWithServicesElementTest {

	private static final String[] properties = SimpleMvp4gElementTest.addProperties( new String[] { "view", "multiple" } );

	@Override
	protected String[] getProperties() {
		return properties;
	}

	@Override
	protected String getTag() {
		return "presenter";
	}

	@Override
	protected SimpleMvp4gElement newElement() {
		return new PresenterElement();
	}

	@Test
	public void testInverseViewTrue() {
		String test = "true";
		PresenterElement presenterElement = (PresenterElement)element;
		presenterElement.setInverseView( test );
		assertEquals( test, presenterElement.getInverseView() );
		assertTrue( presenterElement.hasInverseView() );
	}

	@Test
	public void testInverseViewFalse() {
		String test = "false";
		PresenterElement presenterElement = (PresenterElement)element;
		presenterElement.setInverseView( test );
		assertEquals( test, presenterElement.getInverseView() );
		assertFalse( presenterElement.hasInverseView() );
	}

}
