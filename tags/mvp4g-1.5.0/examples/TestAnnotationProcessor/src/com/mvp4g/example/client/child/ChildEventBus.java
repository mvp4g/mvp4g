package com.mvp4g.example.client.child;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.module.ChildModules;
import com.mvp4g.client.event.EventBus;

@ChildModules( @com.mvp4g.client.annotation.module.ChildModule( moduleClass = ChildModule2.class ) )
@Events( startView = Object.class, module = ChildModule.class )
public interface ChildEventBus extends EventBus {

	@Event
	void childEventOk();

	@Event
	void childEventOk2( int i, String s );

	@Event
	void childEventWrongParam( String s );

	@Event( forwardToParent = true )
	void event1OK();

	@Event( forwardToParent = true )
	void event3OK( String attr1, int attr2 );

	@Event( forwardToParent = true )
	void eventNoParentEvent( String attr1, int attr2 );

}
