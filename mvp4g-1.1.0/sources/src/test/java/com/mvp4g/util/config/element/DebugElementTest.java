package com.mvp4g.util.config.element;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class DebugElementTest extends AbstractMvp4gElementTest<DebugElement> {

	protected static final String[] properties = { "enabled" };

	@Test
	public void testIsEnabled() throws DuplicatePropertyNameException {
		DebugElement childModuleElement = new DebugElement();
		assertFalse( childModuleElement.isEnabled() );
		childModuleElement.setEnabled( "true" );
		assertTrue( childModuleElement.isEnabled() );

		childModuleElement = new DebugElement();
		childModuleElement.setEnabled( "false" );
		assertFalse( childModuleElement.isEnabled() );

		childModuleElement = new DebugElement();
		childModuleElement.setEnabled( "123" );
		assertFalse( childModuleElement.isEnabled() );
	}

	@Override
	protected String[] getProperties() {
		return properties;
	}

	@Override
	protected String getTag() {
		return "debug";
	}

	@Override
	protected String getUniqueIdentifierName() {
		return DebugElement.class.getName();
	}

	@Override
	protected DebugElement newElement() {
		return new DebugElement();
	}

}
