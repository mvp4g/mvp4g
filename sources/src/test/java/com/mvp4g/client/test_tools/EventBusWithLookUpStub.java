package com.mvp4g.client.test_tools;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertTrue;

import com.google.gwt.user.client.Command;
import com.mvp4g.client.event.BaseEventBusWithLookUp;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.client.history.NavigationConfirmationInterface;

public class EventBusWithLookUpStub extends BaseEventBusWithLookUp {

	public static final String MVP4G_EXCEPTION = "mvp4gException";
	public static final String CLASS_CAST_EXCEPTION = "classCastException";

	private String lastDispatchedEventType = null;
	private Object[] lastDispatchedObject = null;

	public void dispatch( String eventType, Object... form ) {
		lastDispatchedEventType = eventType;
		lastDispatchedObject = form;
		if ( MVP4G_EXCEPTION.equals( eventType ) ) {
			Object o = new Object();
			start( (Integer)o );
		} else if ( CLASS_CAST_EXCEPTION.equals( eventType ) ) {
			start( 10 );
		}
	}

	private void start( final Integer i ) {
		Command cmd = new Command() {

			public void execute() {
				Object o = new Object();
				@SuppressWarnings( "unused" )
				Integer i = (Integer)o;
			}

		};
		cmd.execute();
	}

	public String getLastDispatchedEventType() {
		return lastDispatchedEventType;
	}

	public Object[] getLastDispatchedObject() {
		return lastDispatchedObject;
	}

	public void assertEvent( String expectedEventType, Object[] expectedDispatchedObject ) {
		assertEquals( expectedEventType, lastDispatchedEventType );
		if ( expectedDispatchedObject == null ) {
			assertNull( lastDispatchedObject );
		} else {
			assertTrue( expectedDispatchedObject.length == lastDispatchedObject.length );
			for ( int i = 0; i < expectedDispatchedObject.length; i++ ) {
				assertEquals( expectedDispatchedObject[i], lastDispatchedObject[i] );
			}
		}
	}

	@Override
	protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ) {
		return null;
	}

	public void setNavigationConfirmation( NavigationConfirmationInterface navigationConfirmation ) {

	}

	public void confirmNavigation( Command event ) {
		
	}

}
