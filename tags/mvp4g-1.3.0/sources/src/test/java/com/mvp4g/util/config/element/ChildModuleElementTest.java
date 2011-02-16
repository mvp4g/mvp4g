package com.mvp4g.util.config.element;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.test_tools.TypeOracleStub;
import com.mvp4g.util.test_tools.annotation.Events;

public class ChildModuleElementTest extends SimpleMvp4gElementTest {

	private static final String[] properties = SimpleMvp4gElementTest.addProperties( new String[] { "eventToDisplayView", "async", "autoDisplay",
			"historyName" } );

	@Test
	public void testIsAsyncPath() throws DuplicatePropertyNameException {
		ChildModuleElement childModuleElement = new ChildModuleElement();
		assertTrue( childModuleElement.isAsync() );
		childModuleElement.setAsync( "true" );
		assertTrue( childModuleElement.isAsync() );

		childModuleElement = new ChildModuleElement();
		childModuleElement.setAsync( "false" );
		assertFalse( childModuleElement.isAsync() );

		childModuleElement = new ChildModuleElement();
		childModuleElement.setAsync( "123" );
		assertFalse( childModuleElement.isAsync() );
	}

	@Test
	public void testIsAutoLoadPath() throws DuplicatePropertyNameException {
		ChildModuleElement childModuleElement = new ChildModuleElement();
		assertTrue( childModuleElement.isAutoDisplay() );
		childModuleElement.setAutoDisplay( "true" );
		assertTrue( childModuleElement.isAutoDisplay() );

		childModuleElement = new ChildModuleElement();
		childModuleElement.setAutoDisplay( "false" );
		assertFalse( childModuleElement.isAutoDisplay() );

		childModuleElement = new ChildModuleElement();
		childModuleElement.setAutoDisplay( "123" );
		assertFalse( childModuleElement.isAutoDisplay() );
	}
	
	@Test
	public void testParentEventBus() throws DuplicatePropertyNameException {
		ChildModuleElement childModuleElement = new ChildModuleElement();
		assertNull( childModuleElement.getParentEventBus() );
		
		JClassType parentEventBus = new TypeOracleStub().addClass( Events.EventBusOk.class );
		childModuleElement.setParentEventBus( parentEventBus );

		assertSame( parentEventBus, childModuleElement.getParentEventBus() );
	}

	@Override
	protected String[] getProperties() {
		return properties;
	}

	@Override
	protected String getTag() {
		return "childModule";
	}

	@Override
	protected SimpleMvp4gElement newElement() {
		return new ChildModuleElement();
	}

}
