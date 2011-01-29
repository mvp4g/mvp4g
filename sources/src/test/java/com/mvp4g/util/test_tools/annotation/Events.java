package com.mvp4g.util.test_tools.annotation;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.mvp4g.client.DefaultMvp4gGinModule;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Filters;
import com.mvp4g.client.annotation.Forward;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.NotFoundHistory;
import com.mvp4g.client.annotation.PlaceService;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.module.AfterLoadChildModule;
import com.mvp4g.client.annotation.module.BeforeLoadChildModule;
import com.mvp4g.client.annotation.module.ChildModule;
import com.mvp4g.client.annotation.module.ChildModules;
import com.mvp4g.client.annotation.module.DisplayChildModuleView;
import com.mvp4g.client.annotation.module.LoadChildModuleError;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.event.Mvp4gLogger;
import com.mvp4g.client.view.NoStartView;
import com.mvp4g.util.test_tools.CustomPlaceService;
import com.mvp4g.util.test_tools.Modules;

public class Events {

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static class NotInterfaceEventBus {
	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface NotEventBus {
	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface SimpleEventBus extends EventBus {
	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusWithLookUp extends EventBusWithLookup {
	}

	@com.mvp4g.client.annotation.Events( startView = Object.class, startViewName = "name" )
	public static interface EventBusWithStartName extends EventBus {
	}

	@com.mvp4g.client.annotation.Events( startView = String.class, startViewName = "name" )
	public static interface EventBusWithStartNameAndWrongClass extends EventBus {
	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusWithMethodAndNoAnnotation extends EventBus {

		public void event( String obj );
	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusWithSameMethod extends EventBus {

		@Event
		public void event( String obj );

		@Event
		public void event();
	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusOk extends EventBus {

		@NotFoundHistory
		@Event( handlerNames = "name", calledMethod = "treatEvent1", historyConverterName = "history" )
		public void event1( String obj );

		@Start
		@InitHistory
		@Forward
		@Event( handlers = Presenters.PresenterWithName.class, historyConverter = HistoryConverters.HistoryConverterForEvent.class, navigationEvent = true, passive = true )
		public String event2();
	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusDoubleStart extends EventBus {

		@Start
		@Event( handlerNames = "name", calledMethod = "treatEvent1" )
		public void event1( String obj );

		@Start
		@Event( handlers = Presenters.PresenterWithName.class )
		public void event2();
	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusDoubleForward extends EventBus {

		@Forward
		@Event( handlerNames = "name", calledMethod = "treatEvent1" )
		public void event1( String obj );

		@Forward
		@Event( handlers = Presenters.PresenterWithName.class )
		public void event2();
	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusDoubleInitHistory extends EventBus {

		@InitHistory
		@Event( handlerNames = "name", calledMethod = "treatEvent1" )
		public void event1( String obj );

		@InitHistory
		@Event( handlers = Presenters.PresenterWithName.class )
		public void event2();
	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusDoubleNotFoundHistory extends EventBus {

		@NotFoundHistory
		@Event( handlerNames = "name", calledMethod = "treatEvent1" )
		public void event1( String obj );

		@NotFoundHistory
		@Event( handlers = Presenters.PresenterWithName.class )
		public void event2();
	}

	@ChildModules( {} )
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusUselessChildModules extends EventBus {

	}

	@com.mvp4g.client.annotation.Events( startView = Object.class, module = Modules.Module1.class )
	public static interface EventBusForOtherModule extends EventBus {

	}

	@ChildModules( { @ChildModule( moduleClass = Modules.Module1.class ), @ChildModule( moduleClass = Modules.Module1.class ) } )
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusWithSameChild extends EventBus {

	}

	@ChildModules( { @ChildModule( moduleClass = Modules.Module1.class ),
			@ChildModule( moduleClass = Modules.ModuleWithParent.class, async = false, autoDisplay = false ) } )
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusWithChildren extends EventBus {

		@DisplayChildModuleView( Modules.Module1.class )
		@Event( )
		public void event1( String obj );

		@Event( handlers = Presenters.PresenterWithName.class, modulesToLoad = Modules.Module1.class )
		public void event2();

		@Event( handlers = Presenters.PresenterWithName.class, modulesToLoad = { Modules.ModuleWithParent.class, Modules.Module1.class } )
		public void event3();

		@Event( handlers = Presenters.PresenterWithName.class, forwardToParent = true )
		public void event4();
	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusDoubleBefore extends EventBus {

		@BeforeLoadChildModule
		@Event
		public void event1();

		@BeforeLoadChildModule
		@Event
		public void event2();

	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusDoubleAfter extends EventBus {

		@AfterLoadChildModule
		@Event
		public void event1();

		@AfterLoadChildModule
		@Event
		public void event2();

	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusDoubleError extends EventBus {

		@LoadChildModuleError
		@Event
		public void event1();

		@LoadChildModuleError
		@Event
		public void event2();

	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusLoadChildConfig extends EventBus {

		@BeforeLoadChildModule
		@Event
		public void event1();

		@AfterLoadChildModule
		@Event
		public void event2();

		@LoadChildModuleError
		@Event
		public void event3();

	}

	@ChildModules( { @ChildModule( moduleClass = Modules.Module1.class ) } )
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusUnknownModuleForEvent extends EventBus {

		@Event( handlers = Presenters.PresenterWithName.class, modulesToLoad = Modules.ModuleWithParent.class )
		public void event2();

	}

	@ChildModules( { @ChildModule( moduleClass = Modules.Module1.class ) } )
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusUnknownModuleForLoadModuleViewEvent extends EventBus {

		@DisplayChildModuleView( Modules.ModuleWithParent.class )
		@Event( handlers = Presenters.PresenterWithName.class )
		public void event2();

	}

	@ChildModules( { @ChildModule( moduleClass = Modules.Module1.class ) } )
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusSameModuleForLoadModuleViewEvent extends EventBus {

		@DisplayChildModuleView( Modules.Module1.class )
		@Event( handlers = Presenters.PresenterWithName.class )
		public void event1();

		@DisplayChildModuleView( Modules.Module1.class )
		@Event( handlers = Presenters.PresenterWithName.class )
		public void event2();

	}

	@Filters( filterClasses = {} )
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusUselessFilter extends EventBus {

	}

	@Filters( filterClasses = {}, forceFilters = true )
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusNoFilterWithForce extends EventBus {

	}

	@Filters( filterClasses = { EventFilters.EventFilter1.class, EventFilters.EventFilter2.class } )
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusWithFilters extends EventBus {

	}

	@Filters( filterClasses = { EventFilters.EventFilter1.class, EventFilters.EventFilter2.class }, afterHistory = true, filterForward = false, filterStart = false, forceFilters = true )
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusWithFiltersWithParam extends EventBus {

	}

	@Filters( filterClasses = { EventFilters.EventFilter1.class, EventFilters.EventFilter1.class } )
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusWithSameFilter extends EventBus {

	}

	public class TestGinModule implements GinModule {

		public void configure( GinBinder binder ) {

		}

	}

	@com.mvp4g.client.annotation.Events( startView = Object.class, ginModules = TestGinModule.class )
	public static interface EventBusWithGin extends EventBus {

	}

	@com.mvp4g.client.annotation.Events( startView = Object.class, ginModules = { TestGinModule.class, DefaultMvp4gGinModule.class } )
	public static interface EventBusWithGins extends EventBus {

	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	@Debug
	public static interface EventBusWithDefaultLogger extends EventBus {

	}

	public class TestLogger implements Mvp4gLogger {

		public void log( String message, int depth ) {
			// TODO Auto-generated method stub			
		}

	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	@Debug( logger = TestLogger.class, logLevel = LogLevel.DETAILED )
	public static interface EventBusWithCustomLogger extends EventBus {

	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusWithHistoryName extends EventBus {

		@Event
		public void event1( String obj );

		@Event( name = "historyName" )
		public void event2();
	}

	@com.mvp4g.client.annotation.Events( startView = Object.class )
	@PlaceService( CustomPlaceService.class )
	public static interface EventBusWithHistoryConfig extends EventBus {

	}
	
	@com.mvp4g.client.annotation.Events( startView = NoStartView.class )
	public static interface EventBusWithNoStartView extends EventBus {
		
	}
}
