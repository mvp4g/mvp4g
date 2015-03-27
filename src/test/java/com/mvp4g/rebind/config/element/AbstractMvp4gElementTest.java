package com.mvp4g.rebind.config.element;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public abstract class AbstractMvp4gElementTest<T extends Mvp4gElement> {

	protected T element;

	@Before
	public void setUp() {
		element = newElement();
	}

	@Test
	public void testGetTag() {
		assertEquals( getTag(), element.getTagName() );
	}

	@Test
	public void testUniqueIdentifierName() {
		assertEquals( getUniqueIdentifierName(), element.getUniqueIdentifierName() );
	}

	@Test
	public void testEquals() {
		T same = newElement();
		T different = newElement();
		element.setProperty( element.getUniqueIdentifierName(), "id1" );
		same.setProperty( element.getUniqueIdentifierName(), "id1" );
		different.setProperty( element.getUniqueIdentifierName(), "id2" );
		assertEquals( element, same );
		assertFalse( element.equals( different ) );
		assertFalse( element.equals( new Object() ) );

	}

	@Test
	public void testSetAndGetProperties() {
		assertPropertiesSize( 0 );
		assertSetProperty( "first", "first value" );
		assertSetProperty( "second", "second value" );
		assertPropertiesSize( 2 );
	}

	@Test
	public void testSetAndGetValues() {
		String[] english = { "one", "two", "three" };
		String[] french = { "un", "deux", "trois" };
		assertMultiValuesSize( 0 );
		assertSetMultiValues( "english", english );
		assertSetMultiValues( "values", french );
		assertMultiValuesSize( 2 );
	}

	@Test
	public void testGetValuesWhenNameNotFound() {
		assertArrayEquals( null, element.getValues( "nonExistent" ) );
	}

	@Test
	public void testSetValuesWithEmptyArray() {
		String[] emptyArray = { "" };
		element.setValues( "test", emptyArray );
		assertArrayEquals( new String[] {}, element.getValues( "test" ) );
	}

	@Test
	public void testHashCode() {
		element.setProperty( getUniqueIdentifierName(), "test" );
		assertEquals( element.hashCode(), element.getUniqueIdentifier().hashCode() );
	}

	@Test
	public void testEmptyProperties() {
		assertEquals( "", element.getUniqueIdentifier() );
	}

	/**
	 * For each element, test each property getter and setter specific to this loader.<br>
	 * <br>
	 * For example, verify that getName() == getProperty("name")<br>
	 * and setName(name) == setProperty("name", name)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPropertiesSetterAndGetter() throws Exception {

		String val = "val";

		String[] properties = getProperties();
		String setMethodName = null;
		String getMethodName = null;
		Method getter = null;
		Method setter = null;

		Object[] noArg = new Object[0];
		Class<?>[] noParam = new Class[0];

		for ( String property : properties ) {
			setMethodName = "set" + property.substring( 0, 1 ).toUpperCase() + property.substring( 1 );
			getMethodName = "get" + property.substring( 0, 1 ).toUpperCase() + property.substring( 1 );

			getter = element.getClass().getMethod( getMethodName, noParam );
			setter = element.getClass().getMethod( setMethodName, String.class );

			setter.invoke( element, val );
			assertEquals( val, getter.invoke( element, noArg ) );
			assertEquals( val, element.getProperty( property ) );

		}
	}

	/**
	 * For each element, test each value getter and setter specific to this loader.<br>
	 * <br>
	 * For example, verify that getHandlers() == getProperty("handlers")<br>
	 * and setHandlers(handlers) == setProperty("handlers", handlers)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testValuesSetterAndGetter() throws Exception {

		String[] val = { "val" };

		String[] values = getValues();

		String setMethodName = null;
		String getMethodName = null;
		Method getter = null;
		Method setter = null;

		Object[] noArg = new Object[0];
		Class<?>[] noParam = new Class[0];

		for ( String value : values ) {
			setMethodName = "set" + value.substring( 0, 1 ).toUpperCase() + value.substring( 1 );
			getMethodName = "get" + value.substring( 0, 1 ).toUpperCase() + value.substring( 1 );

			getter = element.getClass().getMethod( getMethodName, noParam );
			setter = element.getClass().getMethod( setMethodName, String[].class );

			setter.invoke( element, (Object)val );
			assertArrayEquals( val, (String[])getter.invoke( element, noArg ) );
			assertArrayEquals( val, element.getValues( value ) );

		}
	}

	/**
	 * For each element, test each value getter and setter specific to this loader.<br>
	 * <br>
	 * For example, verify that getHandlers() == getProperty("handlers")<br>
	 * and setHandlers(handlers) == setProperty("handlers", handlers)
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings( "unchecked" )
	@Test
	public void testFlexibleValuesSetterAndGetter() throws Exception {

		String[] val = { "val" };

		List<String> valList = Arrays.asList( val );

		String[] flexibleValues = getFlexibleValues();

		String setMethodName = null;
		String getMethodName = null;
		Method getter = null;
		Method setter = null;

		Object[] noArg = new Object[0];
		Class<?>[] noParam = new Class[0];

		for ( String value : flexibleValues ) {
			setMethodName = "set" + value.substring( 0, 1 ).toUpperCase() + value.substring( 1 );
			getMethodName = "get" + value.substring( 0, 1 ).toUpperCase() + value.substring( 1 );

			getter = element.getClass().getMethod( getMethodName, noParam );
			setter = element.getClass().getMethod( setMethodName, String[].class );

			setter.invoke( element, (Object)val );
			assertListEquals( valList, (List<String>)getter.invoke( element, noArg ) );
			assertListEquals( valList, element.getFlexibleValues( value ) );

		}
	}

	private void assertPropertiesSize( int expectedSize ) {
		assertEquals( expectedSize, element.totalProperties() );
	}

	private void assertSetProperty( String name, String value ) {
		assertNull( element.getProperty( name ) );
		element.setProperty( name, value );
		assertEquals( value, element.getProperty( name ) );
	}

	private void assertMultiValuesSize( int expectedSize ) {
		assertEquals( expectedSize, element.totalMultiValues() );
	}

	private void assertSetMultiValues( String name, String[] values ) {
		assertNull( element.getProperty( name ) );
		element.setValues( name, values );
		assertArrayEquals( values, element.getValues( name ) );
	}

	private <Y> void assertListEquals( List<Y> expected, List<Y> actual ) {
		int size = expected.size();
		assertEquals( size, actual.size() );

		for ( int i = 0; i < size; i++ ) {
			assertEquals( expected.get( i ), actual.get( i ) );
		}
	}

	/**
	 * By default return an empty tab.
	 * 
	 * @return List of values to test for a specific loader
	 */
	protected String[] getValues() {
		return new String[0];
	}

	/**
	 * By default return an empty tab.
	 * 
	 * @return List of flexible values to test for a specific loader
	 */
	protected String[] getFlexibleValues() {
		return new String[0];
	}

	/**
	 * @return List of properties to test for a specific loader
	 */
	abstract protected String[] getProperties();

	abstract protected String getTag();

	abstract protected String getUniqueIdentifierName();

	abstract protected T newElement();

}
