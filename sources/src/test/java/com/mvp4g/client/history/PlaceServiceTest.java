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

		public MyTestPlaceService( PlaceService.HistoryProxy history, String separator, boolean always ) {
			super( history, separator, always );
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

	MyTestPlaceService placeServiceDefault = null;
	MyTestPlaceService placeServiceSeparator = null;
	MyTestPlaceService placeServiceSeparatorAdd = null;
	EventBusWithLookUpStub eventBus = null;
	HistoryProxyStub history;
	HistoryProxyStub historySeparator;
	HistoryProxyStub historySeparatorAdd;
	Mvp4gModuleStub module = null;

	@Before
	public void setUp() {

		history = new HistoryProxyStub();
		historySeparator = new HistoryProxyStub();
		historySeparatorAdd = new HistoryProxyStub();

		eventBus = new EventBusWithLookUpStub();
		module = new Mvp4gModuleStub( eventBus );
		placeServiceDefault = new MyTestPlaceService( history, PlaceService.DEFAULT_SEPARATOR, false );
		placeServiceDefault.setModule( module );

		placeServiceSeparator = new MyTestPlaceService( historySeparator, PlaceService.CRAWLABLE, false );
		placeServiceSeparator.setModule( module );

		placeServiceSeparatorAdd = new MyTestPlaceService( historySeparatorAdd, PlaceService.CRAWLABLE, true );
		placeServiceSeparatorAdd.setModule( module );
	}

	@Test
	public void testConstructor() {
		assertEquals( history.getHandler(), placeServiceDefault );
		assertEquals( historySeparator.getHandler(), placeServiceSeparator );
		assertEquals( historySeparatorAdd.getHandler(), placeServiceSeparatorAdd );
	}

	@Test
	public void testPlaceNoParam() {
		String eventType = "eventType";
		String historyName = "historyName";
		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeServiceDefault.place( eventType, null );
		assertEquals( historyName, history.getToken() );
		assertFalse( history.isIssueEvent() );

		placeServiceSeparator.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeServiceSeparator.place( eventType, null );
		assertEquals( historyName, historySeparator.getToken() );
		assertFalse( historySeparator.isIssueEvent() );

		placeServiceSeparatorAdd.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeServiceSeparatorAdd.place( eventType, null );
		assertEquals( historyName + PlaceService.CRAWLABLE, historySeparatorAdd.getToken() );
		assertFalse( historySeparatorAdd.isIssueEvent() );

	}

	@Test
	public void testPlaceWithParam() {
		String eventType = "eventType";
		String form = "form";
		String historyName = "historyName";
		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeServiceDefault.place( eventType, form );
		assertEquals( historyName + "?" + form, history.getToken() );
		assertFalse( history.isIssueEvent() );

		placeServiceSeparator.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeServiceSeparator.place( eventType, form );
		assertEquals( historyName + PlaceService.CRAWLABLE + form, historySeparator.getToken() );
		assertFalse( historySeparator.isIssueEvent() );

		placeServiceSeparatorAdd.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeServiceSeparatorAdd.place( eventType, form );
		assertEquals( historyName + PlaceService.CRAWLABLE + form, historySeparatorAdd.getToken() );
		assertFalse( historySeparatorAdd.isIssueEvent() );
	}

	@Test
	public void testEmptyToken() {
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( "" );
		placeServiceDefault.onValueChange( event );
		assertTrue( placeServiceDefault.isInitEvent() );

		placeServiceSeparator.onValueChange( event );
		assertTrue( placeServiceSeparator.isInitEvent() );

		placeServiceSeparatorAdd.onValueChange( event );
		assertTrue( placeServiceSeparatorAdd.isInitEvent() );
	}

	@Test
	public void testWrongToken() {
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( "wrongEventType" );
		placeServiceDefault.onValueChange( event );
		assertTrue( placeServiceDefault.isNotFoundEvent() );

		placeServiceSeparator.onValueChange( event );
		assertTrue( placeServiceSeparator.isNotFoundEvent() );

		placeServiceSeparatorAdd.onValueChange( event );
		assertTrue( placeServiceSeparatorAdd.isNotFoundEvent() );
	}

	@Test
	public void testConverterNoParameter() {
		String eventType = "eventType";
		String historyName = "historyName";
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( historyName );

		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeServiceDefault.onValueChange( event );
		eventBus.assertEvent( eventType, new Object[] { null } );

		placeServiceSeparator.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeServiceSeparator.onValueChange( event );
		eventBus.assertEvent( eventType, new Object[] { null } );

		placeServiceSeparatorAdd.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeServiceSeparatorAdd.onValueChange( event );
		eventBus.assertEvent( eventType, new Object[] { null } );
	}

	@Test
	public void testConverterWithParameters() {
		String eventType = "eventType";
		String form = "form";
		String historyName = "historyName";
		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( historyName + "?" + form );
		placeServiceDefault.onValueChange( event );
		eventBus.assertEvent( eventType, new Object[] { form } );

		placeServiceSeparator.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		event = new ValueChangeEventStub<String>( historyName + PlaceService.CRAWLABLE + form );
		placeServiceSeparator.onValueChange( event );
		eventBus.assertEvent( eventType, new Object[] { form } );

		placeServiceSeparatorAdd.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		event = new ValueChangeEventStub<String>( historyName + PlaceService.CRAWLABLE + form );
		placeServiceSeparatorAdd.onValueChange( event );
		eventBus.assertEvent( eventType, new Object[] { form } );
	}

	@Test
	public void testConverterForChildModuleNoParameter() {
		String eventType = "child/eventType";
		String historyName = "child/historyName";
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( historyName );

		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeServiceDefault.onValueChange( event );

		assertEquals( historyName, module.getEventType() );
		Mvp4gEventPasser passer = module.getPasser();
		passer.setEventObject( false );
		passer.pass( module );
		assertTrue( placeServiceDefault.isNotFoundEvent() );

		passer.setEventObject( true );
		passer.pass( module );
		eventBus.assertEvent( "eventType", new Object[] { null } );

		placeServiceSeparator.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeServiceSeparator.onValueChange( event );

		assertEquals( historyName, module.getEventType() );
		passer = module.getPasser();
		passer.setEventObject( false );
		passer.pass( module );
		assertTrue( placeServiceSeparator.isNotFoundEvent() );

		passer.setEventObject( true );
		passer.pass( module );
		eventBus.assertEvent( "eventType", new Object[] { null } );

		placeServiceSeparatorAdd.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeServiceSeparatorAdd.onValueChange( event );

		assertEquals( historyName, module.getEventType() );
		passer = module.getPasser();
		passer.setEventObject( false );
		passer.pass( module );
		assertTrue( placeServiceSeparatorAdd.isNotFoundEvent() );

		passer.setEventObject( true );
		passer.pass( module );
		eventBus.assertEvent( "eventType", new Object[] { null } );

	}

	@Test
	public void testConverterForChildModuleNoConverter() {
		String historyName = "child/historyName";
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( historyName );

		placeServiceDefault.onValueChange( event );
		assertEquals( historyName, module.getEventType() );
		Mvp4gEventPasser passer = module.getPasser();
		passer.setEventObject( true );
		passer.pass( module );
		assertTrue( placeServiceDefault.isNotFoundEvent() );

		placeServiceSeparator.onValueChange( event );
		assertEquals( historyName, module.getEventType() );
		passer = module.getPasser();
		passer.setEventObject( true );
		passer.pass( module );
		assertTrue( placeServiceSeparator.isNotFoundEvent() );

		placeServiceSeparatorAdd.onValueChange( event );
		assertEquals( historyName, module.getEventType() );
		passer = module.getPasser();
		passer.setEventObject( true );
		passer.pass( module );
		assertTrue( placeServiceSeparatorAdd.isNotFoundEvent() );

	}

	@Test
	public void testConverterForChildModuleWithParameter() {
		String eventType = "child/eventType";
		String form = "form";
		placeServiceDefault.addConverter( eventType, eventType, buildHistoryConverter( false ) );
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( eventType + "?" + form );
		placeServiceDefault.onValueChange( event );

		assertEquals( eventType, module.getEventType() );
		Mvp4gEventPasser passer = module.getPasser();
		passer.setEventObject( true );
		passer.pass( module );
		eventBus.assertEvent( "eventType", new Object[] { form } );

		placeServiceSeparator.addConverter( eventType, eventType, buildHistoryConverter( false ) );
		event = new ValueChangeEventStub<String>( eventType + PlaceService.CRAWLABLE + form );
		placeServiceSeparator.onValueChange( event );

		assertEquals( eventType, module.getEventType() );
		passer = module.getPasser();
		passer.setEventObject( true );
		passer.pass( module );
		eventBus.assertEvent( "eventType", new Object[] { form } );

		placeServiceSeparatorAdd.addConverter( eventType, eventType, buildHistoryConverter( false ) );
		event = new ValueChangeEventStub<String>( eventType + PlaceService.CRAWLABLE + form );
		placeServiceSeparatorAdd.onValueChange( event );

		assertEquals( eventType, module.getEventType() );
		passer = module.getPasser();
		passer.setEventObject( true );
		passer.pass( module );
		eventBus.assertEvent( "eventType", new Object[] { form } );

	}

	@Test
	public void testClearHistory() {
		String eventType = "eventType";
		placeServiceDefault.addConverter( eventType, eventType, new ClearHistory() );
		placeServiceDefault.place( eventType, null );
		assertEquals( eventType, history.getToken() );

		placeServiceDefault.clearHistory();
		assertEquals( "", history.getToken() );

		placeServiceSeparator.addConverter( eventType, eventType, new ClearHistory() );
		placeServiceSeparator.place( eventType, null );
		assertEquals( eventType, historySeparator.getToken() );

		placeServiceSeparator.clearHistory();
		assertEquals( "", historySeparator.getToken() );

		placeServiceSeparatorAdd.addConverter( eventType, eventType, new ClearHistory() );
		placeServiceSeparatorAdd.place( eventType, null );
		assertEquals( eventType + PlaceService.CRAWLABLE, historySeparatorAdd.getToken() );

		placeServiceSeparatorAdd.clearHistory();
		assertEquals( "", historySeparatorAdd.getToken() );
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
		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( true ) );
		placeServiceDefault.place( eventType, null );
		assertEquals( "!" + historyName, history.getToken() );
		assertFalse( history.isIssueEvent() );
	}

	@Test
	public void testConverterCrawlable() {
		String eventType = "eventType";
		String historyName = "historyName";
		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( true ) );
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( "!" + historyName );
		placeServiceDefault.onValueChange( event );
		eventBus.assertEvent( eventType, new Object[] { null } );
	}

}