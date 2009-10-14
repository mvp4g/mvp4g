package com.mvp4g.util.config;

import java.util.Set;

import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Before;
import org.junit.Test;

import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.exception.NonUniqueIdentifierException;
import com.mvp4g.util.exception.UnknownConfigurationElementException;

public class Mvp4gConfigurationTest {

	private Mvp4gConfiguration configuration;
	private Set<PresenterElement> presenters;
	private Set<ViewElement> views;
	private Set<EventElement> events;
	private Set<ServiceElement> services;
	private Set<HistoryConverterElement> historyConverters;

	@Before
	public void setUp() {
		configuration = new Mvp4gConfiguration();
		presenters = configuration.getPresenters();
		views = configuration.getViews();
		events = configuration.getEvents();
		services = configuration.getServices();
		historyConverters = configuration.getHistoryConverters();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnPresenterAndViewConflict() {
		presenters.add( newPresenter( "one" ) );
		views.add( newView( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnPresenterAndEventConflict() {
		presenters.add( newPresenter( "one" ) );
		events.add( newEvent( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnPresenterAndServiceConflict() {
		presenters.add( newPresenter( "one" ) );
		services.add( newService( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}
	
	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnPresenterAndHistoryConverterConflict() {
		presenters.add( newPresenter( "one" ) );
		historyConverters.add( newHistoryConverter( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnViewAndEventConflict() {
		views.add( newView( "two" ) );
		events.add( newEvent( "two" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnViewAndServiceConflict() {
		views.add( newView( "two" ) );
		services.add( newService( "two" ) );

		configuration.checkUniquenessOfAllElements();
	}
	
	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnViewAndHistoryConverterConflict() {
		views.add( newView( "one" ) );
		historyConverters.add( newHistoryConverter( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnEventAndServiceConflict() {
		services.add( newService( "three" ) );
		events.add( newEvent( "three" ) );

		configuration.checkUniquenessOfAllElements();
	}
	
	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnEventAndHistoryConverterConflict() {
		events.add( newEvent( "one" ) );
		historyConverters.add( newHistoryConverter( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}
	
	@Test( expected = NonUniqueIdentifierException.class )
	public void testUniquenessFailureOnServiceAndHistoryConverterConflict() {
		services.add( newService( "one" ) );
		historyConverters.add( newHistoryConverter( "one" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test
	public void testUniquenessCheckPasses() {
		presenters.add( newPresenter( "one" ) );
		views.add( newView( "two" ) );
		events.add( newEvent( "three" ) );
		services.add( newService( "four" ) );
		historyConverters.add( newHistoryConverter( "five" ) );

		configuration.checkUniquenessOfAllElements();
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testEventHandlerValidationFails() {
		views.add( newView( "badHandler" ) );
		services.add( newService( "badHandler" ) );
		events.add( newEvent( "badHanlder" ) );
		historyConverters.add( newHistoryConverter( "badHanlder" ) );

		EventElement event = newEvent( "testEvent" );
		event.setHandlers( new String[] { "badHandler" } );
		events.add( event );

		configuration.validateEventHandlers();
	}

	@Test
	public void testEventHandlerValidationSucceedsForPresenter() {
		presenters.add( newPresenter( "testHandler" ) );

		EventElement event = newEvent( "testEvent" );
		event.setHandlers( new String[] { "testHandler" } );
		events.add( event );

		configuration.validateEventHandlers();
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testPresenterViewValidationFails() {
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
	public void testStartViewValidationFails() {
		events.add( newEvent( "badView" ) );
		services.add( newService( "badView" ) );
		presenters.add( newPresenter( "badView" ) );
		historyConverters.add( newHistoryConverter( "badView" ) );
		
		configuration.getStart().setView( "badView" );

		configuration.validateViews();
	}

	@Test
	public void testViewValidationSucceeds() {
		views.add( newView( "testView" ) );

		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.setView( "testView" );
		presenters.add( presenter );

		configuration.getStart().setView( "testView" );

		configuration.validateViews();
	}

	@Test( expected = UnknownConfigurationElementException.class )
	public void testInjectedServiceValidationFailsForPresenter() {
		events.add( newEvent( "badService" ) );
		views.add( newView( "badService" ) );
		presenters.add( newPresenter( "badService" ) );
		historyConverters.add( newHistoryConverter( "badService" ) );
		
		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.setServices( new String[] { "badService" } );
		presenters.add( presenter );

		configuration.validateServices();
	}
	
	@Test( expected = UnknownConfigurationElementException.class )
	public void testInjectedServiceValidationFailsForHistoryConverter() {
		events.add( newEvent( "badService" ) );
		views.add( newView( "badService" ) );
		presenters.add( newPresenter( "badService" ) );
		historyConverters.add( newHistoryConverter( "badService" ) );
		
		HistoryConverterElement historyConverter = newHistoryConverter( "testHistoryConverter" );
		historyConverter.setServices( new String[] { "badService" } );
		historyConverters.add( historyConverter );

		configuration.validateServices();
	}	
	

	@Test
	public void testInjectedServiceValidationSucceeds() {
		services.add( newService( "testService" ) );

		PresenterElement presenter = newPresenter( "testPresenter" );
		presenter.setServices( new String[] { "testService" } );
		presenters.add( presenter );
		
		HistoryConverterElement historyConverter = newHistoryConverter( "testHistoryConverter" );
		historyConverter.setServices( new String[] { "testService" } );
		historyConverters.add( historyConverter );

		configuration.validateServices();
	}
	
	@Test( expected = UnknownConfigurationElementException.class )
	public void testEventHistoryConverterFails(){
			events.add( newEvent( "badHistoryConverter" ) );
			services.add( newService( "badHistoryConverter" ) );
			presenters.add( newPresenter( "badHistoryConverter" ) );
			views.add( newView( "badHistoryConverter" ) );
			
			EventElement event = newEvent( "testEvent" );
			event.setHistory( "badView" );
			events.add( event );

			configuration.validateHistoryConverters();
	}
	
	@Test
	public void testEventHistoryConverterSucceeds(){
			historyConverters.add( newHistoryConverter( "testHistoryConverter" ));
			
			EventElement event = newEvent( "testEvent" );
			event.setHistory( "testHistoryConverter" );
			events.add( event );

			configuration.validateHistoryConverters();
	}	
	
	@Test( expected = UnknownConfigurationElementException.class )
	public void testStartEventFails(){
		services.add( newService( "badEvent" ) );
		presenters.add( newPresenter( "badEvent" ) );
		views.add( newView( "badEvent" ) );
		historyConverters.add( newHistoryConverter( "badEvent" ));
		
		configuration.getStart().setEventType( "badEvent" );
		
		configuration.validateEvents();
		
	}
	
	@Test( expected = UnknownConfigurationElementException.class )
	public void testHistoryInitEventFails(){
		services.add( newService( "badEvent" ) );
		presenters.add( newPresenter( "badEvent" ) );
		views.add( newView( "badEvent" ) );
		historyConverters.add( newHistoryConverter( "badEvent" ));
		
		configuration.getHistory().setInitEvent( "badEvent" );
		
		configuration.validateEvents();
		
	}
	
	@Test
	public void testStartSucceeds(){
		events.add( newEvent( "testEvent" ) );				
		configuration.getStart().setEventType( "testEvent" );
		configuration.getHistory().setInitEvent( "testEvent" );		
		configuration.validateEvents();		
	}
	
	@Test
	public void testLoad() throws Exception {
		configuration.load( new XMLConfiguration( "mvp4g-conf.xml" ) );		
	}

	private PresenterElement newPresenter( String name ) {
		PresenterElement presenter = new PresenterElement();
		presenter.setName( name );
		return presenter;
	}

	private ViewElement newView( String name ) {
		ViewElement view = new ViewElement();
		view.setName( name );
		return view;
	}

	private EventElement newEvent( String type ) {
		EventElement event = new EventElement();
		event.setType( type );
		return event;
	}

	private ServiceElement newService( String name ) {
		ServiceElement service = new ServiceElement();
		service.setName( name );
		return service;
	}
	
	private HistoryConverterElement newHistoryConverter( String name){
		HistoryConverterElement historyConverter = new HistoryConverterElement();
		historyConverter.setName( name );
		return historyConverter;
	}

}
