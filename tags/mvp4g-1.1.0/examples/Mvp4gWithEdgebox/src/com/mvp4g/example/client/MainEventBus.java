package com.mvp4g.example.client;

import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;

@Events( startView = MainView.class )
public interface MainEventBus extends EventBus {

}
