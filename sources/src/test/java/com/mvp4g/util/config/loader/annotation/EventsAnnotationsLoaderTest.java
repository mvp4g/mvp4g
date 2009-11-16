package com.mvp4g.util.config.loader.annotation;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.BaseEventBusWithLookUp;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.EventBusElement;
import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.util.test_tools.TypeOracleStub;
import com.mvp4g.util.test_tools.annotation.Events;
import com.mvp4g.util.test_tools.annotation.HistoryConverters;
import com.mvp4g.util.test_tools.annotation.Presenters;

public class EventsAnnotationsLoaderTest {

	protected Mvp4gConfiguration configuration = null;
	protected TypeOracleStub oracle = null;
	protected EventsAnnotationsLoader loader = null;

	@Before
	public void setUp() {
		oracle = new TypeOracleStub();
		configuration = new Mvp4gConfiguration( null, oracle );
		loader = new EventsAnnotationsLoader();
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testTwoEventBus() throws Mvp4gAnnotationException {
		try {
			configuration.setEventBus( new EventBusElement( Object.class.getName(), BaseEventBus.class.getName(), false ) );
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Object.class ) );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "You can only have one Event Bus interface." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testWithXmlEventBus() throws Mvp4gAnnotationException {
		try {
			configuration.getEvents().add( new EventElement() );
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Object.class ) );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains(
					"You can either define your events thanks to the configuration file or an EventBus interface but not both." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testWithStart() throws Mvp4gAnnotationException {
		try {
			configuration.setStart( new StartElement() );
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Object.class ) );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains(
					"You can't use start tag in your configuration file when you define your events in an EventBus interface." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testNotInterface() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Object.class ) );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "annotation can only be used on interfaces." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testNotEventBus() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Events.NotEventBus.class ) );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "this class must implement" ) );
			throw e;
		}
	}

	@Test
	public void testEventBus() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( Presenters.SimplePresenter.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		JClassType type = oracle.addClass( Events.SimpleEventBus.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		EventBusElement eventBus = configuration.getEventBus();
		assertEquals( BaseEventBus.class.getName(), eventBus.getAbstractClassName() );
		assertEquals( type.getQualifiedSourceName(), eventBus.getInterfaceClassName() );
		assertFalse( eventBus.isWithLookUp() );
		assertFalse( eventBus.isXml() );
	}

	@Test
	public void testEventBusWithLookup() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( Presenters.SimplePresenter.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		JClassType type = oracle.addClass( Events.EventBusWithLookUp.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		EventBusElement eventBus = configuration.getEventBus();
		assertEquals( BaseEventBusWithLookUp.class.getName(), eventBus.getAbstractClassName() );
		assertEquals( type.getQualifiedSourceName(), eventBus.getInterfaceClassName() );
		assertTrue( eventBus.isWithLookUp() );
		assertFalse( eventBus.isXml() );
	}

	@Test
	public void testStart() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( Presenters.SimplePresenter.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		JClassType type = oracle.addClass( Events.SimpleEventBus.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		StartElement start = configuration.getStart();
		assertEquals( configuration.getPresenters().iterator().next().getView(), start.getView() );
		assertFalse( start.hasHistory() );
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testStartNoView() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			JClassType type = oracle.addClass( Events.SimpleEventBus.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "There is no instance of" ) );
			throw e;
		}
	}

	@Test
	public void testStartWithName() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( Presenters.PresenterWithViewName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		JClassType type = oracle.addClass( Events.EventBusWithStartName.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		StartElement start = configuration.getStart();
		assertEquals( configuration.getPresenters().iterator().next().getView(), start.getView() );
		assertFalse( start.hasHistory() );
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testStartNoViewName() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			JClassType type = oracle.addClass( Events.EventBusWithStartName.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "There is no view named" ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testStartWrongViewClass() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Presenters.PresenterWithViewName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusWithStartNameAndWrongClass.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "There is no instance of" ) );
			assertTrue( e.getMessage().contains( "with name" ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testEventNoAnnotation() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Presenters.SimplePresenter.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusWithMethodAndNoAnnotation.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "annotation missing." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testEventWithMoreThanOneParameter() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Presenters.SimplePresenter.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusWithMethodWithMoreThanOneParameter.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "Event method must not have more than 1 argument." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testSameEvent() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Presenters.PresenterWithName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusWithSameMethod.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "Duplicate" ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testDoubleStart() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Presenters.PresenterWithName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusDoubleStart.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "Duplicate value for Start event. It is already defined by another method." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testDoubleInitHistory() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Presenters.PresenterWithName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusDoubleInitHistory.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains(
					"Duplicate value for Init History event. It is already defined by another method or in your configuration file." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testDoubleNotFoundHistory() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Presenters.PresenterWithName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusDoubleNotFoundHistory.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains(
					"Duplicate value for Not Found History event. It is already defined by another method or in your configuration file." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testNoInstanceOfHander() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Presenters.SimplePresenter.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			annotedClasses = new ArrayList<JClassType>();
			JClassType type = oracle.addClass( Events.EventBusOk.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "No instance of" ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testNoInstanceOfHistory() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Presenters.PresenterWithName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			annotedClasses = new ArrayList<JClassType>();
			JClassType type = oracle.addClass( Events.EventBusOk.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "No instance of" ) );
			throw e;
		}
	}

	@Test
	public void testEventOk() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( Presenters.PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();

		annotedClasses.add( oracle.addClass( HistoryConverters.HistoryConverterForEvent.class ) );
		new HistoryAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();

		JClassType type = oracle.addClass( Events.EventBusOk.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );

		Set<EventElement> events = configuration.getEvents();
		assertEquals( 2, events.size() );

		for ( EventElement e : events ) {
			if ( "event1".equals( e.getType() ) ) {
				assertEquals( String.class.getName(), e.getEventObjectClass() );
				assertEquals( "treatEvent1", e.getCalledMethod() );
			} else if ( "event2".equals( e.getType() ) ) {
				assertNull( e.getEventObjectClass() );
				assertEquals( "onEvent2", e.getCalledMethod() );
			} else {
				fail( "Unknown event name" );
			}

			assertEquals( "name", e.getHandlers()[0] );
			assertEquals( "history", e.getHistory() );
		}

		assertEquals( "event2", configuration.getStart().getEventType() );
		assertEquals( "event2", configuration.getHistory().getInitEvent() );
		assertEquals( "event1", configuration.getHistory().getNotFoundEvent() );

	}
}
