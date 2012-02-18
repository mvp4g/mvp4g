package com.mvp4g.util.config.element;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ViewElementTest extends SimpleMvp4gElementTest {

	@Test
	public void testInstantiateAtStartGetterSetter() {
		ViewElement element = new ViewElement();
		assertFalse( element.isInstantiateAtStart() );
		element.setInstantiateAtStart( true );
		assertTrue( element.isInstantiateAtStart() );
		element.setInstantiateAtStart( false );
		assertFalse( element.isInstantiateAtStart() );
	}

	@Override
	protected String getTag() {
		return "view";
	}

	@Override
	protected SimpleMvp4gElement newElement() {
		return new ViewElement();
	}

}
