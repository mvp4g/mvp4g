package com.mvp4g.example.client.child;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.module.ChildModules;

@ChildModules( @com.mvp4g.client.annotation.module.ChildModule( moduleClass = ChildEventBus3.class ) )
@Events( startView = Object.class, module = ChildModule2.class )
public interface ChildEventBus2 extends Mvp4gModule {

}
