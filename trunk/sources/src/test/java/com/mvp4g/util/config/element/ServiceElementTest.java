package com.mvp4g.util.config.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ServiceElementTest extends SimpleMvp4gElementTest {

	private static final String[] properties = SimpleMvp4gElementTest.addProperties( new String[] { "path" } );

	@Test
	public void testHasPath() {
		ServiceElement serviceElement = (ServiceElement)element;
		assertFalse( serviceElement.hasPath() );
		serviceElement.setPath( "path" );
		assertTrue( serviceElement.hasPath() );
	}

	@Test
	public void testSetGeneratedClassName() {
		String className = "Test";
		ServiceElement serviceElement = (ServiceElement)element;
		serviceElement.setGeneratedClassName( className );
		assertEquals( element.getProperty( "generatedClassName" ), className );
	}

	@Test
	public void testDefaultGeneratedClassName() {
		String className = "com.Test";
		ServiceElement serviceElement = (ServiceElement)element;
		serviceElement.setClassName( className );
		assertEquals( serviceElement.getGeneratedClassName(), className + "Async" );
	}

	@Test
	public void testGetGeneratedClassName() {
		String packageName = "com.test";
		String className = "Test";
		ServiceElement serviceElement = (ServiceElement)element;
		serviceElement.setProperty( "package", packageName );
		serviceElement.setProperty( "generatedClassName", className );
		assertEquals( serviceElement.getGeneratedClassName(), new ClassResolver().getClassNameFrom( packageName, className ) );
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
