package com.mvp4g.util.config.element;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class ServiceElementTest extends SimpleMvp4gElementTest {

	private static final String[] properties = SimpleMvp4gElementTest.addProperties( new String[] { "path" } );

	@Test
	public void testHasPath() throws DuplicatePropertyNameException{
		ServiceElement serviceElement = (ServiceElement)element;
		assertFalse( serviceElement.hasPath() );
		serviceElement.setPath( "path" );
		assertTrue( serviceElement.hasPath() );
	}

	@Override
	protected String[] getProperties() {
		return properties;
	}

	@Override
	protected String getTag() {
		return "service";
	}

	@Override
	protected SimpleMvp4gElement newElement() {
		return new ServiceElement();
	}

}
