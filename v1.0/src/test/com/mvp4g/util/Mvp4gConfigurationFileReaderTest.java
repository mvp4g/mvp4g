package com.mvp4g.util;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.dev.util.UnitTestTreeLogger;
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

		assertOutputDoesNotContainViews();
		
		configReader.writeConf();
		
		assertOutputContainsViews();
	}
	
	
	@Test
	public void testWritePresenters() throws UnableToCompleteException {

		assertOutputDoesNotContainPresenters();
		
		configReader.writeConf();
		
		assertOutputContainsPresenters();
	}
	
	
	@Test
	public void testWriteEvents() throws UnableToCompleteException {

		assertOutputDoesNotContainEvents();
		
		configReader.writeConf();
		
		assertOutputContainsEvents();
	}

	
	@Test
	public void testWriteStartEvent() throws UnableToCompleteException {
		
		assertOutputDoesNotContainStart();
		
		configReader.writeConf();
		
		assertOutputContainsStart();
	}

	
	@Test
	public void testWriteServices() throws UnableToCompleteException {

		assertOutputDoesNotContainServices();
		
		configReader.writeConf();
		
		assertOutputContainsServices();
	}
	
	
	@Test
	public void testCapitalized() {
		String input = "donQuixote";
		String expected = "DonQuixote";
		
		assertEquals( expected, configReader.capitalized( input ) );
	}

	
	private void assertOutputDoesNotContainViews() {
		String error = "Unexpected view found in output data:\n" + sourceWriter.getData();
		for ( String viewStatement : getExpectedViews() ) {
			assertFalse( error, sourceWriter.dataContains( viewStatement ) );
		}
	}

	private void assertOutputContainsViews() {
		String error = "View not found in output data:\n" + sourceWriter.getData();
		for ( String viewStatement : getExpectedViews() ) {
			assertTrue( error, sourceWriter.dataContains( viewStatement ) );
		}
	}
	
	private void assertOutputDoesNotContainPresenters() {
		String error = "Unexpected presenter found in output data:\n" + sourceWriter.getData();
		for ( String presenterStatement : getExpectedPresenters() ) {
			assertFalse( error, sourceWriter.dataContains( presenterStatement ) );
		}
	}

	private void assertOutputContainsPresenters() {
		String error = "Presenter not found in output data:\n" + sourceWriter.getData();
		for ( String presenterStatement : getExpectedPresenters() ) {
			assertTrue( error, sourceWriter.dataContains( presenterStatement ) );
		}
	}

	private void assertOutputDoesNotContainEvents() {
		String error = "Unexpected event found in output data:\n" + sourceWriter.getData();
		for ( String eventStatement : getExpectedEvents() ) {
			assertFalse( error, sourceWriter.dataContains( eventStatement ) );
		}
	}

	private void assertOutputContainsEvents() {
		String error = "Event not found in output data:\n" + sourceWriter.getData();
		for ( String eventStatement : getExpectedEvents() ) {
			assertTrue( error, sourceWriter.dataContains( eventStatement ) );
		}
	}
	
	
	private void assertOutputDoesNotContainStart() {
		String error = "Unexpected start event found in output data:\n" + sourceWriter.getData();
		for ( String startEventStatement : getExpectedStartEvent() ) {
			assertFalse( error, sourceWriter.dataContains( startEventStatement ) );
		}
	}

	
	private void assertOutputContainsStart() {
		String error = "Start event dispatching not found in output data:\n" + sourceWriter.getData();
		for ( String startEventStatement : getExpectedStartEvent() ) {
			assertTrue( error, sourceWriter.dataContains( startEventStatement ) );
		}
	}
	
	
	private void assertOutputDoesNotContainServices() {
		String error = "Unexpected service found in output data:\n" + sourceWriter.getData();
		for ( String serviceStatement : getExpectedServices() ) {
			assertFalse( error, sourceWriter.dataContains( serviceStatement ) );
		}
	}

	
	private void assertOutputContainsServices() {
		String error = "Service not found in output data:\n" + sourceWriter.getData();
		for ( String serviceStatement : getExpectedServices() ) {
			assertTrue( error, sourceWriter.dataContains( serviceStatement ) );
		}
	}

	
	private String[] getExpectedViews() {
		return new String[]
		{
		  "final com.mvp4g.example.client.view.RootView " +
		  "rootView = new com.mvp4g.example.client.view.RootView();",
		  
		  "final com.mvp4g.example.client.view.UserCreateView " +
		  "userCreateView = new com.mvp4g.example.client.view.UserCreateView();",
		  
		  "final com.mvp4g.example.client.view.UserDisplayView " +
		  "userDisplayView = new com.mvp4g.example.client.view.UserDisplayView();"
		};
	}
	
	private String[] getExpectedPresenters() {
		return new String[]
		{ 	
			"final com.mvp4g.example.client.presenter.RootPresenter " +
			"rootPresenter = new com.mvp4g.example.client.presenter.RootPresenter();",
			
			"rootPresenter.setEventBus(eventBus);",
			"rootPresenter.setView(rootView);",
		
			"final com.mvp4g.example.client.presenter.UserCreatePresenter " + 
        	"createUserPresenter = new com.mvp4g.example.client.presenter.UserCreatePresenter();",
        
        	"createUserPresenter.setEventBus(eventBus);",
        	"createUserPresenter.setView(userCreateView);",
        	"createUserPresenter.setUserService(userService);",
        
        	"final com.mvp4g.example.client.presenter.UserDisplayPresenter " +
        	"displayUserPresenter = new com.mvp4g.example.client.presenter.UserDisplayPresenter();",
        	
        	"displayUserPresenter.setEventBus(eventBus);",
        	"displayUserPresenter.setView(userDisplayView);"
		};
	}
	
	private String[] getExpectedEvents() {
		return new String[]
		{
		   "Command<com.mvp4g.example.client.bean.UserBean> cmduserCreated = new Command<com.mvp4g.example.client.bean.UserBean>(){",
	          "public void execute(com.mvp4g.example.client.bean.UserBean form) {",
	            "displayUserPresenter.onUserCreated(form);",	       
	       "eventBus.addEvent(\"userCreated\", cmduserCreated);",
	       
	       "Command<com.mvp4g.example.client.view.widget.Page> cmdchangeBody = new Command<com.mvp4g.example.client.view.widget.Page>(){",
	          "public void execute(com.mvp4g.example.client.view.widget.Page form) {",
	            "rootPresenter.onChangeBody(form);",
	        "eventBus.addEvent(\"changeBody\", cmdchangeBody);",
	        
	        "Command<java.lang.String> cmddisplayMessage = new Command<java.lang.String>(){",
	          "public void execute(java.lang.String form) {",
	            "rootPresenter.onDisplayMessage(form);",
	        "eventBus.addEvent(\"displayMessage\", cmddisplayMessage);",

	        "Command<java.lang.Object> cmdstart = new Command<java.lang.Object>(){",
	          "public void execute(java.lang.Object form) {",
	            "rootPresenter.onStart();",
	            "createUserPresenter.onStart();",
	        "eventBus.addEvent(\"start\", cmdstart);"	
		};
	}
	

	private String[] getExpectedStartEvent() {
		return new String[]
		{
		  "RootPanel.get().add(rootView);",
		  "eventBus.dispatch(\"start\");"
		};
	}
	
	
	private String[] getExpectedServices() {
		return new String[]
		{ 	
			"final com.mvp4g.example.client.rpc.UserServiceAsync " +
			"userService = GWT.create(com.mvp4g.example.client.rpc.UserService.class);",
		};
	}
}
