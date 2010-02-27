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
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.util.config.element.ChildModuleElement;
import com.mvp4g.util.config.element.ChildModulesElement;
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
import com.mvp4g.util.test_tools.Modules;
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
	private Set<ChildModuleElement> childModules;
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
		childModules = configuration.getChildModules();
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

	@Test
	public void testFindParentModule() throws NotFoundClassException {
		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		JClassType parentEventBus = oracle.addClass( Events.EventBusOk.class );
		configuration.getOthersEventBusClassMap().put( Mvp4gModule.class.getCanonicalName(), parentEventBus );
		configuration.loadParentModule();
		assertFalse( configuration.isParentEventBusXml() );
		assertEquals( parentEventBus, configuration.getParentEventBus() );
		assertEquals( configuration.getParentModule().getQualifiedSourceName(), Mvp4gModule.class.getCanonicalName() );
	}

	@Test
	public void testHasChildModuleParent() throws NotFoundClassException {
		assertFalse( configuration.hasParentModule( Modules.ModuleWithParent.class.getCanonicalName() ) );
		oracle.addClass( Modules.Module1.class );
		assertFalse( configuration.hasParentModule( Modules.Module1.class.getCanonicalName() ) );
		oracle.addClass( Modules.ModuleWithParent.class );
		assertTrue( configuration.hasParentModule( Modules.ModuleWithParent.class.getCanonicalName() ) );
	}

	@Test
	public void testFindXmlParentModule() throws NotFoundClassException {
		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		configuration.loadParentModule();
		assertTrue( configuration.isParentEventBusXml() );
		assertEquals( EventBusWithLookup.class.getCanonicalName(), configuration.getParentEventBus().getQualifiedSourceName() );
		assertEquals( configuration.getParentModule().getQualifiedSourceName(), Mvp4gModule.class.getCanonicalName() );
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

	@Test( expected = InvalidMvp4gConfigurationException.class )
	public void testHistoryConverterWithNoHistory() throws InvalidMvp4gConfigurationException {
		historyConverters.add( new HistoryConverterElement() );
		configuration.validateHistory();
	}

	@Test
	public void testHistoryOk() throws InvalidMvp4gConfigurationException, DuplicatePropertyNameException {
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
			fail();
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
			fail();
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
			fail();
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
			fail();
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
	public void testStartEventFails() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {
		services.add( newService( "badEvent" ) );
		presenters.add( newPresenter( "badEvent" ) );
		views.add( newView( "badEvent" ) );
		historyConverters.add( newHistoryConverter( "badEvent" ) );

		configuration.getStart().setEventType( "badEvent" );

		configuration.validateEvents();

	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testHistoryInitEventFails() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {
		services.add( newService( "badEvent" ) );
		presenters.add( newPresenter( "badEvent" ) );
		views.add( newView( "badEvent" ) );
		historyConverters.add( newHistoryConverter( "badEvent" ) );

		configuration.getHistory().setInitEvent( "badEvent" );

		configuration.validateEvents();

	}

	@Test
	public void testStartSucceeds() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {
		events.add( newEvent( "testEvent" ) );
		configuration.getStart().setEventType( "testEvent" );
		configuration.getHistory().setInitEvent( "testEvent" );
		configuration.validateEvents();
	}

	@Test
	public void testChildModulesRemove() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {

		ChildModuleElement childModule1 = newChildModule( "child1" );
		childModule1.setEventToDisplayView( "testEvent" );
		childModules.add( childModule1 );

		ChildModuleElement childModule2 = newChildModule( "child2" );
		childModules.add( childModule2 );

		EventElement event = newEvent( "testEvent" );
		event.setModulesToLoad( new String[] { "child1" } );
		event.setEventObjectClass( Object.class.getCanonicalName() );
		events.add( event );

		setEventBus();

		assertEquals( 2, childModules.size() );
		assertTrue( childModules.contains( childModule1 ) );
		assertTrue( childModules.contains( childModule2 ) );
		configuration.validateChildModules();
		assertEquals( 1, childModules.size() );
		assertTrue( childModules.contains( childModule1 ) );
		assertFalse( childModules.contains( childModule2 ) );

	}

	@Test( expected = InvalidClassException.class )
	public void testInvalidChildModule() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {

		ChildModuleElement childModule1 = new ChildModuleElement();
		childModule1.setName( "child1" );
		childModule1.setClassName( Object.class.getName() );
		childModules.add( childModule1 );

		EventElement event = newEvent( "testEvent" );
		event.setModulesToLoad( new String[] { "child1" } );
		event.setEventObjectClass( Object.class.getCanonicalName() );
		events.add( event );

		setEventBus();
		configuration.validateChildModules();

	}

	@Test
	public void testAutoDisplay() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {

		ChildModuleElement childModule1 = newChildModule( "child1" );
		childModules.add( childModule1 );
		assertTrue( childModule1.isAutoDisplay() );

		EventElement event = newEvent( "testEvent" );
		event.setModulesToLoad( new String[] { "child1" } );
		event.setEventObjectClass( Object.class.getCanonicalName() );
		events.add( event );

		setEventBus();

		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		try {
			configuration.validateChildModules();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals( e.getMessage(), String.format( "%s: child module %s doesn't define any event to load its view.",
					Modules.ModuleWithParent.class.getCanonicalName(), childModule1.getClassName() ) );
		}

		childModule1.setAutoDisplay( "false" );
		configuration.validateChildModules();
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testUnknownEventForAutoDisplay() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {

		ChildModuleElement childModule1 = newChildModule( "child1" );
		childModule1.setEventToDisplayView( "unknown" );
		childModules.add( childModule1 );

		EventElement event = newEvent( "testEvent" );
		event.setModulesToLoad( new String[] { "child1" } );
		event.setEventObjectClass( Object.class.getCanonicalName() );
		events.add( event );

		setEventBus();

		configuration.validateChildModules();

	}

	@Test( expected = InvalidMvp4gConfigurationException.class )
	public void testEventWithNoObjectForAutoDisplay() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {

		ChildModuleElement childModule1 = newChildModule( "child1" );
		childModule1.setEventToDisplayView( "testEvent" );
		childModules.add( childModule1 );

		EventElement event = newEvent( "testEvent" );
		event.setModulesToLoad( new String[] { "child1" } );
		events.add( event );

		setEventBus();

		try {
			configuration.validateChildModules();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals( "Event testEvent: event must have an object associated with it as it loads a child view.", e.getMessage() );
			throw e;
		}

	}

	@Test
	public void testChildViewLoadEvent() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {

		ChildModuleElement childModule = newChildModule( "child" );
		childModule.setEventToDisplayView( "testEvent" );

		configuration.getOthersEventBusClassMap().put( Modules.Module1.class.getCanonicalName(), oracle.addClass( Events.EventBusOk.class ) );

		childModules.add( childModule );

		EventElement event = newEvent( "testEvent" );
		event.setModulesToLoad( new String[] { "child" } );
		event.setEventObjectClass( String.class.getCanonicalName() );
		events.add( event );

		setEventBus();
		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		try {
			configuration.validateChildModules();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals( e.getMessage(), String.format(
					"Child Module %s: event %s can not load child module's start view. Can not convert %s to %s.", childModule.getClassName(),
					"testEvent", Object.class.getCanonicalName(), String.class.getCanonicalName() ) );
		}

		event = newEvent( "testEvent" );
		event.setModulesToLoad( new String[] { "child" } );
		event.setEventObjectClass( Object.class.getCanonicalName() );
		events.clear();
		events.add( event );

		configuration.validateChildModules();

	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testMissingChildModule() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {
		EventElement event = newEvent( "testEvent" );
		event.setModulesToLoad( new String[] { "child" } );
		event.setEventObjectClass( String.class.getCanonicalName() );
		events.add( event );

		setEventBus();
		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		configuration.validateChildModules();
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

	@Test( expected = InvalidMvp4gConfigurationException.class )
	public void testFindObjectClassParentChildNoHandler() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {
		EventElement event = newEvent( "event1" );
		event.setForwardToParent( "true" );
		events.add( event );

		try {
			configuration.findEventObjectClass();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals(
					"Event event1: you need to define the class of the object linked to this event since it is forwarded to the parent or child module. Since no presenter of this module handles it, it couldn't be deduce automaticaly. If no object is associated with this event, set eventObjectClass attribute to \"\".",
					e.getMessage() );
		}

		events.clear();

		event = newEvent( "event1" );
		event.setModulesToLoad( new String[] { "module1" } );
		events.add( event );

		try {
			configuration.findEventObjectClass();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals(
					"Event event1: you need to define the class of the object linked to this event since it is forwarded to the parent or child module. Since no presenter of this module handles it, it couldn't be deduce automaticaly. If no object is associated with this event, set eventObjectClass attribute to \"\".",
					e.getMessage() );
			throw e;
		}
	}

	@Test
	public void testEventWithParent() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {
		EventElement e = newEvent( "event" );
		e.setForwardToParent( "true" );
		events.add( e );

		try {
			configuration.validateEvents();
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			assertEquals(
					"Event event: No parent event bus has been found for this module but this event must be forwarded to parent event bus. Have you forgotten to add setParentEventBus method to your module?",
					ex.getMessage() );
		}

		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		JClassType parentEventBus = oracle.addClass( Events.EventBusOk.class );
		configuration.getOthersEventBusClassMap().put( Mvp4gModule.class.getCanonicalName(), parentEventBus );
		configuration.loadParentModule();
		configuration.validateEvents();
	}

	@Test
	public void testUnknownLoadConfigEvent() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {
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
	public void testWrongEventLoadConfigEvent() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {

		configuration.setHistory( null );

		EventElement e = newEvent( "event" );
		e.setEventObjectClass( Object.class.getCanonicalName() );
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
			assertEquals( String.format( "%s: %s event %s can only be associated with %s", childModules.getTagName(), "Error", "event",
					Throwable.class.getName() ), ex.getMessage() );
		}

	}

	@Test
	public void testHistoryWhenParent() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {

		historyConverters.add( new HistoryConverterElement() );

		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		JClassType parentEventBus = oracle.addClass( Events.EventBusOk.class );
		configuration.getOthersEventBusClassMap().put( Mvp4gModule.class.getCanonicalName(), parentEventBus );
		configuration.loadParentModule();

		configuration.getHistory().setInitEvent( "event" );
		try {
			configuration.validateHistory();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals( "History configuration (init and not found event should be configure only for root module (only module with no parent)", e
					.getMessage() );
		}

		HistoryElement history = new HistoryElement();
		configuration.setHistory( history );
		history.setNotFoundEvent( "event" );
		try {
			configuration.validateHistory();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertEquals( "History configuration (init and not found event should be configure only for root module (only module with no parent)", e
					.getMessage() );
		}

		history = new HistoryElement();
		configuration.setHistory( history );
		configuration.validateHistory();
		assertNull( configuration.getHistory() );

	}

	@Test
	public void testHistoryName() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {

		historyConverters.add( new HistoryConverterElement() );

		configuration.setModule( oracle.addClass( Modules.ModuleWithParentNoName.class ) );
		JClassType parentEventBus = oracle.addClass( Events.EventBusOk.class );
		configuration.getOthersEventBusClassMap().put( Mvp4gModule.class.getCanonicalName(), parentEventBus );
		configuration.loadParentModule();

		assertNull( configuration.getHistoryName() );

		try {
			configuration.validateHistory();
			fail();
		} catch ( InvalidMvp4gConfigurationException e ) {
			assertTrue( e.getMessage().contains( "Child module that defines history converter must have a" ) );
		}

		configuration.setModule( oracle.addClass( Modules.ModuleWithParent.class ) );
		configuration.loadParentModule();
		assertEquals( "moduleWithParent", configuration.getHistoryName() );

		configuration.validateHistory();
	}

	@Test
	public void testFindChildModuleHistory() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {

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
	public void testFindChildModuleSameHistory() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {

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
	public void testEventLoadConfigEventOk() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {

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
		e.setEventObjectClass( Throwable.class.getCanonicalName() );
		events.add( e );

		childModules = new ChildModulesElement();
		configuration.setLoadChildConfig( childModules );
		childModules.setErrorEvent( "event2" );
		configuration.validateEvents();

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
		configuration.loadChildConfig( xmlConfig );
		configuration.loadChildModules( xmlConfig );
		configuration.loadDebug( xmlConfig );

		assertEquals( 11, configuration.getEvents().size() );
		assertEquals( 3, configuration.getHistoryConverters().size() );
		assertEquals( 3, configuration.getPresenters().size() );
		assertEquals( 3, configuration.getViews().size() );
		assertEquals( 3, configuration.getServices().size() );
		assertEquals( 2, configuration.getChildModules().size() );

		assertEquals( "init", configuration.getHistory().getInitEvent() );
		assertEquals( "event404", configuration.getHistory().getNotFoundEvent() );
		assertEquals( "start", configuration.getStart().getEventType() );

		assertEquals( "beforeLoad", configuration.getLoadChildConfig().getBeforeEvent() );
		assertEquals( "afterLoad", configuration.getLoadChildConfig().getAfterEvent() );
		assertEquals( "errorOnLoad", configuration.getLoadChildConfig().getErrorEvent() );

		assertTrue( configuration.getEventBus().isXml() );
		assertTrue( configuration.getDebug().isEnabled() );

	}

	@Test
	public void testLoadAnnotation() throws Exception {
		configuration.setModule( oracle.addClass( Mvp4gModule.class ) );

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

	@Test
	public void testAsyncEnabled() {
		assertEquals( configuration.isAsyncEnabled(), true );
		oracle.setGWT2( false );
		assertEquals( configuration.isAsyncEnabled(), false );
	}

	@Test
	public void testStartEventWithParameter() throws DuplicatePropertyNameException, InvalidMvp4gConfigurationException {
		EventElement e = newEvent( "start" );
		e.setEventObjectClass( Object.class.getCanonicalName() );
		events.add( e );

		StartElement start = configuration.getStart();
		start.setView( "startView" );
		start.setEventType( "start" );

		try {
			configuration.validateStart();
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			assertEquals( "start: start event start can't have any object associated with it.", ex.getMessage() );
		}

		events.clear();
		e = newEvent( "start" );
		events.add( e );
		configuration.validateStart();
	}

	private PresenterElement newPresenter( String name ) throws DuplicatePropertyNameException {
		PresenterElement presenter = new PresenterElement();
		presenter.setName( name );
		Class<?> c = Presenters.SimplePresenter.class;
		oracle.addClass( c );
		presenter.setClassName( c.getCanonicalName() );
		return presenter;
	}

	private ChildModuleElement newChildModule( String name ) throws DuplicatePropertyNameException {
		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setName( name );
		Class<?> c = Modules.Module1.class;
		oracle.addClass( c );
		childModule.setClassName( c.getCanonicalName() );
		return childModule;
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
