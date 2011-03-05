package com.mvp4g.util.config.loader.annotation;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.javac.typemodel.TypeOracleStub;
import com.mvp4g.client.DefaultMvp4gGinModule;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.Filters;
import com.mvp4g.client.annotation.module.ChildModules;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.BaseEventBusWithLookUp;
import com.mvp4g.client.event.DefaultMvp4gLogger;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.ChildModuleElement;
import com.mvp4g.util.config.element.ChildModulesElement;
import com.mvp4g.util.config.element.EventBusElement;
import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.EventFilterElement;
import com.mvp4g.util.config.element.EventFiltersElement;
import com.mvp4g.util.config.element.HistoryElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.util.test_tools.CustomPlaceService;
import com.mvp4g.util.test_tools.Modules;
import com.mvp4g.util.test_tools.annotation.EventFilters;
import com.mvp4g.util.test_tools.annotation.EventFilters.EventFilter1;
import com.mvp4g.util.test_tools.annotation.EventFilters.EventFilter2;
import com.mvp4g.util.test_tools.annotation.Events;
import com.mvp4g.util.test_tools.annotation.Events.TestGinModule;
import com.mvp4g.util.test_tools.annotation.Events.TestLogger;
import com.mvp4g.util.test_tools.annotation.Presenters;
import com.mvp4g.util.test_tools.annotation.events.EventBusOk;
import com.mvp4g.util.test_tools.annotation.history_converters.HistoryConverterForEvent;
import com.mvp4g.util.test_tools.annotation.presenters.PresenterWithName;
import com.mvp4g.util.test_tools.annotation.presenters.SimplePresenter;

public class EventsAnnotationsLoaderTest {

	protected Mvp4gConfiguration configuration = null;
	protected TypeOracleStub oracle = null;
	protected EventsAnnotationsLoader loader = null;

	@Before
	public void setUp() {
		oracle = new TypeOracleStub();
		configuration = new Mvp4gConfiguration( null, oracle );
		configuration.setModule( oracle.addClass( Mvp4gModule.class ) );
		loader = new EventsAnnotationsLoader();
	}

	@Test
	public void testOtherEventBus() {
		try {
			configuration.setEventBus( new EventBusElement( Object.class.getName(), BaseEventBus.class.getName(), false ) );
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			JClassType type = oracle.addClass( Events.SimpleEventBus.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
			fail();
		} catch ( Mvp4gAnnotationException e ) {
			assertEquals( e.getMessage(), Events.SimpleEventBus.class.getCanonicalName()
					+ ": You can define only one event bus by Mvp4g module. Do you already have another EventBus interface for the module "
					+ Mvp4gModule.class.getCanonicalName() + "?" );
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testNotInterface() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			JClassType type = oracle.addClass( Events.NotInterfaceEventBus.class );
			annotedClasses.add( type );
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
		annotedClasses.add( oracle.addClass( SimplePresenter.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		JClassType type = oracle.addClass( Events.SimpleEventBus.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		EventBusElement eventBus = configuration.getEventBus();
		assertEquals( BaseEventBus.class.getName(), eventBus.getAbstractClassName() );
		assertEquals( type.getQualifiedSourceName(), eventBus.getInterfaceClassName() );
		assertFalse( eventBus.isWithLookUp() );
	}

	@Test
	public void testEventBusWithLookup() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( SimplePresenter.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		JClassType type = oracle.addClass( Events.EventBusWithLookUp.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		EventBusElement eventBus = configuration.getEventBus();
		assertEquals( BaseEventBusWithLookUp.class.getName(), eventBus.getAbstractClassName() );
		assertEquals( type.getQualifiedSourceName(), eventBus.getInterfaceClassName() );
		assertTrue( eventBus.isWithLookUp() );
	}

	@Test
	public void testStart() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( SimplePresenter.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		JClassType type = oracle.addClass( Events.SimpleEventBus.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		StartElement start = configuration.getStart();
		assertEquals( configuration.getPresenters().iterator().next().getView(), start.getView() );
		assertFalse( start.hasHistory() );
		assertTrue( configuration.getStart().hasView() );
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
		assertTrue( configuration.getStart().hasView() );
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
			annotedClasses.add( oracle.addClass( SimplePresenter.class ) );
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
	public void testSameEvent() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
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
			annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
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
	public void testDoubleForward() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusDoubleForward.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "Duplicate value for Forward event. It is already defined by another method." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testDoubleInitHistory() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
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
			annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
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
			annotedClasses.add( oracle.addClass( SimplePresenter.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			annotedClasses = new ArrayList<JClassType>();
			JClassType type = oracle.addClass( EventBusOk.class );
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
			annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			annotedClasses = new ArrayList<JClassType>();
			JClassType type = oracle.addClass( EventBusOk.class );
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
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();

		annotedClasses.add( oracle.addClass( HistoryConverterForEvent.class ) );
		new HistoryAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();

		JClassType type = oracle.addClass( EventBusOk.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );

		Set<EventElement> events = configuration.getEvents();
		assertEquals( 2, events.size() );

		for ( EventElement e : events ) {
			if ( "event1".equals( e.getType() ) ) {
				assertEquals( String.class.getName(), e.getEventObjectClass()[0] );
				assertEquals( "treatEvent1", e.getCalledMethod() );
				assertFalse( e.isNavigationEvent() );
				assertFalse( e.isWithTokenGeneration() );
				assertFalse( e.isPassive() );
			} else if ( "event2".equals( e.getType() ) ) {
				assertNull( e.getEventObjectClass() );
				assertEquals( "onEvent2", e.getCalledMethod() );
				assertTrue( e.isNavigationEvent() );
				assertTrue( e.isWithTokenGeneration() );
				assertTrue( e.isPassive() );
			} else {
				fail( "Unknown event name" );
			}

			assertEquals( "name", e.getHandlers().get( 0 ) );
			assertEquals( "history", e.getHistory() );
		}

		assertEquals( "event2", configuration.getStart().getEventType() );
		assertEquals( "event2", configuration.getHistory().getInitEvent() );
		assertEquals( "event1", configuration.getHistory().getNotFoundEvent() );
		assertEquals( "event2", configuration.getStart().getForwardEventType() );

	}

	@Test
	public void testEventBusForOtherModule() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( Events.EventBusForOtherModule.class ) );
		loader.load( annotedClasses, configuration );

		Map<String, JClassType> othersEventBusClassMap = configuration.getOthersEventBusClassMap();
		Iterator<String> it = othersEventBusClassMap.keySet().iterator();
		String key = it.next();
		assertEquals( 1, othersEventBusClassMap.size() );
		assertEquals( Modules.Module1.class.getCanonicalName(), key );
		assertEquals( Events.EventBusForOtherModule.class.getCanonicalName(), othersEventBusClassMap.get( key ).getQualifiedSourceName() );
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testEventBusWithSameChild() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			JClassType type = oracle.addClass( Events.EventBusWithSameChild.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains(
					"You can't have two child modules describing the same module: " + Modules.Module1.class.getCanonicalName() ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testUselessChildModulesBus() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Events.EventBusUselessChildModules.class ) );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains(
					"Useless " + ChildModules.class.getSimpleName()
							+ " annotation. Don't use this annotation if your module doesn't have any child module." ) );
			throw e;
		}
	}

	@Test
	public void testEventBusWithChildrenOk() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();

		JClassType type = oracle.addClass( Events.EventBusWithChildren.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );

		Set<ChildModuleElement> childModules = configuration.getChildModules();
		assertEquals( 2, childModules.size() );

		for ( ChildModuleElement child : childModules ) {
			if ( Modules.Module1.class.getCanonicalName().equals( child.getClassName() ) ) {
				assertTrue( child.isAsync() );
				assertTrue( child.isAutoDisplay() );
				assertEquals( "event1", child.getEventToDisplayView() );
			} else if ( Modules.ModuleWithParent.class.getCanonicalName().equals( child.getClassName() ) ) {
				assertFalse( child.isAsync() );
				assertFalse( child.isAutoDisplay() );
			} else {
				fail( "Unknown child module" );
			}
		}

		Set<EventElement> events = configuration.getEvents();
		assertEquals( 4, events.size() );

		List<String> modules;
		for ( EventElement e : events ) {
			if ( "event1".equals( e.getType() ) ) {

			} else if ( "event2".equals( e.getType() ) ) {
				modules = e.getModulesToLoad();
				assertEquals( 1, modules.size() );
				assertEquals( Modules.Module1.class.getCanonicalName().replace( ".", "_" ), modules.get( 0 ) );
				assertFalse( e.hasForwardToParent() );
			} else if ( "event3".equals( e.getType() ) ) {
				modules = e.getModulesToLoad();
				assertEquals( 2, modules.size() );
				assertEquals( Modules.ModuleWithParent.class.getCanonicalName().replace( ".", "_" ), modules.get( 0 ) );
				assertEquals( Modules.Module1.class.getCanonicalName().replace( ".", "_" ), modules.get( 1 ) );
				assertFalse( e.hasForwardToParent() );
			} else if ( "event4".equals( e.getType() ) ) {
				assertTrue( e.hasForwardToParent() );
			} else {
				fail( "Unknown event name" );
			}
		}

	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testDoubleBeforeLoadChild() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusDoubleBefore.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains(
					"Duplicate value for Before Load Child event. It is already defined by another method or in your configuration file." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testDoubleAfterLoadChild() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusDoubleAfter.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains(
					"Duplicate value for After Load Child event. It is already defined by another method or in your configuration file." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testDoubleErrorLoadChild() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusDoubleError.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains(
					"Duplicate value for Error Load Child event. It is already defined by another method or in your configuration file." ) );
			throw e;
		}
	}

	@Test
	public void testLoadChildConfigOk() throws Mvp4gAnnotationException {

		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();

		JClassType type = oracle.addClass( Events.EventBusLoadChildConfig.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		ChildModulesElement childModules = configuration.getLoadChildConfig();
		assertEquals( "event1", childModules.getBeforeEvent() );
		assertEquals( "event2", childModules.getAfterEvent() );
		assertEquals( "event3", childModules.getErrorEvent() );

	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testUnknownModuleForEvent() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusUnknownModuleForEvent.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "No instance of " + Modules.ModuleWithParent.class.getCanonicalName() + " is defined." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testUnknownModuleForLoadModuleViewEvent() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusUnknownModuleForLoadModuleViewEvent.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "No instance of " + Modules.ModuleWithParent.class.getCanonicalName() + " is defined." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testSameModuleForLoadModuleViewEvent() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
			new PresenterAnnotationsLoader().load( annotedClasses, configuration );

			annotedClasses.clear();

			JClassType type = oracle.addClass( Events.EventBusSameModuleForLoadModuleViewEvent.class );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains(
					"Module " + Modules.Module1.class.getCanonicalName() + ": you can't have two events to load this module view." ) );
			throw e;
		}
	}

	@Test
	public void testParentEventBusOtherModule() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();

		JClassType otherEventBus = oracle.addClass( Events.EventBusWithChildren.class );
		annotedClasses.add( oracle.addClass( Events.EventBusForOtherModule.class ) );
		annotedClasses.add( otherEventBus );

		configuration.setModule( oracle.addClass( Modules.Module1.class ) );
		loader.load( annotedClasses, configuration );

		Map<String, ChildModuleElement> othersParentEventBusClassMap = configuration.getModuleParentEventBusClassMap();
		assertEquals( 2, othersParentEventBusClassMap.size() );
		assertEquals( otherEventBus, othersParentEventBusClassMap.get( Modules.Module1.class.getCanonicalName() ).getParentEventBus() );
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testUselessEventFilterBus() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			annotedClasses.add( oracle.addClass( Events.EventBusUselessFilter.class ) );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e
					.getMessage()
					.contains(
							"Useless "
									+ Filters.class.getSimpleName()
									+ " annotation. Don't use this annotation if your module doesn't have any event filters. If you plan on adding filters when the application runs, set the forceFilters option to true." ) );
			throw e;
		}
	}

	@Test
	public void testEventNoFilterWithForceBus() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( Events.EventBusNoFilterWithForce.class ) );
		loader.load( annotedClasses, configuration );
	}

	@Test
	public void testEventBusFilters() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		annotedClasses.add( oracle.addClass( Events.EventBusWithFilters.class ) );
		loader.load( annotedClasses, configuration );

		Set<EventFilterElement> filters = configuration.getEventFilters();
		assertEquals( 2, filters.size() );
		Iterator<EventFilterElement> it = filters.iterator();
		String filterClassName;
		while ( it.hasNext() ) {
			filterClassName = it.next().getClassName();
			if ( !filterClassName.equals( EventFilter1.class.getCanonicalName() ) && !filterClassName.equals( EventFilter2.class.getCanonicalName() ) ) {
				fail();
			}
		}

		EventFiltersElement filterConf = configuration.getEventFilterConfiguration();
		assertTrue( filterConf.isFilterForward() );
		assertTrue( filterConf.isFilterStart() );
		assertFalse( filterConf.isAfterHistory() );
		assertFalse( filterConf.isForceFilters() );

	}

	@Test
	public void testEventBusFiltersWithParam() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		annotedClasses.add( oracle.addClass( Events.EventBusWithFiltersWithParam.class ) );
		loader.load( annotedClasses, configuration );

		Set<EventFilterElement> filters = configuration.getEventFilters();
		assertEquals( 2, filters.size() );
		Iterator<EventFilterElement> it = filters.iterator();
		String filterClassName;
		while ( it.hasNext() ) {
			filterClassName = it.next().getClassName();
			if ( !filterClassName.equals( EventFilter1.class.getCanonicalName() ) && !filterClassName.equals( EventFilter2.class.getCanonicalName() ) ) {
				fail();
			}
		}

		EventFiltersElement filterConf = configuration.getEventFilterConfiguration();
		assertFalse( filterConf.isFilterForward() );
		assertFalse( filterConf.isFilterStart() );
		assertTrue( filterConf.isAfterHistory() );
		assertTrue( filterConf.isForceFilters() );

	}

	@Test
	public void testEventBusSameFilter() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		annotedClasses.add( oracle.addClass( Events.EventBusWithSameFilter.class ) );
		try {
			loader.load( annotedClasses, configuration );
			fail();
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "Multiple definitions for event filter " + EventFilters.EventFilter1.class.getCanonicalName() + "." ) );
		}
	}

	@Test
	public void testDefaultGin() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		annotedClasses.add( oracle.addClass( Events.SimpleEventBus.class ) );
		loader.load( annotedClasses, configuration );
		assertEquals( DefaultMvp4gGinModule.class.getCanonicalName(), configuration.getGinModule().getModules()[0] );

	}

	@Test
	public void testGin() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		annotedClasses.add( oracle.addClass( Events.EventBusWithGin.class ) );
		loader.load( annotedClasses, configuration );
		assertEquals( TestGinModule.class.getCanonicalName(), configuration.getGinModule().getModules()[0] );

	}

	@Test
	public void testGins() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		annotedClasses.add( oracle.addClass( Events.EventBusWithGins.class ) );
		loader.load( annotedClasses, configuration );
		assertEquals( TestGinModule.class.getCanonicalName(), configuration.getGinModule().getModules()[0] );
		assertEquals( DefaultMvp4gGinModule.class.getCanonicalName(), configuration.getGinModule().getModules()[1] );

	}

	@Test
	public void testNoLogger() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		annotedClasses.add( oracle.addClass( Events.SimpleEventBus.class ) );
		loader.load( annotedClasses, configuration );
		assertNull( configuration.getDebug() );

	}

	@Test
	public void testDefaultLogger() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		annotedClasses.add( oracle.addClass( Events.EventBusWithDefaultLogger.class ) );
		loader.load( annotedClasses, configuration );
		assertEquals( configuration.getDebug().getLogger(), DefaultMvp4gLogger.class.getCanonicalName() );
		assertEquals( configuration.getDebug().getLogLevel(), LogLevel.SIMPLE.toString() );

	}

	@Test
	public void testCustomLogger() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		annotedClasses.add( oracle.addClass( Events.EventBusWithCustomLogger.class ) );
		loader.load( annotedClasses, configuration );
		assertEquals( configuration.getDebug().getLogger(), TestLogger.class.getCanonicalName() );
		assertEquals( configuration.getDebug().getLogLevel(), LogLevel.DETAILED.toString() );

	}

	@Test
	public void testHistoryName() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( PresenterWithName.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		annotedClasses.add( oracle.addClass( Events.EventBusWithHistoryName.class ) );
		loader.load( annotedClasses, configuration );

		Iterator<EventElement> it = configuration.getEvents().iterator();
		int found = 0;
		EventElement event;
		while ( it.hasNext() ) {
			event = it.next();
			if ( "event1".equals( event.getType() ) ) {
				assertEquals( "event1", event.getName() );
				found++;
			} else if ( "event2".equals( event.getType() ) ) {
				assertEquals( "historyName", event.getName() );
				found++;
			}
		}
		if ( found < 2 ) {
			fail();
		}
	}

	@Test
	public void testNoHistoryConfiguration() throws Mvp4gAnnotationException {

		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( SimplePresenter.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( Events.SimpleEventBus.class ) );
		loader.load( annotedClasses, configuration );

		HistoryElement historyConfig = configuration.getHistory();
		assertNull( historyConfig );

	}

	@Test
	public void testHistoryConfiguration() throws Mvp4gAnnotationException {

		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( SimplePresenter.class ) );
		new PresenterAnnotationsLoader().load( annotedClasses, configuration );

		annotedClasses.clear();
		annotedClasses.add( oracle.addClass( Events.EventBusWithHistoryConfig.class ) );
		loader.load( annotedClasses, configuration );

		HistoryElement historyConfig = configuration.getHistory();
		assertEquals( CustomPlaceService.class.getCanonicalName(), historyConfig.getPlaceServiceClass() );

	}
	
	@Test
	public void testNoStartView() throws Mvp4gAnnotationException {

		List<JClassType> annotedClasses = new ArrayList<JClassType>();		
		annotedClasses.add( oracle.addClass( Events.EventBusWithNoStartView.class ) );
		loader.load( annotedClasses, configuration );

		assertNull( configuration.getStart().getView() );
		assertFalse( configuration.getStart().hasView() );

	}
}
