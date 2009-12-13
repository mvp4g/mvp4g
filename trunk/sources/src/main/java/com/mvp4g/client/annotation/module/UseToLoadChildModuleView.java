package com.mvp4g.client.annotation.module;

import com.mvp4g.client.Mvp4gModule;

public @interface UseToLoadChildModuleView {
	
	public Class<? extends Mvp4gModule>[] value();

}
