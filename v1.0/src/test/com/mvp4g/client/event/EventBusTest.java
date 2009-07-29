package com.mvp4g.client.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.client.Mvp4gException;

public class EventBusTest {

	private static final String TEST = "Test";

	private EventBus bus = null;

	private static enum EventType {

		TEST_TYPE(TEST);

		private String type;

		private EventType( String type ) {
			this.type = type;
		}

		@Override
		public String toString() {
			return type;
		}
	}

	@Before
	public void setUp() {
		bus = new EventBus();
	}

	@Test
	public void testAddAndDispatchWithFormOk() {
		String formValue = "formValue";
		addAssertCommand( formValue );
		bus.dispatch( TEST, formValue );
		bus.dispatch( EventType.TEST_TYPE, formValue );
	}

	@Test
	public void testAddAndDispatchWithNoFormOk() {
		addAssertCommand( null );
		bus.dispatch( TEST, null );
		bus.dispatch( TEST );
		bus.dispatch( EventType.TEST_TYPE, null );
		bus.dispatch( EventType.TEST_TYPE );
	}

	@Test
	public void testDispatchEventNotHandle() {
		boolean ok = false;
		try {
			bus.dispatch( TEST, null );
		} catch ( Mvp4gException exp ) {
			assertUnknownEvent( TEST, exp.getMessage() );
			ok = true;
		}
		ok = controlOk( ok );
		try {
			bus.dispatch( TEST );
		} catch ( Mvp4gException exp ) {
			assertUnknownEvent( TEST, exp.getMessage() );
			ok = true;
		}
		ok = controlOk( ok );
		try {
			bus.dispatch( EventType.TEST_TYPE, null );
		} catch ( Mvp4gException exp ) {
			assertUnknownEvent( TEST, exp.getMessage() );
			ok = true;
		}
		ok = controlOk( ok );
		try {
			bus.dispatch( EventType.TEST_TYPE );
		} catch ( Mvp4gException exp ) {
			assertUnknownEvent( TEST, exp.getMessage() );
			ok = true;
		}
		ok = controlOk( ok );
	}

	@Test
	public void testDispatchFormClassIncorrect() {
		Integer form = new Integer( 3 );
		addCommand( TEST );

		boolean ok = false;
		try {
			bus.dispatch( TEST, form );
		} catch ( Mvp4gException exp ) {
			assertIncorrectFormClass( TEST, exp.getMessage() );
			ok = true;
		}

		ok = controlOk( ok );

		try {
			bus.dispatch( EventType.TEST_TYPE, form );
		} catch ( Mvp4gException exp ) {
			assertIncorrectFormClass( TEST, exp.getMessage() );
			ok = true;
		}

		ok = controlOk( ok );

	}

	private void addAssertCommand( final String formSent ) {
		bus.addEvent( TEST, new Command<String>() {

			public void execute( String form ) {
				assertSame( form, formSent );
			}

		} );
	}

	private void addCommand( final String formSent ) {
		bus.addEvent( TEST, new Command<String>() {

			public void execute( String form ) {
				// Nothing to do
			}

		} );
	}

	private void assertUnknownEvent( String eventType, String exceptionMessage ) {
		String errorMessage = "Event " + eventType + " doesn't exist. Have you forgotten to add it to your Mvp4g configuration file?";
		assertEquals( errorMessage, exceptionMessage );

	}

	private void assertIncorrectFormClass( String eventType, String exceptionMessage ) {
		String errorMessage = "Class of the object sent with event " + eventType
				+ " is incorrect. It should be the same as the one configured in the Mvp4g configuration file.";
		assertEquals( errorMessage, exceptionMessage );

	}

	private boolean controlOk( boolean ok ) {
		if ( !ok ) {
			fail( "Excepted exception not thrown" );
		}
		return false;
	}

}
