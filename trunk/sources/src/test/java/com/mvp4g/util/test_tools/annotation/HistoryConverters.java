package com.mvp4g.util.test_tools.annotation;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;

public class HistoryConverters {

	private static class BaseHistoryConverter implements HistoryConverter<Object, EventBus> {

		public void convertFromToken( String eventType, String param, EventBus eventBus ) {
		}

		public String convertToToken( String eventType, Object form ) {
			return null;
		}

	}

	@History
	public static class SimpleHistoryConverter extends BaseHistoryConverter {

	}

	@History( name = "name" )
	public static class HistoryConverterWithName extends BaseHistoryConverter {

	}

	@History
	public static class HistoryConverterNotPublic extends BaseHistoryConverter {

		@InjectService
		void setSthg( Services.SimpleServiceAsync service ) {
		}

	}
	
	@History( name = "history" )
	public static class HistoryConverterForEvent extends BaseHistoryConverter {

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
		public void setSthg( Services.SimpleServiceAsync service, Boolean test ) {
		}

	}

	@History
	public static class HistoryConverterNotAsync extends BaseHistoryConverter {

		@InjectService
		public void setSthg( Services.SimpleService service ) {
		}

	}

	@History
	public static class HistoryConverterWithService extends BaseHistoryConverter {

		@InjectService
		public void setSthg( Services.SimpleServiceAsync service ) {
		}

	}
	
	@History
	public static class HistoryConverterWithSameService extends BaseHistoryConverter {

		@InjectService
		public void setSthg( Services.SimpleServiceAsync service ) {
		}

	}

	@History
	public static class HistoryConverterWithServiceAndName extends BaseHistoryConverter {

		@InjectService( serviceName = "name" )
		public void setSthg( Services.SimpleServiceAsync service ) {
		}

	}

}
