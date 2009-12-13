package com.mvp4g.client.annotation.module;

import com.mvp4g.client.Mvp4gModule;

public @interface ChildModule {
	
	Class<? extends Mvp4gModule> moduleClass();
	boolean async() default true;

}
