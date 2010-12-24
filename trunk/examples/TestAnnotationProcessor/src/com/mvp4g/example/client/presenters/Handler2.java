package com.mvp4g.example.client.presenters;

import com.mvp4g.client.annotation.EventHandler;
import com.mvp4g.client.event.BaseEventHandler;
import com.mvp4g.example.client.TestEventBus;

@EventHandler
public class Handler2 extends BaseEventHandler<TestEventBus> {

	public void onEvent1OK() {

	}

	public void onEvent2OK() {

	}

	public void onEvent3OK( String attr1, int attr2 ) {

	}
	
	public void onEventWrongParameters2( String attr, String attr2, String attr3 ){
		
	}
}
