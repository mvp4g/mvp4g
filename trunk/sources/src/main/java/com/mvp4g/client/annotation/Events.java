package com.mvp4g.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( RetentionPolicy.RUNTIME )
public @interface Events {

	String startViewName() default "";
	
	Class<?> startView();

	boolean historyOnStart();

}
