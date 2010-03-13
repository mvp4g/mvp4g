package com.mvp4g.client.test_tools;

import com.google.gwt.user.client.Command;
import com.mvp4g.client.event.BaseEventBusWithLookUp;
import static junit.framework.Assert.*;

public class EventBusWithLookUpStub extends BaseEventBusWithLookUp {

	public static final String MVP4G_EXCEPTION = "mvp4gException";
	public static final String CLASS_CAST_EXCEPTION = "classCastException";

	private String lastDispatchedEventType = null;
	private Object lastDispatchedObject = null;

	public void dispatch( String eventType, Object form ) {
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

	public Object getLastDispatchedObject() {
		return lastDispatchedObject;
	}

	public void assertEvent( String expectedEventType, Object expectedDispatchedObject ) {
		assertEquals( expectedEventType, lastDispatchedEventType );
		assertEquals( expectedDispatchedObject, lastDispatchedObject );
	}

}
