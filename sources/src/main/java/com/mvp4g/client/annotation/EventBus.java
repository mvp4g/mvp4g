package com.mvp4g.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD,
		ElementType.TYPE })
public @interface EventBus {
	
	String name();
	Class<? extends com.mvp4g.client.presenter.Presenter<?>> view();

}
