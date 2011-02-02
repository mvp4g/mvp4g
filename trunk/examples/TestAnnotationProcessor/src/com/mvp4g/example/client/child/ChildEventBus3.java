package com.mvp4g.example.client.child;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.Events;

@Events( startView = Object.class, module = ChildModule.class )
public interface ChildEventBus3 extends Mvp4gModule {

}
