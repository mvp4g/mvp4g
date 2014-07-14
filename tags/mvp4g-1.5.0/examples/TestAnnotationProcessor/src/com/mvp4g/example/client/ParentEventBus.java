package com.mvp4g.example.client;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.example.client.presenters.Handler1;
import com.mvp4g.example.client.presenters.Handler4;

public interface ParentEventBus extends EventBus {
	
	@Event( handlers = Handler1.class )
	void eventParentOK();
	
	@Event( handlers = Handler4.class )
	void eventParentWrongEventBus();

}
