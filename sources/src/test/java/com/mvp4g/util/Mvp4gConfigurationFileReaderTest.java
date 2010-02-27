package com.mvp4g.util;

import static junit.framework.Assert.assertEquals;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.util.UnitTestTreeLogger;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.BaseEventBusWithLookUp;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.ChildModuleElement;
import com.mvp4g.util.config.element.ChildModulesElement;
import com.mvp4g.util.config.element.DebugElement;
import com.mvp4g.util.config.element.EventBusElement;
import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.HistoryElement;
import com.mvp4g.util.config.element.InjectedElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.test_tools.Modules;
import com.mvp4g.util.test_tools.SourceWriterTestStub;
import com.mvp4g.util.test_tools.TypeOracleStub;

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

		StartElement start = new StartElement();
		start.setView( "view" );
		configuration.setStart( start );

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
		assertOutput( getExpectedEventsWithLookup(), false );

		Set<EventElement> events = configuration.getEvents();

		EventElement e1 = new EventElement();
		e1.setType( "userCreated" );
		e1.setHandlers( new String[] { "displayUserPresenter" } );
		e1.setEventObjectClass( "java.lang.String" );

		EventElement e2 = new EventElement();
		e2.setType( "userDisplay" );
		e2.setHandlers( new String[] { "displayUserPresenter" } );
		e2.setEventObjectClass( "java.lang.String" );
		e2.setHistory( "history" );

		EventElement e3 = new EventElement();
		e3.setType( "displayMessage" );
		e3.setHandlers( new String[] { "rootPresenter" } );

		EventElement e4 = new EventElement();
		e4.setType( "userEdit" );
		e4.setHandlers( new String[] { "displayUserPresenter" } );
		e4.setHistory( "history" );

		events.add( e1 );
		events.add( e2 );
		events.add( e3 );
		events.add( e4 );

		writer.writeConf();

		assertOutput( getExpectedEvents(), true );
		assertOutput( getExpectedEventsWithLookup(), false );

	}

	@Test
	public void testWriteEventsWithLookup() throws DuplicatePropertyNameException {

		String eventBusInterface = EventBusWithLookup.class.getName();
		String eventBusClass = BaseEventBusWithLookUp.class.getName();
		configuration.setEventBus( new EventBusElement( eventBusInterface, eventBusClass, true ) );

		assertOutput( getExpectedEvents(), false );
		assertOutput( getExpectedEventsWithLookup(), false );

		EventElement e1 = new EventElement();
		e1.setType( "userCreated" );
		e1.setHandlers( new String[] { "displayUserPresenter" } );
		e1.setEventObjectClass( "java.lang.String" );

		EventElement e2 = new EventElement();
		e2.setType( "userDisplay" );
		e2.setHandlers( new String[] { "displayUserPresenter" } );
		e2.setEventObjectClass( "java.lang.String" );
		e2.setHistory( "history" );

		EventElement e3 = new EventElement();
		e3.setType( "displayMessage" );
		e3.setHandlers( new String[] { "rootPresenter" } );

		EventElement e4 = new EventElement();
		e4.setType( "userEdit" );
		e4.setHandlers( new String[] { "displayUserPresenter" } );
		e4.setHistory( "history" );

		Set<EventElement> events = configuration.getEvents();
		events.add( e1 );
		events.add( e2 );
		events.add( e3 );
		events.add( e4 );

		writer.writeConf();

		assertOutput( getExpectedEvents(), true );
		assertOutput( getExpectedEventsWithLookup(), true );

	}

	@Test
	public void testWriteViews() throws DuplicatePropertyNameException {

		ViewElement view1 = new ViewElement();
		view1.setName( "rootView" );
		view1.setClassName( "com.mvp4g.util.test_tools.RootView" );

		ViewElement view2 = new ViewElement();
		view2.setName( "userCreateView" );
		view2.setClassName( "com.mvp4g.example.client.view.UserCreateView" );

		ViewElement view3 = new ViewElement();
		view3.setName( "userDisplayView" );
		view3.setClassName( "com.mvp4g.example.client.view.display.UserDisplayView" );

		Set<ViewElement> views = configuration.getViews();
		views.add( view1 );
		views.add( view2 );
		views.add( view3 );

		assertOutput( getExpectedViews(), false );

		writer.writeConf();

		assertOutput( getExpectedViews(), true );

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
		writer.writeConf();
		assertOutput( getExpectedPresenters(), true );
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

		PresenterElement presenter = new PresenterElement();
		presenter.setName( "startPresenter" );
		presenter.setView( "rootView" );
		presenter.setClassName( Object.class.getCanonicalName() );
		configuration.getPresenters().add( presenter );

		String eventBusInterface = EventBusWithLookup.class.getName();
		String eventBusClass = BaseEventBusWithLookUp.class.getName();
		configuration.setEventBus( new EventBusElement( eventBusInterface, eventBusClass, false ) );

		StartElement start = new StartElement();
		start.setView( "rootView" );
		start.setEventType( "start" );
		start.setHistory( "true" );
		configuration.setStart( start );

		assertOutput( getExpectedStartXmlEvent(), false );
		writer.writeConf();
		assertOutput( getExpectedStartXmlEvent(), true );

	}

	@Test
	public void testWriteStart() throws DuplicatePropertyNameException {

		PresenterElement presenter = new PresenterElement();
		presenter.setName( "startPresenter" );
		presenter.setView( "rootView" );
		presenter.setClassName( Object.class.getCanonicalName() );
		configuration.getPresenters().add( presenter );

		StartElement start = new StartElement();
		start.setView( "rootView" );
		start.setEventType( "start" );
		start.setHistory( "true" );
		configuration.setStart( start );

		assertOutput( getExpectedStartEvent(), false );
		writer.writeConf();
		assertOutput( getExpectedStartEvent(), true );

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

		assertOutput( getExpectedHistory(), false );
		assertOutput( getExpectedInheritHistory(), false );
		writer.writeConf();
		assertOutput( getExpectedHistory(), true );
		assertOutput( getExpectedInheritHistory(), true );

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

		assertOutput( getExpectedHistoryXml(), false );
		assertOutput( getExpectedInheritHistory(), false );
		writer.writeConf();
		assertOutput( getExpectedHistoryXml(), true );
		assertOutput( getExpectedInheritHistory(), true );

	}

	@Test
	public void testWriteNoHistoryStart() throws DuplicatePropertyNameException {

		configuration.setHistory( null );

		assertOutput( getExpectedHistory(), false );
		assertOutput( getExpectedInheritHistory(), false );
		writer.writeConf();
		assertOutput( getExpectedHistory(), false );
		assertOutput( getExpectedInheritHistory(), true );

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
		assertOutput( new String[] { "private PlaceService placeService = null;" }, false );
		assertOutput( getExpectedSetParent(), false );
		writer.writeConf();
		assertOutput( new String[] { "private PlaceService placeService = null;" }, true );
		assertOutput( getExpectedSetParent(), false );
	}

	@Test
	public void testWriteParent() {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		configuration.setParentModule( oracle.addClass( Mvp4gModule.class ) );
		configuration.setParentEventBus( oracle.addClass( EventBusWithLookup.class ) );

		assertOutput( new String[] { "private PlaceService placeService = null;" }, false );
		assertOutput( getExpectedSetParent(), false );
		writer.writeConf();
		assertOutput( getExpectedSetParent(), true );
		assertOutput( new String[] { "private PlaceService placeService = null;" }, false );
	}

	@Test
	public void testWriteChildWithNoParentNoAsyncNoAutoLoad() throws DuplicatePropertyNameException {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.Module1.class );
		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule" );
		childModule.setAsync( "false" );
		childModule.setAutoDisplay( "false" );

		configuration.getChildModules().add( childModule );
		configuration.setLoadChildConfig( new ChildModulesElement() );

		assertOutput( getExpectedChildModule( Modules.Module1.class.getCanonicalName() ), false );
		assertOutput( getExpectedChildModuleWithParent(), false );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
		writer.writeConf();
		assertOutput( getExpectedChildModule( Modules.Module1.class.getCanonicalName() ), true );
		assertOutput( getExpectedChildModuleWithParent(), false );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
	}

	@Test
	public void testWriteChildWithParentNoAsyncNoAutoLoad() throws DuplicatePropertyNameException {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.ModuleWithParent.class );
		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule" );
		childModule.setAsync( "false" );
		childModule.setAutoDisplay( "false" );

		configuration.getChildModules().add( childModule );
		configuration.setLoadChildConfig( new ChildModulesElement() );

		assertOutput( getExpectedChildModule( Modules.ModuleWithParent.class.getCanonicalName() ), false );
		assertOutput( getExpectedChildModuleWithParent(), false );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
		writer.writeConf();
		assertOutput( getExpectedChildModule( Modules.ModuleWithParent.class.getCanonicalName() ), true );
		assertOutput( getExpectedChildModuleWithParent(), true );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
	}

	@Test
	public void testWriteChildWithNoParentAsyncNoAutoLoad() throws DuplicatePropertyNameException {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.Module1.class );
		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule" );
		childModule.setAutoDisplay( "false" );

		EventElement event = new EventElement();
		event.setType( "errorOnLoad" );
		event.setEventObjectClass( Throwable.class.getCanonicalName() );
		configuration.getEvents().add( event );

		ChildModulesElement loadConfig = new ChildModulesElement();
		loadConfig.setAfterEvent( "afterLoad" );
		loadConfig.setBeforeEvent( "beforeLoad" );
		loadConfig.setErrorEvent( "errorOnLoad" );
		configuration.getChildModules().add( childModule );
		configuration.setLoadChildConfig( loadConfig );

		assertOutput( getExpectedChildModule( Modules.Module1.class.getCanonicalName() ), false );
		assertOutput( getExpectedChildModuleWithParent(), false );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
		writer.writeConf();
		assertOutput( getExpectedChildModule( Modules.Module1.class.getCanonicalName() ), true );
		assertOutput( getExpectedChildModuleWithParent(), false );
		assertOutput( getExpectedAsyncChildModule(), true );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
	}

	@Test
	public void testWriteChildWithNoParentAsyncNoAutoLoadNotGwt2() throws DuplicatePropertyNameException {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		JClassType moduleType = oracle.addClass( Modules.Module1.class );
		oracle.setGWT2( false );
		ChildModuleElement childModule = new ChildModuleElement();
		childModule.setClassName( moduleType.getQualifiedSourceName() );
		childModule.setName( "childModule" );
		childModule.setAutoDisplay( "false" );

		EventElement event = new EventElement();
		event.setType( "errorOnLoad" );
		event.setEventObjectClass( Throwable.class.getCanonicalName() );
		configuration.getEvents().add( event );

		ChildModulesElement loadConfig = new ChildModulesElement();
		loadConfig.setAfterEvent( "afterLoad" );
		loadConfig.setBeforeEvent( "beforeLoad" );
		loadConfig.setErrorEvent( "errorOnLoad" );
		configuration.getChildModules().add( childModule );
		configuration.setLoadChildConfig( loadConfig );

		assertOutput( getExpectedChildModule( Modules.Module1.class.getCanonicalName() ), false );
		assertOutput( getExpectedChildModuleWithParent(), false );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
		writer.writeConf();
		assertOutput( getExpectedChildModule( Modules.Module1.class.getCanonicalName() ), true );
		assertOutput( getExpectedChildModuleWithParent(), false );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
	}

	@Test
	public void testWriteChildWithNoParentAsyncNoAutoLoadErrorEmpty() throws DuplicatePropertyNameException {
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
		assertOutput( getExpectedChildModuleWithParent(), false );
		assertOutput( getExpectedAsyncChildModuleErrorEmpty(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
		writer.writeConf();
		assertOutput( getExpectedChildModule( Modules.Module1.class.getCanonicalName() ), true );
		assertOutput( getExpectedChildModuleWithParent(), false );
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
		event.setEventObjectClass( Throwable.class.getCanonicalName() );
		configuration.getEvents().add( event );

		ChildModulesElement loadConfig = new ChildModulesElement();
		loadConfig.setAfterEvent( "afterLoad" );
		loadConfig.setBeforeEvent( "beforeLoad" );
		loadConfig.setErrorEvent( "errorOnLoad" );
		configuration.getChildModules().add( childModule );
		configuration.setLoadChildConfig( loadConfig );

		assertOutput( getExpectedChildModule( Modules.ModuleWithParent.class.getCanonicalName() ), false );
		assertOutput( getExpectedChildModuleWithParent(), false );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
		writer.writeConf();
		assertOutput( getExpectedChildModule( Modules.ModuleWithParent.class.getCanonicalName() ), true );
		assertOutput( getExpectedChildModuleWithParent(), true );
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
		event.setEventObjectClass( Throwable.class.getCanonicalName() );
		configuration.getEvents().add( event );

		event = new EventElement();
		event.setType( "changeBody" );
		event.setEventObjectClass( Widget.class.getCanonicalName() );
		configuration.getEvents().add( event );

		ChildModulesElement loadConfig = new ChildModulesElement();
		loadConfig.setAfterEvent( "afterLoad" );
		loadConfig.setBeforeEvent( "beforeLoad" );
		loadConfig.setErrorEvent( "errorOnLoad" );
		configuration.getChildModules().add( childModule );
		configuration.setLoadChildConfig( loadConfig );

		assertOutput( getExpectedChildModule( Modules.ModuleWithParent.class.getCanonicalName() ), false );
		assertOutput( getExpectedChildModuleWithParent(), false );
		assertOutput( getExpectedAsyncChildModule(), false );
		assertOutput( getExpectedAutoDisplayChildModule(), false );
		writer.writeConf();
		assertOutput( getExpectedChildModule( Modules.ModuleWithParent.class.getCanonicalName() ), true );
		assertOutput( getExpectedChildModuleWithParent(), true );
		assertOutput( getExpectedAsyncChildModule(), true );
		assertOutput( getExpectedAutoDisplayChildModule(), true );
	}

	@Test
	public void testWriteChildEventXML() throws DuplicatePropertyNameException {

		configuration.setLoadChildConfig( new ChildModulesElement() );
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
		event.setEventObjectClass( Object.class.getCanonicalName() );
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

		EventElement event = new EventElement();
		event.setType( "test" );
		event.setModulesToLoad( new String[] { "child" } );
		configuration.getEvents().add( event );
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
		event.setEventObjectClass( Object.class.getCanonicalName() );
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
		event.setEventObjectClass( Object.class.getCanonicalName() );
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
		event.setEventObjectClass( Object.class.getCanonicalName() );
		configuration.getEvents().add( event );

		event = new EventElement();
		event.setType( "test2" );
		configuration.getEvents().add( event );

		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		configuration.setModule( oracle.addClass( Mvp4gModule.class ) );

		DebugElement debug = new DebugElement();
		debug.setEnabled( "true" );
		configuration.setDebug( debug );
		assertOutput( getExpectedDebug(), false );
		writer.writeConf();
		assertOutput( getExpectedDebug(), true );
	}

	@Test
	public void testWriteChildHistory() {
		TypeOracleStub oracle = (TypeOracleStub)configuration.getOracle();
		configuration.setParentModule( oracle.addClass( Mvp4gModule.class ) );
		configuration.setParentEventBus( oracle.addClass( EventBusWithLookup.class ) );
		configuration.setHistoryName( "child" );

		assertOutput( getExpectedHistoryChild(), false );
		writer.writeConf();
		assertOutput( getExpectedHistoryChild(), true );
	}

	@Test
	public void testWriteParentHistory() throws DuplicatePropertyNameException {

		configuration.setLoadChildConfig( new ChildModulesElement() );
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
		return new String[] {
				"final com.mvp4g.util.test_tools.RootView " + "rootView = new com.mvp4g.util.test_tools.RootView();",

				"final com.mvp4g.example.client.view.UserCreateView " + "userCreateView = new com.mvp4g.example.client.view.UserCreateView();",

				"final com.mvp4g.example.client.view.display.UserDisplayView "
						+ "userDisplayView = new com.mvp4g.example.client.view.display.UserDisplayView();" };
	}

	private String[] getExpectedHistory() {
		return new String[] {
				"placeService = new PlaceService(){",
				"protected void sendInitEvent(){",
				"eventBus.init();",
				"protected void sendNotFoundEvent(){",
				"eventBus.notFound()",
				"placeService.setModule(itself);",
				"final com.mvp4g.example.client.history.display.UserHistoryConverter userConverter = new com.mvp4g.example.client.history.display.UserHistoryConverter();",
				"userConverter.setUserService(userService);",
				"final com.mvp4g.example.client.history.StringHistoryConverter stringConverter = new com.mvp4g.example.client.history.StringHistoryConverter();", };

	}

	private String[] getExpectedInheritHistory() {
		return new String[] { "public void addConverter(String token, HistoryConverter<?,?> hc){", "placeService.addConverter(token, hc);",
				"public <T> void place(String token, T form){", "placeService.place( token, form );",
				"public <T> void dispatchHistoryEvent(String eventType, final Mvp4gEventPasser<Boolean> passer){",
				"int index = eventType.indexOf(PlaceService.MODULE_SEPARATOR);", "if(index > -1){",
				"String moduleHistoryName = eventType.substring(0, index);", "String nextToken = eventType.substring(index + 1);",
				"Mvp4gEventPasser<String> nextPasser = new Mvp4gEventPasser<String>(nextToken) {", "public void pass(Mvp4gModule module) {",
				"module.dispatchHistoryEvent(eventObject, passer);", "passer.setEventObject(false);", "passer.pass(this);", "}else{",
				"passer.pass(this);" };
	}

	private String[] getExpectedHistoryXml() {
		return new String[] { "placeService = new PlaceService(){", "protected void sendInitEvent(){", "eventBus.dispatch(\"init\");",
				"protected void sendNotFoundEvent(){", "eventBus.dispatch(\"notFound\")", "placeService.setModule(itself);", };

	}

	private String[] getExpectedPresenters() {
		return new String[] {
				"final com.mvp4g.util.test_tools.RootPresenter rootPresenter = new com.mvp4g.util.test_tools.RootPresenter();",
				"rootPresenter.setEventBus(eventBus);",
				"rootPresenter.setView(rootView);",

				"final com.mvp4g.example.client.presenter.UserCreatePresenter "
						+ "createUserPresenter = new com.mvp4g.example.client.presenter.UserCreatePresenter();",

				"createUserPresenter.setEventBus(eventBus);",
				"createUserPresenter.setView(userCreateView);",
				"createUserPresenter.setUserService(userService);",

				"final com.mvp4g.example.client.presenter.display.UserDisplayPresenter "
						+ "displayUserPresenter = new com.mvp4g.example.client.presenter.display.UserDisplayPresenter();",

				"displayUserPresenter.setEventBus(eventBus);", "displayUserPresenter.setView(userDisplayView);" };
	}

	private String[] getExpectedEvents() {
		return new String[] { "public void userCreated(java.lang.String form){", "displayUserPresenter.onUserCreated(form);",
				"displayUserPresenter.bindIfNeeded();", "public void userDisplay(java.lang.String form){",
				"displayUserPresenter.onUserDisplay(form);", "displayUserPresenter.bindIfNeeded();", "place( itself, \"userDisplay\", form )",
				"public void displayMessage()", "rootPresenter.bindIfNeeded();", "rootPresenter.onDisplayMessage();",
				"addConverter( \"userDisplay\",history);", "place( itself, \"userEdit\", null )", };
	}

	private String[] getExpectedEventsWithLookup() {
		return new String[] {
				"public void dispatch( String eventType, Object form ){",
				"} else if ( \"userDisplay\".equals( eventType ) ){",
				"userDisplay( (java.lang.String) form);",
				"if ( \"displayMessage\".equals( eventType ) ){",
				"displayMessage();",
				"} else if ( \"userCreated\".equals( eventType ) ){",
				"userCreated( (java.lang.String) form);",
				"throw new Mvp4gException( \"Event \" + eventType + \" doesn't exist. Have you forgotten to add it to your Mvp4g configuration file?\" );",
				"handleClassCastException( e, eventType );" };
	}

	private String[] getExpectedStartXmlEvent() {
		return new String[] { "eventBus.dispatch(\"start\");", "startPresenter.bindIfNeeded();", "History.fireCurrentHistoryState();" };
	}

	private String[] getExpectedStartEvent() {
		return new String[] { "eventBus.start();", "startPresenter.bindIfNeeded();", "History.fireCurrentHistoryState();" };
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

	private String[] getExpectedSetParent() {
		return new String[] { "public void setParentModule(com.mvp4g.client.Mvp4gModule module){", "parentModule = module;",
				"parentEventBus = (com.mvp4g.client.event.EventBusWithLookup) module.getEventBus();" };
	}

	private String[] getExpectedChildModule( String moduleClassName ) {
		return new String[] { "private void loadchildModule(final Mvp4gEventPasser<?> passer){",
				moduleClassName + " newModule = (" + moduleClassName + ") modules.get(" + moduleClassName + ".class);", "if(newModule == null){",
				"newModule = GWT.create(" + moduleClassName + ".class);", "modules.put(" + moduleClassName + ".class, newModule);",
				"newModule.createAndStartModule();", "if(passer != null) passer.pass(newModule);" };
	}

	private String[] getExpectedChildModuleWithParent() {
		return new String[] { "newModule.setParentModule(itself);" };
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
		return new String[] { "loadchild(new Mvp4gEventPasser<java.lang.Object>(form){", "public void pass(Mvp4gModule module){",
				"com.mvp4g.client.event.EventBusWithLookup eventBus = (com.mvp4g.client.event.EventBusWithLookup) module.getEventBus();",
				"eventBus.dispatch(\"test\", eventObject);" };
	}

	private String[] getExpectedEventChildModuleLoad() {
		return new String[] {
				com.mvp4g.util.test_tools.annotation.Events.EventBusOk.class.getCanonicalName() + " eventBus = ("
						+ com.mvp4g.util.test_tools.annotation.Events.EventBusOk.class.getCanonicalName() + ") module.getEventBus();",
				"eventBus.test();" };
	}

	private String[] getExpectedForwardParent() {
		return new String[] { "parentEventBus.test(form);" };
	}

	private String[] getExpectedForwardParentXML() {
		return new String[] { "parentEventBus.dispatch(\"test\");" };
	}

	private String[] getExpectedForwardParentXMLWithForm() {
		return new String[] { "parentEventBus.dispatch(\"test\", form);" };
	}

	private String[] getExpectedDebug() {
		return new String[] { "GWT.log(\"Module: Mvp4gModule || event: test2\", null);",
				"GWT.log(\"Module: Mvp4gModule || event: test || object: \" + form, null);" };
	}

	private String[] getExpectedHistoryChild() {
		return new String[] { "parentModule.addConverter(\"child/\" + token, hc);", "parentModule.place(\"child/\" + token, form );" };
	}

	private String[] getExpectedHistoryParent() {
		return new String[] { "if(\"child\".equals(moduleHistoryName)){", "loadchild(nextPasser);", "return;" };
	}
}
