package com.mvp4g.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.presenter.PresenterInterface;

/**
 * This annotation must be used on method of interfaces that extends <code>EventBus</code> in order
 * to define event.<br/>
 * It replaces event tag in Mvp4g XML configuration. <br/>
 * <br/> {@code @Event(handlers= RootTemplatePresenter.class}, calledMethod=onDisplayMessage,
 * historyConverter=MessageConverter.class) }<br/> {@code public void displayMessage(String m); }<br/>
 * <br/>
 * is similar to<br/>
 * <br/> {@code <event type="displayMessage" calledMethod="onDisplayMessage"
 * eventObjectClass="com.lang.String" handlers="rootTemplate" history="messageConverter" />}<br/>
 * <br/>
 * You don't have to give the name of the presenters/history converter. The framework will
 * automatically deduce the name of the instances to use.<br/>
 * In case no instance of the given class can be found, an error is thrown. <br/>
 * <br/>
 * You can also directly give names of presenters/history converters thanks to attributes
 * handlerNames and historyConverterName. However it is recommanded to use classes in order to
 * prevent typo mistakes.<br/>
 * <br/>
 * Attribute calledMethod is optional. If not precised, its value will be "on" + name of the event.<br/>
 * <br/>
 * Attributes historyConverter and historyConverterName are optional. If not precised, no history
 * converter will be associated to the event. If you precise a name and a class, only the name will
 * be used.<br/>
 * <br/>
 * Attributes handlers and handlerNames represent arrays to define zero to many presenters. You can
 * use none, one or both of these at the same time. If none of these attributes are used, then no
 * handler will be associated to the event.
 * 
 * @author plcoirier
 * 
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface Event {

	Class<? extends PresenterInterface<?, ? extends EventBus>>[] handlers() default {};

	String[] handlerNames() default {};

	Class<? extends Mvp4gModule>[] modulesToLoad() default {};

	boolean forwardToParent() default false;

	String calledMethod() default "";

	String historyConverterName() default "";

	Class<? extends HistoryConverter<?, ?>> historyConverter() default NoHistoryConverter.class;

	class NoHistoryConverter implements HistoryConverter<Object, EventBus> {

		private NoHistoryConverter() {
			//to prevent this class to be used
		}

		public void convertFromToken( String eventType, String param, EventBus eventBus ) {
		}

		public String convertToToken( String eventType, Object form ) {
			return null;
		}

	}

}
