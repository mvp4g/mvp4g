package com.mvp4g.util.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.javac.typemodel.TypeOracleStub;
import com.google.gwt.dev.util.UnitTestTreeLogger;
import com.google.gwt.inject.client.GinModule;
import com.mvp4g.client.DefaultMvp4gGinModule;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.DefaultMvp4gLogger;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.event.EventFilter;
import com.mvp4g.client.event.Mvp4gLogger;
import com.mvp4g.client.history.ClearHistory;
import com.mvp4g.client.history.PlaceService;
import com.mvp4g.util.config.element.ChildModuleElement;
import com.mvp4g.util.config.element.ChildModulesElement;
import com.mvp4g.util.config.element.DebugElement;
import com.mvp4g.util.config.element.EventBusElement;
import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.EventFilterElement;
import com.mvp4g.util.config.element.EventFiltersElement;
import com.mvp4g.util.config.element.EventHandlerElement;
import com.mvp4g.util.config.element.GinModuleElement;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.HistoryElement;
import com.mvp4g.util.config.element.InjectedElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.exception.InvalidClassException;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;
import com.mvp4g.util.exception.InvalidTypeException;
import com.mvp4g.util.exception.NonUniqueIdentifierException;
import com.mvp4g.util.exception.NotFoundClassException;
import com.mvp4g.util.exception.UnknownConfigurationElementException;
import com.mvp4g.util.test_tools.GeneratorContextStub;
import com.mvp4g.util.test_tools.Modules;
import com.mvp4g.util.test_tools.PropertyOracleStub;
import com.mvp4g.util.test_tools.annotation.EventFilters;
import com.mvp4g.util.test_tools.annotation.Events.EventBusWithNoStartPresenter;
import com.mvp4g.util.test_tools.annotation.HistoryConverters;
import com.mvp4g.util.test_tools.annotation.Presenters;
import com.mvp4g.util.test_tools.annotation.TestBroadcast;
import com.mvp4g.util.test_tools.annotation.TestBroadcast2;
import com.mvp4g.util.test_tools.annotation.events.EventBusOk;
import com.mvp4g.util.test_tools.annotation.gin.OneGinModule;
import com.mvp4g.util.test_tools.annotation.handlers.EventHandlerWithEvent;
import com.mvp4g.util.test_tools.annotation.handlers.SimpleEventHandler;
import com.mvp4g.util.test_tools.annotation.history_converters.HistoryConverterForEvent;
import com.mvp4g.util.test_tools.annotation.history_converters.SimpleHistoryConverter;
import com.mvp4g.util.test_tools.annotation.presenters.PresenterWithName;
import com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter;
import com.mvp4g.util.test_tools.annotation.services.ServiceWithName;
import com.mvp4g.util.test_tools.annotation.services.SimpleService;
import com.mvp4g.util.test_tools.annotation.views.SimpleInjectedView;
import com.mvp4g.util.test_tools.annotation.views.SimpleView;

public class Mvp4gConfigurationTest {

	private Mvp4gConfiguration configuration;
	private Set<PresenterElement> presenters;
	private Set<EventHandlerElement> eventHandlers;
	private Set<ViewElement> views;
	private Set<EventElement> events;
	private Set<ServiceElement> services;
	private Set<HistoryConverterElement> historyConverters;
	private Set<ChildModuleElement> childModules;
	private Set<EventFilterElement> eventFilters;
	private TypeOracleStub oracle = null;
	private UnitTestTreeLogger.Builder builder;
	private UnitTestTreeLogger logger;

	@Before
	public void setUp() {
		GeneratorContextStub context = new GeneratorContextStub();

		builder = new UnitTestTreeLogger.Builder();
		logger = builder.createLogger();

		oracle = context.getTypeOracleStub();
		oracle.addClass( SimpleView.class );

		configuration = new Mvp4gConfiguration( logger, context );
		presenters = configuration.getPresenters();
		views = configuration.getViews();
		events = configuration.getEvents();
		services = configuration.getServices();
		historyConverters = configuration.getHistoryConverters();
		childModules = configuration.getChildModules();
		eventHandlers = configuration.getEventHandlers();
		eventFilters = configuration.getEventFilters();
		configuration.setStart( new StartElement() );
		configuration.setHistory( new HistoryElement() );
		configuration.setModule( oracle.addClass( Mvp4gModule.class ) );
	}

	@Test
	public void testSetterAndGetter() {
		StartElement start = new StartElement();
		configuration.setStart( start );
		assertSame( start, configuration.getStart() );

		HistoryElement history = new HistoryElement();
		configuration.setHistory( history );
		assertSame( history, configuration.getHistory() );

		EventBusElement eventBus = new EventBusElement( null, null, false );
		configuration.setEventBus( eventBus );
		assertSame( eventBus, configuration.getEventBus() );

		assertSame( oracle, configuration.getOracle() );
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnPresenterAndViewConflict() throws NonUniqueIdentifierException {
		presenters.add( newPresenter( "one" ) );
		views.add( newView( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnPresenterAndEventConflict() throws NonUniqueIdentifierException {
		presenters.add( newPresenter( "one" ) );
		events.add( newEvent( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnPresenterAndServiceConflict() throws NonUniqueIdentifierException {
		presenters.add( newPresenter( "one" ) );
		services.add( newService( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnPresenterAndHistoryConverterConflict() throws NonUniqueIdentifierException {
		presenters.add( newPresenter( "one" ) );
		historyConverters.add( newHistoryConverter( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnViewAndEventConflict() throws NonUniqueIdentifierException {
		views.add( newView( "two" ) );
		events.add( newEvent( "two" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnViewAndServiceConflict() throws NonUniqueIdentifierException {
		views.add( newView( "two" ) );
		services.add( newService( "two" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnViewAndHistoryConverterConflict() throws NonUniqueIdentifierException {
		views.add( newView( "one" ) );
		historyConverters.add( newHistoryConverter( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnEventAndServiceConflict() throws NonUniqueIdentifierException {
		services.add( newService( "three" ) );
		events.add( newEvent( "three" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnEventAndHistoryConverterConflict() throws NonUniqueIdentifierException {
		events.add( newEvent( "one" ) );
		historyConverters.add( newHistoryConverter( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnServiceAndHistoryConverterConflict() throws NonUniqueIdentifierException {
		services.add( newService( "one" ) );
		historyConverters.add( newHistoryConverter( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test
	public void testUniquenessCheckPasses() throws NonUniqueIdentifierException {
		presenters.add( newPresenter( "one" ) );
		views.add( newView( "two" ) );
		events.add( newEvent( "three" ) );
		services.add( newService( "four" ) );
		historyConverters.add( newHistoryConverter( "five" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = InvalidMvp4gConfigurationException.class )
	public void testHistoryConverterWithNoHistory() throws InvalidMvp4gConfigurationException {
		historyConverters.add( new HistoryConverterElement() );
		configuration.validateHistory();
	}
	
	@Test
	public void testEventBindsAnnotationRestriction() throws InvalidMvp4gConfigurationException {
		// checking situation when handlers has the same attributes as binds
		EventElement event = newEvent( "testEvent1" );
		event.setHandlers( new String[] { "handler1", "handler2" } );
		event.setBinds( new String[] { "hander3", "handler1" } );
		events.add( event );
		setEventBus();
		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertTrue( e.getMessage().contains( "Event testEvent1: the same handler handler1 is used in the bind and handlers attributes. If you need handler1 to handle this event, you should remove it from the bind attribute." ));
		} finally {
			events.remove( event );
		}
		// checking situation when passive event has some binds
		EventElement event2 = newEvent( "testEvent2");
		event2.setBinds( new String[] { "someHandler" } );
		event2.setPassive( "true" );
		events.add( event2 );
		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertTrue( e.getMessage().contains( "Passive event can't bind any elements. Remove bind attribute from the testEvent2 event in order to keep it passive." ));
		} finally {
			events.remove( event2 );
		}
		
		// checking that combination with broadcastTo and passive event don't lead to exception
		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.setMultiple( "true" );
		presenter.setView( "view" );
		presenters.add( presenter );
		
		ViewElement view = newView( "view" );
		view.setClassName( SimpleView.class.getCanonicalName() );
		views.add( view );
		
		oracle.addClass( TestBroadcast.class );
		
		EventElement event3 = newEvent( "testEvent3" );
		event3.setPassive( "true" );
		event3.setBroadcastTo( TestBroadcast.class.getCanonicalName() );
		event3.setGenerate( new String[] { "testPresenter" } );
		events.add( event3 );
		
		try {
			assertTrue( event3.isPassive() );
			assertTrue(  event3.getBinds() == null );
			configuration.validateEventHandlers();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertTrue( e.getMessage().contains( "Passive event can't have any binds elements. Remove bind annotation from the testEvent3 event in order to keep it passive" ));
			fail();
		} finally {
			events.remove( event3 );
			presenters.remove( presenter );
		}
	}

	@Test
	public void testHistoryOk() throws InvalidMvp4gConfigurationException {
		configuration.getHistory().setInitEvent( "init" );
		historyConverters.add( new HistoryConverterElement() );
		configuration.validateHistory();
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testEventHandlerValidationFails() throws InvalidMvp4gConfigurationException {
		views.add( newView( "badHandler" ) );
		services.add( newService( "badHandler" ) );
		events.add( newEvent( "badHanlder" ) );
		historyConverters.add( newHistoryConverter( "badHanlder" ) );

		EventElement event = newEvent( "testEvent" );
		event.setHandlers( new String[] { "badHandler" } );
		events.add( event );
		setEventBus();
		configuration.validateEventHandlers();
	}
	
	

	@Test( expected = InvalidClassException.class )
	public void testEventHandlerWrongInterface() throws InvalidMvp4gConfigurationException {

		ViewElement view = newView( "view" );
		view.setClassName( String.class.getName() );
		views.add( view );

		PresenterElement presenter = new PresenterElement();
		presenter.setName( "testHandler" );
		presenter.setView( "view" );
		presenter.setClassName( Object.class.getName() );
		presenters.add( presenter );

		EventElement event = newEvent( "testEvent" );
		event.setHandlers( new String[] { "testHandler" } );
		events.add( event );

		setEventBus();
		configuration.validateEventHandlers();
	}

	@Test
	public void testEventHandlerValidationInvalidEventBus() throws InvalidMvp4gConfigurationException {

		ViewElement view = newView( "view" );
		view.setClassName( String.class.getName() );
		views.add( view );

		PresenterElement presenter = newPresenter( "testHandler" );
		presenter.setView( "view" );
		presenters.add( presenter );

		EventElement event = newEvent( "testEvent" );
		event.setHandlers( new String[] { "testHandler" } );
		events.add( event );

		EventBusElement eventBus = new EventBusElement( EventBus.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( "Event Bus" ) );
		}

		presenter.setMultiple( Boolean.TRUE.toString() );
		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( "Event Bus" ) );
		}

		events.clear();

		configuration.validateEventHandlers();
		assertTrue( presenters.size() == 0 );

		EventHandlerElement eventHandler = newEventHandler( "testHandler" );
		eventHandlers.add( eventHandler );

		events.add( event );
		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( "Event Bus" ) );
		}

		eventHandler.setMultiple( Boolean.TRUE.toString() );
		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( "Event Bus" ) );
		}

		events.clear();

		configuration.validateEventHandlers();
		assertTrue( eventHandlers.size() == 0 );

	}

	@Test
	public void testEventHandlerValidationInvalidView() throws InvalidMvp4gConfigurationException {

		ViewElement view = newView( "view" );
		view.setClassName( Integer.class.getName() );
		views.add( view );

		PresenterElement presenter = newPresenter( "testHandler" );
		presenter.setView( "view" );
		presenters.add( presenter );

		EventElement event = newEvent( "testEvent" );
		event.setHandlers( new String[] { "testHandler" } );
		events.add( event );
		try {
			setEventBus();
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( "View" ) );
		}

		presenter.setMultiple( Boolean.TRUE.toString() );

		try {
			setEventBus();
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( "View" ) );
		}

	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testEventHandlerViewMissing() throws InvalidMvp4gConfigurationException {

		PresenterElement presenter = newPresenter( "testHandler" );
		presenter.setView( "view" );
		presenters.add( presenter );

		EventElement event = newEvent( "testEvent" );
		event.setHandlers( new String[] { "testHandler" } );
		events.add( event );

		setEventBus();
		configuration.validateEventHandlers();
	}

	@Test
	public void testEventHandlerValidationSucceedsNoInjectedView() throws InvalidMvp4gConfigurationException {

		ViewElement view = newView( "view" );
		view.setClassName( SimpleView.class.getCanonicalName() );
		views.add( view );

		PresenterElement presenter = newPresenter( "testHandler" );
		presenter.setView( "view" );
		presenters.add( presenter );

		EventElement event = newEvent( "testEvent" );
		event.setHandlers( new String[] { "testHandler" } );
		events.add( event );

		setEventBus();
		configuration.validateEventHandlers();
		assertFalse( presenter.hasInverseView() );
	}

	@Test
	public void testEventHandlerValidationSucceedsWithInjectedView() throws InvalidMvp4gConfigurationException {

		ViewElement view = newView( "view" );
		Class<?> c = SimpleInjectedView.class;
		view.setClassName( c.getCanonicalName() );
		oracle.addClass( c );
		views.add( view );

		PresenterElement presenter = new PresenterElement();
		presenter.setName( "testHandler" );
		c = SimplePresenter.class;
		presenter.setClassName( c.getCanonicalName() );
		oracle.addClass( c );
		presenter.setView( "view" );
		presenters.add( presenter );

		EventElement event = newEvent( "testEvent" );
		event.setHandlers( new String[] { "testHandler" } );
		events.add( event );

		setEventBus();
		configuration.validateEventHandlers();
		assertTrue( presenter.hasInverseView() );

	}

	@Test
	public void testEventHandlerWrongInjectedView() throws InvalidMvp4gConfigurationException {

		ViewElement view = newView( "view" );
		Class<?> c = SimpleInjectedView.class;
		view.setClassName( c.getCanonicalName() );
		oracle.addClass( c );
		views.add( view );

		PresenterElement presenter = new PresenterElement();
		presenter.setName( "testHandler" );
		c = Presenters.MultiplePresenter.class;
		presenter.setClassName( c.getCanonicalName() );
		oracle.addClass( c );
		presenter.setView( "view" );
		presenters.add( presenter );

		EventElement event = newEvent( "testEvent" );
		event.setHandlers( new String[] { "testHandler" } );
		events.add( event );

		setEventBus();
		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidTypeException e ) {
			assertEquals(
					"view view: Invalid Presenter. Can not convert com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter to com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter",
					e.getMessage() );
		}

	}

	@Test
	public void testEventHandlerRemove() throws InvalidMvp4gConfigurationException {

		ViewElement view = newView( "view" );
		Class<?> c = SimpleView.class;
		view.setClassName( c.getCanonicalName() );
		oracle.addClass( c );
		views.add( view );

		PresenterElement presenter1 = newPresenter( "presenter1" );
		presenter1.setView( "view" );
		presenters.add( presenter1 );

		PresenterElement presenter2 = newPresenter( "presenter2" );
		presenter2.setView( "view" );
		presenters.add( presenter2 );

		PresenterElement presenter3 = newPresenter( "presenter3" );
		presenter3.setView( "view" );
		presenter3.setMultiple( "true" );
		presenters.add( presenter3 );
		
		PresenterElement presenter4 = newPresenter( "presenter4" );
		presenter4.setView( "view" );
		presenters.add( presenter4 );

		EventHandlerElement handler1 = newEventHandler( "handler1" );
		eventHandlers.add( handler1 );

		EventHandlerElement handler2 = newEventHandler( "handler2" );
		eventHandlers.add( handler2 );

		EventHandlerElement handler3 = newEventHandler( "handler3" );
		handler3.setMultiple( "true" );
		eventHandlers.add( handler3 );
		
		EventHandlerElement handler4 = newEventHandler( "handler4" );
		eventHandlers.add( handler4 );

		EventElement event = newEvent( "testEvent" );
		event.setHandlers( new String[] { "presenter1", "handler1" } );
		event.setGenerate( new String[] { "presenter3", "handler3" } );
		event.setBinds( new String[] { "presenter4", "handler4" } );
		events.add( event );

		setEventBus();

		assertEquals( 4, presenters.size() );
		assertTrue( presenters.contains( presenter1 ) );
		assertTrue( presenters.contains( presenter2 ) );
		assertEquals( 4, eventHandlers.size() );
		assertTrue( eventHandlers.contains( handler1 ) );
		assertTrue( eventHandlers.contains( handler2 ) );
		configuration.validateEventHandlers();
		assertEquals( 3, presenters.size() );
		assertTrue( presenters.contains( presenter1 ) );
		assertFalse( presenters.contains( presenter2 ) );
		assertEquals( 3, eventHandlers.size() );
		assertTrue( eventHandlers.contains( handler1 ) );
		assertFalse( eventHandlers.contains( handler2 ) );

		presenter2.setMultiple( Boolean.TRUE.toString() );
		presenters.add( presenter2 );
		handler2.setMultiple( Boolean.TRUE.toString() );
		eventHandlers.add( handler2 );

		assertEquals( 4, presenters.size() );
		assertTrue( presenters.contains( presenter1 ) );
		assertTrue( presenters.contains( presenter2 ) );
		assertEquals( 4, eventHandlers.size() );
		assertTrue( eventHandlers.contains( handler1 ) );
		assertTrue( eventHandlers.contains( handler2 ) );
		configuration.validateEventHandlers();
		assertEquals( 4, presenters.size() );
		assertTrue( presenters.contains( presenter1 ) );
		assertTrue( presenters.contains( presenter2 ) );
		assertEquals( 4, eventHandlers.size() );
		assertTrue( eventHandlers.contains( handler1 ) );
		assertTrue( eventHandlers.contains( handler2 ) );

	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testPresenterViewValidationFails() throws UnknownConfigurationElementException, InvalidTypeException, InvalidClassException,
			NotFoundClassException {
		events.add( newEvent( "badView" ) );
		services.add( newService( "badView" ) );
		presenters.add( newPresenter( "badView" ) );
		historyConverters.add( newHistoryConverter( "badView" ) );

		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.setView( "badView" );
		presenters.add( presenter );

		configuration.validateViews();
	}

	@Test
	public void testBroadcastHandlers() throws InvalidMvp4gConfigurationException {

		oracle.addClass( TestBroadcast.class );

		ViewElement view = newView( "view" );
		Class<?> c = SimpleView.class;
		view.setClassName( c.getCanonicalName() );
		oracle.addClass( c );
		views.add( view );

		PresenterElement presenter = new PresenterElement();
		presenter.setName( "presenter" );
		c = Presenters.BroadcastPresenter.class;
		presenter.setClassName( c.getCanonicalName() );
		oracle.addClass( c );
		presenter.setView( "view" );
		presenters.add( presenter );

		PresenterElement presenter2 = new PresenterElement();
		presenter2.setName( "presenter2" );
		c = Presenters.BroadcastPresenter2.class;
		presenter2.setClassName( c.getCanonicalName() );
		oracle.addClass( c );
		presenter2.setView( "view" );
		presenters.add( presenter2 );

		PresenterElement presenter3 = new PresenterElement();
		presenter3.setName( "presenter3" );
		c = Presenters.BroadcastPresenter2.class;
		presenter3.setClassName( c.getCanonicalName() );
		presenter3.setView( "view" );
		presenters.add( presenter3 );

		EventElement event = newEvent( "event" );
		event.setBroadcastTo( TestBroadcast.class.getCanonicalName() );
		event.setHandlers( new String[0] );
		events.add( event );

		EventElement event2 = newEvent( "event2" );
		event2.setBroadcastTo( TestBroadcast2.class.getCanonicalName() );
		event2.setHandlers( new String[] { "presenter3" } );
		events.add( event2 );

		setEventBus();
		configuration.validateEventHandlers();
		List<String> handlers = event.getHandlers();
		assertEquals( 3, handlers.size() );
		assertEquals( "presenter3", handlers.get( 0 ) );
		assertEquals( "presenter2", handlers.get( 1 ) );
		assertEquals( "presenter", handlers.get( 2 ) );

		handlers = event2.getHandlers();
		assertEquals( 2, handlers.size() );
		assertEquals( "presenter3", handlers.get( 0 ) );
		assertEquals( "presenter2", handlers.get( 1 ) );

	}

	@Test
	public void testViewValidationSucceeds() throws UnknownConfigurationElementException, InvalidTypeException, InvalidClassException,
			NotFoundClassException {
		views.add( newView( "testView" ) );

		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.setView( "testView" );
		presenters.add( presenter );

		configuration.validateViews();
	}

	@Test
	public void testViewRemove() throws UnknownConfigurationElementException, InvalidTypeException, InvalidClassException, NotFoundClassException {
		ViewElement view1 = newView( "view1" );
		ViewElement view2 = newView( "view2" );
		ViewElement view3 = newView( "view3" );
		views.add( view1 );
		views.add( view2 );
		views.add( view3 );

		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.setView( "view1" );
		presenters.add( presenter );

		assertEquals( views.size(), 3 );
		assertTrue( views.contains( view1 ) );
		assertTrue( views.contains( view2 ) );
		assertTrue( views.contains( view3 ) );
		configuration.validateViews();
		assertEquals( views.size(), 1 );
		assertTrue( views.contains( view1 ) );
		assertFalse( views.contains( view2 ) );
		assertFalse( views.contains( view3 ) );
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testInjectedServiceValidationFailsForPresenter() throws UnknownConfigurationElementException, InvalidTypeException,
			InvalidClassException, NotFoundClassException {
		events.add( newEvent( "badService" ) );
		views.add( newView( "badService" ) );
		presenters.add( newPresenter( "badService" ) );
		historyConverters.add( newHistoryConverter( "badService" ) );

		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.getInjectedServices().add( new InjectedElement( "badService", "setBadService" ) );
		presenters.add( presenter );

		configuration.validateServices();
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testInjectedServiceValidationFailsForHistoryConverter() throws UnknownConfigurationElementException, InvalidTypeException,
			InvalidClassException, NotFoundClassException {
		events.add( newEvent( "badService" ) );
		views.add( newView( "badService" ) );
		presenters.add( newPresenter( "badService" ) );
		historyConverters.add( newHistoryConverter( "badService" ) );

		HistoryConverterElement historyConverter = newHistoryConverter( "testHistoryConverter" );
		historyConverter.getInjectedServices().add( new InjectedElement( "badService", "setBadService" ) );
		historyConverters.add( historyConverter );

		configuration.validateServices();
	}

	@Test
	public void testInjectedServiceValidationSucceeds() throws UnknownConfigurationElementException {
		services.add( newService( "testService" ) );

		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.getInjectedServices().add( new InjectedElement( "testService", "setTestService" ) );
		presenters.add( presenter );

		HistoryConverterElement historyConverter = newHistoryConverter( "testHistoryConverter" );
		historyConverter.getInjectedServices().add( new InjectedElement( "testService", "setTestService" ) );
		historyConverters.add( historyConverter );

		configuration.validateServices();
	}

	@Test
	public void testInjectedServiceRemove() throws UnknownConfigurationElementException {
		ServiceElement service1 = newService( "service1" );
		ServiceElement service2 = newService( "service2" );
		ServiceElement service3 = newService( "service3" );
		ServiceElement service4 = newService( "service4" );

		services.add( service1 );
		services.add( service2 );
		services.add( service3 );
		services.add( service4 );

		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.getInjectedServices().add( new InjectedElement( "service1", "setTestService" ) );
		presenters.add( presenter );

		HistoryConverterElement historyConverter = newHistoryConverter( "testHistoryConverter" );
		historyConverter.getInjectedServices().add( new InjectedElement( "service2", "setTestService" ) );
		historyConverters.add( historyConverter );

		EventHandlerElement eventHandlerElement = newEventHandler( "testEventHandler" );
		eventHandlerElement.getInjectedServices().add( new InjectedElement( "service3", "setTestService" ) );
		eventHandlers.add( eventHandlerElement );

		assertEquals( services.size(), 4 );
		assertTrue( services.contains( service1 ) );
		assertTrue( services.contains( service2 ) );
		assertTrue( services.contains( service3 ) );
		assertTrue( services.contains( service4 ) );
		configuration.validateServices();
		assertEquals( services.size(), 3 );
		assertTrue( services.contains( service1 ) );
		assertTrue( services.contains( service2 ) );
		assertTrue( services.contains( service3 ) );
		assertFalse( services.contains( service4 ) );
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testEventHistoryConverterFails() throws InvalidMvp4gConfigurationException {

		events.add( newEvent( "badHistoryConverter" ) );
		services.add( newService( "badHistoryConverter" ) );
		presenters.add( newPresenter( "badHistoryConverter" ) );
		views.add( newView( "badHistoryConverter" ) );

		EventElement event = newEvent( "testEvent" );
		event.setHistory( "badView" );
		events.add( event );

		setEventBus();
		configuration.validateHistoryConverters();
	}

	@Test( expected = InvalidClassException.class )
	public void testEventHistoryConverterWrongInterface() throws InvalidMvp4gConfigurationException {

		HistoryConverterElement hc = new HistoryConverterElement();
		hc.setName( "testHistoryConverter" );
		hc.setClassName( Object.class.getName() );

		historyConverters.add( hc );

		EventElement event = newEvent( "testEvent" );
		event.setHistory( "testHistoryConverter" );
		events.add( event );

		setEventBus();
		configuration.validateHistoryConverters();
	}

	@Test( expected = InvalidTypeException.class )
	public void testEventHistoryConverterWrongEventBus() throws InvalidMvp4gConfigurationException {

		try {
			HistoryConverterElement hc = new HistoryConverterElement();
			hc.setName( "testHistoryConverter" );
			Class<?> c = HistoryConverters.HistoryConverterWithLookup.class;
			oracle.addClass( c );
			hc.setClassName( c.getCanonicalName() );

			historyConverters.add( hc );

			EventElement event = newEvent( "testEvent" );
			event.setHistory( "testHistoryConverter" );
			events.add( event );

			EventBusElement eventBus = new EventBusElement( EventBus.class.getName(), BaseEventBus.class.getName(), false );
			configuration.setEventBus( eventBus );
			configuration.validateHistoryConverters();
			fail();
		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( "Event Bus" ) );
			throw e;
		}
	}

	@Test
	public void testEventHistoryConverterRemove() throws InvalidMvp4gConfigurationException {
		HistoryConverterElement hc1 = newHistoryConverter( "hc1" );
		HistoryConverterElement hc2 = newHistoryConverter( "hc2" );
		historyConverters.add( hc1 );
		historyConverters.add( hc2 );

		EventElement event = newEvent( "testEvent" );
		event.setHistory( "hc1" );
		events.add( event );

		setEventBus();
		assertEquals( 2, historyConverters.size() );
		assertTrue( historyConverters.contains( hc1 ) );
		assertTrue( historyConverters.contains( hc2 ) );
		configuration.validateHistoryConverters();
		assertEquals( 1, historyConverters.size() );
		assertTrue( historyConverters.contains( hc1 ) );
		assertFalse( historyConverters.contains( hc2 ) );
		configuration.validateHistoryConverters();

	}

	@Test
	public void testEventHistoryConverterSucceeds() throws InvalidMvp4gConfigurationException {
		historyConverters.add( newHistoryConverter( "testHistoryConverter" ) );

		EventElement event = newEvent( "testEvent" );
		event.setHistory( "testHistoryConverter" );
		events.add( event );

		setEventBus();
		configuration.validateHistoryConverters();
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testStartEventFails() throws InvalidMvp4gConfigurationException {
		services.add( newService( "badEvent" ) );
		presenters.add( newPresenter( "badEvent" ) );
		views.add( newView( "badEvent" ) );
		historyConverters.add( newHistoryConverter( "badEvent" ) );

		configuration.getStart().setEventType( "badEvent" );

		configuration.validateEvents();

	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testHistoryInitEventFails() throws InvalidMvp4gConfigurationException {
		services.add( newService( "badEvent" ) );
		presenters.add( newPresenter( "badEvent" ) );
		views.add( newView( "badEvent" ) );
		historyConverters.add( newHistoryConverter( "badEvent" ) );

		configuration.getHistory().setInitEvent( "badEvent" );

		configuration.validateEvents();

	}

	@Test
	public void testStartSucceeds() throws InvalidMvp4gConfigurationException {
		events.add( newEvent( "testEvent" ) );
		configuration.getStart().setEventType( "testEvent" );
		configuration.getHistory().setInitEvent( "testEvent" );
		configuration.validateEvents();
	}

	@Test
	public void testChildModulesRemove() throws InvalidMvp4gConfigurationException {

		ChildModuleElement childModule1 = newChildModule( "child1" );
		childModule1.setEventToDisplayView( "testEvent" );
		childModules.add( childModule1 );
		configuration.getOthersEventBusClassMap().put( Modules.Module1.class.getCanonicalName(), oracle.addClass( EventBusOk.class ) );

		ChildModuleElement childModule2 = newChildModule( "child2" );
		childModules.add( childModule2 );

		EventElement event = newEvent( "testEvent" );
		event.setForwardToModules( new String[] { "child1" } );
		event.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		events.add( event );

		builder.expectWarn(
				"Module Mvp4gModule: the child module child2 is not loaded by any of the event of this module. You should remove it if it is not used by another child module (ie used for sibling communication).",
				null );
		logger = builder.createLogger();
		configuration.setLogger( logger );

		setEventBus();

		assertEquals( 2, childModules.size() );
		assertTrue( childModules.contains( childModule1 ) );
		assertTrue( childModules.contains( childModule2 ) );
		configuration.validateChildModules();
		assertEquals( 2, childModules.size() );
		assertTrue( childModules.contains( childModule1 ) );
		assertTrue( childModules.contains( childModule2 ) );

		logger.assertCorrectLogEntries();

	}

	@Test( expected = InvalidMvp4gConfigurationException.class )
	public void testChildModulesNoStart() throws InvalidMvp4gConfigurationException {

		ChildModuleElement childModule1 = newChildModule( "child1" );
		childModule1.setEventToDisplayView( "testEvent" );
		childModules.add( childModule1 );
		configuration.getOthersEventBusClassMap()
				.put( Modules.Module1.class.getCanonicalName(), oracle.addClass( EventBusWithNoStartPresenter.class ) );

		EventElement event = newEvent( "testEvent" );
		event.setForwardToModules( new String[] { "child1" } );
		event.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		events.add( event );
		try {
			configuration.validateChildModules();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals(
					"Module com.mvp4g.util.test_tools.Modules.Module1: You must define a start presenter since this module has a parent module that uses the auto-displayed feature for this module.",
					e.getMessage() );
			throw e;
		}
	}

	@Test
	public void testChildModulesBroadcast() throws InvalidMvp4gConfigurationException {

		oracle.addClass( TestBroadcast.class );

		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setName( "child" );
		Class<?> c = Modules.BroadcastModule.class;
		oracle.addClass( c );
		childModule.setClassName( c.getCanonicalName() );
		childModule.setAutoDisplay( "false" );
		childModules.add( childModule );

		ChildModuleElement childModule2 = new ChildModuleElement();
		childModule2.setName( "child2" );
		c = Modules.BroadcastModule2.class;
		oracle.addClass( c );
		childModule2.setClassName( c.getCanonicalName() );
		childModule2.setAutoDisplay( "false" );
		childModules.add( childModule2 );

		ChildModuleElement childModule3 = new ChildModuleElement();
		childModule3.setName( "child3" );
		c = Modules.BroadcastModule2.class;
		childModule3.setClassName( c.getCanonicalName() );
		childModule3.setAutoDisplay( "false" );
		childModules.add( childModule3 );

		EventElement event = newEvent( "testEvent" );
		event.setBroadcastTo( TestBroadcast.class.getCanonicalName() );
		event.setForwardToModules( new String[0] );
		events.add( event );

		EventElement event2 = newEvent( "testEvent2" );
		event2.setBroadcastTo( TestBroadcast2.class.getCanonicalName() );
		event2.setForwardToModules( new String[] { "child3" } );
		events.add( event2 );

		setEventBus();

		configuration.validateChildModules();

		List<String> modules = event.getForwardToModules();
		assertEquals( 3, modules.size() );
		assertEquals( "child", modules.get( 0 ) );
		assertEquals( "child3", modules.get( 1 ) );
		assertEquals( "child2", modules.get( 2 ) );

		modules = event2.getForwardToModules();
		assertEquals( 2, modules.size() );
		assertEquals( "child3", modules.get( 0 ) );
		assertEquals( "child2", modules.get( 1 ) );

	}

	@Test( expected = InvalidClassException.class )
	public void testInvalidChildModule() throws InvalidMvp4gConfigurationException {

		ChildModuleElement childModule1 = new ChildModuleElement();
		childModule1.setName( "child1" );
		childModule1.setClassName( Object.class.getName() );
		childModules.add( childModule1 );

		EventElement event = newEvent( "testEvent" );
		event.setForwardToModules( new String[] { "child1" } );
		event.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		events.add( event );

		setEventBus();
		configuration.validateChildModules();

	}

	@Test
	public void testAutoDisplay() throws InvalidMvp4gConfigurationException {

		ChildModuleElement childModule1 = newChildModule( "child1" );
		childModules.add( childModule1 );
		assertTrue( childModule1.isAutoDisplay() );

		EventElement event = newEvent( "testEvent" );
		event.setForwardToModules( new String[] { "child1" } );
		event.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		events.add( event );

		setEventBus();

		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		try {
			configuration.validateChildModules();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals(
					e.getMessage(),
					String.format( "%s: child module %s doesn't define any event to load its view.",
							Modules.ModuleWithParent.class.getCanonicalName(), childModule1.getClassName() ) );
		}

		childModule1.setAutoDisplay( "false" );
		configuration.validateChildModules();
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testUnknownEventForAutoDisplay() throws InvalidMvp4gConfigurationException {

		ChildModuleElement childModule1 = newChildModule( "child1" );
		childModule1.setEventToDisplayView( "unknown" );
		childModules.add( childModule1 );

		EventElement event = newEvent( "testEvent" );
		event.setForwardToModules( new String[] { "child1" } );
		event.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		events.add( event );

		setEventBus();

		configuration.validateChildModules();

	}

	@Test( expected = InvalidMvp4gConfigurationException.class )
	public void testEventWithNoObjectForAutoDisplay() throws InvalidMvp4gConfigurationException {

		ChildModuleElement childModule1 = newChildModule( "child1" );
		childModule1.setEventToDisplayView( "testEvent" );
		childModules.add( childModule1 );

		EventElement event = newEvent( "testEvent" );
		event.setForwardToModules( new String[] { "child1" } );
		events.add( event );

		setEventBus();

		try {
			configuration.validateChildModules();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals( "Event testEvent: event must have one and only one an object associated with it as it loads a child view.", e.getMessage() );
			throw e;
		}

	}

	@Test
	public void testChildViewLoadEvent() throws InvalidMvp4gConfigurationException {

		ChildModuleElement childModule = newChildModule( "child" );
		childModule.setEventToDisplayView( "testEvent" );

		configuration.getOthersEventBusClassMap().put( Modules.Module1.class.getCanonicalName(), oracle.addClass( EventBusOk.class ) );

		childModules.add( childModule );

		EventElement event = newEvent( "testEvent" );
		event.setForwardToModules( new String[] { "child" } );
		event.setEventObjectClass( new String[] { String.class.getCanonicalName() } );
		events.add( event );

		setEventBus();
		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		try {
			configuration.validateChildModules();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals(
					e.getMessage(),
					String.format( "Child Module %s: event %s can not load child module's start view. Can not convert %s to %s.",
							childModule.getClassName(), "testEvent", SimpleView.class.getCanonicalName(), String.class.getCanonicalName() ) );
		}

		event = newEvent( "testEvent" );
		event.setForwardToModules( new String[] { "child" } );
		event.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		events.clear();
		events.add( event );

		configuration.validateChildModules();

	}

	@Test
	public void testMissingChildModule() throws InvalidMvp4gConfigurationException {
		EventElement event = newEvent( "testEvent" );
		event.setForwardToModules( new String[] { "child" } );
		event.setEventObjectClass( new String[] { String.class.getCanonicalName() } );
		events.add( event );

		setEventBus();
		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		try {
			configuration.validateChildModules();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals(
					"Event testEvent: No instance of child has been found. Is this module a child module, a parent module or a silbling module? If it's supposed to be a child module, have you forgotten to add it to @ChildModules of your event bus interface?",
					e.getMessage() );
		}
	}

	@Test
	public void testForwardSiblingModule() throws InvalidMvp4gConfigurationException {
		String siblingModule = oracle.addClass( Modules.Module1.class ).getQualifiedSourceName();

		EventElement event = newEvent( "testEvent" );
		event.setForwardToModules( new String[] { siblingModule } );
		event.setEventObjectClass( new String[] { String.class.getCanonicalName() } );
		events.add( event );

		String currentParentModule = oracle.addClass( Modules.ModuleWithParentNoName.class ).getQualifiedSourceName();

		Map<String, ChildModuleElement> childModuleMap = configuration.getModuleParentEventBusClassMap();
		JClassType parentEventBus = oracle.addClass( EventBusOk.class );

		ChildModuleElement sibling = new ChildModuleElement();
		sibling.setParentEventBus( parentEventBus );
		sibling.setParentModuleClass( currentParentModule );
		sibling.setClassName( siblingModule );

		configuration.setParentEventBus( parentEventBus );

		String currentModule = oracle.addClass( Modules.ModuleWithParent.class ).getQualifiedSourceName();

		childModuleMap.put( "sibling", sibling );

		ChildModuleElement module = new ChildModuleElement();
		module.setParentEventBus( parentEventBus );
		module.setParentModuleClass( currentParentModule );
		module.setClassName( currentModule );
		childModuleMap.put( currentModule, module );

		setEventBus();
		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );

		List<ChildModuleElement> siblings = configuration.getSiblings();
		assertEquals( 1, siblings.size() );
		assertSame( sibling, siblings.get( 0 ) );

		assertEquals( 1, event.getForwardToModules().size() );
		assertEquals( 0, event.getSiblingsToLoad().size() );

		configuration.validateChildModules();

		assertEquals( 0, event.getForwardToModules().size() );
		assertEquals( 1, event.getSiblingsToLoad().size() );

	}

	@Test
	public void testSiblingBroadcast() throws InvalidMvp4gConfigurationException {

		String siblingModule1 = oracle.addClass( Modules.BroadcastModule.class ).getQualifiedSourceName();
		String siblingModule2 = oracle.addClass( Modules.BroadcastModule2.class ).getQualifiedSourceName();
		String siblingModule3 = oracle.addClass( Modules.Module1.class ).getQualifiedSourceName();

		EventElement event = newEvent( "testEvent" );
		event.setBroadcastTo( TestBroadcast.class.getCanonicalName() );
		event.setEventObjectClass( new String[] { String.class.getCanonicalName() } );
		events.add( event );

		EventElement event2 = newEvent( "testEvent2" );
		event2.setBroadcastTo( TestBroadcast2.class.getCanonicalName() );
		event2.setForwardToModules( new String[] { siblingModule3 } );
		events.add( event2 );

		String currentParentModule = oracle.addClass( Modules.ModuleWithParentNoName.class ).getQualifiedSourceName();

		Map<String, ChildModuleElement> childModuleMap = configuration.getModuleParentEventBusClassMap();
		JClassType parentEventBus = oracle.addClass( EventBusOk.class );

		ChildModuleElement sibling1 = new ChildModuleElement();
		sibling1.setParentEventBus( parentEventBus );
		sibling1.setParentModuleClass( currentParentModule );
		sibling1.setClassName( siblingModule1 );

		ChildModuleElement sibling2 = new ChildModuleElement();
		sibling2.setParentEventBus( parentEventBus );
		sibling2.setParentModuleClass( currentParentModule );
		sibling2.setClassName( siblingModule2 );

		ChildModuleElement sibling3 = new ChildModuleElement();
		sibling3.setParentEventBus( parentEventBus );
		sibling3.setParentModuleClass( currentParentModule );
		sibling3.setClassName( siblingModule3 );

		childModuleMap.put( "sibling1", sibling1 );
		childModuleMap.put( "sibling2", sibling2 );
		childModuleMap.put( "sibling3", sibling3 );

		configuration.setParentEventBus( parentEventBus );

		String currentModule = oracle.addClass( Modules.ModuleWithParent.class ).getQualifiedSourceName();

		ChildModuleElement module = new ChildModuleElement();
		module.setParentEventBus( parentEventBus );
		module.setParentModuleClass( currentParentModule );
		module.setClassName( currentModule );
		childModuleMap.put( currentModule, module );

		setEventBus();
		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );

		List<ChildModuleElement> siblings = configuration.getSiblings();
		assertEquals( 3, siblings.size() );
		assertSame( sibling1, siblings.get( 0 ) );
		assertSame( sibling3, siblings.get( 1 ) );
		assertSame( sibling2, siblings.get( 2 ) );

		assertEquals( 0, event.getSiblingsToLoad().size() );
		assertEquals( 1, event2.getForwardToModules().size() );

		configuration.validateChildModules();

		List<String> siblingsToLoad = event.getSiblingsToLoad();
		assertEquals( 2, siblingsToLoad.size() );
		assertEquals( sibling1.getClassName(), siblingsToLoad.get( 0 ) );
		assertEquals( sibling2.getClassName(), siblingsToLoad.get( 1 ) );

		siblingsToLoad = event2.getSiblingsToLoad();
		assertEquals( 2, siblingsToLoad.size() );
		assertEquals( sibling3.getClassName(), siblingsToLoad.get( 0 ) );
		assertEquals( sibling2.getClassName(), siblingsToLoad.get( 1 ) );

	}

	@Test
	public void testForwardParentModule() throws InvalidMvp4gConfigurationException {
		String parentModule = oracle.addClass( Modules.Module1.class ).getQualifiedSourceName();

		EventElement event = newEvent( "testEvent" );
		event.setForwardToModules( new String[] { parentModule } );
		event.setEventObjectClass( new String[] { String.class.getCanonicalName() } );
		events.add( event );

		Map<String, ChildModuleElement> childModuleMap = configuration.getModuleParentEventBusClassMap();
		JClassType parentEventBus = oracle.addClass( EventBusOk.class );

		configuration.setParentEventBus( parentEventBus );

		String currentModule = oracle.addClass( Modules.ModuleWithParent.class ).getQualifiedSourceName();

		ChildModuleElement module = new ChildModuleElement();
		module.setParentEventBus( parentEventBus );
		module.setParentModuleClass( parentModule );
		module.setClassName( currentModule );
		childModuleMap.put( currentModule, module );

		setEventBus();
		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );

		List<ChildModuleElement> siblings = configuration.getSiblings();
		assertEquals( 0, siblings.size() );

		assertFalse( event.hasForwardToParent() );

		configuration.validateChildModules();

		assertTrue( event.hasForwardToParent() );

	}

	@Test
	public void testParentBroadcast() throws InvalidMvp4gConfigurationException {

		String parentModule = oracle.addClass( Modules.BroadcastModule2.class ).getQualifiedSourceName();

		EventElement event = newEvent( "testEvent" );
		event.setBroadcastTo( TestBroadcast.class.getCanonicalName() );
		event.setEventObjectClass( new String[] { String.class.getCanonicalName() } );
		events.add( event );

		EventElement event2 = newEvent( "testEvent2" );
		event2.setBroadcastTo( TestBroadcast2.class.getCanonicalName() );
		events.add( event2 );

		Map<String, ChildModuleElement> childModuleMap = configuration.getModuleParentEventBusClassMap();
		JClassType parentEventBus = oracle.addClass( EventBusOk.class );

		configuration.setParentEventBus( parentEventBus );

		String currentModule = oracle.addClass( Modules.ModuleWithParent.class ).getQualifiedSourceName();

		ChildModuleElement module = new ChildModuleElement();
		module.setParentEventBus( parentEventBus );
		module.setParentModuleClass( parentModule );
		module.setClassName( currentModule );
		childModuleMap.put( currentModule, module );

		setEventBus();
		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );

		List<ChildModuleElement> siblings = configuration.getSiblings();
		assertEquals( 0, siblings.size() );

		assertFalse( event.hasForwardToParent() );
		assertFalse( event2.hasForwardToParent() );

		configuration.validateChildModules();

		assertTrue( event.hasForwardToParent() );
		assertTrue( event2.hasForwardToParent() );

	}

	@Test
	public void testEventWithParent() throws InvalidMvp4gConfigurationException {
		EventElement e = newEvent( "event" );
		e.setForwardToParent( "true" );
		events.add( e );

		try {
			configuration.validateEvents();
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			assertEquals( "Event event: Root module has no parent so you can't forward event to parent.", ex.getMessage() );
		}

		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );

		setParentEventBus( Modules.ModuleWithParent.class, EventBusOk.class );
		configuration.loadParentModule();
		configuration.validateEvents();
	}

	@Test
	public void testUnknownLoadConfigEvent() throws InvalidMvp4gConfigurationException {
		ChildModulesElement childModules = new ChildModulesElement();
		configuration.setHistory( null );

		configuration.setLoadChildConfig( childModules );
		childModules.setBeforeEvent( "unknown" );
		try {
			configuration.validateEvents();
			fail();
		} catch ( UnknownConfigurationElementException e ) {
			// nothing to test
		}
		childModules = new ChildModulesElement();
		configuration.setLoadChildConfig( childModules );
		childModules.setAfterEvent( "unknown" );
		try {
			configuration.validateEvents();
			fail();
		} catch ( UnknownConfigurationElementException e ) {
			// nothing to test
		}
		childModules = new ChildModulesElement();
		configuration.setLoadChildConfig( childModules );
		childModules.setErrorEvent( "unknown" );
		try {
			configuration.validateEvents();
			fail();
		} catch ( UnknownConfigurationElementException e ) {
			// nothing to test
		}
	}

	@Test
	public void testWrongEventLoadConfigEvent() throws InvalidMvp4gConfigurationException {

		configuration.setHistory( null );

		EventElement e = newEvent( "event" );
		e.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		events.add( e );

		ChildModulesElement childModules = new ChildModulesElement();
		configuration.setLoadChildConfig( childModules );
		childModules.setBeforeEvent( "event" );
		try {
			configuration.validateEvents();
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			assertEquals( String.format( "%s: %s event %s can't have any object associated with it.", childModules.getTagName(), "Before", "event" ),
					ex.getMessage() );
		}

		childModules = new ChildModulesElement();
		configuration.setLoadChildConfig( childModules );
		childModules.setAfterEvent( "event" );
		try {
			configuration.validateEvents();
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			assertEquals( String.format( "%s: %s event %s can't have any object associated with it.", childModules.getTagName(), "After", "event" ),
					ex.getMessage() );
		}

		childModules = new ChildModulesElement();
		configuration.setLoadChildConfig( childModules );
		childModules.setErrorEvent( "event" );
		try {
			configuration.validateEvents();
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			assertEquals( "childModules: Error event event can only be associated with one and only one object with type java.lang.Throwable",
					ex.getMessage() );
		}

	}

	@Test
	public void testHistoryWhenParent() throws InvalidMvp4gConfigurationException {

		historyConverters.add( new HistoryConverterElement() );

		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		setParentEventBus( Modules.ModuleWithParent.class, EventBusOk.class );
		configuration.loadParentModule();

		configuration.getHistory().setInitEvent( "event" );
		try {
			configuration.validateHistory();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals(
					"Module com.mvp4g.util.test_tools.Modules.ModuleWithParent: History configuration (init, not found event and history parameter separator) should be set only for root module (only module with no parent)",
					e.getMessage() );
		}

		HistoryElement history = new HistoryElement();
		configuration.setHistory( history );
		history.setNotFoundEvent( "event" );
		try {
			configuration.validateHistory();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals(
					"Module com.mvp4g.util.test_tools.Modules.ModuleWithParent: History configuration (init, not found event and history parameter separator) should be set only for root module (only module with no parent)",
					e.getMessage() );
		}

		history = new HistoryElement();
		configuration.setHistory( history );
		configuration.validateHistory();
		assertNull( configuration.getHistory() );

	}

	@Test
	public void testHistoryName() throws InvalidMvp4gConfigurationException {

		historyConverters.add( new HistoryConverterElement() );

		configuration.setModule( oracle.addClass( Modules.ModuleWithParentNoName.class ) );
		setParentEventBus( Modules.ModuleWithParentNoName.class, EventBusOk.class );

		configuration.loadParentModule();

		assertNull( configuration.getHistoryName() );

		try {
			configuration.validateHistory();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertTrue( e.getMessage().contains( "Child module that defines history converter must have a" ) );
		}

		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		setParentEventBus( Modules.ModuleWithParent.class, EventBusOk.class );
		configuration.loadParentModule();
		assertEquals( "moduleWithParent", configuration.getHistoryName() );

		configuration.validateHistory();
	}

	@Test
	public void testFindChildModuleHistory() throws InvalidMvp4gConfigurationException {

		oracle.addClass( Modules.ModuleWithParent.class );
		oracle.addClass( Modules.ModuleWithParentNoName.class );
		oracle.addClass( Modules.Module1.class );

		ChildModuleElement childModule1 = new ChildModuleElement();
		childModule1.setName( "child1" );
		childModule1.setClassName( Modules.ModuleWithParent.class.getCanonicalName() );
		childModules.add( childModule1 );

		ChildModuleElement childModule2 = new ChildModuleElement();
		childModule2.setName( "child2" );
		childModule2.setClassName( Modules.Module1.class.getCanonicalName() );
		childModules.add( childModule2 );

		ChildModuleElement childModule3 = new ChildModuleElement();
		childModule3.setName( "child3" );
		childModule3.setClassName( Modules.ModuleWithParentNoName.class.getCanonicalName() );
		childModules.add( childModule3 );

		configuration.findChildModuleHistoryName();

		assertNull( childModule2.getHistoryName() );
		assertNull( childModule3.getHistoryName() );
		assertEquals( "moduleWithParent", childModule1.getHistoryName() );
	}

	@Test( expected = InvalidMvp4gConfigurationException.class )
	public void testFindChildModuleSameHistory() throws InvalidMvp4gConfigurationException {

		oracle.addClass( Modules.ModuleWithParent.class );

		ChildModuleElement childModule1 = new ChildModuleElement();
		childModule1.setName( "child1" );
		childModule1.setClassName( Modules.ModuleWithParent.class.getCanonicalName() );
		childModules.add( childModule1 );

		ChildModuleElement childModule2 = new ChildModuleElement();
		childModule2.setName( "child2" );
		childModule2.setClassName( Modules.ModuleWithParent.class.getCanonicalName() );
		childModules.add( childModule2 );

		JClassType module = oracle.addClass( Mvp4gModule.class );
		configuration.setModule( module );

		try {
			configuration.findChildModuleHistoryName();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			String.format( "Module %s: You can't have two child modules with the same history name \"%s\".", module.getQualifiedSourceName(),
					"moduleWithParent" );
			throw e;
		}
	}

	@Test
	public void testEventLoadConfigEventOk() throws InvalidMvp4gConfigurationException {

		configuration.setHistory( null );

		EventElement e = newEvent( "event" );
		events.add( e );

		ChildModulesElement childModules = new ChildModulesElement();
		configuration.setLoadChildConfig( childModules );
		childModules.setBeforeEvent( "event" );
		childModules.setAfterEvent( "event" );
		childModules.setErrorEvent( "event" );
		configuration.validateEvents();

		e = newEvent( "event2" );
		e.setEventObjectClass( new String[] { Throwable.class.getCanonicalName() } );
		events.add( e );

		childModules = new ChildModulesElement();
		configuration.setLoadChildConfig( childModules );
		childModules.setErrorEvent( "event2" );
		configuration.validateEvents();

	}

	@Test
	public void testLoadAnnotation() throws Exception {
		configuration.setModule( oracle.addClass( Mvp4gModule.class ) );

		List<JClassType> aPresenters = new ArrayList<JClassType>();
		aPresenters.add( oracle.findType( SimplePresenter.class.getName() ) );
		aPresenters.add( oracle.findType( PresenterWithName.class.getName() ) );
		configuration.loadPresenters( aPresenters );
		assertEquals( 2, presenters.size() );

		List<JClassType> aHC = new ArrayList<JClassType>();
		aHC.add( oracle.findType( SimpleHistoryConverter.class.getName() ) );
		aHC.add( oracle.findType( HistoryConverterForEvent.class.getName() ) );
		configuration.loadHistoryConverters( aHC );
		assertEquals( 2, historyConverters.size() );

		List<JClassType> aEvents = new ArrayList<JClassType>();
		aEvents.add( oracle.findType( EventBusOk.class.getName() ) );
		configuration.setStart( null );
		configuration.setHistory( null );
		configuration.loadEvents( aEvents );
		assertEquals( 5, events.size() );

		List<JClassType> aService = new ArrayList<JClassType>();
		aService.add( oracle.findType( SimpleService.class.getName() ) );
		aService.add( oracle.findType( ServiceWithName.class.getName() ) );
		configuration.loadServices( aService );
		assertEquals( 2, services.size() );

		List<JClassType> aEventHandlers = new ArrayList<JClassType>();
		aEventHandlers.add( oracle.findType( SimpleEventHandler.class.getName() ) );
		aEventHandlers.add( oracle.findType( EventHandlerWithEvent.class.getName() ) );
		configuration.loadEventHandlers( aEventHandlers );
		assertEquals( 2, aEventHandlers.size() );

	}

	@Test
	public void testAsyncEnabled() {
		assertEquals( configuration.isAsyncEnabled(), true );
		oracle.setGWT2( false );
		assertEquals( configuration.isAsyncEnabled(), false );
	}

	@Test
	public void testStartEventWithParameter() throws InvalidMvp4gConfigurationException {
		EventElement e = newEvent( "start" );
		e.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		events.add( e );

		StartElement start = configuration.getStart();
		start.setPresenter( "startPresenter" );
		start.setEventType( "start" );

		try {
			configuration.validateStart();
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			assertEquals( "Start: Start event start can't have any object associated with it.", ex.getMessage() );
		}

		events.clear();
		e = newEvent( "start" );
		events.add( e );
		configuration.validateStart();
	}

	@Test
	public void testEmptyHistoryNameForRoot() {
		EventElement e = newEvent( "start" );
		e.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		e.setHistory( "history" );
		e.setName( "" );
		events.add( e );

		try {
			configuration.validateHistoryConverters();
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			assertEquals( "Event start: An event of the Mvp4g Root module can't have an history name equal to empty string.", ex.getMessage() );
		}
	}

	@Test
	public void testSameHistoryName() {
		EventElement e = newEvent( "start" );
		e.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		e.setHistory( "history" );
		e.setName( "name" );
		events.add( e );

		e = newEvent( "start2" );
		e.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		e.setHistory( "history" );
		e.setName( "name" );
		events.add( e );

		try {
			configuration.validateHistoryConverters();
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			assertEquals( "Event start2: history name already used for another event: name.", ex.getMessage() );
		}
	}

	@Test
	public void testHistoryNameNoConveter() {
		EventElement e = newEvent( "start" );
		e.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		e.setName( "name" );
		events.add( e );

		try {
			configuration.validateHistoryConverters();
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			assertEquals( "Event start: you defined an history name for this event but this event has no history converter.", ex.getMessage() );
		}
	}

	@Test
	public void testRemoveClearHistory() throws InvalidMvp4gConfigurationException {
		HistoryConverterElement e = new HistoryConverterElement();
		e.setClassName( ClearHistory.class.getCanonicalName() );
		historyConverters.add( e );
		EventBusElement eventBus = new EventBusElement( EventBus.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );
		configuration.validateHistoryConverters();
	}

	@Test
	public void testWrongEventHandlerClass() throws InvalidMvp4gConfigurationException {
		EventBusElement eventBus = new EventBusElement( EventBusWithLookup.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		EventElement event = newEvent( "event" );
		event.setHandlers( new String[] { "test" } );
		events.add( event );

		EventHandlerElement handler = new EventHandlerElement();
		handler.setClassName( Object.class.getCanonicalName() );
		handler.setName( "test" );
		eventHandlers.add( handler );

		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidClassException e ) {

		}

		handler.setMultiple( Boolean.TRUE.toString() );

		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidClassException e ) {

		}

		PresenterElement presenter = new PresenterElement();
		Class<?> c = SimpleEventHandler.class;
		oracle.addClass( c );
		presenter.setClassName( c.getCanonicalName() );
		presenter.setName( "test" );
		presenters.add( presenter );

		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidClassException e ) {

		}

		presenter.setMultiple( Boolean.TRUE.toString() );

		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidClassException e ) {

		}

	}

	@Test
	public void testActivateDeActivateOk() throws InvalidMvp4gConfigurationException {
		EventBusElement eventBus = new EventBusElement( EventBusWithLookup.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		EventElement event = newEvent( "event" );
		event.setHandlers( new String[] { "activate1", "deactivate1", "activate2", "deactivate2" } );
		events.add( event );

		EventElement event1 = newEvent( "event1" );
		event1.setActivate( new String[] { "activate1", "activate2" } );
		event1.setDeactivate( new String[] { "deactivate1", "deactivate2" } );
		events.add( event1 );

		PresenterElement activate1 = newPresenter( "activate1" );
		EventHandlerElement activate2 = newEventHandler( "activate2" );
		PresenterElement deactivate1 = newPresenter( "deactivate1" );
		EventHandlerElement deactivate2 = newEventHandler( "deactivate2" );

		eventHandlers.add( activate1 );
		eventHandlers.add( activate2 );
		eventHandlers.add( deactivate1 );
		eventHandlers.add( deactivate2 );

		configuration.validateEventHandlers();

		List<String> activate = event1.getActivate();
		assertTrue( activate.size() == 2 );
		assertEquals( activate.get( 0 ), activate1.getName() );
		assertEquals( activate.get( 1 ), activate2.getName() );

		List<String> deactivate = event1.getDeactivate();
		assertTrue( deactivate.size() == 2 );
		assertEquals( deactivate.get( 0 ), deactivate1.getName() );
		assertEquals( deactivate.get( 1 ), deactivate2.getName() );

	}

	@Test
	public void testActivateDeActivateUseless() throws InvalidMvp4gConfigurationException {
		EventBusElement eventBus = new EventBusElement( EventBusWithLookup.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		EventElement event1 = newEvent( "event1" );
		event1.setActivate( new String[] { "activate1", "activate2" } );
		event1.setDeactivate( new String[] { "deactivate1", "deactivate2" } );
		events.add( event1 );

		PresenterElement activate1 = newPresenter( "activate1" );
		EventHandlerElement activate2 = newEventHandler( "activate2" );
		PresenterElement deactivate1 = newPresenter( "deactivate1" );
		EventHandlerElement deactivate2 = newEventHandler( "deactivate2" );

		eventHandlers.add( activate1 );
		eventHandlers.add( activate2 );
		eventHandlers.add( deactivate1 );
		eventHandlers.add( deactivate2 );

		configuration.validateEventHandlers();

		List<String> activate = event1.getActivate();
		assertTrue( activate.size() == 0 );

		List<String> deactivate = event1.getDeactivate();
		assertTrue( deactivate.size() == 0 );

	}

	@Test
	public void testActivateDeActivateMultiple() throws InvalidMvp4gConfigurationException {
		EventBusElement eventBus = new EventBusElement( EventBusWithLookup.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		EventElement event1 = newEvent( "event1" );
		event1.setActivate( new String[] { "activate1", "activate2" } );
		event1.setDeactivate( new String[] { "deactivate1", "deactivate2" } );
		events.add( event1 );

		PresenterElement activate1 = newPresenter( "activate1" );
		EventHandlerElement activate2 = newEventHandler( "activate2" );
		PresenterElement deactivate1 = newPresenter( "deactivate1" );
		EventHandlerElement deactivate2 = newEventHandler( "deactivate2" );

		eventHandlers.add( activate1 );
		eventHandlers.add( activate2 );
		eventHandlers.add( deactivate1 );
		eventHandlers.add( deactivate2 );

		activate1.setMultiple( Boolean.TRUE.toString() );
		activate2.setMultiple( Boolean.TRUE.toString() );
		deactivate1.setMultiple( Boolean.TRUE.toString() );
		deactivate2.setMultiple( Boolean.TRUE.toString() );

		configuration.validateEventHandlers();

		List<String> activate = event1.getActivate();
		assertTrue( activate.size() == 2 );
		assertEquals( activate.get( 0 ), activate1.getName() );
		assertEquals( activate.get( 1 ), activate2.getName() );

		List<String> deactivate = event1.getDeactivate();
		assertTrue( deactivate.size() == 2 );
		assertEquals( deactivate.get( 0 ), deactivate1.getName() );
		assertEquals( deactivate.get( 1 ), deactivate2.getName() );

	}

	@Test
	public void testSameActivateDeactivate() {
		EventBusElement eventBus = new EventBusElement( EventBusWithLookup.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		EventElement event1 = newEvent( "event1" );
		event1.setActivate( new String[] { "activate" } );
		event1.setDeactivate( new String[] { "activate" } );
		events.add( event1 );

		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertTrue( e.getMessage().contains( "an event can't activate and deactivate the same handler: activate." ) );
		}
	}

	@Test
	public void testGenerate() throws InvalidMvp4gConfigurationException {
		EventBusElement eventBus = new EventBusElement( EventBusWithLookup.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		EventElement event1 = newEvent( "event1" );
		event1.setHandlers( new String[] { "generate2" } );
		event1.setGenerate( new String[] { "generate1", "generate2" } );
		events.add( event1 );

		ViewElement view = newView( "view" );
		view.setClassName( SimpleView.class.getCanonicalName() );
		views.add( view );

		PresenterElement generate1 = newPresenter( "generate1" );
		generate1.setView( "view" );
		EventHandlerElement generate2 = newEventHandler( "generate2" );

		presenters.add( generate1 );
		eventHandlers.add( generate2 );

		generate1.setMultiple( Boolean.TRUE.toString() );
		generate2.setMultiple( Boolean.TRUE.toString() );

		configuration.validateEventHandlers();

		String[] generate = event1.getGenerate();
		assertEquals( 2, generate.length );
		assertEquals( generate[0], generate1.getName() );
		assertEquals( generate[1], generate2.getName() );
		
		List<String> handlers = event1.getHandlers();
		assertEquals( 1, handlers.size() );
		assertEquals( generate2.getName(), handlers.get(0) );
	}

	@Test
	public void testGenerateNotMultiple() throws InvalidMvp4gConfigurationException {
		EventBusElement eventBus = new EventBusElement( EventBusWithLookup.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		EventElement event1 = newEvent( "event1" );
		event1.setHandlers( new String[] { "generate2" } );
		event1.setGenerate( new String[] { "generate1", "generate2" } );
		events.add( event1 );

		ViewElement view = newView( "view" );
		view.setClassName( SimpleView.class.getCanonicalName() );
		views.add( view );

		PresenterElement generate1 = newPresenter( "generate1" );
		generate1.setView( "view" );
		EventHandlerElement generate2 = newEventHandler( "generate2" );

		presenters.add( generate1 );

		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertTrue( e.getMessage().contains(
					"Event event1: you can generate only multiple handlers. Did you forget to set the attribute multiple to true for generate1?" ) );
		}

		presenters.clear();

		eventHandlers.clear();
		eventHandlers.add( generate2 );

		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertTrue( e.getMessage().contains(
					"Event event1: you can generate only multiple handlers. Did you forget to set the attribute multiple to true for generate2?" ) );
		}

	}

	@Test
	public void testUnknownActivateDeactivate() throws InvalidMvp4gConfigurationException {
		EventBusElement eventBus = new EventBusElement( EventBusWithLookup.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		EventElement event1 = newEvent( "event1" );
		event1.setActivate( new String[] { "activate" } );
		events.add( event1 );

		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( UnknownConfigurationElementException e ) {
			assertTrue( e.getMessage().contains( "Encountered a reference to unknown element 'activate'" ) );
		}

		events.clear();
		EventElement event2 = newEvent( "event2" );
		event2.setDeactivate( new String[] { "deactivate" } );
		events.add( event2 );

		try {
			configuration.validateEventHandlers();
			fail();
		} catch ( UnknownConfigurationElementException e ) {
			assertTrue( e.getMessage().contains( "Encountered a reference to unknown element 'deactivate'" ) );
		}
	}

	@Test
	public void testEventFiltersOk() throws InvalidMvp4gConfigurationException {
		EventBusElement eventBus = new EventBusElement( EventBusWithLookup.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		configuration.validateEventFilters();

		eventFilters.add( newEventFilter( "filter" ) );

		configuration.validateEventFilters();

	}

	@Test
	public void testEventFiltersWrongClass() throws InvalidMvp4gConfigurationException {
		EventBusElement eventBus = new EventBusElement( EventBusWithLookup.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		EventFilterElement filter = new EventFilterElement();
		oracle.addClass( Object.class );
		filter.setClassName( Object.class.getName() );
		eventFilters.add( filter );

		try {
			configuration.validateEventFilters();
			fail();
		} catch ( InvalidClassException e ) {
			assertTrue( e.getMessage().contains( "This class must extend " + EventFilter.class.getCanonicalName() ) );
		}

	}

	@Test
	public void testEventFiltersWrongEventBus() throws InvalidMvp4gConfigurationException {
		EventBusElement eventBus = new EventBusElement( EventBus.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		EventFilterElement filter = new EventFilterElement();
		oracle.addClass( EventFilters.EventFilter3.class );
		filter.setClassName( EventFilters.EventFilter3.class.getCanonicalName() );
		eventFilters.add( filter );

		try {
			configuration.validateEventFilters();
			fail();
		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( "Can not convert " + EventBus.class.getCanonicalName() ) );
		}

	}

	@Test
	public void testForwardEventWithParameter() throws InvalidMvp4gConfigurationException {
		configuration.setModule( oracle.addClass( Modules.Module1.class ) );
		EventElement e = newEvent( "forward" );
		e.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		events.add( e );

		StartElement start = configuration.getStart();
		start.setForwardEventType( "forward" );

		try {
			setEventBus();
			JClassType parentEventBus = oracle.addClass( EventBus.class );
			configuration.setParentEventBus( parentEventBus );
			configuration.validateEvents();
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			assertEquals( "Forward: Forward event forward can't have any object associated with it.", ex.getMessage() );
		}
	}

	@Test
	public void testForwardEventRootModule() throws InvalidMvp4gConfigurationException {
		StartElement start = configuration.getStart();
		start.setForwardEventType( "forward" );

		try {
			configuration.validateEvents();
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			assertEquals( "You can't define a forward event for RootModule since no event from parent can be forwarded to it.", ex.getMessage() );
		}
	}

	@Test
	public void testForwardOk() throws InvalidMvp4gConfigurationException {
		configuration.setModule( oracle.addClass( Modules.Module1.class ) );
		EventElement e = newEvent( "forward" );
		events.add( e );

		StartElement start = configuration.getStart();
		start.setForwardEventType( "forward" );
		setEventBus();
		JClassType parentEventBus = oracle.addClass( EventBus.class );
		configuration.setParentEventBus( parentEventBus );
		configuration.validateEvents();
	}

	@Test
	public void testSetterGetter() {
		StartElement start = new StartElement();
		configuration.setStart( start );
		assertSame( start, configuration.getStart() );

		HistoryElement history = new HistoryElement();
		configuration.setHistory( history );
		assertSame( history, configuration.getHistory() );

		EventBusElement eventBus = new EventBusElement( "", "", false );
		configuration.setEventBus( eventBus );
		assertSame( eventBus, configuration.getEventBus() );

		JClassType module = oracle.addClass( Mvp4gModule.class );
		configuration.setModule( module );
		assertSame( module, configuration.getModule() );

		JClassType parentEventBus = oracle.addClass( EventBus.class );
		configuration.setParentEventBus( parentEventBus );
		assertSame( parentEventBus, configuration.getParentEventBus() );

		String historyName = "historyName";
		configuration.setHistoryName( "historyName" );
		assertSame( historyName, configuration.getHistoryName() );

		ChildModulesElement childConfig = new ChildModulesElement();
		configuration.setLoadChildConfig( childConfig );
		assertSame( childConfig, configuration.getLoadChildConfig() );

		DebugElement debug = new DebugElement();
		configuration.setDebug( debug );
		assertSame( debug, configuration.getDebug() );

		GinModuleElement gin = new GinModuleElement();
		configuration.setGinModule( gin );
		assertSame( gin, configuration.getGinModule() );

		EventFiltersElement filters = new EventFiltersElement();
		configuration.setEventFilterConfiguration( filters );
		assertSame( filters, configuration.getEventFilterConfiguration() );

	}

	@Test
	public void validateHistoryName() throws InvalidMvp4gConfigurationException {
		EventElement element = newEvent( "forward" );
		try {
			configuration.validateHistoryName( "!test", element );
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertTrue( e.getMessage().contains(
					"history name can't start with '" + PlaceService.CRAWLABLE + "' or contain '" + PlaceService.MODULE_SEPARATOR + "'." ) );
		}
		try {
			configuration.validateHistoryName( "/test", element );
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertTrue( e.getMessage().contains(
					"history name can't start with '" + PlaceService.CRAWLABLE + "' or contain '" + PlaceService.MODULE_SEPARATOR + "'." ) );
		}
		try {
			configuration.validateHistoryName( "te/st", element );
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertTrue( e.getMessage().contains(
					"history name can't start with '" + PlaceService.CRAWLABLE + "' or contain '" + PlaceService.MODULE_SEPARATOR + "'." ) );
		}

		configuration.validateHistoryName( "test", element );
		configuration.validateHistoryName( "tes!t", element );
	}

	@Test
	public void testRootModule() {
		configuration.setModule( oracle.addClass( Mvp4gModule.class ) );
		assertTrue( configuration.isRootModule() );

		setEventBus();
		assertTrue( configuration.isRootModule() );

		configuration.setModule( oracle.addClass( Modules.Module1.class ) );
		assertTrue( configuration.isRootModule() );

		configuration.setParentEventBus( oracle.addClass( EventBusOk.class ) );
		assertFalse( configuration.isRootModule() );
	}

	@Test
	public void testRootModuleWithHistoryName() {
		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		EventBusElement eventBus = new EventBusElement( EventBusOk.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		assertTrue( configuration.isRootModule() );

		try {
			configuration.loadParentModule();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals( "Module " + Modules.ModuleWithParent.class.getCanonicalName() + " can't have an history name since it's a root module.",
					e.getMessage() );
		}

	}

	@Test
	public void testParentEventBus() throws InvalidMvp4gConfigurationException {

		EventBusElement eventBus = new EventBusElement( EventBusOk.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );

		configuration.loadParentModule();
		assertNull( configuration.getParentEventBus() );
		assertTrue( configuration.isRootModule() );

		configuration.setModule( oracle.addClass( Modules.Module1.class ) );
		setParentEventBus( Modules.Module1.class, EventBus.class );
		JClassType c = oracle.addClass( EventBus.class );
		configuration.loadParentModule();
		assertEquals( c.getQualifiedSourceName(), configuration.getParentEventBus().getQualifiedSourceName() );
		assertFalse( configuration.isRootModule() );
	}

	@Test
	public void testDebug() throws NotFoundClassException, InvalidTypeException {
		DebugElement debug = new DebugElement();
		oracle.addClass( Object.class );
		debug.setLogger( Object.class.getCanonicalName() );
		configuration.setDebug( debug );
		try {
			configuration.validateDebug();
			fail();
		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( Mvp4gLogger.class.getCanonicalName() ) );
		}

		debug = new DebugElement();
		oracle.addClass( DefaultMvp4gLogger.class );
		debug.setLogger( DefaultMvp4gLogger.class.getCanonicalName() );
		configuration.setDebug( debug );
		configuration.validateDebug();

		configuration.setDebug( null );
		configuration.validateDebug();
	}

	@Test
	public void testGin() throws InvalidMvp4gConfigurationException {
		GinModuleElement gin = new GinModuleElement();
		configuration.setGinModule( gin );
		try {
			configuration.validateGinModule();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals(
					e.getMessage(),
					"You need to define at least one GIN module. If you don't want to specify a GIN module, don't override the GIN modules option to use the default Mvp4g GIN module." );
		}

		gin = new GinModuleElement();
		oracle.addClass( Object.class );
		gin.setModules( new String[] { Object.class.getCanonicalName() } );
		configuration.setGinModule( gin );
		try {
			configuration.validateGinModule();
			fail();
		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( GinModule.class.getCanonicalName() ) );
		}

		String[] propertiesValues;
		gin = new GinModuleElement();
		oracle.addClass( DefaultMvp4gGinModule.class );
		gin.setModules( new String[] { DefaultMvp4gGinModule.class.getCanonicalName() } );
		configuration.setGinModule( gin );
		propertiesValues = configuration.validateGinModule();
		assertNull( propertiesValues );
		assertEquals( gin.getModules().size(), 1 );

		gin = new GinModuleElement();
		oracle.addClass( DefaultMvp4gGinModule.class );
		oracle.addClass( OneGinModule.class );
		gin.setModules( new String[] { DefaultMvp4gGinModule.class.getCanonicalName(), OneGinModule.class.getCanonicalName() } );
		configuration.setGinModule( gin );
		propertiesValues = configuration.validateGinModule();
		assertNull( propertiesValues );
		assertEquals( gin.getModules().size(), 2 );
	}

	@Test
	public void testGinWithProperties() throws InvalidMvp4gConfigurationException {
		String[] propertiesValues;

		GinModuleElement gin = new GinModuleElement();
		oracle.addClass( DefaultMvp4gGinModule.class );
		gin.setModules( new String[] { DefaultMvp4gGinModule.class.getCanonicalName() } );
		gin.setModuleProperties( new String[] { PropertyOracleStub.PROPERTY_OK } );
		configuration.setGinModule( gin );
		propertiesValues = configuration.validateGinModule();
		assertEquals( gin.getModules().size(), 2 );
		assertEquals( gin.getModules().get( 0 ), DefaultMvp4gGinModule.class.getCanonicalName() );
		assertEquals( gin.getModules().get( 1 ), DefaultMvp4gGinModule.class.getCanonicalName() );
		assertEquals( 1, propertiesValues.length );
		assertEquals( DefaultMvp4gGinModule.class.getCanonicalName(), propertiesValues[0] );

		gin = new GinModuleElement();
		oracle.addClass( OneGinModule.class );
		gin.setModules( new String[] { DefaultMvp4gGinModule.class.getCanonicalName() } );
		gin.setModuleProperties( new String[] { PropertyOracleStub.PROPERTY_OK, PropertyOracleStub.PROPERTY_OK2 } );
		configuration.setGinModule( gin );
		propertiesValues = configuration.validateGinModule();
		assertEquals( gin.getModules().size(), 3 );
		assertEquals( gin.getModules().get( 0 ), DefaultMvp4gGinModule.class.getCanonicalName() );
		assertEquals( gin.getModules().get( 1 ), DefaultMvp4gGinModule.class.getCanonicalName() );
		assertEquals( gin.getModules().get( 2 ), OneGinModule.class.getCanonicalName() );
		assertEquals( 2, propertiesValues.length );
		assertEquals( DefaultMvp4gGinModule.class.getCanonicalName(), propertiesValues[0] );
		assertEquals( OneGinModule.class.getCanonicalName(), propertiesValues[1] );

		gin = new GinModuleElement();
		gin.setModuleProperties( new String[] { "unknown" } );
		configuration.setGinModule( gin );
		try {
			configuration.validateGinModule();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertTrue( e.getMessage().contains( "couldn't find a value for the GIN module property unknown" ) );
		}

		gin = new GinModuleElement();
		oracle.addClass( String.class );
		gin.setModuleProperties( new String[] { PropertyOracleStub.PROPERTY_NOT_GIN_MODULE } );
		configuration.setGinModule( gin );
		try {
			configuration.validateGinModule();
			fail();
		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( GinModule.class.getCanonicalName() ) );
		}
	}

	@Test
	public void testTokenGenerationNotOk() {
		EventElement event = new EventElement();
		event.setWithTokenGeneration( "true" );
		event.setType( "event1" );
		events.add( event );

		try {
			configuration.validateHistoryConverters();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals( "Event event1: you can't generate a token for this event if it has no history converter.", e.getMessage() );
		}

		configuration.setModule( oracle.addClass( Modules.Module1.class ) );

		try {
			configuration.validateHistoryConverters();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals( "Event event1: you can't generate a token for this event if it has no history converter.", e.getMessage() );
		}

		configuration.setParentEventBus( oracle.addClass( EventBusOk.class ) );

		try {
			configuration.validateHistoryConverters();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals( "Event event1: you can't generate a token for this event if it has no history converter.", e.getMessage() );
		}
	}

	@Test
	public void testTokenGenerationOk() throws InvalidMvp4gConfigurationException {
		EventElement event = new EventElement();
		event.setWithTokenGeneration( "true" );
		event.setType( "event1" );
		event.setHistory( "history" );
		events.add( event );

		event = new EventElement();
		event.setWithTokenGeneration( "true" );
		event.setType( "event2" );
		event.setForwardToParent( "true" );
		events.add( event );

		HistoryConverterElement hc1 = newHistoryConverter( "history" );
		historyConverters.add( hc1 );

		configuration.setModule( oracle.addClass( Modules.Module1.class ) );
		setEventBus();
		configuration.setParentEventBus( oracle.addClass( EventBusOk.class ) );
		configuration.validateHistoryConverters();

	}

	private PresenterElement newPresenter( String name ) {
		PresenterElement presenter = new PresenterElement();
		presenter.setName( name );
		Class<?> c = SimplePresenter.class;
		oracle.addClass( c );
		presenter.setClassName( c.getCanonicalName() );
		return presenter;
	}

	private EventHandlerElement newEventHandler( String name ) {
		EventHandlerElement eventHandler = new EventHandlerElement();
		eventHandler.setName( name );
		Class<?> c = SimpleEventHandler.class;
		oracle.addClass( c );
		eventHandler.setClassName( c.getCanonicalName() );
		return eventHandler;
	}

	private ChildModuleElement newChildModule( String name ) {
		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setName( name );
		Class<?> c = Modules.Module1.class;
		oracle.addClass( c );
		childModule.setClassName( c.getCanonicalName() );
		return childModule;
	}

	private ViewElement newView( String name ) {
		ViewElement view = new ViewElement();
		view.setName( name );
		return view;
	}

	private EventElement newEvent( String type ) {
		EventElement event = new EventElement();
		event.setType( type );
		event.setBinds( new String[0] );
		event.setHandlers( new String[0] );
		return event;
	}
	
	private ServiceElement newService( String name ) {
		ServiceElement service = new ServiceElement();
		service.setName( name );
		return service;
	}

	private HistoryConverterElement newHistoryConverter( String name ) {
		HistoryConverterElement historyConverter = new HistoryConverterElement();
		historyConverter.setName( name );
		Class<?> c = SimpleHistoryConverter.class;
		oracle.addClass( c );
		historyConverter.setClassName( c.getCanonicalName() );
		return historyConverter;
	}

	private EventFilterElement newEventFilter( String name ) {
		EventFilterElement eventFilter = new EventFilterElement();
		eventFilter.setName( name );
		Class<?> c = EventFilters.EventFilter1.class;
		oracle.addClass( c );
		eventFilter.setClassName( c.getCanonicalName() );
		return eventFilter;
	}

	private void setEventBus() {
		EventBusElement eventBus = new EventBusElement( EventBusWithLookup.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );
	}

	private void setParentEventBus( Class<?> moduleClass, Class<?> parentEventBusClass ) {
		ChildModuleElement elt = new ChildModuleElement();
		elt.setParentEventBus( oracle.addClass( parentEventBusClass ) );
		configuration.getModuleParentEventBusClassMap().put( moduleClass.getCanonicalName(), elt );
	}

}
