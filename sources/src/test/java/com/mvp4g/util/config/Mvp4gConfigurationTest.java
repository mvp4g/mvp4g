package com.mvp4g.util.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.util.UnitTestTreeLogger;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.util.config.element.EventBusElement;
import com.mvp4g.util.config.element.EventElement;
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
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.test_tools.TypeOracleStub;
import com.mvp4g.util.test_tools.annotation.Events;
import com.mvp4g.util.test_tools.annotation.HistoryConverters;
import com.mvp4g.util.test_tools.annotation.Presenters;
import com.mvp4g.util.test_tools.annotation.Services;
import com.mvp4g.util.test_tools.annotation.HistoryConverters.SimpleHistoryConverter;
import com.mvp4g.util.test_tools.annotation.Presenters.SimplePresenter;

public class Mvp4gConfigurationTest {

	private Mvp4gConfiguration configuration;
	private Set<PresenterElement> presenters;
	private Set<ViewElement> views;
	private Set<EventElement> events;
	private Set<ServiceElement> services;
	private Set<HistoryConverterElement> historyConverters;
	private TypeOracleStub oracle = null;

	@Before
	public void setUp() {
		oracle = new TypeOracleStub();
		configuration = new Mvp4gConfiguration( new UnitTestTreeLogger.Builder().createLogger(), oracle );
		presenters = configuration.getPresenters();
		views = configuration.getViews();
		events = configuration.getEvents();
		services = configuration.getServices();
		historyConverters = configuration.getHistoryConverters();
		configuration.setStart( new StartElement() );
		configuration.setHistory( new HistoryElement() );
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
	public void testUniquenessFailureOnPresenterAndViewConflict() throws NonUniqueIdentifierException, DuplicatePropertyNameException {
		presenters.add( newPresenter( "one" ) );
		views.add( newView( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnPresenterAndEventConflict() throws NonUniqueIdentifierException, DuplicatePropertyNameException {
		presenters.add( newPresenter( "one" ) );
		events.add( newEvent( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnPresenterAndServiceConflict() throws NonUniqueIdentifierException, DuplicatePropertyNameException {
		presenters.add( newPresenter( "one" ) );
		services.add( newService( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnPresenterAndHistoryConverterConflict() throws NonUniqueIdentifierException, DuplicatePropertyNameException {
		presenters.add( newPresenter( "one" ) );
		historyConverters.add( newHistoryConverter( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnViewAndEventConflict() throws NonUniqueIdentifierException, DuplicatePropertyNameException {
		views.add( newView( "two" ) );
		events.add( newEvent( "two" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnViewAndServiceConflict() throws NonUniqueIdentifierException, DuplicatePropertyNameException {
		views.add( newView( "two" ) );
		services.add( newService( "two" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnViewAndHistoryConverterConflict() throws NonUniqueIdentifierException, DuplicatePropertyNameException {
		views.add( newView( "one" ) );
		historyConverters.add( newHistoryConverter( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnEventAndServiceConflict() throws NonUniqueIdentifierException, DuplicatePropertyNameException {
		services.add( newService( "three" ) );
		events.add( newEvent( "three" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnEventAndHistoryConverterConflict() throws NonUniqueIdentifierException, DuplicatePropertyNameException {
		events.add( newEvent( "one" ) );
		historyConverters.add( newHistoryConverter( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnServiceAndHistoryConverterConflict() throws NonUniqueIdentifierException, DuplicatePropertyNameException {
		services.add( newService( "one" ) );
		historyConverters.add( newHistoryConverter( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test
	public void testUniquenessCheckPasses() throws NonUniqueIdentifierException, DuplicatePropertyNameException {
		presenters.add( newPresenter( "one" ) );
		views.add( newView( "two" ) );
		events.add( newEvent( "three" ) );
		services.add( newService( "four" ) );
		historyConverters.add( newHistoryConverter( "five" ) );

		configuration.checkUniquenessOfAllElements();
	}
	
	@Test(expected=InvalidMvp4gConfigurationException.class)
	public void testHistoryConverterWithNoHistory() throws InvalidMvp4gConfigurationException{
		historyConverters.add( new HistoryConverterElement() );
		configuration.validateHistory();
	}
	
	@Test
	public void testHistoryOk() throws InvalidMvp4gConfigurationException, DuplicatePropertyNameException{
		configuration.getHistory().setInitEvent( "init" );
		historyConverters.add( new HistoryConverterElement() );
		configuration.validateHistory();
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testEventHandlerValidationFails() throws DuplicatePropertyNameException, UnknownConfigurationElementException, InvalidTypeException,
			InvalidClassException, NotFoundClassException {
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
	public void testEventHandlerWrongInterface() throws UnknownConfigurationElementException, InvalidTypeException, InvalidClassException,
			NotFoundClassException, DuplicatePropertyNameException {

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

	@Test( expected = InvalidTypeException.class )
	public void testEventHandlerValidationInvalidEventBus() throws UnknownConfigurationElementException, InvalidTypeException, InvalidClassException,
			NotFoundClassException, DuplicatePropertyNameException {
		try {
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
			configuration.validateEventHandlers();

		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( "Event Bus" ) );
			throw e;
		}
	}

	@Test( expected = InvalidTypeException.class )
	public void testEventHandlerValidationInvalidView() throws UnknownConfigurationElementException, InvalidTypeException, InvalidClassException,
			NotFoundClassException, DuplicatePropertyNameException {
		try {
			ViewElement view = newView( "view" );
			view.setClassName( Integer.class.getName() );
			views.add( view );

			PresenterElement presenter = newPresenter( "testHandler" );
			presenter.setView( "view" );
			presenters.add( presenter );

			EventElement event = newEvent( "testEvent" );
			event.setHandlers( new String[] { "testHandler" } );
			events.add( event );

			setEventBus();
			configuration.validateEventHandlers();

		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( "View" ) );
			throw e;
		}
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testEventHandlerViewMissing() throws UnknownConfigurationElementException, InvalidTypeException, InvalidClassException,
			NotFoundClassException, DuplicatePropertyNameException {

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
	public void testEventHandlerValidationSucceedsForPresenter() throws UnknownConfigurationElementException, InvalidTypeException,
			InvalidClassException, NotFoundClassException, DuplicatePropertyNameException {

		ViewElement view = newView( "view" );
		view.setClassName( String.class.getName() );
		views.add( view );

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
	public void testEventHandlerRemove() throws UnknownConfigurationElementException, InvalidTypeException, InvalidClassException,
			NotFoundClassException, DuplicatePropertyNameException {

		ViewElement view = newView( "view" );
		view.setClassName( String.class.getName() );
		views.add( view );

		PresenterElement presenter1 = newPresenter( "presenter1" );
		presenter1.setView( "view" );
		presenters.add( presenter1 );

		PresenterElement presenter2 = newPresenter( "presenter2" );
		presenter2.setView( "view" );
		presenters.add( presenter2 );

		EventElement event = newEvent( "testEvent" );
		event.setHandlers( new String[] { "presenter1" } );
		events.add( event );

		setEventBus();

		assertEquals( 2, presenters.size() );
		assertTrue( presenters.contains( presenter1 ) );
		assertTrue( presenters.contains( presenter2 ) );
		configuration.validateEventHandlers();
		assertEquals( 1, presenters.size() );
		assertTrue( presenters.contains( presenter1 ) );
		assertFalse( presenters.contains( presenter2 ) );

	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testPresenterViewValidationFails() throws UnknownConfigurationElementException, InvalidTypeException, InvalidClassException,
			NotFoundClassException, DuplicatePropertyNameException {
		events.add( newEvent( "badView" ) );
		services.add( newService( "badView" ) );
		presenters.add( newPresenter( "badView" ) );
		historyConverters.add( newHistoryConverter( "badView" ) );

		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.setView( "badView" );
		presenters.add( presenter );

		configuration.validateViews();
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testStartViewValidationFails() throws UnknownConfigurationElementException, InvalidTypeException, InvalidClassException,
			NotFoundClassException, DuplicatePropertyNameException {
		events.add( newEvent( "badView" ) );
		services.add( newService( "badView" ) );
		presenters.add( newPresenter( "badView" ) );
		historyConverters.add( newHistoryConverter( "badView" ) );

		configuration.getStart().setView( "badView" );

		configuration.validateViews();
	}

	@Test
	public void testStartMissing() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {
		try {
			configuration.setStart( null );
			configuration.validateStart();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {

		}

		try {
			configuration.setStart( new StartElement() );
			configuration.validateStart();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {

		}

		try {
			StartElement startElement = new StartElement();
			startElement.setView( "" );
			configuration.setStart( startElement );
			configuration.validateStart();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {

		}

		StartElement startElement = new StartElement();
		startElement.setView( "view" );
		configuration.setStart( startElement );
		configuration.validateStart();

	}

	@Test
	public void testViewValidationSucceeds() throws UnknownConfigurationElementException, InvalidTypeException, InvalidClassException,
			NotFoundClassException, DuplicatePropertyNameException {
		views.add( newView( "testView" ) );

		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.setView( "testView" );
		presenters.add( presenter );

		configuration.getStart().setView( "testView" );

		configuration.validateViews();
	}

	@Test
	public void testViewRemove() throws UnknownConfigurationElementException, InvalidTypeException, InvalidClassException, NotFoundClassException,
			DuplicatePropertyNameException {
		ViewElement view1 = newView( "view1" );
		ViewElement view2 = newView( "view2" );
		ViewElement view3 = newView( "view3" );
		views.add( view1 );
		views.add( view2 );
		views.add( view3 );

		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.setView( "view1" );
		presenters.add( presenter );

		configuration.getStart().setView( "view2" );

		assertEquals( views.size(), 3 );
		assertTrue( views.contains( view1 ) );
		assertTrue( views.contains( view2 ) );
		assertTrue( views.contains( view3 ) );
		configuration.validateViews();
		assertEquals( views.size(), 2 );
		assertTrue( views.contains( view1 ) );
		assertTrue( views.contains( view2 ) );
		assertFalse( views.contains( view3 ) );
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testInjectedServiceValidationFailsForPresenter() throws UnknownConfigurationElementException, InvalidTypeException,
			InvalidClassException, NotFoundClassException, DuplicatePropertyNameException {
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
			InvalidClassException, NotFoundClassException, DuplicatePropertyNameException {
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
	public void testInjectedServiceValidationSucceeds() throws UnknownConfigurationElementException, DuplicatePropertyNameException {
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
	public void testInjectedServiceRemove() throws UnknownConfigurationElementException, DuplicatePropertyNameException {
		ServiceElement service1 = newService( "service1" );
		ServiceElement service2 = newService( "service2" );
		ServiceElement service3 = newService( "service3" );

		services.add( service1 );
		services.add( service2 );
		services.add( service3 );

		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.getInjectedServices().add( new InjectedElement( "service1", "setTestService" ) );
		presenters.add( presenter );

		HistoryConverterElement historyConverter = newHistoryConverter( "testHistoryConverter" );
		historyConverter.getInjectedServices().add( new InjectedElement( "service2", "setTestService" ) );
		historyConverters.add( historyConverter );

		assertEquals( services.size(), 3 );
		assertTrue( services.contains( service1 ) );
		assertTrue( services.contains( service2 ) );
		assertTrue( services.contains( service3 ) );
		configuration.validateServices();
		assertEquals( services.size(), 2 );
		assertTrue( services.contains( service1 ) );
		assertTrue( services.contains( service2 ) );
		assertFalse( services.contains( service3 ) );
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testEventHistoryConverterFails() throws DuplicatePropertyNameException, InvalidClassException, InvalidTypeException,
			UnknownConfigurationElementException, NotFoundClassException {
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
	public void testEventHistoryConverterWrongInterface() throws DuplicatePropertyNameException, InvalidClassException, InvalidTypeException,
			UnknownConfigurationElementException, NotFoundClassException {

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
	public void testEventHistoryConverterWrongEventBus() throws DuplicatePropertyNameException, InvalidClassException, InvalidTypeException,
			UnknownConfigurationElementException, NotFoundClassException {

		try {
			HistoryConverterElement hc = newHistoryConverter( "testHistoryConverter" );

			historyConverters.add( hc );

			EventElement event = newEvent( "testEvent" );
			event.setHistory( "testHistoryConverter" );
			events.add( event );

			EventBusElement eventBus = new EventBusElement( EventBus.class.getName(), BaseEventBus.class.getName(), false );
			configuration.setEventBus( eventBus );
			configuration.validateHistoryConverters();
		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( "Event Bus" ) );
			throw e;
		}
	}

	@Test( expected = InvalidTypeException.class )
	public void testEventHistoryConverterWrongEventObjectClass() throws DuplicatePropertyNameException, InvalidClassException, InvalidTypeException,
			UnknownConfigurationElementException, NotFoundClassException {

		try {
			HistoryConverterElement hc = newHistoryConverter( "testHistoryConverter" );

			historyConverters.add( hc );

			EventElement event = newEvent( "testEvent" );
			event.setHistory( "testHistoryConverter" );
			event.setEventObjectClass( Integer.class.getName() );
			events.add( event );

			setEventBus();
			configuration.validateHistoryConverters();
		} catch ( InvalidTypeException e ) {
			assertTrue( e.getMessage().contains( "History Converter" ) );
			throw e;
		}
	}

	@Test
	public void testEventHistoryConverterRemove() throws DuplicatePropertyNameException, InvalidClassException, InvalidTypeException,
			UnknownConfigurationElementException, NotFoundClassException {
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
	public void testEventHistoryConverterSucceeds() throws DuplicatePropertyNameException, InvalidClassException, InvalidTypeException,
			UnknownConfigurationElementException, NotFoundClassException {
		historyConverters.add( newHistoryConverter( "testHistoryConverter" ) );

		EventElement event = newEvent( "testEvent" );
		event.setHistory( "testHistoryConverter" );
		events.add( event );

		setEventBus();
		configuration.validateHistoryConverters();
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testStartEventFails() throws DuplicatePropertyNameException, UnknownConfigurationElementException {
		services.add( newService( "badEvent" ) );
		presenters.add( newPresenter( "badEvent" ) );
		views.add( newView( "badEvent" ) );
		historyConverters.add( newHistoryConverter( "badEvent" ) );

		configuration.getStart().setEventType( "badEvent" );

		configuration.validateEvents();

	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testHistoryInitEventFails() throws DuplicatePropertyNameException, UnknownConfigurationElementException {
		services.add( newService( "badEvent" ) );
		presenters.add( newPresenter( "badEvent" ) );
		views.add( newView( "badEvent" ) );
		historyConverters.add( newHistoryConverter( "badEvent" ) );

		configuration.getHistory().setInitEvent( "badEvent" );

		configuration.validateEvents();

	}

	@Test
	public void testStartSucceeds() throws DuplicatePropertyNameException, UnknownConfigurationElementException {
		events.add( newEvent( "testEvent" ) );
		configuration.getStart().setEventType( "testEvent" );
		configuration.getHistory().setInitEvent( "testEvent" );
		configuration.validateEvents();
	}

	@Test
	public void testFindObjectClassNoChange() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {
		String className = Object.class.getName();

		EventElement e = newEvent( "event" );
		e.setEventObjectClass( className );
		events.add( e );

		assertEquals( className, e.getEventObjectClass() );
		configuration.findEventObjectClass();
		assertEquals( className, e.getEventObjectClass() );
	}

	@Test
	public void testFindObjectClass() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {
		PresenterElement presenter = new PresenterElement();
		presenter.setName( "testHandler" );
		Class<?> c = Presenters.PresenterWithEvent.class;
		presenter.setClassName( c.getCanonicalName() );
		oracle.addClass( c );
		presenters.add( presenter );

		EventElement event1 = newEvent( "event1" );
		event1.setHandlers( new String[] { "testHandler" } );
		events.add( event1 );

		EventElement event2 = newEvent( "event2" );
		event2.setHandlers( new String[] { "testHandler" } );
		events.add( event2 );

		assertNull( event1.getEventObjectClass() );
		assertNull( event2.getEventObjectClass() );
		configuration.findEventObjectClass();
		assertEquals( String.class.getName(), event1.getEventObjectClass() );
		assertNull( event2.getEventObjectClass() );

	}

	@Test( expected = InvalidMvp4gConfigurationException.class )
	public void testFindObjectClassNoMethod() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {
		PresenterElement presenter = newPresenter( "testHandler" );
		presenters.add( presenter );

		EventElement event = newEvent( "event1" );
		event.setHandlers( new String[] { "testHandler" } );
		events.add( event );

		configuration.findEventObjectClass();

	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testFindObjectClassNoHandler() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {
		EventElement event = newEvent( "event1" );
		event.setHandlers( new String[] { "testHandler" } );
		events.add( event );

		configuration.findEventObjectClass();

	}
	
	@Test
	public void testLoadEventXml() throws Exception {
		configuration.loadEvents( new ArrayList<JClassType>() );
		assertTrue( configuration.getEventBus().isXml() );
	}
	
	@Test
	public void testLoadEventAnnotation() throws Exception {
		
		List<JClassType> aPresenters = new ArrayList<JClassType>();
		aPresenters.add( oracle.findType( Presenters.PresenterWithName.class.getName() ) );
		configuration.loadPresenters( aPresenters );
		List<JClassType> aHC = new ArrayList<JClassType>();
		aHC.add( oracle.findType( HistoryConverters.HistoryConverterForEvent.class.getName() ) );
		configuration.loadHistoryConverters( aHC );
		List<JClassType> aEvents = new ArrayList<JClassType>();
		aEvents.add( oracle.findType( Events.EventBusOk.class.getName() ) );
		configuration.setStart( null );
		configuration.setHistory( null );
		configuration.loadEvents( aEvents );
		assertFalse( configuration.getEventBus().isXml() );
	}

	@Test
	public void testLoadXml() throws Exception {
		XMLConfiguration xmlConfig = new XMLConfiguration( "mvp4g-conf.xml" );

		configuration.loadEvents( xmlConfig );
		configuration.loadHistory( xmlConfig );
		configuration.loadHistoryConverters( xmlConfig );
		configuration.loadPresenters( xmlConfig );
		configuration.loadServices( xmlConfig );
		configuration.loadViews( xmlConfig );
		configuration.loadStart( xmlConfig );

		assertEquals( 8, configuration.getEvents().size() );
		assertEquals( 3, configuration.getHistoryConverters().size() );
		assertEquals( 3, configuration.getPresenters().size() );
		assertEquals( 3, configuration.getViews().size() );
		assertEquals( 3, configuration.getServices().size() );

		assertEquals( "init", configuration.getHistory().getInitEvent() );
		assertEquals( "event404", configuration.getHistory().getNotFoundEvent() );
		assertEquals( "start", configuration.getStart().getEventType() );

	}

	@Test
	public void testLoadAnnotation() throws Exception {
		
		List<JClassType> aPresenters = new ArrayList<JClassType>();
		aPresenters.add( oracle.findType( SimplePresenter.class.getName() ) );
		aPresenters.add( oracle.findType( Presenters.PresenterWithName.class.getName() ) );
		configuration.loadPresenters( aPresenters );
		assertEquals( 2, presenters.size() );
		
		List<JClassType> aHC = new ArrayList<JClassType>();
		aHC.add( oracle.findType( SimpleHistoryConverter.class.getName() ) );
		aHC.add( oracle.findType( HistoryConverters.HistoryConverterForEvent.class.getName() ) );
		configuration.loadHistoryConverters( aHC );
		assertEquals( 2, historyConverters.size() );
		
		List<JClassType> aEvents = new ArrayList<JClassType>();
		aEvents.add( oracle.findType( Events.EventBusOk.class.getName() ) );
		configuration.setStart( null );
		configuration.setHistory( null );
		configuration.loadEvents( aEvents );
		assertEquals( 2, events.size() );

		List<JClassType> aService = new ArrayList<JClassType>();
		aService.add( oracle.findType( Services.SimpleService.class.getName() ) );
		aService.add( oracle.findType( Services.ServiceWithName.class.getName() ) );
		configuration.loadServices( aService );
		assertEquals( 2, services.size() );		
		

	}

	private PresenterElement newPresenter( String name ) throws DuplicatePropertyNameException {
		PresenterElement presenter = new PresenterElement();
		presenter.setName( name );
		Class<?> c = Presenters.SimplePresenter.class;
		oracle.addClass( c );
		presenter.setClassName( c.getCanonicalName() );
		return presenter;
	}

	private ViewElement newView( String name ) throws DuplicatePropertyNameException {
		ViewElement view = new ViewElement();
		view.setName( name );
		return view;
	}

	private EventElement newEvent( String type ) throws DuplicatePropertyNameException {
		EventElement event = new EventElement();
		event.setType( type );
		return event;
	}

	private ServiceElement newService( String name ) throws DuplicatePropertyNameException {
		ServiceElement service = new ServiceElement();
		service.setName( name );
		return service;
	}

	private HistoryConverterElement newHistoryConverter( String name ) throws DuplicatePropertyNameException {
		HistoryConverterElement historyConverter = new HistoryConverterElement();
		historyConverter.setName( name );
		Class<?> c = HistoryConverters.SimpleHistoryConverter.class;
		oracle.addClass( c );
		historyConverter.setClassName( c.getCanonicalName() );
		return historyConverter;
	}

	private void setEventBus() {
		EventBusElement eventBus = new EventBusElement( EventBusWithLookup.class.getName(), BaseEventBus.class.getName(), false );
		configuration.setEventBus( eventBus );
	}

}
