package com.mvp4g.util.test_tools.annotation;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.NotFoundHistory;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;

public class Events {

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
		
		public void event(String obj);
	}
	
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusWithMethodWithMoreThanOneParameter extends EventBus {
	
		@Event
		public void event(String obj, String obj2);
	}
	
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusWithSameMethod extends EventBus {
		
		@Event
		public void event(String obj);
		
		@Event
		public void event();
	}
	
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusOk extends EventBus {
		
		@NotFoundHistory
		@Event(handlerNames="name", calledMethod="treatEvent1", historyConverterName="history")
		public void event1(String obj);
		
		@Start
		@InitHistory
		@Event(handlers=Presenters.PresenterWithName.class, historyConverter=HistoryConverters.HistoryConverterForEvent.class)
		public void event2();
	}
	
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusDoubleStart extends EventBus {
		
		@Start
		@Event(handlerNames="name", calledMethod="treatEvent1")
		public void event1(String obj);
		 
		@Start
		@Event(handlers=Presenters.PresenterWithName.class)
		public void event2();
	}
	
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusDoubleInitHistory extends EventBus {
		
		@InitHistory
		@Event(handlerNames="name", calledMethod="treatEvent1")
		public void event1(String obj);
		 
		@InitHistory
		@Event(handlers=Presenters.PresenterWithName.class)
		public void event2();
	}
	
	@com.mvp4g.client.annotation.Events( startView = Object.class )
	public static interface EventBusDoubleNotFoundHistory extends EventBus {
		
		@NotFoundHistory
		@Event(handlerNames="name", calledMethod="treatEvent1")
		public void event1(String obj);
		 
		@NotFoundHistory
		@Event(handlers=Presenters.PresenterWithName.class)
		public void event2();
	}

}
