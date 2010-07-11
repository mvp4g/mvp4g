package com.mvp4g.example.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.mvp4g.example.client.util.index.IndexGenerator;

@GinModules( Mvp4gGinModule.class )
public interface TestInjector extends Ginjector {

	IndexGenerator getIndexGenerator();

}
