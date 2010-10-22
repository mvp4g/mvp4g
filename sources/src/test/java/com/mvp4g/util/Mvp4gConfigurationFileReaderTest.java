package com.mvp4g.util;

import static junit.framework.Assert.assertEquals;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.util.UnitTestTreeLogger;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.DefaultMvp4gGinModule;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.BaseEventBusWithLookUp;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.history.ClearHistory;
import com.mvp4g.client.history.PlaceService;
import com.mvp4g.util.config.Mvp4gConfiguration;
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
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.test_tools.Modules;
import com.mvp4g.util.test_tools.OneGinModule;
import com.mvp4g.util.test_tools.OneLogger;
import com.mvp4g.util.test_tools.SourceWriterTestStub;
import com.mvp4g.util.test_tools.TypeOracleStub;
import com.mvp4g.util.test_tools.annotation.EventHandlers;
import com.mvp4g.util.test_tools.annotation.HistoryConverters;
import com.mvp4g.util.test_tools.annotation.Presenters;
import com.mvp4g.util.test_tools.annotation.EventFilters.EventFilter1;
import com.mvp4g.util.test_tools.annotation.EventFilters.EventFilter2;
import com.mvp4g.util.test_tools.annotation.HistoryConverters.SimpleHistoryConverter;
import com.mvp4g.util.test_tools.annotation.Presenters.SimplePresenter;

public class Mvp4gConfigurationFileReaderTest {

	private SourceWriterTestStub sourceWriter;
	private Mvp4gConfigurationFileWriter writer;
	private Mvp4gConfiguration configuration;

	@Before
	public void setUp() throws DuplicatePropertyNameException {
		sourceWriter = new SourceWriterTestStub();
		TreeLogger tl = new UnitTestTreeLogger.Builder().createLogger();
		configuration = new Mvp4gConfiguration( tl, new TypeOracleStub() );
		writer = new Mvp4gConfigurationFileWriter( sourceWriter, configuration );

		String eventBusInterface = EventBus.class.getName();
		String eventBusClass = BaseEventBus.class.getName();
		configuration.setEventBus( new EventBusElement( eventBusInterface, eventBusClass, false ) );

		ViewElement view = new ViewElement();
		view.setClassName( Object.class.getName() );
		view.setName( "startView" );
		configuration.getViews().add( view );

		PresenterElement presenter = new PresenterElement();
		presenter.setClassName( SimplePresenter.class.getCanonicalName() );
		presenter.setName( "startPresenter" );
		presenter.setView( "startView" );
		configuration.getPresenters().add( presenter );

		StartElement start = new StartElement();
		start.setView( "startView" );
		configuration.setStart( start );

		GinModuleElement ginModule = new GinModuleElement();
		ginModule.setModules( new String[] { DefaultMvp4gGinModule.class.getCanonicalName() } );
		configuration.setGinModule( ginModule );

		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		configuration.setModule( oracle.addClass( Mvp4gModule.class ) );

	}

	@Test
	public void testWriteEventBusClass() {

		EventBusElement eventBus = configuration.getEventBus();

		String[] output = { "private abstract class AbstractEventBus extends " + eventBus.getAbstractClassName() + " implements "
				+ eventBus.getInterfaceClassName() + "{}" };

		assertOutput( output, false );
		writer.writeConf();
		assertOutput( output, true );
	}

	@Test
	public void testWriteEvents() throws DuplicatePropertyNameException {

		assertOutput( getExpectedEvents(), false );
		assertOutput( getExpectedNotNavigationEvents(), false );
		assertOutput( getExpectedEventsInheritMethods(), false );
		assertOutput( getExpectedEventsWithLookup(), false );
		assertOutput( getExpectedNavigationEvents(), false );

		createHandlers();

		Set<EventElement> events = configuration.getEvents();

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setHandlers( new String[] { "handler1" } );
		e1.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setHandlers( new String[] { "handler2" } );
		e2.setEventObjectClass( new String[] { "java.lang.String" } );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );
		e3.setHandlers( new String[] { "handler3" } );

		EventElement e4 = new EventElement();
		e4.setType( "event4" );
		e4.setHandlers( new String[] { "handler4" } );

		events.add( e1 );
		events.add( e2 );
		events.add( e3 );
		events.add( e4 );

		writer.writeConf();

		assertOutput( getExpectedEvents(), true );
		assertOutput( getExpectedNotNavigationEvents(), true );
		assertOutput( getExpectedEventsInheritMethods(), true );
		assertOutput( getExpectedEventsWithLookup(), false );
		assertOutput( getExpectedNavigationEvents(), false );

	}

	@Test
	public void testWriteActivateDeactivate() throws DuplicatePropertyNameException {

		assertOutput( getExpectedActivateDeactivate(), false );

		createHandlers();

		Set<EventElement> events = configuration.getEvents();

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setHandlers( new String[] { "handler1" } );
		e1.setActivate( new String[] { "handler3", "handler4" } );
		e1.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setActivate( new String[] {} );
		e2.setHandlers( new String[] { "handler2" } );
		e2.setEventObjectClass( new String[] { "java.lang.String" } );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );
		e3.setHandlers( new String[] { "handler3" } );
		e3.setDeactivate( new String[] { "handler1", "handler2" } );

		EventElement e4 = new EventElement();
		e4.setType( "event4" );
		e4.setHandlers( new String[] { "handler4" } );
		e4.setDeactivate( new String[] {} );

		events.add( e1 );
		events.add( e2 );
		events.add( e3 );
		events.add( e4 );

		writer.writeConf();

		assertOutput( getExpectedActivateDeactivate(), true );

	}

	@Test
	public void testWriteNavigationEvents() throws DuplicatePropertyNameException {

		assertOutput( getExpectedNotNavigationEvents(), false );
		assertOutput( getExpectedNavigationEvents(), false );

		Set<EventElement> events = configuration.getEvents();

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setNavigationEvent( "true" );
		e1.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );

		events.add( e1 );

		writer.writeConf();

		assertOutput( getExpectedNotNavigationEvents(), false );
		assertOutput( getExpectedNavigationEvents(), true );

	}

	@Test
	public void testWriteEventsWithHistory() throws DuplicatePropertyNameException {

		assertOutput( getExpectedHistoryEvents(), false );

		createHandlers();

		HistoryConverterElement c1 = new HistoryConverterElement();
		c1.setClassName( ClearHistory.class.getCanonicalName() );
		c1.setName( "clear" );
		HistoryConverterElement c2 = new HistoryConverterElement();
		c2.setClassName( SimpleHistoryConverter.class.getCanonicalName() );
		c2.setName( "history2" );
		c2.setConvertParams( Boolean.FALSE.toString() );
		configuration.getHistoryConverters().add( c1 );
		configuration.getHistoryConverters().add( c2 );

		Set<EventElement> events = configuration.getEvents();

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setHandlers( new String[] { "handler1" } );
		e1.setHistory( "history" );
		e1.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setHandlers( new String[] { "handler2" } );
		e2.setHistory( "history" );
		e2.setHistoryName( "historyName" );
		e2.setEventObjectClass( new String[] { "java.lang.String" } );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );
		e3.setHandlers( new String[] { "handler3" } );
		e3.setHistory( "clear" );

		EventElement e4 = new EventElement();
		e4.setType( "event4" );
		e4.setHandlers( new String[] { "handler3" } );
		e4.setHistory( "history2" );

		events.add( e1 );
		events.add( e2 );
		events.add( e3 );
		events.add( e4 );

		writer.writeConf();

		assertOutput( getExpectedHistoryEvents(), true );
	}

	@Test
	public void testWriteEventsWithLookup() throws DuplicatePropertyNameException {

		String eventBusInterface = EventBusWithLookup.class.getName();
		String eventBusClass = BaseEventBusWithLookUp.class.getName();
		configuration.setEventBus( new EventBusElement( eventBusInterface, eventBusClass, true ) );

		assertOutput( getExpectedEvents(), false );
		assertOutput( getExpectedEventsInheritMethods(), false );
		assertOutput( getExpectedEventsWithLookup(), false );

		createHandlers();

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setHandlers( new String[] { "handler1" } );
		e1.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setHandlers( new String[] { "handler2" } );
		e2.setEventObjectClass( new String[] { "java.lang.String" } );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );
		e3.setHandlers( new String[] { "handler3" } );

		EventElement e4 = new EventElement();
		e4.setType( "event4" );
		e4.setHandlers( new String[] { "handler4" } );

		Set<EventElement> events = configuration.getEvents();
		events.add( e1 );
		events.add( e2 );
		events.add( e3 );
		events.add( e4 );

		writer.writeConf();

		assertOutput( getExpectedEvents(), true );
		assertOutput( getExpectedEventsWithLookup(), true );
		assertOutput( getExpectedEventsInheritMethods(), true );

	}

	@Test
	public void testWriteViews() throws DuplicatePropertyNameException {

		ViewElement view1 = new ViewElement();
		view1.setName( "rootView" );
		view1.setClassName( "com.mvp4g.util.test_tools.RootView" );
		view1.setInstantiateAtStart( true );

		ViewElement view2 = new ViewElement();
		view2.setName( "userCreateView" );
		view2.setClassName( "com.mvp4g.example.client.view.UserCreateView" );
		view2.setInstantiateAtStart( true );

		ViewElement view3 = new ViewElement();
		view3.setName( "userDisplayView" );
		view3.setClassName( "com.mvp4g.example.client.view.display.UserDisplayView" );
		view3.setInstantiateAtStart( true );

		Set<ViewElement> views = configuration.getViews();
		views.add( view1 );
		views.add( view2 );
		views.add( view3 );

		assertOutput( getExpectedViews(), false );

		writer.writeConf();

		assertOutput( getExpectedViews(), true );

	}

	@Test
	public void testWriteViewsNotAtStart() throws DuplicatePropertyNameException {

		ViewElement view1 = new ViewElement();
		view1.setName( "rootView" );
		view1.setClassName( "com.mvp4g.util.test_tools.RootView" );
		view1.setInstantiateAtStart( false );

		ViewElement view2 = new ViewElement();
		view2.setName( "userCreateView" );
		view2.setClassName( "com.mvp4g.example.client.view.UserCreateView" );
		view2.setInstantiateAtStart( false );

		ViewElement view3 = new ViewElement();
		view3.setName( "userDisplayView" );
		view3.setClassName( "com.mvp4g.example.client.view.display.UserDisplayView" );
		view3.setInstantiateAtStart( false );

		Set<ViewElement> views = configuration.getViews();
		views.add( view1 );
		views.add( view2 );
		views.add( view3 );

		assertOutput( getExpectedViews(), false );

		writer.writeConf();

		assertOutput( getExpectedViews(), false );

	}

	@Test
	public void testWritePresenters() throws DuplicatePropertyNameException {

		PresenterElement p1 = new PresenterElement();
		p1.setName( "rootPresenter" );
		p1.setClassName( "com.mvp4g.util.test_tools.RootPresenter" );
		p1.setView( "rootView" );

		PresenterElement p2 = new PresenterElement();
		p2.setName( "createUserPresenter" );
		p2.setClassName( "com.mvp4g.example.client.presenter.UserCreatePresenter" );
		p2.setView( "userCreateView" );
		p2.getInjectedServices().add( new InjectedElement( "userService", "setUserService" ) );

		PresenterElement p3 = new PresenterElement();
		p3.setName( "displayUserPresenter" );
		p3.setClassName( "com.mvp4g.example.client.presenter.display.UserDisplayPresenter" );
		p3.setView( "userDisplayView" );

		Set<PresenterElement> presenters = configuration.getPresenters();
		presenters.add( p1 );
		presenters.add( p2 );
		presenters.add( p3 );

		assertOutput( getExpectedPresenters(), false );
		assertOutput( getReverseView(), false );
		writer.writeConf();
		assertOutput( getExpectedPresenters(), true );
		assertOutput( getReverseView(), false );
	}
	
	@Test
	public void testWritePresentersWithReverseView() throws DuplicatePropertyNameException {

		PresenterElement p1 = new PresenterElement();
		p1.setName( "rootPresenter" );
		p1.setClassName( "com.mvp4g.util.test_tools.RootPresenter" );
		p1.setView( "rootView" );
		p1.setInverseView( "true" );

		PresenterElement p2 = new PresenterElement();
		p2.setName( "createUserPresenter" );
		p2.setClassName( "com.mvp4g.example.client.presenter.UserCreatePresenter" );
		p2.setView( "userCreateView" );
		p2.getInjectedServices().add( new InjectedElement( "userService", "setUserService" ) );

		PresenterElement p3 = new PresenterElement();
		p3.setName( "displayUserPresenter" );
		p3.setClassName( "com.mvp4g.example.client.presenter.display.UserDisplayPresenter" );
		p3.setView( "userDisplayView" );

		Set<PresenterElement> presenters = configuration.getPresenters();
		presenters.add( p1 );
		presenters.add( p2 );
		presenters.add( p3 );

		assertOutput( getExpectedPresenters(), false );
		assertOutput( getReverseView(), false );
		writer.writeConf();
		assertOutput( getExpectedPresenters(), true );
		assertOutput( getReverseView(), true );
	}
	
	@Test
	public void testWriteMultiplePresenters() throws DuplicatePropertyNameException {

		PresenterElement p1 = new PresenterElement();
		p1.setName( "rootPresenter" );
		p1.setClassName( "com.mvp4g.util.test_tools.RootPresenter" );
		p1.setView( "rootView" );
		p1.setMultiple( "true" );

		configuration.getPresenters().add( p1 );
		
		ViewElement view = new ViewElement();
		view.setName( "rootView" );
		view.setClassName( "com.mvp4g.util.test_tools.RootView" );
		
		configuration.getViews().add( view );

		assertOutput( getReverseView(), false );
		assertOutput( getMultiplePresenters(), false );
		writer.writeConf();
		assertOutput( getReverseView(), false );
		assertOutput( getMultiplePresenters(), true );
	}

	@Test
	public void testWriteMultiplePresentersWithInverseView() throws DuplicatePropertyNameException {

		PresenterElement p1 = new PresenterElement();
		p1.setName( "rootPresenter" );
		p1.setClassName( "com.mvp4g.util.test_tools.RootPresenter" );
		p1.setView( "rootView" );
		p1.setMultiple( "true" );
		p1.setInverseView( "true" );

		configuration.getPresenters().add( p1 );
		
		ViewElement view = new ViewElement();
		view.setName( "rootView" );
		view.setClassName( "com.mvp4g.util.test_tools.RootView" );
		
		configuration.getViews().add( view );

		assertOutput( getReverseView(), false );
		assertOutput( getMultiplePresenters(), false );
		writer.writeConf();
		assertOutput( getReverseView(), true );
		assertOutput( getMultiplePresenters(), true );
	}	
	
	@Test
	public void testWriteServices() throws DuplicatePropertyNameException {

		ServiceElement s1 = new ServiceElement();
		s1.setName( "userRpcService" );
		s1.setClassName( "com.mvp4g.example.client.rpc.UserService" );

		ServiceElement s2 = new ServiceElement();
		s2.setName( "userService" );
		s2.setClassName( "com.mvp4g.example.client.services.UserService" );
		s2.setPath( "/service/user" );

		ServiceElement s3 = new ServiceElement();
		s3.setName( "userDisplayService" );
		s3.setClassName( "com.mvp4g.example.client.services.display.UserService" );

		Set<ServiceElement> services = configuration.getServices();
		services.add( s1 );
		services.add( s2 );
		services.add( s3 );

		assertOutput( getExpectedServices(), false );
		writer.writeConf();
		assertOutput( getExpectedServices(), true );

	}

	@Test
	public void testWriteXmlStart() throws DuplicatePropertyNameException {

		String eventBusInterface = EventBusWithLookup.class.getName();
		String eventBusClass = BaseEventBusWithLookUp.class.getName();
		configuration.setEventBus( new EventBusElement( eventBusInterface, eventBusClass, false ) );

		StartElement start = new StartElement();
		start.setView( "startView" );
		start.setEventType( "start" );
		start.setHistory( "true" );
		configuration.setStart( start );

		assertOutput( getExpectedStartXmlEvent(), false );
		assertOutput( getExpectedStartPresenterView(), false );
		assertOutput( getExpectedStartPresenterViewMultiple(), false );
		writer.writeConf();
		assertOutput( getExpectedStartXmlEvent(), true );
		assertOutput( getExpectedStartPresenterView(), true );
		assertOutput( getExpectedStartPresenterViewMultiple(), false );

	}

	@Test
	public void testWriteStart() throws DuplicatePropertyNameException {

		StartElement start = configuration.getStart();
		start.setEventType( "start" );
		start.setHistory( Boolean.TRUE.toString() );

		assertOutput( getExpectedStartEvent(), false );
		assertOutput( getExpectedNoFiltering(), false );
		assertOutput( getExpectedStartPresenterView(), false );
		assertOutput( getExpectedStartPresenterViewMultiple(), false );
		writer.writeConf();
		assertOutput( getExpectedStartEvent(), true );
		assertOutput( getExpectedNoFiltering(), false );
		assertOutput( getExpectedStartPresenterView(), true );
		assertOutput( getExpectedStartPresenterViewMultiple(), false );
	}

	@Test
	public void testWriteStartNoFilter() throws DuplicatePropertyNameException {

		StartElement start = configuration.getStart();
		start.setEventType( "start" );
		start.setHistory( Boolean.TRUE.toString() );

		EventFiltersElement filterConf = new EventFiltersElement();
		filterConf.setFilterStart( Boolean.FALSE.toString() );
		configuration.setEventFilterConfiguration( filterConf );

		assertOutput( getExpectedStartEvent(), false );
		assertOutput( getExpectedNoFiltering(), false );
		assertOutput( getExpectedStartPresenterView(), false );
		assertOutput( getExpectedStartPresenterViewMultiple(), false );
		writer.writeConf();
		assertOutput( getExpectedStartEvent(), true );
		assertOutput( getExpectedNoFiltering(), true );
		assertOutput( getExpectedStartPresenterView(), true );
		assertOutput( getExpectedStartPresenterViewMultiple(), false );

	}

	@Test
	public void testWriteStartMultiple() throws DuplicatePropertyNameException {

		StartElement start = configuration.getStart();
		start.setEventType( "start" );
		start.setHistory( Boolean.TRUE.toString() );

		configuration.getPresenters().iterator().next().setMultiple( Boolean.TRUE.toString() );

		assertOutput( getExpectedStartEvent(), false );
		assertOutput( getExpectedNoFiltering(), false );
		assertOutput( getExpectedStartPresenterView(), false );
		assertOutput( getExpectedStartPresenterViewMultiple(), false );
		writer.writeConf();
		assertOutput( getExpectedStartEvent(), true );
		assertOutput( getExpectedNoFiltering(), false );
		assertOutput( getExpectedStartPresenterView(), false );
		assertOutput( getExpectedStartPresenterViewMultiple(), true );

	}

	@Test
	public void testWriteNoForward() throws DuplicatePropertyNameException {

		assertOutput( getExpectedNoForwardEvent(), false );
		assertOutput( getExpectedForwardEvent(), false );
		assertOutput( getExpectedNoFiltering(), false );
		writer.writeConf();
		assertOutput( getExpectedNoForwardEvent(), true );
		assertOutput( getExpectedForwardEvent(), false );
		assertOutput( getExpectedNoFiltering(), false );

	}

	@Test
	public void testWriteForward() throws DuplicatePropertyNameException {

		StartElement start = configuration.getStart();
		start.setForwardEventType( "forward" );

		assertOutput( getExpectedNoForwardEvent(), false );
		assertOutput( getExpectedForwardEvent(), false );
		assertOutput( getExpectedNoFiltering(), false );
		writer.writeConf();
		assertOutput( getExpectedNoForwardEvent(), true );
		assertOutput( getExpectedForwardEvent(), true );
		assertOutput( getExpectedNoFiltering(), false );

	}

	@Test
	public void testWriteForwardNoFilter() throws DuplicatePropertyNameException {

		StartElement start = configuration.getStart();
		start.setForwardEventType( "forward" );

		EventFiltersElement filterConf = new EventFiltersElement();
		filterConf.setFilterForward( Boolean.FALSE.toString() );
		configuration.setEventFilterConfiguration( filterConf );

		assertOutput( getExpectedNoForwardEvent(), false );
		assertOutput( getExpectedForwardEvent(), false );
		assertOutput( getExpectedNoFiltering(), false );
		writer.writeConf();
		assertOutput( getExpectedNoForwardEvent(), true );
		assertOutput( getExpectedForwardEvent(), true );
		assertOutput( getExpectedNoFiltering(), true );

	}

	@Test
	public void testHistory() throws DuplicatePropertyNameException {

		HistoryConverterElement hc1 = new HistoryConverterElement();
		hc1.setName( "userConverter" );
		hc1.setClassName( "com.mvp4g.example.client.history.display.UserHistoryConverter" );
		hc1.getInjectedServices().add( new InjectedElement( "userService", "setUserService" ) );

		HistoryConverterElement hc2 = new HistoryConverterElement();
		hc2.setName( "userConverter" );
		hc2.setClassName( "com.mvp4g.example.client.history.display.UserHistoryConverter" );

		HistoryConverterElement hc3 = new HistoryConverterElement();
		hc3.setName( "stringConverter" );
		hc3.setClassName( "com.mvp4g.example.client.history.StringHistoryConverter" );

		Set<HistoryConverterElement> hcs = configuration.getHistoryConverters();
		hcs.add( hc1 );
		hcs.add( hc2 );
		hcs.add( hc3 );

		HistoryElement history = new HistoryElement();
		history.setInitEvent( "init" );
		history.setNotFoundEvent( "notFound" );
		configuration.setHistory( history );

		assertOutput( getExpectedDefaultHistory(), false );
		assertOutput( getExpectedWithHistory(), false );
		assertOutput( getExpectedInheritModuleMethods(), false );
		writer.writeConf();
		assertOutput( getExpectedDefaultHistory(), true );
		assertOutput( getExpectedWithHistory(), true );
		assertOutput( getExpectedInheritModuleMethods(), true );

	}

	@Test
	public void testWriteXmlHistoryStart() throws DuplicatePropertyNameException {
		String eventBusInterface = EventBusWithLookup.class.getName();
		String eventBusClass = BaseEventBusWithLookUp.class.getName();
		configuration.setEventBus( new EventBusElement( eventBusInterface, eventBusClass, false ) );

		HistoryElement history = new HistoryElement();
		history.setInitEvent( "init" );
		history.setNotFoundEvent( "notFound" );
		configuration.setHistory( history );

		assertOutput( getExpectedDefaultHistory(), false );
		assertOutput( getExpectedHistoryXml(), false );
		assertOutput( getExpectedInheritModuleMethods(), false );
		writer.writeConf();
		assertOutput( getExpectedDefaultHistory(), true );
		assertOutput( getExpectedHistoryXml(), true );
		assertOutput( getExpectedInheritModuleMethods(), true );

	}

	@Test
	public void testWriteHistoryWithConfig() throws DuplicatePropertyNameException {
		String eventBusInterface = EventBusWithLookup.class.getName();
		String eventBusClass = BaseEventBusWithLookUp.class.getName();
		configuration.setEventBus( new EventBusElement( eventBusInterface, eventBusClass, false ) );

		HistoryElement history = new HistoryElement();
		history.setParamSeparatorAlwaysAdded( "true" );
		history.setParamSeparator( PlaceService.CRAWLABLE );
		configuration.setHistory( history );

		assertOutput( getExpectedHistoryWithConfig(), false );
		writer.writeConf();
		assertOutput( getExpectedHistoryWithConfig(), true );

	}

	@Test
	public void testWriteNoHistoryStart() throws DuplicatePropertyNameException {

		configuration.setHistory( null );

		assertOutput( getExpectedDefaultHistory(), false );
		assertOutput( getExpectedWithHistory(), false );
		assertOutput( getExpectedInheritModuleMethods(), false );
		writer.writeConf();
		assertOutput( getExpectedDefaultHistory(), true );
		assertOutput( getExpectedWithHistory(), false );
		assertOutput( getExpectedInheritModuleMethods(), true );

		String eventBusInterface = EventBusWithLookup.class.getName();
		String eventBusClass = BaseEventBusWithLookUp.class.getName();
		configuration.setEventBus( new EventBusElement( eventBusInterface, eventBusClass, false ) );

		assertOutput( getExpectedHistoryXml(), false );
		writer.writeConf();
		assertOutput( getExpectedHistoryXml(), false );

	}

	@Test
	public void testWriteGetters() throws DuplicatePropertyNameException {

		assertOutput( getExpectedGetters(), false );
		writer.writeConf();
		assertOutput( getExpectedGetters(), true );

	}

	@Test
	public void testWriteNoParent() {
		assertOutput( getExpectedSetNoParent(), false );
		writer.writeConf();
		assertOutput( getExpectedSetNoParent(), true );
	}

	@Test
	public void testWriteParent() {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		configuration.setParentEventBus( oracle.addClass( EventBusWithLookup.class ) );
		configuration.setModule( oracle.addClass( Modules.Module1.class ) );

		assertOutput( new String[] { "private PlaceService placeService = null;" }, false );
		assertOutput( getExpectedSetParent(), false );
		writer.writeConf();
		assertOutput( getExpectedSetParent(), true );
		assertOutput( new String[] { "private PlaceService placeService = null;" }, false );
	}

	@Test
	public void testWriteChildNoAsyncNoAutoLoad() throws DuplicatePropertyNameException {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.ModuleWithParent.class );
		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule" );
		childModule.setAsync( "false" );
		childModule.setAutoDisplay( "false" );

		configuration.getChildModules().add( childModule );

		assertOutput( getExpectedChildModule( Modules.ModuleWithParent.class.getCanonicalName() ), false );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
		writer.writeConf();
		assertOutput( getExpectedChildModule( Modules.ModuleWithParent.class.getCanonicalName() ), true );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
	}

	@Test
	public void testWriteChildWithParentAsyncNoAutoLoadNotGwt2() throws DuplicatePropertyNameException {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.Module1.class );
		oracle.setGWT2( false );
		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule" );
		childModule.setAutoDisplay( "false" );

		EventElement event = new EventElement();
		event.setType( "errorOnLoad" );
		event.setEventObjectClass( new String[] { Throwable.class.getCanonicalName() } );
		configuration.getEvents().add( event );

		ChildModulesElement loadConfig = new ChildModulesElement();
		loadConfig.setAfterEvent( "afterLoad" );
		loadConfig.setBeforeEvent( "beforeLoad" );
		loadConfig.setErrorEvent( "errorOnLoad" );
		configuration.getChildModules().add( childModule );
		configuration.setLoadChildConfig( loadConfig );

		assertOutput( getExpectedChildModule( Modules.Module1.class.getCanonicalName() ), false );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
		writer.writeConf();
		assertOutput( getExpectedChildModule( Modules.Module1.class.getCanonicalName() ), true );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
	}

	@Test
	public void testWriteChildWithParentAsyncNoAutoLoadErrorEmpty() throws DuplicatePropertyNameException {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.Module1.class );
		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule" );
		childModule.setAutoDisplay( "false" );

		EventElement event = new EventElement();
		event.setType( "errorOnLoad" );
		configuration.getEvents().add( event );

		ChildModulesElement loadConfig = new ChildModulesElement();
		loadConfig.setAfterEvent( "afterLoad" );
		loadConfig.setBeforeEvent( "beforeLoad" );
		loadConfig.setErrorEvent( "errorOnLoad" );
		configuration.getChildModules().add( childModule );
		configuration.setLoadChildConfig( loadConfig );

		assertOutput( getExpectedChildModule( Modules.Module1.class.getCanonicalName() ), false );
		assertOutput( getExpectedAsyncChildModuleErrorEmpty(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
		writer.writeConf();
		assertOutput( getExpectedChildModule( Modules.Module1.class.getCanonicalName() ), true );
		assertOutput( getExpectedAsyncChildModuleErrorEmpty(), true );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
	}

	@Test
	public void testWriteChildWithParentAsyncNoAutoLoad() throws DuplicatePropertyNameException {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.ModuleWithParent.class );

		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule" );
		childModule.setAutoDisplay( "false" );

		EventElement event = new EventElement();
		event.setType( "errorOnLoad" );
		event.setEventObjectClass( new String[] { Throwable.class.getCanonicalName() } );
		configuration.getEvents().add( event );

		ChildModulesElement loadConfig = new ChildModulesElement();
		loadConfig.setAfterEvent( "afterLoad" );
		loadConfig.setBeforeEvent( "beforeLoad" );
		loadConfig.setErrorEvent( "errorOnLoad" );
		configuration.getChildModules().add( childModule );
		configuration.setLoadChildConfig( loadConfig );

		assertOutput( getExpectedChildModule( Modules.ModuleWithParent.class.getCanonicalName() ), false );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
		writer.writeConf();
		assertOutput( getExpectedChildModule( Modules.ModuleWithParent.class.getCanonicalName() ), true );
		assertOutput( getExpectedAsyncChildModule(), true );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
	}

	@Test
	public void testWriteChildWithParentAsyncoAutoLoad() throws DuplicatePropertyNameException {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.ModuleWithParent.class );

		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule" );
		childModule.setEventToDisplayView( "changeBody" );

		EventElement event = new EventElement();
		event.setType( "errorOnLoad" );
		event.setEventObjectClass( new String[] { Throwable.class.getCanonicalName() } );
		configuration.getEvents().add( event );

		event = new EventElement();
		event.setType( "changeBody" );
		event.setEventObjectClass( new String[] { Widget.class.getCanonicalName() } );
		configuration.getEvents().add( event );

		ChildModulesElement loadConfig = new ChildModulesElement();
		loadConfig.setAfterEvent( "afterLoad" );
		loadConfig.setBeforeEvent( "beforeLoad" );
		loadConfig.setErrorEvent( "errorOnLoad" );
		configuration.getChildModules().add( childModule );
		configuration.setLoadChildConfig( loadConfig );

		assertOutput( getExpectedChildModule( Modules.ModuleWithParent.class.getCanonicalName() ), false );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
		writer.writeConf();
		assertOutput( getExpectedChildModule( Modules.ModuleWithParent.class.getCanonicalName() ), true );
		assertOutput( getExpectedAsyncChildModule(), true );
		assertOutput( getExpectedAutoDisplayChildModule(), true );
	}

	@Test
	public void testWriteChildEventXML() throws DuplicatePropertyNameException {

		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.ModuleWithParent.class );

		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "child" );
		childModule.setAutoDisplay( "false" );
		childModule.setAsync( "false" );
		configuration.getChildModules().add( childModule );

		EventElement event = new EventElement();
		event.setType( "test" );
		event.setModulesToLoad( new String[] { "child" } );
		event.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		configuration.getEvents().add( event );

		assertOutput( getExpectedEventChildModuleLoadXML(), false );
		writer.writeConf();
		assertOutput( getExpectedEventChildModuleLoadXML(), true );
	}

	@Test
	public void testWriteChildEvent() throws DuplicatePropertyNameException {

		configuration.setLoadChildConfig( new ChildModulesElement() );
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.ModuleWithParent.class );

		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "child" );
		childModule.setAutoDisplay( "false" );
		childModule.setAsync( "false" );
		configuration.getChildModules().add( childModule );

		EventElement event1 = new EventElement();
		event1.setType( "event1" );
		event1.setModulesToLoad( new String[] { "child" } );
		EventElement event2 = new EventElement();
		event2.setType( "event2" );
		event2.setEventObjectClass( new String[] { "java.lang.String" } );
		event2.setModulesToLoad( new String[] { "child" } );
		EventElement event3 = new EventElement();
		event3.setType( "event3" );
		event3.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );
		event3.setModulesToLoad( new String[] { "child" } );
		configuration.getEvents().add( event1 );
		configuration.getEvents().add( event2 );
		configuration.getEvents().add( event3 );
		configuration.getOthersEventBusClassMap().put( Modules.ModuleWithParent.class.getCanonicalName(),
				oracle.addClass( com.mvp4g.util.test_tools.annotation.Events.EventBusOk.class ) );

		assertOutput( getExpectedEventChildModuleLoad(), false );
		writer.writeConf();
		assertOutput( getExpectedEventChildModuleLoad(), true );
	}

	@Test
	public void testWriteForwardParent() throws DuplicatePropertyNameException {

		EventElement event = new EventElement();
		event.setType( "test" );
		event.setForwardToParent( "true" );
		event.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		configuration.getEvents().add( event );

		assertOutput( getExpectedForwardParent(), false );
		writer.writeConf();
		assertOutput( getExpectedForwardParent(), true );
	}

	@Test
	public void testWriteForwardParentXML() throws DuplicatePropertyNameException {

		EventElement event = new EventElement();
		event.setType( "test" );
		event.setForwardToParent( "true" );
		configuration.getEvents().add( event );

		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		configuration.setParentEventBus( oracle.addClass( EventBusWithLookup.class ) );

		assertOutput( getExpectedForwardParentXML(), false );
		writer.writeConf();
		assertOutput( getExpectedForwardParentXML(), true );
	}

	@Test
	public void testWriteForwardParentXMLWithForm() throws DuplicatePropertyNameException {

		EventElement event = new EventElement();
		event.setType( "test" );
		event.setForwardToParent( "true" );
		event.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		configuration.getEvents().add( event );

		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		configuration.setParentEventBus( oracle.addClass( EventBusWithLookup.class ) );

		assertOutput( getExpectedForwardParentXMLWithForm(), false );
		writer.writeConf();
		assertOutput( getExpectedForwardParentXMLWithForm(), true );
	}

	@Test
	public void testWriteDebug() throws DuplicatePropertyNameException {

		EventElement event = new EventElement();
		event.setType( "test" );
		configuration.getEvents().add( event );

		event = new EventElement();
		event.setType( "test2" );
		event.setEventObjectClass( new String[] { "java.lang.String" } );
		configuration.getEvents().add( event );

		event = new EventElement();
		event.setType( "test3" );
		event.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );
		configuration.getEvents().add( event );

		DebugElement debug = new DebugElement();
		configuration.setDebug( debug );
		assertOutput( getExpectedDebug(), false );
		writer.writeConf();
		assertOutput( getExpectedDebug(), true );
	}

	@Test
	public void testWriteComplexDebug() throws DuplicatePropertyNameException {

		createHandlers();

		EventElement event = new EventElement();
		event.setType( "test" );
		event.setHandlers( new String[] { "handler1" } );
		event.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		configuration.getEvents().add( event );

		event = new EventElement();
		event.setType( "test2" );
		event.setHandlers( new String[] { "handler2" } );
		configuration.getEvents().add( event );

		DebugElement debug = new DebugElement();
		debug.setLogger( OneLogger.class.getName() );
		debug.setLogLevel( LogLevel.DETAILED.toString() );
		configuration.setDebug( debug );
		assertOutput( getExpectedDetailedDebug(), false );
		writer.writeConf();
		assertOutput( getExpectedDetailedDebug(), true );
	}

	@Test
	public void testWriteChildModuleMethods() {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		configuration.setModule( oracle.addClass( Modules.Module1.class ) );
		configuration.setParentEventBus( oracle.addClass( EventBusWithLookup.class ) );
		configuration.setHistoryName( "child" );

		assertOutput( getExpectedChildMethod(), false );
		writer.writeConf();
		assertOutput( getExpectedChildMethod(), true );
	}

	@Test
	public void testWriteParentHistory() throws DuplicatePropertyNameException {

		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.ModuleWithParent.class );

		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "child" );
		childModule.setHistoryName( "child" );
		childModule.setAutoDisplay( "false" );
		childModule.setAsync( "false" );
		configuration.getChildModules().add( childModule );

		EventElement event = new EventElement();
		event.setType( "test" );
		event.setModulesToLoad( new String[] { "child" } );
		configuration.getEvents().add( event );
		configuration.getOthersEventBusClassMap().put( Modules.ModuleWithParent.class.getCanonicalName(),
				oracle.addClass( com.mvp4g.util.test_tools.annotation.Events.EventBusOk.class ) );

		assertOutput( getExpectedHistoryParent(), false );
		writer.writeConf();
		assertOutput( getExpectedHistoryParent(), true );

	}

	@Test
	public void testWriteGinInjector() throws DuplicatePropertyNameException {

		assertOutput( getExpectedGinInjector(), false );
		assertOutput( getExpectedDefaultGinModule(), false );
		assertOutput( getExpectedCustomGinModule(), false );

		createHandlers();
		writer.writeConf();

		assertOutput( getExpectedGinInjector(), true );
		assertOutput( getExpectedDefaultGinModule(), true );
		assertOutput( getExpectedCustomGinModule(), false );

	}

	@Test
	public void testWriteCustomGinInjector() throws DuplicatePropertyNameException {

		assertOutput( getExpectedGinInjector(), false );
		assertOutput( getExpectedDefaultGinModule(), false );
		assertOutput( getExpectedCustomGinModule(), false );

		createHandlers();
		GinModuleElement ginModule = new GinModuleElement();
		ginModule.setModules( new String[] { OneGinModule.class.getName(), DefaultMvp4gGinModule.class.getCanonicalName() } );
		configuration.setGinModule( ginModule );
		writer.writeConf();

		assertOutput( getExpectedGinInjector(), true );
		assertOutput( getExpectedDefaultGinModule(), false );
		assertOutput( getExpectedCustomGinModule(), true );

	}

	@Test
	public void testWriteEventFilters() throws DuplicatePropertyNameException {

		assertOutput( getExpectedEventFiltersInstantiation(), false );
		assertOutput( getExpectedEventFilters(), false );

		EventFilterElement filter = new EventFilterElement();
		filter.setClassName( EventFilter1.class.getCanonicalName() );
		filter.setName( "filter1" );
		EventFilterElement filter2 = new EventFilterElement();
		filter2.setClassName( EventFilter2.class.getCanonicalName() );
		filter2.setName( "filter2" );

		configuration.getEventFilters().add( filter );
		configuration.getEventFilters().add( filter2 );

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setEventObjectClass( new String[] { "java.lang.String" } );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );

		configuration.getEvents().add( e1 );
		configuration.getEvents().add( e2 );
		configuration.getEvents().add( e3 );

		writer.writeConf();

		assertOutput( getExpectedEventFilters(), true );
		assertOutput( getExpectedEventFiltersInstantiation(), true );

	}

	@Test
	public void testWriteNoEventFilters() throws DuplicatePropertyNameException {

		assertOutput( getExpectedEventFilters(), false );
		assertOutput( getExpectedEventFiltersInstantiation(), false );

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setEventObjectClass( new String[] { "java.lang.String" } );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );

		configuration.getEvents().add( e1 );
		configuration.getEvents().add( e2 );
		configuration.getEvents().add( e3 );

		writer.writeConf();

		assertOutput( getExpectedEventFilters(), false );
		assertOutput( getExpectedEventFiltersInstantiation(), false );

	}

	@Test
	public void testWriteForceEventFilters() throws DuplicatePropertyNameException {

		assertOutput( getExpectedEventFilters(), false );
		assertOutput( getExpectedEventFiltersInstantiation(), false );

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setEventObjectClass( new String[] { "java.lang.String" } );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );

		configuration.getEvents().add( e1 );
		configuration.getEvents().add( e2 );
		configuration.getEvents().add( e3 );

		EventFiltersElement filterConf = new EventFiltersElement();
		filterConf.setForceFilters( Boolean.TRUE.toString() );
		configuration.setEventFilterConfiguration( filterConf );

		writer.writeConf();

		assertOutput( getExpectedEventFilters(), true );
		assertOutput( getExpectedEventFiltersInstantiation(), false );

	}

	@Test
	public void testWriteEventFiltersWithLog() throws DuplicatePropertyNameException {

		assertOutput( getExpectedEventFilters(), false );
		assertOutput( getExpectedEventFiltersLog(), false );
		assertOutput( getExpectedEventFiltersInstantiation(), false );

		EventFilterElement filter = new EventFilterElement();
		filter.setClassName( EventFilter1.class.getCanonicalName() );
		filter.setName( "filter1" );
		EventFilterElement filter2 = new EventFilterElement();
		filter2.setClassName( EventFilter2.class.getCanonicalName() );
		filter2.setName( "filter2" );

		configuration.getEventFilters().add( filter );
		configuration.getEventFilters().add( filter2 );

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setEventObjectClass( new String[] { "java.lang.String" } );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );

		configuration.getEvents().add( e1 );
		configuration.getEvents().add( e2 );
		configuration.getEvents().add( e3 );

		DebugElement debug = new DebugElement();
		debug.setLogLevel( LogLevel.DETAILED.name() );
		configuration.setDebug( debug );

		writer.writeConf();

		assertOutput( getExpectedEventFilters(), true );
		assertOutput( getExpectedEventFiltersLog(), true );
		assertOutput( getExpectedEventFiltersInstantiation(), true );

	}

	@Test
	public void testWriteAfterEventFilters() throws DuplicatePropertyNameException {

		assertOutput( getExpectedEventFilters(), false );
		assertOutput( getExpectedEventFiltersInstantiation(), false );

		EventFilterElement filter = new EventFilterElement();
		filter.setClassName( EventFilter1.class.getCanonicalName() );
		filter.setName( "filter1" );
		EventFilterElement filter2 = new EventFilterElement();
		filter2.setClassName( EventFilter2.class.getCanonicalName() );
		filter2.setName( "filter2" );

		configuration.getEventFilters().add( filter );
		configuration.getEventFilters().add( filter2 );

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setEventObjectClass( new String[] { "java.lang.String" } );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );

		configuration.getEvents().add( e1 );
		configuration.getEvents().add( e2 );
		configuration.getEvents().add( e3 );

		EventFiltersElement filterConf = new EventFiltersElement();
		filterConf.setAfterHistory( Boolean.TRUE.toString() );
		configuration.setEventFilterConfiguration( filterConf );

		writer.writeConf();

		assertOutput( getExpectedEventFilters(), true );
		assertOutput( getExpectedEventFiltersInstantiation(), true );

	}

	@Test
	public void testWriteEventWithPrimitives() throws DuplicatePropertyNameException {

		assertOutput( getExpectedPrimitives(), false );

		configuration.setLoadChildConfig( new ChildModulesElement() );
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.ModuleWithParent.class );

		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "child" );
		childModule.setAutoDisplay( "false" );
		childModule.setAsync( "false" );
		configuration.getChildModules().add( childModule );

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setModulesToLoad( new String[] { "child" } );
		e1.setEventObjectClass( new String[] { "boolean", "byte", "char", "double", "float", "int", "long", "short" } );

		configuration.getEvents().add( e1 );

		writer.writeConf();

		assertOutput( getExpectedPrimitives(), true );

	}

	private void assertOutput( String[] statements, boolean expected ) {
		String error = null;
		if ( expected ) {
			error = " not found in output data:\n" + sourceWriter.getData();
		} else {
			error = " unexpected in output data:\n" + sourceWriter.getData();
		}

		for ( String statement : statements ) {
			assertEquals( statement + error, expected, sourceWriter.dataContains( statement ) );
		}

	}

	private String[] getExpectedViews() {
		return new String[] { "final com.mvp4g.util.test_tools.RootView " + "rootView = injector.getrootView();",

		"final com.mvp4g.example.client.view.UserCreateView " + "userCreateView = injector.getuserCreateView();",

		"final com.mvp4g.example.client.view.display.UserDisplayView " + "userDisplayView = injector.getuserDisplayView();" };
	}

	private String[] getExpectedWithHistory() {
		return new String[] { "eventBus.init();", "eventBus.notFound()",
				"final com.mvp4g.example.client.history.display.UserHistoryConverter userConverter = injector.getuserConverter()",
				"userConverter.setUserService(userService);",
				"final com.mvp4g.example.client.history.StringHistoryConverter stringConverter = injector.getstringConverter()", };

	}

	private String[] getExpectedDefaultHistory() {
		return new String[] { "placeService = new PlaceService(\"?\",false){", "protected void sendInitEvent(){",
				"protected void sendNotFoundEvent(){", "placeService.setModule(itself);", };

	}

	private String[] getExpectedInheritModuleMethods() {
		return new String[] { "public void addConverter(String eventType, String historyName, HistoryConverter<?> hc){",
				"placeService.addConverter(eventType, historyName, hc);", "public void place(String token, String form){",
				"placeService.place( token, form );", "public void dispatchHistoryEvent(String eventType, final Mvp4gEventPasser passer){",
				"int index = eventType.indexOf(PlaceService.MODULE_SEPARATOR);", "if(index > -1){",
				"String moduleHistoryName = eventType.substring(0, index);", "String nextToken = eventType.substring(index + 1);",
				"Mvp4gEventPasser nextPasser = new Mvp4gEventPasser(nextToken) {", "public void pass(Mvp4gModule module) {",
				"module.dispatchHistoryEvent((String) eventObjects[0], passer);", "passer.setEventObject(false);", "passer.pass(this);", "}else{",
				"passer.pass(this);", "public void confirmEvent( NavigationEventCommand event ){",
				"placeService.setNavigationConfirmation(navigationConfirmation);",
				"public void setNavigationConfirmation( NavigationConfirmationInterface navigationConfirmation ) {",
				"placeService.confirmEvent(event);" };
	}

	private String[] getExpectedHistoryXml() {
		return new String[] { "eventBus.dispatch(\"init\");", "eventBus.dispatch(\"notFound\")" };

	}

	private String[] getExpectedHistoryWithConfig() {
		return new String[] { "placeService = new PlaceService(\"!\",true){" };

	}

	private String[] getExpectedPresenters() {
		return new String[] {
				"final com.mvp4g.util.test_tools.RootPresenter rootPresenter = injector.getrootPresenter();",
				"rootPresenter.setEventBus(eventBus);",
				"rootPresenter.setView(rootView);",

				"final com.mvp4g.example.client.presenter.UserCreatePresenter " + "createUserPresenter = injector.getcreateUserPresenter()",

				"createUserPresenter.setEventBus(eventBus);",
				"createUserPresenter.setView(userCreateView);",
				"createUserPresenter.setUserService(userService);",

				"final com.mvp4g.example.client.presenter.display.UserDisplayPresenter "
						+ "displayUserPresenter = injector.getdisplayUserPresenter();",

				"displayUserPresenter.setEventBus(eventBus);", "displayUserPresenter.setView(userDisplayView);" };
	}
	
	private String[] getReverseView() {
		return new String[] {
				"rootView.setPresenter(rootPresenter);"
		};
	}
	
	private String[] getMultiplePresenters(){
		return new String[] {
				"if (com.mvp4g.util.test_tools.RootPresenter.class.equals(handlerClass)){",
				"com.mvp4g.util.test_tools.RootPresenter rootPresenter = injector.getrootPresenter();",
				"com.mvp4g.util.test_tools.RootView rootView = injector.getrootView();",
				"rootPresenter.setView(rootView);",
				"rootPresenter.setEventBus(eventBus);",
				"return (T) rootPresenter;"
		};
	}

	private String[] getExpectedEvents() {
		return new String[] {
				"public void event4(){",
				"List<com.mvp4g.util.test_tools.annotation.EventHandlers.EventHandlerWithEvent> handlershandler4 = getHandlers(com.mvp4g.util.test_tools.annotation.EventHandlers.EventHandlerWithEvent.class);",
				"if(handlershandler4!= null){",
				"com.mvp4g.util.test_tools.annotation.EventHandlers.EventHandlerWithEvent handler;",
				"int handlerCount = handlershandler4.size();",
				"for(int i=0; i<handlerCount; i++){",
				"handler = handlershandler4.get(i);",
				"if (handler.isActivated()){",
				"handler.onEvent4();",
				"public void event2(java.lang.String attr0){",
				"List<com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter> handlershandler2 = getHandlers(com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter.class);",
				"if(handlershandler2!= null){", "com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter handler;",
				"int handlerCount = handlershandler2.size();", "for(int i=0; i<handlerCount; i++){", "handler = handlershandler2.get(i);",
				"if (handler.isActivated()){", "handler.onEvent2(attr0);", "public void event3(){", "if (handler3.isActivated()){",
				"handler3.onEvent3();", "if (handler1.isActivated()){", "handler1.onEvent1(attr0,attr1);" };
	}

	private String[] getExpectedNotNavigationEvents() {
		return new String[] { "public void event1(java.lang.String attr0,java.lang.Object attr1){" };
	};

	private String[] getExpectedEventsInheritMethods() {
		return new String[] { "public void setNavigationConfirmation( NavigationConfirmationInterface navigationConfirmation ) {",
				"itself.setNavigationConfirmation(navigationConfirmation);", "public void confirmNavigation(NavigationEventCommand event){",
				"itself.confirmEvent(event);","protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ){" };
	}

	private String[] getExpectedHistoryEvents() {
		return new String[] { "place( itself, \"event2\",history.onEvent2(attr0));", "clearHistory(itself);",
				"place( itself, \"event1\",history.onEvent1(attr0,attr1));", "place( itself, \"event4\",null);",
				"addConverter( \"event4\", \"event4\",history2);", "addConverter( \"event2\", \"historyName\",history);",
				"addConverter( \"event1\", \"event1\",history);" };
	}

	private String[] getExpectedEventsWithLookup() {
		return new String[] {
				"public void dispatch( String eventType, Object... data ){",
				"try{",
				"if ( \"event4\".equals( eventType ) ){",
				"event4();",
				"} else if ( \"event2\".equals( eventType ) ){",
				"event2((java.lang.String) data[0]);",
				"} else if ( \"event3\".equals( eventType ) ){",
				"event3();",
				"if ( \"event1\".equals( eventType ) ){",
				"event1((java.lang.String) data[0],(java.lang.Object) data[1]);",
				"} else {",
				"throw new Mvp4gException( \"Event \" + eventType + \" doesn't exist. Have you forgotten to add it to your Mvp4g configuration file?\" );",
				"} catch ( ClassCastException e ) {", "handleClassCastException( e, eventType );" };
	}

	private String[] getExpectedStartXmlEvent() {
		return new String[] { "eventBus.dispatch(\"start\");", "startPresenter.setActivated(true);", "History.fireCurrentHistoryState();" };
	}

	private String[] getExpectedStartPresenterView() {
		return new String[] { "this.startPresenter = startPresenter;", "this.startView = startView;" };
	}

	private String[] getExpectedStartPresenterViewMultiple() {
		return new String[] { "this.startPresenter = eventBus.addHandler(com.mvp4g.util.test_tools.annotation.Presenters.SimplePresenter.class);",
				"this.startView = startPresenter.getView();" };
	}

	private String[] getExpectedStartEvent() {
		return new String[] { "eventBus.start();", "startPresenter.setActivated(true);", "History.fireCurrentHistoryState();" };
	}

	private String[] getExpectedNoForwardEvent() {
		return new String[] { "public void onForward(){" };
	}

	private String[] getExpectedForwardEvent() {
		return new String[] { "eventBus.forward();" };
	}

	private String[] getExpectedServices() {
		return new String[] {
				"final com.mvp4g.example.client.rpc.UserServiceAsync "
						+ "userRpcService = GWT.create(com.mvp4g.example.client.rpc.UserService.class);",
				"final com.mvp4g.example.client.services.UserServiceAsync "
						+ "userService = GWT.create(com.mvp4g.example.client.services.UserService.class);",
				"((ServiceDefTarget) userService).setServiceEntryPoint(\"/service/user\");",
				"final com.mvp4g.example.client.services.display.UserServiceAsync "
						+ "userDisplayService = GWT.create(com.mvp4g.example.client.services.display.UserService.class);" };
	}

	private String[] getExpectedGetters() {
		return new String[] { "public Object getStartView(){", "return startView;", "public EventBus getEventBus(){", "return eventBus;" };
	}

	private String[] getExpectedSetNoParent() {
		return new String[] { "public void setParentModule(com.mvp4g.client.Mvp4gModule module){}", "private PlaceService placeService = null;" };
	}

	private String[] getExpectedSetParent() {
		return new String[] { "public void setParentModule(com.mvp4g.client.Mvp4gModule module){", "parentModule = module;",
				"parentEventBus = (com.mvp4g.client.event.EventBusWithLookup) module.getEventBus();" };
	}

	private String[] getExpectedChildModule( String moduleClassName ) {
		return new String[] { "private void loadchildModule(final Mvp4gEventPasser passer){",
				moduleClassName + " newModule = (" + moduleClassName + ") modules.get(" + moduleClassName + ".class);", "if(newModule == null){",
				"newModule = GWT.create(" + moduleClassName + ".class);", "modules.put(" + moduleClassName + ".class, newModule);",
				"newModule.createAndStartModule();", "if(passer != null) passer.pass(newModule);", "newModule.setParentModule(itself);" };
	}

	private String[] getExpectedAsyncChildModule() {
		return new String[] { "eventBus.beforeLoad();", "GWT.runAsync(new com.google.gwt.core.client.RunAsyncCallback() {",
				"public void onSuccess() {", "eventBus.afterLoad();", "public void onFailure(Throwable reason) {", "eventBus.afterLoad();",
				"eventBus.errorOnLoad(reason);" };
	}

	private String[] getExpectedAsyncChildModuleErrorEmpty() {
		return new String[] { "eventBus.beforeLoad();", "GWT.runAsync(new com.google.gwt.core.client.RunAsyncCallback() {",
				"public void onSuccess() {", "eventBus.afterLoad();", "public void onFailure(Throwable reason) {", "eventBus.afterLoad();",
				"eventBus.errorOnLoad();" };
	}

	private String[] getExpectedAutoDisplayChildModule() {
		return new String[] { "eventBus.changeBody((com.google.gwt.user.client.ui.Widget) newModule.getStartView());" };
	}

	private String[] getExpectedEventChildModuleLoadXML() {
		return new String[] { "loadchild(new Mvp4gEventPasser(attr0){", "public void pass(Mvp4gModule module){",
				"com.mvp4g.client.event.EventBusWithLookup eventBus = (com.mvp4g.client.event.EventBusWithLookup) module.getEventBus();",
				"eventBus.dispatch(\"test\", (java.lang.Object) eventObjects[0]);" };
	}

	private String[] getExpectedEventChildModuleLoad() {
		return new String[] {
				"loadchild(new Mvp4gEventPasser(attr0){",
				"public void pass(Mvp4gModule module){",
				"com.mvp4g.util.test_tools.annotation.Events.EventBusOk eventBus = (com.mvp4g.util.test_tools.annotation.Events.EventBusOk) module.getEventBus();",
				"eventBus.event2((java.lang.String) eventObjects[0]);",
				"loadchild(new Mvp4gEventPasser(attr0,attr1){",
				"com.mvp4g.util.test_tools.annotation.Events.EventBusOk eventBus = (com.mvp4g.util.test_tools.annotation.Events.EventBusOk) module.getEventBus();",
				"eventBus.event3((java.lang.String) eventObjects[0],(java.lang.Object) eventObjects[1]);",
				"loadchild(new Mvp4gEventPasser(){",
				"public void pass(Mvp4gModule module){",
				"com.mvp4g.util.test_tools.annotation.Events.EventBusOk eventBus = (com.mvp4g.util.test_tools.annotation.Events.EventBusOk) module.getEventBus();",
				"eventBus.event1();" };
	}

	private String[] getExpectedForwardParent() {
		return new String[] { "parentEventBus.test(attr0);" };
	}

	private String[] getExpectedForwardParentXML() {
		return new String[] { "parentEventBus.dispatch(\"test\");" };
	}

	private String[] getExpectedForwardParentXMLWithForm() {
		return new String[] { "parentEventBus.dispatch(\"test\", attr0);" };
	}

	private String[] getExpectedDebug() {
		return new String[] { "final com.mvp4g.client.event.DefaultMvp4gLogger logger = new com.mvp4g.client.event.DefaultMvp4gLogger();",
				"int startLogDepth = BaseEventBus.logDepth;", "++BaseEventBus.logDepth;", "++BaseEventBus.logDepth;",
				"BaseEventBus.logDepth = startLogDepth;",
				"logger.log(\"Module: Mvp4gModule || event: test2 || param(s): \" + attr0, BaseEventBus.logDepth);",
				"logger.log(\"Module: Mvp4gModule || event: test3 || param(s): \" + attr0+ \", \" + attr1, BaseEventBus.logDepth);",
				"logger.log(\"Module: Mvp4gModule || event: test\", BaseEventBus.logDepth);", };
	}

	private String[] getExpectedDetailedDebug() {
		return new String[] { "final com.mvp4g.util.test_tools.OneLogger logger = new com.mvp4g.util.test_tools.OneLogger();",
				"int startLogDepth = BaseEventBus.logDepth;", "++BaseEventBus.logDepth;", "++BaseEventBus.logDepth;",
				"BaseEventBus.logDepth = startLogDepth;", "logger.log(\"Module: Mvp4gModule || event: test2\", BaseEventBus.logDepth);",
				"logger.log(\"Module: Mvp4gModule || event: test || param(s): \" + attr0, BaseEventBus.logDepth);",
				"logger.log(handler.toString() + \" handles test2\", BaseEventBus.logDepth);" };
	}

	private String[] getExpectedChildMethod() {
		return new String[] { "parentModule.addConverter(\"child/\" + eventType, \"child/\" + historyName, hc);",
				"parentModule.place(\"child/\" + token, form );", "parentModule.place(\"child/\" + token, form );", "parentModule.clearHistory();",
				"parentModule.setNavigationConfirmation(navigationConfirmation);", "parentModule.confirmEvent(event);" };
	}

	private String[] getExpectedHistoryParent() {
		return new String[] { "if(\"child\".equals(moduleHistoryName)){", "loadchild(nextPasser);", "return;" };
	}

	private String[] getExpectedDefaultGinModule() {
		return new String[] { "@GinModules({com.mvp4g.client.DefaultMvp4gGinModule.class})" };
	}

	private String[] getExpectedCustomGinModule() {
		return new String[] { "@GinModules({com.mvp4g.util.test_tools.OneGinModule.class,com.mvp4g.client.DefaultMvp4gGinModule.class})" };
	}

	private String[] getExpectedActivateDeactivate() {
		return new String[] {
				"handler1.setActivated(false);",
				"List<com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter> handlershandler2de = getHandlers(com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter.class);",
				"if(handlershandler2de!= null){",
				"com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter handler;",
				"int handlerCount = handlershandler2de.size();",
				"for(int i=0; i<handlerCount; i++){",
				"handler = handlershandler2de.get(i);",
				"handler.setActivated(false);",
				"handler3.setActivated(true);",
				"List<com.mvp4g.util.test_tools.annotation.EventHandlers.EventHandlerWithEvent> handlershandler4act = getHandlers(com.mvp4g.util.test_tools.annotation.EventHandlers.EventHandlerWithEvent.class);",
				"if(handlershandler4act!= null){", "com.mvp4g.util.test_tools.annotation.EventHandlers.EventHandlerWithEvent handler;",
				"int handlerCount = handlershandler4act.size();", "for(int i=0; i<handlerCount; i++){", "handler = handlershandler4act.get(i);",
				"handler.setActivated(true);" };
	}

	public String[] getExpectedNavigationEvents() {
		return new String[] { "public void event1(final java.lang.String attr0,final java.lang.Object attr1){",
				"itself.confirmEvent(new NavigationEventCommand(this){", "public void execute(){" };
	}

	private String[] getExpectedGinInjector() {
		return new String[] { "public interface Mvp4gGinjector extends Ginjector {",
				"com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter gethandler2();",
				"com.mvp4g.util.test_tools.annotation.Presenters.SimplePresenter gethandler1();",
				"com.mvp4g.util.test_tools.annotation.EventHandlers.SimpleEventHandler gethandler3();",
				"com.mvp4g.util.test_tools.annotation.EventHandlers.EventHandlerWithEvent gethandler4();", "java.lang.String getview();",
				"com.mvp4g.util.test_tools.annotation.HistoryConverters.SimpleHistoryConverter gethistory();" };
	}

	private String[] getExpectedEventFiltersInstantiation() {
		return new String[] { "com.mvp4g.util.test_tools.annotation.EventFilters.EventFilter2 getfilter2();",
				"com.mvp4g.util.test_tools.annotation.EventFilters.EventFilter1 getfilter1();",
				"final com.mvp4g.util.test_tools.annotation.EventFilters.EventFilter2 filter2 = injector.getfilter2();",
				"eventBus.addEventFilter(filter2);",
				"final com.mvp4g.util.test_tools.annotation.EventFilters.EventFilter1 filter1 = injector.getfilter1();",
				"eventBus.addEventFilter(filter1);" };
	}

	private String[] getExpectedEventFilters() {
		return new String[] { "if (!filterEvent(\"event2\",attr0)){", "if (!filterEvent(\"event3\")){", "if (!filterEvent(\"event1\",attr0,attr1)){",
				"return;", };
	}

	private String[] getExpectedPrimitives() {
		return new String[] { "eventBus.dispatch(\"event1\", (Boolean) eventObjects[0],(Byte) eventObjects[1],(Character) eventObjects[2],(Double) eventObjects[3],(Float) eventObjects[4],(Integer) eventObjects[5],(Long) eventObjects[6],(Short) eventObjects[7]);" };
	}

	private String[] getExpectedEventFiltersLog() {
		return new String[] { "logger.log(\"event event2 didn't pass filter(s)\", BaseEventBus.logDepth);",
				"logger.log(\"event event3 didn't pass filter(s)\", BaseEventBus.logDepth);",
				"logger.log(\"event event1 didn't pass filter(s)\", BaseEventBus.logDepth);" };
	}

	private String[] getExpectedNoFiltering() {
		return new String[] { "eventBus.setFilteringEnabledForNextOne(false);" };
	}

	private void createHandlers() throws DuplicatePropertyNameException {
		Set<PresenterElement> presenters = configuration.getPresenters();
		Set<EventHandlerElement> eventHandlers = configuration.getEventHandlers();

		ViewElement view = new ViewElement();
		view.setName( "view" );
		view.setClassName( String.class.getCanonicalName() );
		configuration.getViews().add( view );

		HistoryConverterElement history = new HistoryConverterElement();
		history.setName( "history" );
		history.setClassName( HistoryConverters.SimpleHistoryConverter.class.getCanonicalName() );
		configuration.getHistoryConverters().add( history );

		PresenterElement p1 = new PresenterElement();
		p1.setName( "handler1" );
		p1.setMultiple( Boolean.FALSE.toString() );
		p1.setClassName( Presenters.SimplePresenter.class.getCanonicalName() );
		p1.setView( "view" );
		PresenterElement p2 = new PresenterElement();
		p2.setName( "handler2" );
		p2.setMultiple( Boolean.TRUE.toString() );
		p2.setClassName( Presenters.MultiplePresenter.class.getCanonicalName() );
		p2.setView( "view" );
		presenters.add( p1 );
		presenters.add( p2 );

		EventHandlerElement eh1 = new EventHandlerElement();
		eh1.setName( "handler3" );
		eh1.setMultiple( Boolean.FALSE.toString() );
		eh1.setClassName( EventHandlers.SimpleEventHandler.class.getCanonicalName() );
		EventHandlerElement eh2 = new EventHandlerElement();
		eh2.setName( "handler4" );
		eh2.setMultiple( Boolean.TRUE.toString() );
		eh2.setClassName( EventHandlers.EventHandlerWithEvent.class.getCanonicalName() );
		eventHandlers.add( eh1 );
		eventHandlers.add( eh2 );
	}
}
