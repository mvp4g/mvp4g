package com.mvp4g.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.presenter.PresenterInterface;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface Event {

	Class<? extends PresenterInterface<?, ? extends EventBus>>[] handlers() default {};

	String[] handlerNames() default {};
	
	String calledMethod() default "";
	
	String historyConverterName() default "";
	
	Class<? extends HistoryConverter<?,?>> historyConverter() default NoHistoryConverter.class;
	
	
	class NoHistoryConverter implements HistoryConverter<Object, EventBus>{
		
		private NoHistoryConverter(){
			//to prevent this class to be used
		}

		public void convertFromToken( String eventType, String param, EventBus eventBus ) {}

		public String convertToToken( String eventType, Object form ) {
			return null;
		}
		
	}

}
