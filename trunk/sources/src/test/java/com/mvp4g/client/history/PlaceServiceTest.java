package com.mvp4g.client.history;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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

	private class MyTestNavigationConfirmation implements NavigationConfirmationInterface {

		private NavigationEventCommand event;

		public void confirm( NavigationEventCommand event ) {
			this.event = event;
		}

		public NavigationEventCommand getEvent() {
			return event;
		}

	}

	MyTestPlaceService placeServiceDefault = null;
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
		placeServiceDefault = new MyTestPlaceService( history );
		placeServiceDefault.setModule( module );
		
	}

	@Test
	public void testConstructor() {
		assertEquals( history.getHandler(), placeServiceDefault );		
	}

	@Test
	public void testPlaceNoParam() {
		String eventType = "eventType";
		String historyName = "historyName";
		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		String token = placeServiceDefault.place( eventType, null, false );
		assertEquals( historyName, history.getToken() );
		assertFalse( history.isIssueEvent() );
		assertEquals( historyName, token );
	}

	@Test
	public void testPlaceWithParam() {
		String eventType = "eventType";
		String form = "form";
		String historyName = "historyName";
		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		String token = placeServiceDefault.place( eventType, form, false );
		assertEquals( historyName + "?" + form, history.getToken() );
		assertFalse( history.isIssueEvent() );
		assertEquals( historyName + "?" + form, token );
	}

	@Test
	public void testPlaceTokenOnly() {
		String eventType = "eventType";
		String form = "form";
		String historyName = "historyName";
		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		String token = placeServiceDefault.place( eventType, form, true );
		assertEquals( historyName + "?" + form, token );
		assertNull( history.getToken() );
	}

	@Test
	public void testEmptyToken() {
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( "" );
		placeServiceDefault.onValueChange( event );
		assertTrue( placeServiceDefault.isInitEvent() );
	}

	@Test
	public void testWrongToken() {
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( "wrongEventType" );
		placeServiceDefault.onValueChange( event );
		assertTrue( placeServiceDefault.isNotFoundEvent() );
	}

	@Test
	public void testConverterNoParameter() {
		String eventType = "eventType";
		String historyName = "historyName";
		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( historyName );

		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		placeServiceDefault.onValueChange( event );
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

	}

	@Test
	public void testClearHistory() {
		String eventType = "eventType";
		placeServiceDefault.addConverter( eventType, eventType, new ClearHistory() );
		placeServiceDefault.place( eventType, null, false );
		assertEquals( eventType, history.getToken() );

		placeServiceDefault.clearHistory();
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
		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( true ) );
		placeServiceDefault.place( eventType, null, false );
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

	@Test
	public void testNavigationConfirmationForHistory() {
		String eventType = "eventType";
		String historyName = "historyName";
		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( true ) );

		MyTestNavigationConfirmation navConf = new MyTestNavigationConfirmation();
		placeServiceDefault.setNavigationConfirmation( navConf );

		ValueChangeEvent<String> event = new ValueChangeEventStub<String>( historyName );
		placeServiceDefault.onValueChange( event );
		eventBus.assertEvent( null, null );

		navConf.getEvent().execute();
		eventBus.assertEvent( eventType, new Object[] { null } );

	}

	@Test
	public void testNoNavigationConfirmationForEvent() {
		final String eventType = "eventType";
		final String form = "form";

		NavigationEventCommand event = new NavigationEventCommand( eventBus ) {

			public void execute() {
				eventBus.dispatch( eventType, form );
			}
		};

		placeServiceDefault.confirmEvent( event );
		eventBus.assertEvent( eventType, new Object[] { form } );

	}

	@Test
	public void testNavigationConfirmationForEvent() {
		final String eventType = "eventType";
		final String form = "form";

		NavigationEventCommand event = new NavigationEventCommand( eventBus ) {

			public void execute() {
				eventBus.dispatch( eventType, form );
			}
		};

		MyTestNavigationConfirmation navConf = new MyTestNavigationConfirmation();
		placeServiceDefault.setNavigationConfirmation( navConf );

		placeServiceDefault.confirmEvent( event );

		eventBus.assertEvent( null, null );

		navConf.getEvent().execute();
		eventBus.assertEvent( eventType, new Object[] { form } );

	}
	
	@Test
	public void testEnabled(){
		String eventType = "eventType";
		String form = "form";
		String historyName = "historyName";
		placeServiceDefault.addConverter( eventType, historyName, buildHistoryConverter( false ) );
		
		placeServiceDefault.setEnabled( false );
		String token = placeServiceDefault.place( eventType, form, true );
		assertEquals( historyName + "?" + form, token );
		assertNull( history.getToken() );
		
		token = placeServiceDefault.place( eventType, form, false );
		assertNull( token );
		assertNull( history.getToken() );
		
		placeServiceDefault.setEnabled( true );
		token = placeServiceDefault.place( eventType, form, false );
		assertEquals( historyName + "?" + form, token );
		assertEquals( historyName + "?" + form, history.getToken() );
	}

}