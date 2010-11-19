package com.mvp4g.util.config.loader.xml;

import static org.junit.Assert.assertEquals;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.EventElement;

public class EventsLoaderTest extends AbstractMvp4gElementLoaderTest<EventElement, EventsLoader> {

	@Override
	protected String getTagName() {
		return "event";
	}

	@Override
	protected EventsLoader newLoader( XMLConfiguration xml ) {
		return new EventsLoader( xml );
	}

	@Override
	protected boolean isSingleNode() {
		return false;
	}

	protected void assertMultiValue( String value, EventElement element ) {
		if ( "activate".equals( value ) ) {
			assertEquals( value, element.getActivate().get( 0 ) );
		} else if ( "deactivate".equals( value ) ) {
			assertEquals( value, element.getDeactivate().get( 0 ) );
		} else if ("handlers".equals( value )){
			assertEquals( value, element.getHandlers().get( 0 ) );
		} else if ("modulesToLoad".equals( value )){
			assertEquals( value, element.getModulesToLoad().get( 0 ) );			
		} else {
			super.assertMultiValue( value, element );
		}
	}

}
