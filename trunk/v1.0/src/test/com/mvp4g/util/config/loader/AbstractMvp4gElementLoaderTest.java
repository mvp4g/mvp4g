package com.mvp4g.util.config.loader;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Test;

import com.mvp4g.util.config.element.Mvp4gElement;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;
import com.mvp4g.util.test_tools.XMLConfigurationBuilder;

public abstract class AbstractMvp4gElementLoaderTest<E extends Mvp4gElement, L extends Mvp4gElementLoader<E>> {

	protected abstract String getTagName();

	protected abstract L newLoader( XMLConfiguration xml );

	protected abstract boolean isSingleNode();

	protected XMLConfigurationBuilder xmlBuilder = new XMLConfigurationBuilder( getTagName() );

	protected L basicLoader = newLoader( xmlBuilder.getEmptyConf() );

	@Test( expected = InvalidMvp4gConfigurationException.class )
	public void testEmptyElement() {
		basicLoader.loadElements();
	}

	@Test( expected = InvalidMvp4gConfigurationException.class )
	public void testDuplicateElement() {
		L loader = newLoader( xmlBuilder.getConfigAttribute( convertToList( basicLoader.getRequiredAttributeNames() ), convertToList( basicLoader
				.getMultiValueAttributeNames() ), new ArrayList<String>(), 2, false, isSingleNode() ) );
		loader.loadElements();
	}

	/**
	 * Test mandatory condition for each mandatory attribute.<br/>
	 * <br/>
	 * For each mandatory attribute, try to load element without it (only one is removed at a time).
	 * 
	 */
	@Test
	public void testMandatoryAttributeMissing() {
		List<String> mandAttributes = convertToList( basicLoader.getRequiredAttributeNames() );
		List<String> mandMultiValue = convertToList( basicLoader.getMultiValueAttributeNames() );
		List<String> parent = new ArrayList<String>();

		
		int nbAtt = mandAttributes.size();
		String temp = null;
		L loader = null;
		for ( int i = 0; i < nbAtt; i++ ) {
			temp = mandAttributes.remove( 0 );
			try {
				loader = newLoader( xmlBuilder.getConfigAttribute( mandAttributes, mandMultiValue, parent, 1, false, isSingleNode() ) );
				loader.loadElements();
				fail();
			} catch ( InvalidMvp4gConfigurationException e ) {
				assertTrue( "Wrong error message for missing attribute " + temp, e.getMessage().contains( temp + "' is missing" ) );				
			}
			
			mandAttributes.add( temp );
		}
	}

	/**
	 * Test mandatory condition for each mandatory value.<br/>
	 * <br/>
	 * For each mandatory value, try to load element without it (only one is removed at a time).
	 * 
	 */
	@Test
	public void testMandatoryValuesMissing() {
		List<String> mandAttributes = convertToList( basicLoader.getRequiredAttributeNames() );
		List<String> mandMultiValue = convertToList( basicLoader.getMultiValueAttributeNames() );
		List<String> parent = new ArrayList<String>();

		int nbAtt = mandMultiValue.size();
		String temp = null;
		L loader = null;
		for ( int i = 0; i < nbAtt; i++ ) {
			temp = mandMultiValue.remove( 0 );
			try {
				loader = newLoader( xmlBuilder.getConfigAttribute( mandAttributes, mandMultiValue, parent, 1, false, isSingleNode() ) );
				loader.loadElements();
				fail();
			} catch ( InvalidMvp4gConfigurationException e ) {
				assertTrue( "Wrong error message for missing attribute" + temp, e.getMessage().contains( temp + "' is missing" ) );
			}
			mandMultiValue.add( temp );
		}
	}

	/**
	 * Load an element and verify that all its parameters have been loaded correctly
	 * 
	 */
	@Test
	public void testLoadOk() {
		List<String> attributes = convertToList( basicLoader.getRequiredAttributeNames() );
		attributes.addAll( convertToList( basicLoader.getOptionalAttributeNames() ) );
		List<String> multiValues = convertToList( basicLoader.getMultiValueAttributeNames() );
		multiValues.addAll( convertToList( basicLoader.getOptionalMultiValueAttributeNames() ) );
		List<String> parents = convertToList( basicLoader.getParentAttributeNames() );
		L loader = newLoader( xmlBuilder.getConfigAttribute( attributes, multiValues, parents, 1, false, isSingleNode() ) );
		Mvp4gElement element = loader.loadElements().iterator().next();

		for ( String attribute : attributes ) {
			assertEquals( attribute, element.getProperty( attribute ) );
		}

		String[] tab = new String[1];
		for ( String value : multiValues ) {
			tab[0] = value;
			assertArrayEquals( tab, element.getValues( value ) );
		}

		for ( String parent : parents ) {
			assertEquals( parent, element.getProperty( parent ) );
		}

	}

	protected List<String> convertToList( String[] tab ) {
		List<String> list = new ArrayList<String>();
		int tabSize = tab.length;
		for ( int i = 0; i < tabSize; i++ ) {
			list.add( tab[i] );
		}
		return list;
	}
}
