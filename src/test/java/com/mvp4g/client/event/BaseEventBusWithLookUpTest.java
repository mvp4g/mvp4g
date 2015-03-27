package com.mvp4g.client.event;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.client.Mvp4gException;
import com.mvp4g.client.test_tools.EventBusWithLookUpStub;

public class BaseEventBusWithLookUpTest {

	private EventBusWithLookUpStub bus = null;

	private static final String TEST = "test";
	private static final String FORM = "form";

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
		bus = new EventBusWithLookUpStub();
	}

	@Test
	public void testDispatch() {
		bus.dispatch( TEST );
		bus.assertEvent( TEST, new Object[0] );

		bus.dispatch( EventType.TEST_TYPE );
		bus.assertEvent( TEST, new Object[0] );

		bus.dispatch( EventType.TEST_TYPE, FORM );
		bus.assertEvent( TEST, new Object[]{FORM} );
	}

	@Test( expected = Mvp4gException.class )
	public void testDispatchWithMvp4gException() {
		try {
			bus.dispatch( EventBusWithLookUpStub.MVP4G_EXCEPTION );
		} catch ( ClassCastException e ) {
			bus.handleClassCastException( e, EventBusWithLookUpStub.MVP4G_EXCEPTION );
		}
	}

	@Test( expected = ClassCastException.class )
	public void testDispatchWithClassCastException() {
		try {
			bus.dispatch( EventBusWithLookUpStub.CLASS_CAST_EXCEPTION );
		} catch ( ClassCastException e ) {
			bus.handleClassCastException( e, EventBusWithLookUpStub.CLASS_CAST_EXCEPTION );
		}
	}

}
