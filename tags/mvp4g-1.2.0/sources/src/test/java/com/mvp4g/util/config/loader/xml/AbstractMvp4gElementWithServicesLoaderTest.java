package com.mvp4g.util.config.loader.xml;

import static org.junit.Assert.assertEquals;

import com.mvp4g.util.config.element.InjectedElement;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;

public abstract class AbstractMvp4gElementWithServicesLoaderTest<E extends Mvp4gWithServicesElement, L extends Mvp4gElementLoader<E>> extends AbstractMvp4gElementLoaderTest<E, L> {

	protected void assertMultiValue( String value, E element ) {
		if ( "services".equals( value ) ) {
			InjectedElement injectedElement = element.getInjectedServices().get( 0 );
			assertEquals( value, injectedElement.getElementName() );
			assertEquals( "set" + value.substring( 0, 1 ).toUpperCase() + value.substring( 1 ), injectedElement.getSetterName() );
		}
		else{
			super.assertMultiValue( value, element );
		}
	}

}
