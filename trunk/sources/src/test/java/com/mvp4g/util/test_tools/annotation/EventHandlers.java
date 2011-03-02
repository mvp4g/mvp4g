package com.mvp4g.util.test_tools.annotation;

import com.mvp4g.client.annotation.EventHandler;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.event.BaseEventHandler;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.util.test_tools.annotation.services.SimpleService;
import com.mvp4g.util.test_tools.annotation.services.SimpleServiceAsync;

@SuppressWarnings( "deprecation" )
public class EventHandlers {

	@EventHandler( multiple = true )
	public static class MultipleEventHandler extends BaseEventHandler<EventBus> {
	}

	@EventHandler( name = "name" )
	public static class EventHandlerWithName extends BaseEventHandler<EventBus> {
	}

	@EventHandler
	public static class EventHandlerNotPublic extends BaseEventHandler<EventBus> {

		@InjectService
		void setSthg( SimpleServiceAsync service ) {
		}

	}

	@EventHandler
	public static class EventHandlerNoParameter extends BaseEventHandler<EventBus> {

		@InjectService
		public void setSthg() {
		}

	}

	@EventHandler
	public static class EventHandlerWithMoreThanOneParameter extends BaseEventHandler<EventBus> {

		@InjectService
		public void setSthg( SimpleServiceAsync service, Boolean test ) {
		}

	}

	@EventHandler
	public static class EventHandlerNotAsync extends BaseEventHandler<EventBus> {

		@InjectService
		public void setSthg( SimpleService service ) {
		}

	}

	@EventHandler
	public static class EventHandlerWithService extends BaseEventHandler<EventBus> {

		@InjectService
		public void setSthg( SimpleServiceAsync service ) {
		}

	}

	@EventHandler
	public static class EventHandlerWithSameService extends BaseEventHandler<EventBus> {

		@InjectService
		public void setSthg( SimpleServiceAsync service ) {
		}

	}

	@EventHandler
	public static class EventHandlerWithServiceAndName extends BaseEventHandler<EventBus> {

		@InjectService( serviceName = "name" )
		public void setSthg( SimpleServiceAsync service ) {
		}

	}

}
