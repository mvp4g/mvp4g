package com.mvp4g.util;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.dev.util.UnitTestTreeLogger;
import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;
import com.mvp4g.util.test_tools.SourceWriterTestStub;

public class Mvp4gConfigurationFileReaderTest {

	private SourceWriterTestStub sourceWriter;
	private Mvp4gConfigurationFileReader configReader;

	@Before
	public void setUp() {
		sourceWriter = new SourceWriterTestStub();
		TreeLogger tl = new UnitTestTreeLogger.Builder().createLogger();
		configReader = new Mvp4gConfigurationFileReader( sourceWriter, tl );
	}

	@Test
	public void testWriteConfConstructsEventBus() throws UnableToCompleteException {

		String output = "EventBus eventBus = new EventBus();";

		assertFalse( sourceWriter.dataContains( output ) );

		configReader.writeConf();

		assertTrue( eventBusError(), sourceWriter.dataContains( output ) );
	}

	private String eventBusError() {
		return "EventBus construction not found in data:\n" + sourceWriter.getData();
	}

	@Test
	public void testWriteViews() throws UnableToCompleteException {

		assertOutput( getExpectedViews(), false );

		configReader.writeConf();

		assertOutput( getExpectedViews(), true );
	}

	@Test
	public void testWritePresenters() throws UnableToCompleteException {

		assertOutput( getExpectedPresenters(), false );

		configReader.writeConf();

		assertOutput( getExpectedPresenters(), true );
	}

	@Test
	public void testWriteEvents() throws UnableToCompleteException {

		assertOutput( getExpectedEvents(), false );

		configReader.writeConf();

		assertOutput( getExpectedEvents(), true );
	}

	@Test
	public void testWriteStartEvent() throws UnableToCompleteException {

		assertOutput( getExpectedStartEvent(), false );

		configReader.writeConf();

		assertOutput( getExpectedStartEvent(), true );
	}

	@Test
	public void testWriteHistory() throws UnableToCompleteException {

		assertOutput( getExpectedHistory(), false );

		configReader.writeConf();

		assertOutput( getExpectedHistory(), true );
	}

	@Test
	public void testWriteServices() throws UnableToCompleteException {

		assertOutput( getExpectedServices(), false );

		configReader.writeConf();

		assertOutput( getExpectedServices(), true );
	}

	@Test
	public void testCapitalized() {
		String input = "donQuixote";
		String expected = "DonQuixote";

		assertEquals( expected, configReader.capitalized( input ) );
	}

	@Test
	public void testGetObjectClass() throws UnableToCompleteException {

		configReader.writeConf();

		EventElement event = new EventElement();
		assertNull( configReader.getObjectClass( event ) );

		event = new EventElement();
		event.setEventObjectClass( String.class.getName() );
		assertEquals( String.class.getName(), configReader.getObjectClass( event ) );

		event = new EventElement();
		event.setCalledMethod( "onInit" );
		event.setHandlers( new String[] { "rootPresenter" } );
		assertNull( configReader.getObjectClass( event ) );

		event = new EventElement();
		event.setCalledMethod( "onDisplayMessage" );
		event.setHandlers( new String[] { "rootPresenter" } );
		assertEquals( String.class.getName(), configReader.getObjectClass( event ) );

		try {
			event = new EventElement();
			event.setType( "one" );
			event.setCalledMethod( "unknownMethod" );
			event.setHandlers( new String[] { "rootPresenter" } );
			configReader.getObjectClass( event );
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			String expected = "Tag " + event.getTagName() + " one: handler rootPresenter doesn't define a method unknownMethod with 1 or 0 parameter.";
			assertEquals( expected, ex.getMessage() );
		}
		
		try {
			event = new EventElement();
			event.setType( "one" );
			event.setCalledMethod( "onTest" );
			event.setHandlers( new String[] { "rootPresenter" } );
			configReader.getObjectClass( event );
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			String expected = "Tag " + event.getTagName() + " one: handler rootPresenter doesn't define a method onTest with 1 or 0 parameter.";
			assertEquals( expected, ex.getMessage() );
		}
		
		try {
			event = new EventElement();
			event.setType( "one" );
			event.setCalledMethod( "onInit" );
			event.setHandlers( new String[] { "displayUserPresenter" } );
			configReader.getObjectClass( event );
			fail();
		} catch ( InvalidMvp4gConfigurationException ex ) {
			String expected = "Tag " + event.getTagName() + " one: displayUserPresenter handler class: com.mvp4g.example.client.presenter.UserDisplayPresenter is not found";
			assertEquals( expected, ex.getMessage() );
		}

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
		return new String[] { "final com.mvp4g.util.test_tools.RootView " + "rootView = new com.mvp4g.util.test_tools.RootView();",

		"final com.mvp4g.example.client.view.UserCreateView " + "userCreateView = new com.mvp4g.example.client.view.UserCreateView();",

		"final com.mvp4g.example.client.view.UserDisplayView " + "userDisplayView = new com.mvp4g.example.client.view.UserDisplayView();" };
	}

	private String[] getExpectedHistory() {
		return new String[] {
				"final PlaceService placeService = new PlaceService(eventBus);",
				"placeService.setInitEvent( \"init\");",
				"final com.mvp4g.example.client.history.UserHistoryConverter userConverter = new com.mvp4g.example.client.history.UserHistoryConverter();",
				"userConverter.setUserService(userService);",
				"final com.mvp4g.example.client.history.StringHistoryConverter stringConverter = new com.mvp4g.example.client.history.StringHistoryConverter();" };

	}

	private String[] getExpectedPresenters() {
		return new String[] {
				"final com.mvp4g.util.test_tools.RootPresenter " + "rootPresenter = new com.mvp4g.util.test_tools.RootPresenter();",

				"rootPresenter.setEventBus(eventBus);",
				"rootPresenter.setView(rootView);",

				"final com.mvp4g.example.client.presenter.UserCreatePresenter "
						+ "createUserPresenter = new com.mvp4g.example.client.presenter.UserCreatePresenter();",

				"createUserPresenter.setEventBus(eventBus);",
				"createUserPresenter.setView(userCreateView);",
				"createUserPresenter.setUserService(userService);",

				"final com.mvp4g.example.client.presenter.UserDisplayPresenter "
						+ "displayUserPresenter = new com.mvp4g.example.client.presenter.UserDisplayPresenter();",

				"displayUserPresenter.setEventBus(eventBus);", "displayUserPresenter.setView(userDisplayView);" };
	}

	private String[] getExpectedEvents() {
		return new String[] {
				"Command<com.mvp4g.example.client.bean.UserBean> cmduserCreated = new Command<com.mvp4g.example.client.bean.UserBean>(){",
				"public void execute(com.mvp4g.example.client.bean.UserBean form, boolean storeInHistory) {",
				"displayUserPresenter.onUserCreated(form);", "eventBus.addEvent(\"userCreated\", cmduserCreated);",

				"Command<com.mvp4g.example.client.bean.UserBean> cmduserDisplay = new Command<com.mvp4g.example.client.bean.UserBean>(){",
				"public void execute(com.mvp4g.example.client.bean.UserBean form, boolean storeInHistory) {",
				"displayUserPresenter.onUserDisplay(form);", "if(storeInHistory){", "placeService.place( \"userDisplay\", form )",
				"eventBus.addEvent(\"userDisplay\", cmduserDisplay);",

				"Command<com.mvp4g.example.client.view.widget.Page> cmdchangeBody = new Command<com.mvp4g.example.client.view.widget.Page>(){",
				"public void execute(com.mvp4g.example.client.view.widget.Page form, boolean storeInHistory) {", "rootPresenter.onChangeBody(form);",
				"eventBus.addEvent(\"changeBody\", cmdchangeBody);",

				"Command<java.lang.String> cmddisplayMessage = new Command<java.lang.String>(){",
				"public void execute(java.lang.String form, boolean storeInHistory) {", "rootPresenter.onDisplayMessage(form);",
				"if(storeInHistory){", "placeService.place( \"displayMessage\", form )", "eventBus.addEvent(\"displayMessage\", cmddisplayMessage);",

				"Command<java.lang.Object> cmdstart = new Command<java.lang.Object>(){",
				"public void execute(java.lang.Object form, boolean storeInHistory) {", "rootPresenter.onStart();",
				"eventBus.addEvent(\"start\", cmdstart);",

				"Command<java.lang.Object> cmdinit = new Command<java.lang.Object>(){",
				"public void execute(java.lang.Object form, boolean storeInHistory) {", "rootPresenter.onInit();",
				"eventBus.addEvent(\"init\", cmdinit);" };
	}

	private String[] getExpectedStartEvent() {
		return new String[] { "RootPanel.get().add(rootView);", "eventBus.dispatch(\"start\");", "History.fireCurrentHistoryState();" };
	}

	private String[] getExpectedServices() {
		return new String[] { "final com.mvp4g.example.client.rpc.UserServiceAsync "
				+ "userService = GWT.create(com.mvp4g.example.client.rpc.UserService.class);", };
	}
}
