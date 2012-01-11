package com.mvp4g.util;

import static junit.framework.Assert.assertEquals;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.javac.typemodel.TypeOracleStub;
import com.google.gwt.dev.util.UnitTestTreeLogger;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.DefaultMvp4gGinModule;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.History.HistoryConverterType;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.BaseEventBusWithLookUp;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.history.ClearHistory;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.ChildModuleElement;
import com.mvp4g.util.config.element.ChildModulesElement;
import com.mvp4g.util.config.element.DebugElement;
import com.mvp4g.util.config.element.EventAssociation;
import com.mvp4g.util.config.element.EventBusElement;
import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.EventFilterElement;
import com.mvp4g.util.config.element.EventFiltersElement;
import com.mvp4g.util.config.element.EventHandlerElement;
import com.mvp4g.util.config.element.GinModuleElement;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.HistoryElement;
import com.mvp4g.util.config.element.InjectedElement;
import com.mvp4g.util.config.element.LoaderElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.config.element.SplitterElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.test_tools.CustomPlaceService;
import com.mvp4g.util.test_tools.GeneratorContextStub;
import com.mvp4g.util.test_tools.Loaders;
import com.mvp4g.util.test_tools.Modules;
import com.mvp4g.util.test_tools.OneGinModule;
import com.mvp4g.util.test_tools.OneLogger;
import com.mvp4g.util.test_tools.SourceWriterTestStub;
import com.mvp4g.util.test_tools.annotation.EventFilters.EventFilter1;
import com.mvp4g.util.test_tools.annotation.EventFilters.EventFilter2;
import com.mvp4g.util.test_tools.annotation.Presenters;
import com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter;
import com.mvp4g.util.test_tools.annotation.events.EventBusOk;
import com.mvp4g.util.test_tools.annotation.handlers.EventHandlerWithEvent;
import com.mvp4g.util.test_tools.annotation.handlers.SimpleEventHandler;
import com.mvp4g.util.test_tools.annotation.history_converters.SimpleHistoryConverter;
import com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter;

public class Mvp4gConfigurationFileReaderTest {

	private SourceWriterTestStub sourceWriter;
	private Mvp4gConfigurationFileWriter writer;
	private Mvp4gConfiguration configuration;

	@Before
	public void setUp() {
		sourceWriter = new SourceWriterTestStub();
		TreeLogger tl = new UnitTestTreeLogger.Builder().createLogger();
		configuration = new Mvp4gConfiguration( tl, new GeneratorContextStub() );
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
		start.setPresenter( "startPresenter" );
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
	public void testWriteEvents() {

		assertOutput( getExpectedEvents(), false );
		assertOutput( getExpectedNotNavigationEvents(), false );
		assertOutput( getExpectedEventsInheritMethods(), false );
		assertOutput( getExpectedEventsWithLookup(), false );
		assertOutput( getExpectedNavigationEvents(), false );
		assertOutput( getExpectedGenerateEvents(), false );
		assertOutput( getExpectedBindedEvents(), false );

		createHandlers();

		Set<EventElement> events = configuration.getEvents();

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setHandlers( new String[] { "handler1" } );
		e1.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );
		e1.setBinds( new String[] { "handler3" } );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setHandlers( new String[] { "handler2" } );
		e2.setEventObjectClass( new String[] { "java.lang.String" } );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );
		e3.setName( "name3" );
		e3.setHandlers( new String[] { "handler3", "handler4" } );
		e3.setBinds( new String[] { "handler2" } );

		EventElement e4 = new EventElement();
		e4.setType( "event4" );
		e4.setHandlers( new String[] { "handler3", "handler4" } );
		e4.setPassive( "true" );

		events.add( e1 );
		events.add( e2 );
		events.add( e3 );
		events.add( e4 );

		writer.writeConf();

		assertOutput( getExpectedEvents(), true );
		assertOutput( getExpectedNotNavigationEvents(), true );
		assertOutput( getExpectedEventsInheritMethods(), true );
		assertOutput( getExpectedBindedEvents(), true );
		assertOutput( getExpectedEventsWithLookup(), false );
		assertOutput( getExpectedNavigationEvents(), false );
		assertOutput( getExpectedGenerateEvents(), false );

	}

	@Test
	public void testWriteActivateDeactivate() {

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
	public void testWriteNavigationEvents() {

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
	public void testWriteEventsWithToken() {

		assertOutput( getExpectedEventsWithToken(), false );

		HistoryConverterElement hc = new HistoryConverterElement();
		hc.setClassName( SimpleHistoryConverter.class.getCanonicalName() );
		hc.setName( "history" );
		configuration.getHistoryConverters().add( hc );

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setWithTokenGeneration( "true" );
		e1.setHistory( "history" );
		e1.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setForwardToParent( "true" );
		e2.setWithTokenGeneration( "true" );
		e2.setTokenGenerationFromParent( "true" );
		e2.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );
		e3.setHistory( "history" );
		e3.setWithTokenGeneration( "true" );
		e3.setNavigationEvent( "true" );
		e3.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );

		EventFiltersElement filterConf = new EventFiltersElement();
		filterConf.setForceFilters( "true" );
		configuration.setEventFilterConfiguration( filterConf );

		Set<EventElement> events = configuration.getEvents();
		events.add( e1 );
		events.add( e2 );
		events.add( e3 );

		writer.writeConf();

		assertOutput( getExpectedEventsWithToken(), true );

	}

	@Test
	public void testWriteEventsWithHistory() {
		testWriteEventsWithHistory( false );
	}

	@Test
	public void testWriteEventsWithHistoryAndToken() {
		testWriteEventsWithHistory( true );
	}

	private void testWriteEventsWithHistory( boolean withToken ) {

		assertOutput( getExpectedHistoryEvents( false ), false );
		assertOutput( getExpectedHistoryEvents(), false );
		assertOutput( getExpectedHistoryEvents( true ), false );

		createHandlers();

		HistoryConverterElement c1 = new HistoryConverterElement();
		c1.setClassName( ClearHistory.class.getCanonicalName() );
		c1.setName( "clear" );
		HistoryConverterElement c2 = new HistoryConverterElement();
		c2.setClassName( SimpleHistoryConverter.class.getCanonicalName() );
		c2.setName( "history2" );
		c2.setType( HistoryConverterType.NONE.name() );
		HistoryConverterElement c3 = new HistoryConverterElement();
		c3.setClassName( SimpleHistoryConverter.class.getCanonicalName() );
		c3.setName( "history3" );
		c3.setType( HistoryConverterType.SIMPLE.name() );
		configuration.getHistoryConverters().add( c1 );
		configuration.getHistoryConverters().add( c2 );
		configuration.getHistoryConverters().add( c3 );

		Set<EventElement> events = configuration.getEvents();

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setHandlers( new String[] { "handler1" } );
		e1.setHistory( "history" );
		e1.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );
		e1.setWithTokenGeneration( Boolean.toString( withToken ) );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setHandlers( new String[] { "handler2" } );
		e2.setHistory( "history" );
		e2.setName( "historyName" );
		e2.setEventObjectClass( new String[] { "java.lang.String" } );
		e2.setWithTokenGeneration( Boolean.toString( withToken ) );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );
		e3.setHandlers( new String[] { "handler3" } );
		e3.setHistory( "clear" );
		e3.setWithTokenGeneration( Boolean.toString( withToken ) );

		EventElement e4 = new EventElement();
		e4.setType( "event4" );
		e4.setHandlers( new String[] { "handler3" } );
		e4.setHistory( "history2" );
		e4.setWithTokenGeneration( Boolean.toString( withToken ) );

		EventElement e5 = new EventElement();
		e5.setType( "event5" );
		e5.setHandlers( new String[] { "handler3" } );
		e5.setHistory( "history3" );
		e5.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );
		e5.setWithTokenGeneration( Boolean.toString( withToken ) );

		EventElement e6 = new EventElement();
		e6.setType( "event6" );
		e6.setHistory( "history3" );
		e6.setWithTokenGeneration( Boolean.toString( withToken ) );

		EventElement e7 = new EventElement();
		e7.setType( "event7" );
		e7.setHistory( "history" );
		e7.setWithTokenGeneration( Boolean.toString( withToken ) );

		events.add( e1 );
		events.add( e2 );
		events.add( e3 );
		events.add( e4 );
		events.add( e5 );
		events.add( e6 );
		events.add( e7 );

		writer.writeConf();

		assertOutput( getExpectedHistoryEvents( false ), true );
		assertOutput( getExpectedHistoryEvents(), true );
		assertOutput( getExpectedHistoryEvents( true ), withToken );
	}

	@Test
	public void testWriteEventsWithLookup() {

		String eventBusInterface = EventBusWithLookup.class.getName();
		String eventBusClass = BaseEventBusWithLookUp.class.getName();
		configuration.setEventBus( new EventBusElement( eventBusInterface, eventBusClass, true ) );

		assertOutput( getExpectedEvents(), false );
		assertOutput( getExpectedEventsInheritMethods(), false );
		assertOutput( getExpectedEventsWithLookup(), false );
		assertOutput( getExpectedGenerateEvents(), false );

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
		e3.setName( "name3" );
		e3.setHandlers( new String[] { "handler3", "handler4" } );

		EventElement e4 = new EventElement();
		e4.setType( "event4" );
		e4.setHandlers( new String[] { "handler4", "handler3" } );
		e4.setPassive( "true" );

		Set<EventElement> events = configuration.getEvents();
		events.add( e1 );
		events.add( e2 );
		events.add( e3 );
		events.add( e4 );

		writer.writeConf();

		assertOutput( getExpectedEvents(), true );
		assertOutput( getExpectedEventsWithLookup(), true );
		assertOutput( getExpectedEventsInheritMethods(), true );
		assertOutput( getExpectedGenerateEvents(), false );

	}

	@Test
	public void testWriteEventsWithGenerate() {

		assertOutput( getExpectedEvents(), false );
		assertOutput( getExpectedNotNavigationEvents(), false );
		assertOutput( getExpectedEventsInheritMethods(), false );
		assertOutput( getExpectedEventsWithLookup(), false );
		assertOutput( getExpectedNavigationEvents(), false );
		assertOutput( getExpectedGenerateEvents(), false );

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
		e3.setName( "name3" );
		e3.setHandlers( new String[] { "handler3", "handler4" } );

		EventElement e4 = new EventElement();
		e4.setType( "event4" );
		e4.setHandlers( new String[] { "handler3", "handler4" } );
		e4.setPassive( "true" );
		e4.setGenerate( new String[] { "handler2", "handler4" } );

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
		assertOutput( getExpectedGenerateEvents(), true );

	}

	@Test
	public void testWriteViews() {

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
	public void testWriteViewsNotAtStart() {

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
	public void testWritePresenters() {

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
	public void testWritePresentersWithReverseView() {

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
	public void testWriteMultiplePresenters() {

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
	public void testWriteMultiplePresentersWithInverseView() {

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
	public void testWriteServices() {

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
	public void testWriteStart() {

		StartElement start = configuration.getStart();
		start.setEventType( "start" );
		start.setHistory( Boolean.TRUE.toString() );

		assertOutput( getExpectedStartEvent(), false );
		assertOutput( getExpectedNoFiltering(), false );
		assertOutput( getExpectedStartPresenterViewCommon(), false );
		assertOutput( getExpectedStartPresenterView(), false );
		assertOutput( getExpectedStartPresenterViewMultiple(), false );
		writer.writeConf();
		assertOutput( getExpectedStartEvent(), true );
		assertOutput( getExpectedNoFiltering(), false );
		assertOutput( getExpectedStartPresenterViewCommon(), true );
		assertOutput( getExpectedStartPresenterView(), true );
		assertOutput( getExpectedStartPresenterViewMultiple(), false );
	}

	@Test
	public void testWriteStartNoFilter() {

		StartElement start = configuration.getStart();
		start.setEventType( "start" );
		start.setHistory( Boolean.TRUE.toString() );

		EventFiltersElement filterConf = new EventFiltersElement();
		filterConf.setFilterStart( Boolean.FALSE.toString() );
		configuration.setEventFilterConfiguration( filterConf );

		assertOutput( getExpectedStartEvent(), false );
		assertOutput( getExpectedNoFiltering(), false );
		assertOutput( getExpectedStartPresenterViewCommon(), false );
		assertOutput( getExpectedStartPresenterView(), false );
		assertOutput( getExpectedStartPresenterViewMultiple(), false );
		writer.writeConf();
		assertOutput( getExpectedStartEvent(), true );
		assertOutput( getExpectedNoFiltering(), true );
		assertOutput( getExpectedStartPresenterViewCommon(), true );
		assertOutput( getExpectedStartPresenterView(), true );
		assertOutput( getExpectedStartPresenterViewMultiple(), false );

	}

	@Test
	public void testWriteNoStartView() {
		StartElement start = new StartElement();
		configuration.setStart( start );

		assertOutput( getNoStartView(), false );
		writer.writeConf();
		assertOutput( getNoStartView(), true );
	}

	@Test
	public void testWriteStartMultiple() {

		StartElement start = configuration.getStart();
		start.setEventType( "start" );
		start.setHistory( Boolean.TRUE.toString() );

		configuration.getPresenters().iterator().next().setMultiple( Boolean.TRUE.toString() );

		assertOutput( getExpectedStartEvent(), false );
		assertOutput( getExpectedNoFiltering(), false );
		assertOutput( getExpectedStartPresenterViewCommon(), false );
		assertOutput( getExpectedStartPresenterView(), false );
		assertOutput( getExpectedStartPresenterViewMultiple(), false );
		writer.writeConf();
		assertOutput( getExpectedStartEvent(), true );
		assertOutput( getExpectedNoFiltering(), false );
		assertOutput( getExpectedStartPresenterViewCommon(), true );
		assertOutput( getExpectedStartPresenterView(), false );
		assertOutput( getExpectedStartPresenterViewMultiple(), true );

	}

	@Test
	public void testWriteNoForward() {

		assertOutput( getExpectedNoForwardEvent(), false );
		assertOutput( getExpectedForwardEvent(), false );
		assertOutput( getExpectedNoFiltering(), false );
		writer.writeConf();
		assertOutput( getExpectedNoForwardEvent(), true );
		assertOutput( getExpectedForwardEvent(), false );
		assertOutput( getExpectedNoFiltering(), false );

	}

	@Test
	public void testWriteForward() {

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
	public void testWriteForwardNoFilter() {

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
	public void testHistory() {

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
	public void testWriteHistoryWithConfig() {
		String eventBusInterface = EventBusWithLookup.class.getName();
		String eventBusClass = BaseEventBusWithLookUp.class.getName();
		configuration.setEventBus( new EventBusElement( eventBusInterface, eventBusClass, false ) );

		HistoryElement history = new HistoryElement();
		history.setPlaceServiceClass( CustomPlaceService.class.getCanonicalName() );
		configuration.setHistory( history );

		assertOutput( getExpectedHistoryWithConfig(), false );
		writer.writeConf();
		assertOutput( getExpectedHistoryWithConfig(), true );

	}

	@Test
	public void testWriteNoHistoryStart() {

		configuration.setHistory( null );

		assertOutput( getExpectedDefaultHistory(), false );
		assertOutput( getExpectedWithHistory(), false );
		assertOutput( getExpectedInheritModuleMethods(), false );
		writer.writeConf();
		assertOutput( getExpectedDefaultHistory(), true );
		assertOutput( getExpectedWithHistory(), false );
		assertOutput( getExpectedInheritModuleMethods(), true );

	}

	@Test
	public void testWriteGetters() {

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
	public void testWriteChildNoAsyncNoAutoLoad() {
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
	public void testWriteChildWithParentAsyncNoAutoLoadNotGwt2() {
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
	public void testWriteChildWithParentAsyncNoAutoLoadErrorEmpty() {
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
	public void testWriteChildWithParentAsyncNoAutoLoad() {
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
	public void testWriteChildWithParentAsyncoAutoLoad() {
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
	public void testWriteLoadChildModule() {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();

		Set<ChildModuleElement> children = configuration.getChildModules();

		JClassType moduleType = oracle.addClass( Modules.ModuleWithParent.class );
		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule1" );
		childModule.setAutoDisplay( "false" );
		children.add( childModule );

		moduleType = oracle.addClass( Modules.Module1.class );
		childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule2" );
		childModule.setAutoDisplay( "false" );
		children.add( childModule );

		assertOutput( getExpectedLoadChildModule(), false );
		writer.writeConf();
		assertOutput( getExpectedLoadChildModule(), true );

	}

	@Test
	public void testWriteLoadChildModuleWithLoader() {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();

		Set<ChildModuleElement> children = configuration.getChildModules();

		JClassType loaderType = oracle.addClass( Loaders.Loader1.class );
		LoaderElement loader = new LoaderElement();
		loader.setName( "loader" );
		loader.setClassName( loaderType.getQualifiedSourceName() );
		configuration.getLoaders().add( loader );

		JClassType moduleType = oracle.addClass( Modules.ModuleWithParent.class );
		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule1" );
		childModule.setLoader( "loader" );
		childModule.setAutoDisplay( "false" );
		children.add( childModule );

		moduleType = oracle.addClass( Modules.Module1.class );
		childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule2" );
		childModule.setAsync( "false" );
		childModule.setLoader( "loader" );
		childModule.setAutoDisplay( "false" );
		children.add( childModule );

		assertOutput( getExpectedLoadChildModuleWithLoader(), false );
		writer.writeConf();
		assertOutput( getExpectedLoadChildModuleWithLoader(), true );

	}

	@Test
	public void testWriteChildEvent() {

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
		event1.setForwardToModules( new String[] { "child" } );
		EventElement event2 = new EventElement();
		event2.setType( "event2" );
		event2.setEventObjectClass( new String[] { "java.lang.String" } );
		event2.setForwardToModules( new String[] { "child" } );
		EventElement event3 = new EventElement();
		event3.setType( "event3" );
		event3.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );
		event3.setForwardToModules( new String[] { "child" } );
		configuration.getEvents().add( event1 );
		configuration.getEvents().add( event2 );
		configuration.getEvents().add( event3 );
		configuration.getOthersEventBusClassMap().put( Modules.ModuleWithParent.class.getCanonicalName(), oracle.addClass( EventBusOk.class ) );

		assertOutput( getExpectedEventChildModuleLoad(), false );
		writer.writeConf();
		assertOutput( getExpectedEventChildModuleLoad(), true );
	}

	@Test
	public void testWriteChildPassiveEvent() {

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
		event1.setForwardToModules( new String[] { "child" } );
		event1.setPassive( "true" );
		EventElement event2 = new EventElement();
		event2.setType( "event2" );
		event2.setEventObjectClass( new String[] { "java.lang.String" } );
		event2.setForwardToModules( new String[] { "child" } );
		event2.setPassive( "true" );
		EventElement event3 = new EventElement();
		event3.setType( "event3" );
		event3.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );
		event3.setForwardToModules( new String[] { "child" } );
		event3.setPassive( "true" );
		configuration.getEvents().add( event1 );
		configuration.getEvents().add( event2 );
		configuration.getEvents().add( event3 );
		configuration.getOthersEventBusClassMap().put( Modules.ModuleWithParent.class.getCanonicalName(), oracle.addClass( EventBusOk.class ) );

		assertOutput( getExpectedPassiveEventChildModuleLoad(), false );
		writer.writeConf();
		assertOutput( getExpectedPassiveEventChildModuleLoad(), true );
	}

	@Test
	public void testWriteForwardParent() {

		EventElement event = new EventElement();
		event.setType( "test" );
		event.setForwardToParent( "true" );
		event.setEventObjectClass( new String[] { Object.class.getCanonicalName() } );
		configuration.getEvents().add( event );

		assertOutput( getExpectedForwardParent(), false );
		writer.writeConf();
		assertOutput( getExpectedForwardParent(), true );
	}

	private void testWriteSiblingEvent( boolean passive ) {

		String passiveStr = Boolean.toString( passive );

		configuration.setLoadChildConfig( new ChildModulesElement() );
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		String moduleType = oracle.addClass( Modules.ModuleWithParent.class ).getQualifiedSourceName();

		EventElement event1 = new EventElement();
		event1.setType( "event1" );
		event1.setPassive( passiveStr );
		event1.setSiblingsToLoad( new String[] { moduleType } );
		EventElement event2 = new EventElement();
		event2.setType( "event2" );
		event2.setEventObjectClass( new String[] { "java.lang.String" } );
		event2.setSiblingsToLoad( new String[] { moduleType } );
		event2.setPassive( passiveStr );
		EventElement event3 = new EventElement();
		event3.setType( "event3" );
		event3.setEventObjectClass( new String[] { "java.lang.String", "java.lang.Object" } );
		event3.setSiblingsToLoad( new String[] { moduleType } );
		event3.setPassive( passiveStr );
		configuration.getEvents().add( event1 );
		configuration.getEvents().add( event2 );
		configuration.getEvents().add( event3 );
		configuration.getOthersEventBusClassMap().put( moduleType, oracle.addClass( EventBusOk.class ) );

		assertOutput( getExpectedEventSiblingLoad( passive ), false );
		writer.writeConf();
		assertOutput( getExpectedEventSiblingLoad( passive ), true );
	}

	@Test
	public void testWriteSiblingEvent() {
		testWriteSiblingEvent( false );
	}

	@Test
	public void testWriteSiblingEventPassive() {
		testWriteSiblingEvent( true );
	}

	@Test
	public void testWriteDebug() {

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
	public void testWriteComplexDebug() {

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
	public void testWriteChildModuleMethodsNoHistory() {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		configuration.setModule( oracle.addClass( Modules.Module1.class ) );
		configuration.setParentEventBus( oracle.addClass( EventBusWithLookup.class ) );

		assertOutput( getExpectedChildMethodNoHistory(), false );
		writer.writeConf();
		assertOutput( getExpectedChildMethodNoHistory(), true );
	}

	@Test
	public void testWriteParentHistory() {

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
		event.setForwardToModules( new String[] { "child" } );
		configuration.getEvents().add( event );
		configuration.getOthersEventBusClassMap().put( Modules.ModuleWithParent.class.getCanonicalName(), oracle.addClass( EventBusOk.class ) );

		assertOutput( getExpectedHistoryParent(), false );
		writer.writeConf();
		assertOutput( getExpectedHistoryParent(), true );

	}

	@Test
	public void testWriteGinInjector() {

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
	public void testWriteCustomGinInjector() {

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
	public void testWriteEventFilters() {

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
	public void testWriteNoEventFilters() {

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
	public void testWriteForceEventFilters() {

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
	public void testWriteEventFiltersWithLog() {

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
	public void testWriteAfterEventFilters() {

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
	public void testWriteEventWithPrimitives() {

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
		e1.setForwardToModules( new String[] { "child" } );
		e1.setEventObjectClass( new String[] { "boolean", "byte", "char", "double", "float", "int", "long", "short" } );

		configuration.getEvents().add( e1 );

		configuration.getOthersEventBusClassMap().put( moduleType.getQualifiedSourceName(), oracle.addClass( EventBusOk.class ) );

		writer.writeConf();

		assertOutput( getExpectedPrimitives(), true );

	}

	@Test
	public void testWriteSplitter() {
		PresenterElement presenter = new PresenterElement();
		presenter.setName( "presenter" );
		presenter.setClassName( SimplePresenter.class.getCanonicalName() );
		presenter.setView( "view" );
		configuration.getPresenters().add( presenter );

		EventHandlerElement eventHandler = new EventHandlerElement();
		eventHandler.setName( "eventHandler" );
		eventHandler.setClassName( SimpleEventHandler.class.getCanonicalName() );
		configuration.getEventHandlers().add( eventHandler );

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setSplitters( new String[] { "splitter" } );
		e1.setEventObjectClass( new String[] { "int", "String" } );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setSplitters( new String[] { "splitter" } );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );
		e3.setSplitters( new String[] { "splitter" } );

		SplitterElement splitter = new SplitterElement();
		splitter.getHandlers().add( presenter );
		splitter.getHandlers().add( eventHandler );
		splitter.setName( "splitter" );
		splitter.setClassName( "Splitter" );
		EventAssociation<String> ea1 = new EventAssociation<String>();
		ea1.getActivated().add( "eventHandler" );
		ea1.getDeactivated().add( "presenter" );
		ea1.getBinds().add( "presenter" );
		ea1.getHandlers().add( "eventHandler" );
		splitter.getEvents().put( e1, ea1 );

		EventAssociation<String> ea2 = new EventAssociation<String>();
		ea2.getActivated().add( "eventHandler" );
		ea2.getDeactivated().add( "presenter" );
		splitter.getEvents().put( e2, ea2 );

		EventAssociation<String> ea3 = new EventAssociation<String>();
		ea3.getBinds().add( "presenter" );
		splitter.getEvents().put( e3, ea3 );

		Set<EventElement> events = configuration.getEvents();
		events.add( e1 );
		events.add( e2 );
		events.add( e3 );

		configuration.getSplitters().add( splitter );

		assertOutput( getExpectedSplitter( false ), false );
		assertOutput( getExpectedSplitterLoadingConf(), false );
		assertOutput( getExpectedSplitterPassiveGenerateMultiple(), false );
		assertOutput( getExpectedSplitterNotPassive(), false );
		assertOutput( getExpectedSplitterWithLoader(), false );
		writer.writeConf();
		assertOutput( getExpectedSplitter( false ), true );
		assertOutput( getExpectedSplitterLoadingConf(), false );
		assertOutput( getExpectedSplitterPassiveGenerateMultiple(), false );
		assertOutput( getExpectedSplitterNotPassive(), true );
		assertOutput( getExpectedSplitterWithLoader(), false );
	}

	@Test
	public void testWriteSplitterPassiveLoadingGenerate() {
		EventElement event = new EventElement();
		event.setType( "errorOnLoad" );
		event.setEventObjectClass( new String[] { Throwable.class.getCanonicalName() } );
		configuration.getEvents().add( event );

		ChildModulesElement loadConfig = new ChildModulesElement();
		loadConfig.setAfterEvent( "afterLoad" );
		loadConfig.setBeforeEvent( "beforeLoad" );
		loadConfig.setErrorEvent( "errorOnLoad" );
		configuration.setLoadChildConfig( loadConfig );

		PresenterElement presenter = new PresenterElement();
		presenter.setName( "presenter" );
		presenter.setClassName( SimplePresenter.class.getCanonicalName() );
		presenter.setView( "view" );
		presenter.setAsync( "Splitter" );
		configuration.getPresenters().add( presenter );

		ViewElement view = new ViewElement();
		view.setName( "viewMultiple" );
		view.setClassName( Object.class.getCanonicalName() );
		configuration.getViews().add( view );

		PresenterElement presenterMultiple = new PresenterElement();
		presenterMultiple.setName( "presenterMultiple" );
		presenterMultiple.setClassName( SimplePresenter.class.getCanonicalName() );
		presenterMultiple.setView( "viewMultiple" );
		presenterMultiple.setMultiple( "true" );
		presenter.setAsync( "Splitter" );
		configuration.getPresenters().add( presenterMultiple );

		EventHandlerElement eventHandler = new EventHandlerElement();
		eventHandler.setName( "eventHandler" );
		eventHandler.setClassName( SimpleEventHandler.class.getCanonicalName() );
		presenter.setAsync( "Splitter" );
		configuration.getEventHandlers().add( eventHandler );

		EventHandlerElement eventHandlerMultiple = new EventHandlerElement();
		eventHandlerMultiple.setName( "eventHandlerMultiple" );
		eventHandlerMultiple.setClassName( SimpleEventHandler.class.getCanonicalName() );
		eventHandlerMultiple.setMultiple( "true" );
		presenter.setAsync( "Splitter" );
		configuration.getEventHandlers().add( eventHandlerMultiple );

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setSplitters( new String[] { "splitter" } );
		e1.setEventObjectClass( new String[] { "int", "String" } );
		e1.setPassive( "true" );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setSplitters( new String[] { "splitter" } );
		e2.setPassive( "true" );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );
		e3.setSplitters( new String[] { "splitter" } );
		e3.setPassive( "true" );

		EventElement e4 = new EventElement();
		e4.setType( "event4" );
		e4.setSplitters( new String[] { "splitter" } );
		e4.setEventObjectClass( new String[] { "String", "String", "String", "int" } );

		EventElement e5 = new EventElement();
		e5.setType( "event5" );
		e5.setSplitters( new String[] { "splitter" } );
		e5.setPassive( "true" );
		e5.setEventObjectClass( new String[] { "String", "String", "String" } );

		SplitterElement splitter = new SplitterElement();
		splitter.getHandlers().add( presenter );
		splitter.getHandlers().add( eventHandler );
		splitter.getHandlers().add( eventHandlerMultiple );
		splitter.getHandlers().add( presenterMultiple );
		splitter.setName( "splitter" );
		splitter.setClassName( "Splitter" );
		EventAssociation<String> ea1 = new EventAssociation<String>();
		ea1.getActivated().add( "eventHandler" );
		ea1.getDeactivated().add( "presenter" );
		ea1.getHandlers().add( "eventHandler" );
		ea1.getHandlers().add( "presenter" );
		ea1.getHandlers().add( "presenterMultiple" );
		ea1.getHandlers().add( "eventHandlerMultiple" );
		splitter.getEvents().put( e1, ea1 );

		Set<EventElement> events = configuration.getEvents();
		events.add( e1 );
		events.add( e2 );
		events.add( e3 );
		events.add( e4 );
		events.add( e5 );

		EventAssociation<String> ea2 = new EventAssociation<String>();
		ea2.getActivated().add( "eventHandler" );
		ea2.getActivated().add( "eventHandlerMultiple" );
		ea2.getDeactivated().add( "presenter" );
		ea2.getDeactivated().add( "presenterMultiple" );
		splitter.getEvents().put( e2, ea2 );

		EventAssociation<String> ea3 = new EventAssociation<String>();
		ea3.getBinds().add( "eventHandler" );
		ea3.getBinds().add( "eventHandlerMultiple" );
		ea3.getBinds().add( "presenterMultiple" );
		ea3.getBinds().add( "presenter" );
		splitter.getEvents().put( e3, ea3 );

		EventAssociation<String> ea4 = new EventAssociation<String>();
		ea4.getGenerate().add( "eventHandlerMultiple" );
		splitter.getEvents().put( e4, ea4 );

		EventAssociation<String> ea5 = new EventAssociation<String>();
		ea5.getGenerate().add( "presenterMultiple" );
		splitter.getEvents().put( e5, ea5 );

		configuration.getSplitters().add( splitter );

		assertOutput( getExpectedSplitter( true ), false );
		assertOutput( getExpectedSplitterLoadingConf(), false );
		assertOutput( getExpectedSplitterPassiveGenerateMultiple(), false );
		assertOutput( getExpectedSplitterNotPassive(), false );
		assertOutput( getExpectedSplitterWithLoader(), false );
		writer.writeConf();
		assertOutput( getExpectedSplitter( true ), true );
		assertOutput( getExpectedSplitterLoadingConf(), true );
		assertOutput( getExpectedSplitterPassiveGenerateMultiple(), true );
		assertOutput( getExpectedSplitterNotPassive(), false );
		assertOutput( getExpectedSplitterWithLoader(), false );
	}

	@Test
	public void testWriteMultipleImpl() {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.ModuleWithParent.class );
		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule" );
		childModule.setAutoDisplay( "false" );
		configuration.getChildModules().add( childModule );

		SplitterElement splitter = new SplitterElement();
		splitter.setName( "splitter" );
		splitter.setClassName( "Splitter" );
		configuration.getSplitters().add( splitter );

		configuration.setPropertiesValues( new String[] { "test" } );

		assertOutput( getExpectedMultipleImpl(), false );
		writer.writeConf();
		assertOutput( getExpectedMultipleImpl(), true );
	}

	@Test
	public void testWriteSplitterWithLoader() {
		PresenterElement presenter = new PresenterElement();
		presenter.setName( "presenter" );
		presenter.setClassName( SimplePresenter.class.getCanonicalName() );
		presenter.setView( "view" );
		configuration.getPresenters().add( presenter );

		EventHandlerElement eventHandler = new EventHandlerElement();
		eventHandler.setName( "eventHandler" );
		eventHandler.setClassName( SimpleEventHandler.class.getCanonicalName() );
		configuration.getEventHandlers().add( eventHandler );

		EventElement e1 = new EventElement();
		e1.setType( "event1" );
		e1.setSplitters( new String[] { "splitter" } );
		e1.setEventObjectClass( new String[] { "int", "String" } );

		EventElement e2 = new EventElement();
		e2.setType( "event2" );
		e2.setSplitters( new String[] { "splitter" } );

		EventElement e3 = new EventElement();
		e3.setType( "event3" );
		e3.setSplitters( new String[] { "splitter" } );

		SplitterElement splitter = new SplitterElement();
		splitter.getHandlers().add( presenter );
		splitter.getHandlers().add( eventHandler );
		splitter.setName( "splitter" );
		splitter.setClassName( "Splitter" );
		EventAssociation<String> ea1 = new EventAssociation<String>();
		ea1.getActivated().add( "eventHandler" );
		ea1.getDeactivated().add( "presenter" );
		ea1.getBinds().add( "presenter" );
		ea1.getHandlers().add( "eventHandler" );
		splitter.getEvents().put( e1, ea1 );

		EventAssociation<String> ea2 = new EventAssociation<String>();
		ea2.getActivated().add( "eventHandler" );
		ea2.getDeactivated().add( "presenter" );
		splitter.getEvents().put( e2, ea2 );

		EventAssociation<String> ea3 = new EventAssociation<String>();
		ea3.getBinds().add( "presenter" );
		splitter.getEvents().put( e3, ea3 );

		JClassType loaderType = ( (TypeOracleStub)configuration.getOracle() ).addClass( Loaders.Loader1.class );
		LoaderElement loader = new LoaderElement();
		loader.setName( "loader" );
		loader.setClassName( loaderType.getQualifiedSourceName() );
		configuration.getLoaders().add( loader );

		splitter.setLoader( "loader" );

		Set<EventElement> events = configuration.getEvents();
		events.add( e1 );
		events.add( e2 );
		events.add( e3 );

		configuration.getSplitters().add( splitter );

		assertOutput( getExpectedSplitter( false ), false );
		assertOutput( getExpectedSplitterLoadingConf(), false );
		assertOutput( getExpectedSplitterPassiveGenerateMultiple(), false );
		assertOutput( getExpectedSplitterNotPassive(), false );
		assertOutput( getExpectedSplitterWithLoader(), false );
		writer.writeConf();
		assertOutput( getExpectedSplitter( false ), true );
		assertOutput( getExpectedSplitterLoadingConf(), false );
		assertOutput( getExpectedSplitterPassiveGenerateMultiple(), false );
		assertOutput( getExpectedSplitterNotPassive(), true );
		assertOutput( getExpectedSplitterWithLoader(), true );
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
		return new String[] { "placeService = new com.mvp4g.client.history.PlaceService(){", "protected void sendInitEvent(){",
				"protected void sendNotFoundEvent(){", "placeService.setModule(itself);", };

	}

	private String[] getExpectedInheritModuleMethods() {
		return new String[] { "public void addConverter(String historyName, HistoryConverter<?> hc){", "placeService.addConverter(historyName, hc);",
				"public String place(String token, String form, boolean onlyToken){", "return placeService.place( token, form, onlyToken );",
				"public void dispatchHistoryEvent(String eventType, final Mvp4gEventPasser passer){",
				"int index = eventType.indexOf(PlaceService.MODULE_SEPARATOR);", "if(index > -1){",
				"String moduleHistoryName = eventType.substring(0, index);", "String nextToken = eventType.substring(index + 1);",
				"Mvp4gEventPasser nextPasser = new Mvp4gEventPasser(nextToken) {", "public void pass(Mvp4gModule module) {",
				"module.dispatchHistoryEvent((String) eventObjects[0], passer);", "passer.setEventObject(false);", "passer.pass(this);", "}else{",
				"passer.pass(this);",
				"public void loadChildModule(String childModuleClassName, String eventName, boolean passive, Mvp4gEventPasser passer){" };
	}

	private String[] getExpectedHistoryWithConfig() {
		return new String[] { "placeService = new com.mvp4g.util.test_tools.CustomPlaceService(){" };

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
		return new String[] { "rootView.setPresenter(rootPresenter);" };
	}

	private String[] getMultiplePresenters() {
		return new String[] { "if (com.mvp4g.util.test_tools.RootPresenter.class.equals(handlerClass)){",
				"com.mvp4g.util.test_tools.RootPresenter rootPresenter = injector.getrootPresenter();",
				"com.mvp4g.util.test_tools.RootView rootView = injector.getrootView();", "rootPresenter.setView(rootView);",
				"rootPresenter.setEventBus(eventBus);", "return (T) rootPresenter;" };
	}

	private String[] getExpectedEvents() {
		return new String[] {
				"public void event4(){",
				"List<com.mvp4g.util.test_tools.annotation.handlers.EventHandlerWithEvent> handlershandler4 = getHandlers(com.mvp4g.util.test_tools.annotation.handlers.EventHandlerWithEvent.class);",
				"if(handlershandler4!= null){",
				"com.mvp4g.util.test_tools.annotation.handlers.EventHandlerWithEvent handler;",
				"int handlerCount = handlershandler4.size();",
				"for(int i=0; i<handlerCount; i++){",
				"handler = handlershandler4.get(i);",
				"if (handler.isActivated(false, \"name3\")){",
				"if (handler.isActivated(true, \"event4\")){",
				"handler.onEvent4();",
				"handler.onEvent3();",
				"public void event2(java.lang.String attr0){",
				"List<com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter> handlershandler2 = getHandlers(com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter.class);",
				"if(handlershandler2!= null){", "com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter handler;",
				"int handlerCount = handlershandler2.size();", "for(int i=0; i<handlerCount; i++){", "handler = handlershandler2.get(i);",
				"if (handler.isActivated(false, \"event2\", new Object[]{attr0})){", "handler.onEvent2(attr0);", "public void event3(){",
				"if (handler3.isActivated(false, \"name3\")){", "if (handler3.isActivated(true, \"event4\")){", "handler3.onEvent4();",
				"handler3.onEvent3();", "if (handler1.isActivated(false, \"event1\", new Object[]{attr0,attr1})){", "handler1.onEvent1(attr0,attr1);" };
	}

	private String[] getExpectedNotNavigationEvents() {
		return new String[] { "public void event1(java.lang.String attr0,java.lang.Object attr1){" };
	};

	private String[] getExpectedBindedEvents() {
		return new String[] {
				"handler3.isActivated(false, \"event1\", new Object[]{attr0,attr1});",
				"List<com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter> handlershandler2 = getHandlers(com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter.class);",
				"if(handlershandler2!= null){", "com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter handler;",
				"int handlerCount = handlershandler2.size();", "for(int i=0; i<handlerCount; i++){", "handler = handlershandler2.get(i);",
				"handler.isActivated(false, \"name3\");" };
	}

	private String[] getExpectedEventsInheritMethods() {
		return new String[] { "public void setNavigationConfirmation( NavigationConfirmationInterface navigationConfirmation ) {",
				"placeService.setNavigationConfirmation(navigationConfirmation);", "public void confirmNavigation(NavigationEventCommand event){",
				"placeService.confirmEvent(event);", "protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ){",
				"public void setApplicationHistoryStored( boolean historyStored ){", "placeService.setEnabled(historyStored);" };
	}

	private String[] getExpectedHistoryEvents( boolean withToken ) {
		return new String[] { "place( itself, \"historyName\",history.onEvent2(attr0)," + withToken + ");",
				"place( itself, \"event1\",history.onEvent1(attr0,attr1)," + withToken + ");", "place( itself, \"event4\",null," + withToken + ");",
				"place( itself, \"event5\",history3.convertToToken(\"event5\",attr0,attr1)," + withToken + ");",
				"place( itself, \"event6\",history3.convertToToken(\"event6\")," + withToken + ");",
				"place( itself, \"event7\",history.onEvent7()," + withToken + ");" };
	}

	private String[] getExpectedHistoryEvents() {
		return new String[] { "clearHistory(itself);", "addConverter( \"event4\",history2);", "addConverter( \"historyName\",history);",
				"addConverter( \"event1\",history);", "addConverter( \"event5\",history3);", "addConverter( \"event6\",history3);",
				"addConverter( \"event7\",history);" };
	}

	private String[] getExpectedSplitterPassiveGenerateMultiple() {
		return new String[] {
				"List<com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter> handlerspresenterMultiple = eventBus.getHandlers(com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter.class);",
				"if(handlerspresenterMultiple!= null){",
				"com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter handler;",
				"int handlerCount = handlerspresenterMultiple.size();",
				"for(int i=0; i<handlerCount; i++){",
				"handler = handlerspresenterMultiple.get(i);",
				"if (handler.isActivated(true, \"event1\", new Object[]{attr0,attr1})){",
				"handler.onEvent1(attr0,attr1);",
				"List<com.mvp4g.util.test_tools.annotation.handlers.SimpleEventHandler> handlerseventHandlerMultiple = eventBus.getHandlers(com.mvp4g.util.test_tools.annotation.handlers.SimpleEventHandler.class);",
				"if(handlerseventHandlerMultiple!= null){",
				"com.mvp4g.util.test_tools.annotation.handlers.SimpleEventHandler handler;",
				"int handlerCount = handlerseventHandlerMultiple.size();",
				"handler = handlerseventHandlerMultiple.get(i);",
				"handler.setActivated(true);",
				"handler.setActivated(false);",
				"public void event5(String attr0,String attr1,String attr2){",
				"com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter presenterMultiple = BaseEventBus.setPresenter( injector.getpresenterMultiple(), injector.getviewMultiple(), eventBus);",
				"eventBus.finishAddHandler(presenterMultiple,com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter.class, true);",
				"if (presenterMultiple.isActivated(true, \"event5\", new Object[]{attr0,attr1,attr2})){",
				"presenterMultiple.onEvent5(attr0,attr1,attr2);",
				"public void event4(String attr0,String attr1,String attr2,int attr3){",
				"com.mvp4g.util.test_tools.annotation.handlers.SimpleEventHandler eventHandlerMultiple = BaseEventBus.setEventHandler( injector.geteventHandlerMultiple(), eventBus);",
				"eventBus.finishAddHandler(eventHandlerMultiple,com.mvp4g.util.test_tools.annotation.handlers.SimpleEventHandler.class, true);",
				"if (eventHandlerMultiple.isActivated(false, \"event4\", new Object[]{attr0,attr1,attr2,attr3})){",
				"eventHandlerMultiple.onEvent4(attr0,attr1,attr2,attr3);", "handler.isActivated(false, \"event3\");", "if (splitter != null ){",
				"splitter.event1(attr0,attr1);", "public void event4(String attr0,String attr1,String attr2,int attr3){",
				"loadsplitter(\"event4\", new Mvp4gEventPasser(new Object[]{attr0,attr1,attr2,attr3}){",
				"splitter.event4((String) eventObjects[0],(String) eventObjects[1],(String) eventObjects[2],(Integer) eventObjects[3]);",
				"public void event5(String attr0,String attr1,String attr2){",
				"loadsplitter(\"event5\", new Mvp4gEventPasser(new Object[]{attr0,attr1,attr2}){",
				"splitter.event5((String) eventObjects[0],(String) eventObjects[1],(String) eventObjects[2]);" };
	}

	private String[] getExpectedSplitterNotPassive() {
		return new String[] { "loadsplitter(\"event1\", new Mvp4gEventPasser(new Object[]{attr0,attr1}){",
				"loadsplitter(\"event2\", new Mvp4gEventPasser(){", "presenter.isActivated(false, \"event1\", new Object[]{attr0,attr1});",
				"splitter.event1((Integer) eventObjects[0],(String) eventObjects[1]);" };
	}

	private String[] getExpectedSplitterWithLoader() {
		return new String[] { "loader.preLoad( eventBus, eventName, params, new Command(){", "public void execute() {",
				"loader.onSuccess(eventBus, eventName, params );", "loader.onFailure( eventBus, eventName, params, reason );" };
	}

	private String[] getExpectedMultipleImpl() {
		return new String[] { "interface SplitterMultipleRunAsyncCallback extends com.google.gwt.core.client.RunAsyncCallback {}",
				"interface SplitterRunAsyncImpl extends com.mvp4g.client.Mvp4gRunAsync<SplitterMultipleRunAsyncCallback> {}",
				"((SplitterRunAsyncImpl) GWT.create(SplitterRunAsyncImpl.class)).load(new SplitterMultipleRunAsyncCallback(){",
				"interface childModuleRunAsyncCallback extends com.google.gwt.core.client.RunAsyncCallback {}",
				"interface childModuleRunAsync extends com.mvp4g.client.Mvp4gRunAsync<childModuleRunAsyncCallback> {}",
				"((com.mvp4g.client.Mvp4gRunAsync) GWT.create(childModuleRunAsync.class )).load( new childModuleRunAsyncCallback() {public void onSuccess() {" };
	}

	private String[] getExpectedSplitterLoadingConf() {
		return new String[] { "eventBus.beforeLoad();", "eventBus.afterLoad();", "eventBus.errorOnLoad(reason);" };
	}

	private String[] getExpectedSplitter( boolean passive ) {
		return new String[] { "private Splitter splitter;", "private void loadsplitter(final String eventName, final Mvp4gEventPasser passer) {",
				"GWT.runAsync(new RunAsyncCallback(){", "public void onSuccess() {", "if (splitter == null) {", "splitter = new Splitter();",
				"passer.pass(null);", "public void onFailure( Throwable reason ) {", "public class Splitter {",
				"private com.mvp4g.util.test_tools.annotation.handlers.SimpleEventHandler eventHandler;",
				"private com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter presenter;", "public Splitter(){",
				"eventHandler = BaseEventBus.setEventHandler( injector.geteventHandler(), eventBus);",
				"presenter = BaseEventBus.setPresenter( injector.getpresenter(), injector.getview(), eventBus);",
				"public void event1(int attr0,String attr1){", "eventHandler.setActivated(true);", "presenter.setActivated(false);",
				"if (eventHandler.isActivated(" + passive + ", \"event1\", new Object[]{attr0,attr1})){", "eventHandler.onEvent1(attr0,attr1);",
				"public void event2(){", "eventHandler.setActivated(true);", "presenter.setActivated(false);", "public void event3(){",
				"presenter.isActivated(false, \"event3\");", "splitter.event2();", "splitter.event3();", "public void pass(Mvp4gModule module){" };
	}

	private String[] getExpectedEventsWithLookup() {
		return new String[] {
				"public void dispatch( String eventType, Object... data ){",
				"try{",
				"if ( \"event4\".equals( eventType ) ){",
				"event4();",
				"} else if ( \"event2\".equals( eventType ) ){",
				"event2((java.lang.String) data[0]);",
				"} else if ( \"name3\".equals( eventType ) ){",
				"event3();",
				"if ( \"event1\".equals( eventType ) ){",
				"event1((java.lang.String) data[0],(java.lang.Object) data[1]);",
				"} else {",
				"throw new Mvp4gException( \"Event \" + eventType + \" doesn't exist. Have you forgotten to add it to your Mvp4g configuration file?\" );",
				"} catch ( ClassCastException e ) {", "handleClassCastException( e, eventType );" };
	}

	private String[] getExpectedStartPresenterViewCommon() {
		return new String[] { "this.startView = startPresenter.getView();" };
	}

	private String[] getExpectedStartPresenterView() {
		return new String[] { "this.startPresenter = startPresenter;" };
	}

	private String[] getExpectedStartPresenterViewMultiple() {
		return new String[] { "this.startPresenter = eventBus.addHandler(com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter.class);" };
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

	private String[] getNoStartView() {
		return new String[] { "throw new Mvp4gException(\"getStartView shouldn't be called since this module has no start view.\");" };
	}

	private String[] getExpectedSetNoParent() {
		return new String[] { "public void setParentModule(com.mvp4g.client.Mvp4gModule module){}", "private PlaceService placeService = null;" };
	}

	private String[] getExpectedSetParent() {
		return new String[] { "public void setParentModule(com.mvp4g.client.Mvp4gModule module){", "parentModule = module;",
				"parentEventBus = (com.mvp4g.client.event.EventBusWithLookup) module.getEventBus();" };
	}

	private String[] getExpectedChildModule( String moduleClassName ) {
		return new String[] { "private void loadchildModule(final String eventName, final Mvp4gEventPasser passer){",
				moduleClassName + " newModule = (" + moduleClassName + ") modules.get(\"" + moduleClassName + "\");", "if(newModule == null){",
				"newModule = GWT.create(" + moduleClassName + ".class);", "modules.put(\"" + moduleClassName + "\", newModule);",
				"newModule.createAndStartModule();", "if(passer != null) passer.pass(newModule);", "newModule.setParentModule(itself);" };
	}

	private String[] getExpectedLoadChildModule() {
		return new String[] {
				"public void loadChildModule(String childModuleClassName, String eventName, boolean passive, Mvp4gEventPasser passer){",
				"Mvp4gModule childModule = modules.get(childModuleClassName);", "if((childModule != null) && (passer != null)){",
				"passer.pass(childModule);", "else if(\"com.mvp4g.util.test_tools.Modules.ModuleWithParent\".equals(childModuleClassName)){",
				"loadchildModule2(eventName, passer);", "else if(\"com.mvp4g.util.test_tools.Modules.Module1\".equals(childModuleClassName)){",
				"loadchildModule1(eventName, passer);",
				"throw new Mvp4gException( \"ChildModule \" + childModuleClassName + \" not found. Is this module a sibling module?\" );" };
	}

	private String[] getExpectedLoadChildModuleWithLoader() {
		return new String[] { "final Object[] params = (passer == null) ? null : passer.getEventObjects();",
				"loader.preLoad( eventBus, eventName, params, new Command(){", "public void execute() {",
				"loader.onFailure( eventBus, eventName, params, reason );", "loader.onSuccess(eventBus, eventName, params );",
				"com.mvp4g.util.test_tools.Loaders.Loader1 loader;", "loader = injector.getloader();" };
	}

	private String[] getExpectedAsyncChildModule() {
		return new String[] { "eventBus.beforeLoad();", "GWT.runAsync(new RunAsyncCallback() {", "public void onSuccess() {",
				"eventBus.afterLoad();", "public void onFailure(Throwable reason) {", "eventBus.afterLoad();", "eventBus.errorOnLoad(reason);" };
	}

	private String[] getExpectedAsyncChildModuleErrorEmpty() {
		return new String[] { "eventBus.beforeLoad();", "GWT.runAsync(new RunAsyncCallback() {", "public void onSuccess() {",
				"eventBus.afterLoad();", "public void onFailure(Throwable reason) {", "eventBus.afterLoad();", "eventBus.errorOnLoad();" };
	}

	private String[] getExpectedAutoDisplayChildModule() {
		return new String[] { "eventBus.changeBody((com.google.gwt.user.client.ui.Widget) newModule.getStartView());" };
	}

	private String[] getExpectedEventChildModuleLoad() {
		return new String[] {
				"loadchild(\"event2\", new Mvp4gEventPasser(new Object[]{attr0}){",
				"public void pass(Mvp4gModule module){",
				"com.mvp4g.util.test_tools.annotation.events.EventBusOk eventBus = (com.mvp4g.util.test_tools.annotation.events.EventBusOk) module.getEventBus();",
				"eventBus.event2((java.lang.String) eventObjects[0]);",
				"loadchild(\"event3\", new Mvp4gEventPasser(new Object[]{attr0,attr1}){",
				"com.mvp4g.util.test_tools.annotation.events.EventBusOk eventBus = (com.mvp4g.util.test_tools.annotation.events.EventBusOk) module.getEventBus();",
				"eventBus.event3((java.lang.String) eventObjects[0],(java.lang.Object) eventObjects[1]);",
				"loadchild(\"event1\", new Mvp4gEventPasser(){",
				"public void pass(Mvp4gModule module){",
				"com.mvp4g.util.test_tools.annotation.events.EventBusOk eventBus = (com.mvp4g.util.test_tools.annotation.events.EventBusOk) module.getEventBus();",
				"eventBus.event1();" };
	}

	private String[] getExpectedEventSiblingLoad( boolean passive ) {
		return new String[] {
				"parentModule.loadChildModule(\"com.mvp4g.util.test_tools.Modules.ModuleWithParent\", \"event1\", " + passive
						+ ", new Mvp4gEventPasser(){",
				"public void pass(Mvp4gModule module){",
				"com.mvp4g.util.test_tools.annotation.events.EventBusOk eventBus = (com.mvp4g.util.test_tools.annotation.events.EventBusOk) module.getEventBus();",
				"eventBus.event1();",
				"parentModule.loadChildModule(\"com.mvp4g.util.test_tools.Modules.ModuleWithParent\", \"event2\", " + passive
						+ ", new Mvp4gEventPasser(new Object[]{attr0}){",
				"eventBus.event2((java.lang.String) eventObjects[0]);",
				"parentModule.loadChildModule(\"com.mvp4g.util.test_tools.Modules.ModuleWithParent\", \"event3\", " + passive
						+ ", new Mvp4gEventPasser(new Object[]{attr0,attr1}){",
				"eventBus.event3((java.lang.String) eventObjects[0],(java.lang.Object) eventObjects[1]);" };
	}

	private String[] getExpectedPassiveEventChildModuleLoad() {
		return new String[] {
				"Mvp4gModule module;",
				"module = modules.get(\"com.mvp4g.util.test_tools.Modules.ModuleWithParent\");",
				"if(module != null){",
				"com.mvp4g.util.test_tools.annotation.events.EventBusOk eventBus = (com.mvp4g.util.test_tools.annotation.events.EventBusOk) module.getEventBus();",
				"eventBus.event2(attr0);",
				"com.mvp4g.util.test_tools.annotation.events.EventBusOk eventBus = (com.mvp4g.util.test_tools.annotation.events.EventBusOk) module.getEventBus();",
				"eventBus.event3(attr0,attr1);",
				"com.mvp4g.util.test_tools.annotation.events.EventBusOk eventBus = (com.mvp4g.util.test_tools.annotation.events.EventBusOk) module.getEventBus();",
				"eventBus.event1();" };
	}

	private String[] getExpectedForwardParent() {
		return new String[] { "parentEventBus.test(attr0);" };
	}

	private String[] getExpectedDebug() {
		return new String[] { "protected com.mvp4g.client.event.DefaultMvp4gLogger logger;",
				"logger = new com.mvp4g.client.event.DefaultMvp4gLogger();", "int startLogDepth = BaseEventBus.logDepth;",
				"++BaseEventBus.logDepth;", "++BaseEventBus.logDepth;", "BaseEventBus.logDepth = startLogDepth;",
				"logger.log(\"Module: Mvp4gModule || event: test2 || param(s): \" + attr0, BaseEventBus.logDepth);",
				"logger.log(\"Module: Mvp4gModule || event: test3 || param(s): \" + attr0+ \", \" + attr1, BaseEventBus.logDepth);",
				"logger.log(\"Module: Mvp4gModule || event: test\", BaseEventBus.logDepth);", };
	}

	private String[] getExpectedDetailedDebug() {
		return new String[] { "protected com.mvp4g.util.test_tools.OneLogger logger;", "logger = new com.mvp4g.util.test_tools.OneLogger();",
				"int startLogDepth = BaseEventBus.logDepth;", "++BaseEventBus.logDepth;", "++BaseEventBus.logDepth;",
				"BaseEventBus.logDepth = startLogDepth;", "logger.log(\"Module: Mvp4gModule || event: test2\", BaseEventBus.logDepth);",
				"logger.log(\"Module: Mvp4gModule || event: test || param(s): \" + attr0, BaseEventBus.logDepth);",
				"logger.log(handler.toString() + \" handles test2\", BaseEventBus.logDepth);" };
	}

	private String[] getExpectedChildMethod() {
		return new String[] { "parentModule.addConverter(\"child/\" + historyName, hc);",
				"return parentModule.place(\"child/\" + token, form, onlyToken );", "parentModule.clearHistory();",
				"parentEventBus.setNavigationConfirmation(navigationConfirmation);", "parentEventBus.confirmNavigation(event);",
				"parentEventBus.setApplicationHistoryStored(historyStored);" };
	}

	private String[] getExpectedChildMethodNoHistory() {
		return new String[] { "throw new Mvp4gException(\"This method shouldn't be called. There is no history support for this module.\");" };
	}

	private String[] getExpectedHistoryParent() {
		return new String[] { "if(\"child\".equals(moduleHistoryName)){", "loadchild(null, nextPasser);", "return;" };
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
				"List<com.mvp4g.util.test_tools.annotation.handlers.EventHandlerWithEvent> handlershandler4act = getHandlers(com.mvp4g.util.test_tools.annotation.handlers.EventHandlerWithEvent.class);",
				"if(handlershandler4act!= null){", "com.mvp4g.util.test_tools.annotation.handlers.EventHandlerWithEvent handler;",
				"int handlerCount = handlershandler4act.size();", "for(int i=0; i<handlerCount; i++){", "handler = handlershandler4act.get(i);",
				"handler.setActivated(true);" };
	}

	public String[] getExpectedNavigationEvents() {
		return new String[] { "public void event1(final java.lang.String attr0,final java.lang.Object attr1){",
				"confirmNavigation(new NavigationEventCommand(this){", "public void execute(){" };
	}

	public String[] getExpectedGenerateEvents() {
		return new String[] {
				MultiplePresenter.class.getCanonicalName()
						+ " handler2 = BaseEventBus.setPresenter( injector.gethandler2(), injector.getview(), eventBus);",
				"eventBus.finishAddHandler(handler4,com.mvp4g.util.test_tools.annotation.handlers.EventHandlerWithEvent.class, true);",
				EventHandlerWithEvent.class.getCanonicalName() + " handler4 = BaseEventBus.setEventHandler( injector.gethandler4(), eventBus);",
				"eventBus.finishAddHandler(handler4,com.mvp4g.util.test_tools.annotation.handlers.EventHandlerWithEvent.class, true);",
				"if (handler2.isActivated(true, \"event4\")){", "handler2.onEvent4();", "if (handler4.isActivated(true, \"event4\")){",
				"handler4.onEvent4();" };
	}

	public String[] getExpectedEventsWithToken() {
		return new String[] { "public String event2(java.lang.String attr0,java.lang.Object attr1){", "if(tokenMode){", "tokenMode=false;",
				"((com.mvp4g.client.event.BaseEventBus) parentEventBus).tokenMode = true;", "return parentEventBus.event2(attr0,attr1);", "} else {",
				"return null;", "public String event1(java.lang.String attr0,java.lang.Object attr1){",
				"return place( itself, \"event1\",history.onEvent1(attr0,attr1),true);",
				"return place( itself, \"event3\",history.onEvent3(attr0,attr1),true);",
				"public String event3(final java.lang.String attr0,final java.lang.Object attr1){", "return;" };
	}

	private String[] getExpectedGinInjector() {
		return new String[] { "public interface com_mvp4g_client_Mvp4gModuleGinjector extends Ginjector {",
				"com.mvp4g.util.test_tools.annotation.Presenters.MultiplePresenter gethandler2();",
				"com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter gethandler1();",
				"com.mvp4g.util.test_tools.annotation.handlers.SimpleEventHandler gethandler3();",
				"com.mvp4g.util.test_tools.annotation.handlers.EventHandlerWithEvent gethandler4();", "java.lang.String getview();",
				"com.mvp4g.util.test_tools.annotation.history_converters.SimpleHistoryConverter gethistory();" };
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
		return new String[] { "if (!filterEvent(\"event2\", new Object[]{attr0})){", "if (!filterEvent(\"event3\")){",
				"if (!filterEvent(\"event1\", new Object[]{attr0,attr1})){", "return;", };
	}

	private String[] getExpectedPrimitives() {
		return new String[] { "eventBus.event1((Boolean) eventObjects[0],(Byte) eventObjects[1],(Character) eventObjects[2],(Double) eventObjects[3],(Float) eventObjects[4],(Integer) eventObjects[5],(Long) eventObjects[6],(Short) eventObjects[7]);" };
	}

	private String[] getExpectedEventFiltersLog() {
		return new String[] { "logger.log(\"event event2 didn't pass filter(s)\", BaseEventBus.logDepth);",
				"logger.log(\"event event3 didn't pass filter(s)\", BaseEventBus.logDepth);",
				"logger.log(\"event event1 didn't pass filter(s)\", BaseEventBus.logDepth);" };
	}

	private String[] getExpectedNoFiltering() {
		return new String[] { "eventBus.setFilteringEnabledForNextOne(false);" };
	}

	private void createHandlers() {
		Set<PresenterElement> presenters = configuration.getPresenters();
		Set<EventHandlerElement> eventHandlers = configuration.getEventHandlers();

		ViewElement view = new ViewElement();
		view.setName( "view" );
		view.setClassName( String.class.getCanonicalName() );
		configuration.getViews().add( view );

		HistoryConverterElement history = new HistoryConverterElement();
		history.setName( "history" );
		history.setClassName( SimpleHistoryConverter.class.getCanonicalName() );
		configuration.getHistoryConverters().add( history );

		PresenterElement p1 = new PresenterElement();
		p1.setName( "handler1" );
		p1.setMultiple( Boolean.FALSE.toString() );
		p1.setClassName( SimplePresenter.class.getCanonicalName() );
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
		eh1.setClassName( SimpleEventHandler.class.getCanonicalName() );
		EventHandlerElement eh2 = new EventHandlerElement();
		eh2.setName( "handler4" );
		eh2.setMultiple( Boolean.TRUE.toString() );
		eh2.setClassName( EventHandlerWithEvent.class.getCanonicalName() );
		eventHandlers.add( eh1 );
		eventHandlers.add( eh2 );
	}
}
