package com.mvp4g.util.config.element;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ClassResolverTest {

	private ClassResolver resolver;

	@Before
	public void setUp() {
		resolver = new ClassResolver();
	}

	@Test
	public void testNullPackageAndFullClassName() {
		String generatedName = resolver.getClassNameFrom( null, "com.mvp4g.foo.SimpleClass" );
		assertEquals( "com.mvp4g.foo.SimpleClass", generatedName );
	}

	@Test
	public void testEmptyPackageAndFullClassName() {
		String generatedName = resolver.getClassNameFrom( "", "com.mvp4g.foo.SimpleClass" );
		assertEquals( "com.mvp4g.foo.SimpleClass", generatedName );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testNullPackageAndSimpleClassName() {
		resolver.getClassNameFrom( null, "SimpleClass" );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testEmptyPackageAndSimpleClassName() {
		resolver.getClassNameFrom( "", "SimpleClass" );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testNullPackageAndNullClassName() {
		resolver.getClassNameFrom( null, null );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testNullPackageAndEmptyClassName() {
		resolver.getClassNameFrom( null, "" );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testEmptyPackageAndNullClassName() {
		resolver.getClassNameFrom( "", null );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testEmptyPackageAndEmptyClassName() {
		resolver.getClassNameFrom( "", "" );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testPackageAndNullClassName() {
		resolver.getClassNameFrom( "com.mvp4g.foo", null );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testPackageAndEmptyClassName() {
		resolver.getClassNameFrom( "com.mvp4g.foo", "" );
	}

	@Test
	public void testPackageAndSimpleClassName() {
		String generatedName = resolver.getClassNameFrom( "com.mvp4g.foo", "SimpleClass" );
		assertEquals( "com.mvp4g.foo.SimpleClass", generatedName );
	}

	@Test
	public void testPackageMatchingFullClassName() {
		String generatedName = resolver.getClassNameFrom( "com.mvp4g.foo", "com.mvp4g.foo.SimpleClass" );
		assertEquals( "com.mvp4g.foo.SimpleClass", generatedName );
	}

	@Test
	public void testPackageNotMatchingFullClassName() {
		String generatedName = resolver.getClassNameFrom( "com.mvp4g.foo", "com.bar.SimpleClass" );
		assertEquals( "com.bar.SimpleClass", generatedName );
	}
}
