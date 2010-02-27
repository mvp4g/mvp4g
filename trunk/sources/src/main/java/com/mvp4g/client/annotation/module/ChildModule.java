package com.mvp4g.client.annotation.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.mvp4g.client.Mvp4gModule;

@Retention( RetentionPolicy.RUNTIME )
public @interface ChildModule {

	Class<? extends Mvp4gModule> moduleClass();

	boolean async() default true;

	boolean autoDisplay() default true;

}
