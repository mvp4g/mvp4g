package com.mvp4g.util.test_tools.annotation;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.util.test_tools.annotation.history_converters.BaseHistoryConverter;
import com.mvp4g.util.test_tools.annotation.services.SimpleService;
import com.mvp4g.util.test_tools.annotation.services.SimpleServiceAsync;

@SuppressWarnings( "deprecation" )
public class HistoryConverters {

	@History( name = "name" )
	public static class HistoryConverterWithName extends BaseHistoryConverter {

	}

	@History
	public static class HistoryConverterNotPublic extends BaseHistoryConverter {

		@InjectService
		void setSthg( SimpleServiceAsync service ) {
		}

	}

	@History
	public static class HistoryConverterWithNoParameter extends BaseHistoryConverter {

		@InjectService
		public void setSthg() {
		}

	}

	@History
	public static class HistoryConverterWithMoreThanOneParameter extends BaseHistoryConverter {

		@InjectService
		public void setSthg( SimpleServiceAsync service, Boolean test ) {
		}

	}

	@History
	public static class HistoryConverterNotAsync extends BaseHistoryConverter {

		@InjectService
		public void setSthg( SimpleService service ) {
		}

	}

	@History
	public static class HistoryConverterWithService extends BaseHistoryConverter {

		@InjectService
		public void setSthg( SimpleServiceAsync service ) {
		}

	}

	@History
	public static class HistoryConverterWithSameService extends BaseHistoryConverter {

		@InjectService
		public void setSthg( SimpleServiceAsync service ) {
		}

	}

	@History
	public static class HistoryConverterWithServiceAndName extends BaseHistoryConverter {

		@InjectService( serviceName = "name" )
		public void setSthg( SimpleServiceAsync service ) {
		}

	}

	@History
	public static class HistoryConverterWithLookup implements HistoryConverter<EventBusWithLookup> {

		public void convertFromToken( String eventType, String param, EventBusWithLookup eventBus ) {
		}

		public boolean isCrawlable() {
			return false;
		}

	}

}
