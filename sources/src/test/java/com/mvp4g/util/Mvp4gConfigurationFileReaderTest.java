package com.mvp4g.util;

import static junit.framework.Assert.assertEquals;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.util.UnitTestTreeLogger;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.BaseEventBusWithLookUp;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.util.config.Mvp4gConfiguration;
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

		events.add( e1 );
		events.add( e3 );
		events.add( e2 );

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

		Set<EventElement> events = configuration.getEvents();
		events.add( e1 );
		events.add( e3 );
		events.add( e2 );

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
		writer.writeConf();
		assertOutput( getExpectedHistory(), true );

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
		writer.writeConf();
		assertOutput( getExpectedHistoryXml(), true );

	}
	
	@Test
	public void testWriteNoHistoryStart() throws DuplicatePropertyNameException {
		
		configuration.setHistory( null );

		assertOutput( getExpectedHistory(), false );
		writer.writeConf();
		assertOutput( getExpectedHistory(), false );
		
		String eventBusInterface = EventBusWithLookup.class.getName();
		String eventBusClass = BaseEventBusWithLookUp.class.getName();
		configuration.setEventBus( new EventBusElement( eventBusInterface, eventBusClass, false ) );
		
		assertOutput( getExpectedHistoryXml(), false );
		writer.writeConf();
		assertOutput( getExpectedHistoryXml(), false );

	}

	/*
	 * 
	 * @Test public void testWriteConfConstructsEventBus() throws UnableToCompleteException {
	 * 
	 * String output = "EventBus eventBus = new EventBus();";
	 * 
	 * assertFalse( sourceWriter.dataContains( output ) );
	 * 
	 * configReader.writeConf();
	 * 
	 * assertTrue( eventBusError(), sourceWriter.dataContains( output ) ); }
	 * 
	 * private String eventBusError() { return "EventBus construction not found in data:\n" +
	 * sourceWriter.getData(); }
	 * 
	 * @Test public void testWriteViews() throws UnableToCompleteException {
	 * 
	 * assertOutput( getExpectedViews(), false );
	 * 
	 * configReader.writeConf();
	 * 
	 * assertOutput( getExpectedViews(), true ); }
	 * 
	 * @Test public void testWritePresenters() throws UnableToCompleteException {
	 * 
	 * assertOutput( getExpectedPresenters(), false );
	 * 
	 * configReader.writeConf();
	 * 
	 * assertOutput( getExpectedPresenters(), true ); }
	 * 
	 * @Test public void testWriteEvents() throws UnableToCompleteException {
	 * 
	 * assertOutput( getExpectedEvents(), false );
	 * 
	 * configReader.writeConf();
	 * 
	 * assertOutput( getExpectedEvents(), true ); }
	 * 
	 * @Test public void testWriteStartEvent() throws UnableToCompleteException {
	 * 
	 * assertOutput( getExpectedStartEvent(), false );
	 * 
	 * configReader.writeConf();
	 * 
	 * assertOutput( getExpectedStartEvent(), true ); }
	 * 
	 * @Test public void testWriteHistory() throws UnableToCompleteException {
	 * 
	 * assertOutput( getExpectedHistory(), false );
	 * 
	 * configReader.writeConf();
	 * 
	 * assertOutput( getExpectedHistory(), true ); }
	 * 
	 * @Test public void testWriteServices() throws UnableToCompleteException {
	 * 
	 * assertOutput( getExpectedServices(), false );
	 * 
	 * configReader.writeConf();
	 * 
	 * assertOutput( getExpectedServices(), true ); }
	 * 
	 * @Test public void testCapitalized() { String input = "donQuixote"; String expected =
	 * "DonQuixote";
	 * 
	 * assertEquals( expected, configReader.capitalized( input ) ); }
	 * 
	 * @Test public void testGetObjectClass() throws UnableToCompleteException {
	 * 
	 * configReader.writeConf();
	 * 
	 * EventElement event = new EventElement(); assertNull( configReader.getObjectClass( event ) );
	 * 
	 * event = new EventElement(); event.setEventObjectClass( String.class.getName() );
	 * assertEquals( String.class.getName(), configReader.getObjectClass( event ) );
	 * 
	 * event = new EventElement(); event.setCalledMethod( "onInit" ); event.setHandlers( new
	 * String[] { "rootPresenter" } ); assertNull( configReader.getObjectClass( event ) );
	 * 
	 * event = new EventElement(); event.setCalledMethod( "onDisplayMessage" ); event.setHandlers(
	 * new String[] { "rootPresenter" } ); assertEquals( String.class.getName(),
	 * configReader.getObjectClass( event ) );
	 * 
	 * try { event = new EventElement(); event.setType( "one" ); event.setCalledMethod(
	 * "unknownMethod" ); event.setHandlers( new String[] { "rootPresenter" } );
	 * configReader.getObjectClass( event ); fail(); } catch ( InvalidMvp4gConfigurationException ex
	 * ) { String expected = "Tag " + event.getTagName() +
	 * " one: handler rootPresenter doesn't define a method unknownMethod with 1 or 0 parameter.";
	 * assertEquals( expected, ex.getMessage() ); }
	 * 
	 * try { event = new EventElement(); event.setType( "one" ); event.setCalledMethod( "onTest" );
	 * event.setHandlers( new String[] { "rootPresenter" } ); configReader.getObjectClass( event );
	 * fail(); } catch ( InvalidMvp4gConfigurationException ex ) { String expected = "Tag " +
	 * event.getTagName() +
	 * " one: handler rootPresenter doesn't define a method onTest with 1 or 0 parameter.";
	 * assertEquals( expected, ex.getMessage() ); }
	 * 
	 * try { event = new EventElement(); event.setType( "one" ); event.setCalledMethod( "onInit" );
	 * event.setHandlers( new String[] { "displayUserPresenter" } ); configReader.getObjectClass(
	 * event ); fail(); } catch ( InvalidMvp4gConfigurationException ex ) { String expected = "Tag "
	 * + event.getTagName() +
	 * " one: displayUserPresenter handler class: com.mvp4g.example.client.presenter.display.UserDisplayPresenter is not found"
	 * ; assertEquals( expected, ex.getMessage() ); }
	 * 
	 * }
	 */

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
				"final PlaceService<com.mvp4g.client.event.EventBus> placeService = new PlaceService<com.mvp4g.client.event.EventBus>(){",
				"protected void sendInitEvent(){",
				"getEventBus().init();",
				"protected void sendNotFoundEvent(){",
				"getEventBus().notFound()",
				"placeService.setEventBus(eventBus);",
				"final com.mvp4g.example.client.history.display.UserHistoryConverter userConverter = new com.mvp4g.example.client.history.display.UserHistoryConverter();",
				"userConverter.setUserService(userService);",
				"final com.mvp4g.example.client.history.StringHistoryConverter stringConverter = new com.mvp4g.example.client.history.StringHistoryConverter();"
				 };

	}
	
	private String[] getExpectedHistoryXml() {
		return new String[] {
				"final PlaceService<com.mvp4g.client.event.EventBusWithLookup> placeService = new PlaceService<com.mvp4g.client.event.EventBusWithLookup>(){",
				"protected void sendInitEvent(){",
				"getEventBus().dispatch(\"init\");",
				"protected void sendNotFoundEvent(){",
				"getEventBus().dispatch(\"notFound\")",
				"placeService.setEventBus(eventBus);"
				 };

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
				"public void userDisplay(java.lang.String form){", "displayUserPresenter.onUserDisplay(form);",
				"place( placeService, \"userDisplay\", form )", "public void displayMessage()", "rootPresenter.onDisplayMessage();","placeService.addConverter( \"userDisplay\",history);" };
	}

	private String[] getExpectedEventsWithLookup() {
		return new String[] {
				"public void dispatch( String eventType, Object form ){",
				"if ( \"userDisplay\".equals( eventType ) ){",
				"userDisplay( (java.lang.String) form);",
				"} else if ( \"displayMessage\".equals( eventType ) ){",
				"displayMessage();",
				"} else if ( \"userCreated\".equals( eventType ) ){",
				"userCreated( (java.lang.String) form);",
				"throw new Mvp4gException( \"Event \" + eventType + \" doesn't exist. Have you forgotten to add it to your Mvp4g configuration file?\" );",
				"handleClassCastException( e, eventType );" };
	}

	private String[] getExpectedStartXmlEvent() {
		return new String[] { "RootPanel.get().add(rootView);", "eventBus.dispatch(\"start\");", "History.fireCurrentHistoryState();" };
	}

	private String[] getExpectedStartEvent() {
		return new String[] { "RootPanel.get().add(rootView);", "eventBus.start();", "History.fireCurrentHistoryState();" };
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
}
