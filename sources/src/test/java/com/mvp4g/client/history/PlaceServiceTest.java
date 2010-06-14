package com.mvp4g.client.history;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.mvp4g.client.Mvp4gEventPasser;
import com.mvp4g.client.test_tools.EventBusWithLookUpStub;
import com.mvp4g.client.test_tools.HistoryProxyStub;
import com.mvp4g.client.test_tools.Mvp4gModuleStub;
import com.mvp4g.client.test_tools.ValueChangeEventStub;

/**
 * TODO: find a way to test this class without using GWT History.
 * 
 * 
 * @author PL
 * 
 */
public class PlaceServiceTest {

	private class MyTestPlaceService extends PlaceService {

		private boolean initEvent = false;
		private boolean notFoundEvent = false;

		public MyTestPlaceService( PlaceService.HistoryProxy history ) {
			super( history );
		}

		public boolean isInitEvent() {
			return initEvent;
		}

		public boolean isNotFoundEvent() {
			return notFoundEvent;
		}

		@Override
		protected void sendInitEvent() {
			initEvent = true;
		}

		@Override
		protected void sendNotFoundEvent() {
			notFoundEvent = true;
		}

	}

	MyTestPlaceService placeService = null;
	EventBusWithLookUpStub eventBus = null;
	HistoryProxyStub history = new HistoryProxyStub();
	Mvp4gModuleStub module = null;

	@Before
	public void setUp() {
		eventBus = new EventBusWithLookUpStub();
		module = new Mvp4gModuleStub( eventBus );
		placeService = new MyTestPlaceService( history );
		placeService.setModule( module );
	}

	@Test
	public void testConstructor() {
		assertEquals( history.getHandler(), placeService );
	}

	@Test
	public void testPlaceNoParam() {
		String eventType = "eventType";
		String historyName = "historyName";
		placeService.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeService.place( eventType, null );
		assertEquals( historyName, history.getToken() );
		assertFalse( history.isIssueEvent() );
	}

	@Test
	public void testPlaceWithParam() {
		String eventType = "eventType";
		String form = "form";
		String historyName = "historyName";
		placeService.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeService.place( eventType, form );
		assertEquals( historyName + "?" + form, history.getToken() );
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
		assertTrue( placeService.isNotFoundEvent() );
	}

	@Test
	public void testConverterNoParameter() {
		String eventType = "eventType";
		String historyName = "historyName";
		placeService.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( historyName );
		placeService.onValueChange( event );
		eventBus.assertEvent( eventType, new Object[] { null } );
	}

	@Test
	public void testConverterWithParameters() {
		String eventType = "eventType";
		String form = "form";
		String historyName = "historyName";
		placeService.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( historyName + "?" + form );
		placeService.onValueChange( event );
		eventBus.assertEvent( eventType, new Object[] { form } );
	}

	@Test
	public void testConverterForChildModuleNoParameter() {
		String eventType = "child/eventType";
		String historyName = "child/historyName";
		placeService.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( historyName );
		placeService.onValueChange( event );

		assertEquals( historyName, module.getEventType() );
		Mvp4gEventPasser passer = module.getPasser();
		passer.setEventObject( false );
		passer.pass( module );
		assertTrue( placeService.isNotFoundEvent() );

		passer.setEventObject( true );
		passer.pass( module );
		eventBus.assertEvent( "eventType", new Object[] { null } );

	}

	@Test
	public void testConverterForChildModuleWithParameter() {
		String eventType = "child/eventType";
		String form = "form";
		placeService.addConverter( eventType, eventType, buildHistoryConverter( false ) );
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( eventType + "?" + form );
		placeService.onValueChange( event );

		assertEquals( eventType, module.getEventType() );
		Mvp4gEventPasser passer = module.getPasser();
		passer.setEventObject( true );
		passer.pass( module );
		eventBus.assertEvent( "eventType", new Object[] { form } );

	}

	@Test
	public void testClearHistory() {
		String eventType = "eventType";
		placeService.addConverter( eventType, eventType, new ClearHistory() );
		placeService.place( eventType, null );
		assertEquals( eventType, history.getToken() );

		placeService.clearHistory();
		assertEquals( "", history.getToken() );
	}

	@Test( expected = RuntimeException.class )
	public void testClearHistoryConvertFromToken() {
		ClearHistory clearHistory = new ClearHistory();
		clearHistory.convertFromToken( null, null, null );
	}

	private HistoryConverter<EventBusWithLookUpStub> buildHistoryConverter( final boolean crawlable ) {
		return new HistoryConverter<EventBusWithLookUpStub>() {

			public void convertFromToken( String eventType, String form, EventBusWithLookUpStub eventBus ) {
				eventBus.dispatch( eventType, form );
			}

			public boolean isCrawlable() {
				return crawlable;
			}

		};
	}

	@Test
	public void testPlaceCrawlable() {
		String eventType = "eventType";
		String historyName = "historyName";
		placeService.addConverter( eventType, historyName, buildHistoryConverter( true ) );
		placeService.place( eventType, null );
		assertEquals( "!" + historyName, history.getToken() );
		assertFalse( history.isIssueEvent() );
	}

	@Test
	public void testConverterCrawlable() {
		String eventType = "eventType";
		String historyName = "historyName";
		placeService.addConverter( eventType, historyName, buildHistoryConverter( true ) );
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( "!" + historyName );
		placeService.onValueChange( event );
		eventBus.assertEvent( eventType, new Object[] { null } );
	}

}