package com.mvp4g.client.history;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.test_tools.EventBusStub;
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

	PlaceService placeService = null;
	EventBusStub eventBus = null;
	HistoryProxyStub history = new HistoryProxyStub();

	@Before
	public void setUp() {
		eventBus = new EventBusStub();
		placeService = new PlaceService( eventBus, history );
	}

	@Test
	public void testConstructor() {
		assertEquals( history.getHandler(), placeService );
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
	public void testInitEvent() {
		String initEvent = "initEvent";
		placeService.setInitEvent( initEvent );
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( "" );
		placeService.onValueChange( event );
		eventBus.assertEvent( initEvent, null, false );
	}

	@Test
	public void testConverterNoParameter() {
		String eventType = "eventType";
		placeService.addConverter( eventType, buildHistoryConverter() );
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( "eventType" );
		placeService.onValueChange( event );
		eventBus.assertEvent( eventType, null, false );
	}

	@Test
	public void testConverterWithParameters() {
		String eventType = "eventType";
		String form = "form";
		placeService.addConverter( eventType, buildHistoryConverter() );
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( eventType + "?" + form );
		placeService.onValueChange( event );
		eventBus.assertEvent( eventType, form, false );
	}

	private HistoryConverter<String> buildHistoryConverter() {
		return new HistoryConverter<String>() {

			public void convertFromToken( String eventType, String param, EventBus eventBus ) {
				eventBus.dispatch( eventType, param, false );
			}

			public String convertToToken( String eventType, String form ) {
				return form;
			}

		};
	}

}