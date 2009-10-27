package com.mvp4g.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Presenter {
	
	String name() default "";
	Class<?> view();
	String viewName() default "";
	

}
