package com.mvp4g.client.annotation.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mvp4g.client.Mvp4gModule;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface DisplayChildModuleView {

	public Class<? extends Mvp4gModule>[] value();

}
