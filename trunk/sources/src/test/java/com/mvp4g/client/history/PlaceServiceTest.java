package com.mvp4g.client.history;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.mvp4g.client.test_tools.EventBusWithLookUpStub;
import com.mvp4g.client.test_tools.HistoryProxyStub;
import com.mvp4g.client.test_tools.ValueChangeEventStub;

/**
 * TODO: find a way to test this class without using GWT History.
 * 
 * 
 * @author PL
 * 
 */
public class PlaceServiceTest {

	private class MyTestPlaceService extends PlaceService<EventBusWithLookUpStub> {

		private boolean initEvent = false;

		public MyTestPlaceService( PlaceService.HistoryProxy history ) {
			super( history );
		}

		@Override
		protected void sendInitEvent() {
			initEvent = true;
		}

		public boolean isInitEvent() {
			return initEvent;
		}

	}

	MyTestPlaceService placeService = null;
	EventBusWithLookUpStub eventBus = null;
	HistoryProxyStub history = new HistoryProxyStub();

	@Before
	public void setUp() {
		eventBus = new EventBusWithLookUpStub();
		placeService = new MyTestPlaceService( history );
		placeService.setEventBus( eventBus );
	}

	@Test
	public void testConstructor() {
		assertEquals( history.getHandler(), placeService );
	}

	@Test
	public void testEventBusGetter() {
		assertEquals( eventBus, placeService.getEventBus() );
	}

	@Test
	public void testPlaceNoParam() {
		String eventType = "eventType";
		placeService.addConverter( eventType, buildHistoryConverter() );
		placeService.place( eventType, null );
		assertEquals( eventType, history.getToken() );
		assertFalse( history.isIssueEvent() );
	}

	@Test
	public void testPlaceWithParam() {
		String eventType = "eventType";
		String form = "form";
		placeService.addConverter( eventType, buildHistoryConverter() );
		placeService.place( eventType, "form" );
		assertEquals( eventType + "?" + form, history.getToken() );
		assertFalse( history.isIssueEvent() );
	}

	@Test
	public void testEmptyToken() {
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( "" );
		placeService.onValueChange( event );
		assertTrue( placeService.isInitEvent() );
	}

	@Test
	public void testWrongToken() {
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( "wrongEventType" );
		placeService.onValueChange( event );
		assertTrue( placeService.isInitEvent() );
	}

	@Test
	public void testConverterNoParameter() {
		String eventType = "eventType";
		placeService.addConverter( eventType, buildHistoryConverter() );
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( "eventType" );
		placeService.onValueChange( event );
		eventBus.assertEvent( eventType, null );
	}

	@Test
	public void testConverterWithParameters() {
		String eventType = "eventType";
		String form = "form";
		placeService.addConverter( eventType, buildHistoryConverter() );
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( eventType + "?" + form );
		placeService.onValueChange( event );
		eventBus.assertEvent( eventType, form );
	}

	private HistoryConverter<String, EventBusWithLookUpStub> buildHistoryConverter() {
		return new HistoryConverter<String, EventBusWithLookUpStub>() {

			public void convertFromToken( String eventType, String param, EventBusWithLookUpStub eventBus ) {
				eventBus.dispatch( eventType, param );
			}

			public String convertToToken( String eventType, String form ) {
				return form;
			}

		};
	}

}