package com.mvp4g.example.client;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.module.ChildModules;
import com.mvp4g.example.client.child.ChildModule;
import com.mvp4g.example.client.child.ChildModule2;
import com.mvp4g.example.client.child.ChildModuleNoEventBus;
import com.mvp4g.example.client.history.HistoryConverter1;
import com.mvp4g.example.client.history.HistoryConverter2;
import com.mvp4g.example.client.history.HistoryConverter3;
import com.mvp4g.example.client.history.HistoryConverter4;
import com.mvp4g.example.client.history.HistoryConverter5;
import com.mvp4g.example.client.presenters.Handler1;
import com.mvp4g.example.client.presenters.Handler2;
import com.mvp4g.example.client.presenters.Handler3;
import com.mvp4g.example.client.presenters.Handler4;
import com.mvp4g.example.client.presenters.Handler5;
import com.mvp4g.example.client.presenters.Handler6;
import com.mvp4g.example.client.presenters.SubHandler;

@ChildModules( @com.mvp4g.client.annotation.module.ChildModule( moduleClass = ChildModule.class ) )
@Events( startView = Object.class )
public interface TestEventBus extends ParentEventBus {

	@Event( handlers = Handler1.class )
	void event1OK();

	@Event( handlers = { Handler1.class, Handler2.class, SubHandler.class } )
	void event2OK();

	@Event( handlers = { Handler1.class, Handler2.class } )
	void event3OK( String attr1, int attr2 );

	@Event( handlers = Handler1.class )
	void eventNoHandlingMethod();

	@Event( handlers = { Handler1.class, Handler2.class } )
	void eventNoHandlingMethod2();

	@Event( handlers = Handler1.class )
	void eventWrongParameters( String attr, int attr2 );

	@Event( handlers = { Handler1.class, Handler2.class } )
	void eventWrongParameters2( String attr, int attr2 );

	@Event( handlers = { Handler4.class, Handler5.class } )
	void eventWrongEventBus();

	@Event( handlers = Handler3.class )
	void eventNoAnnotation();

	@Event( handlers = Handler3.class )
	void eventNoAnnotationNotPublic();

	@Event( handlers = Handler6.class )
	void eventWrongView();

	@Event( historyConverter = HistoryConverter1.class )
	void eventHistoryOk();

	@Event( historyConverter = HistoryConverter1.class )
	void eventHistoryOkWithParams( String param );

	@Event( historyConverter = HistoryConverter2.class )
	void eventHistoryOk2();

	@Event( historyConverter = HistoryConverter5.class )
	void eventHistoryOk3();

	@Event( historyConverter = HistoryConverter5.class )
	void eventHistoryOk4();

	@Event( historyConverter = HistoryConverter5.class )
	void eventHistoryOk5();

	@Event( historyConverter = HistoryConverter5.class )
	void eventHistoryOk6( int i, String s );

	@Event( historyConverter = HistoryConverter1.class )
	void eventHistoryNoConvertMethod();

	@Event( historyConverter = HistoryConverter5.class )
	void eventHistoryNoConvertMethod1( String s );

	@Event( historyConverter = HistoryConverter5.class )
	void eventHistoryNoConvertMethod1( int i );

	@Event( historyConverter = HistoryConverter1.class )
	void eventHistoryWrongParams( String test );

	@Event( historyConverter = HistoryConverter3.class )
	void eventHistoryNoAnnotation();

	@Event( historyConverter = HistoryConverter4.class )
	void eventHistoryWrongEventBus();

	@Event( modulesToLoad = ChildModule.class )
	void childEventOk();

	@Event( modulesToLoad = ChildModule.class )
	void childEventOk2( int i, String s );

	@Event( modulesToLoad = ChildModuleNoEventBus.class )
	void childModuleNoEventBus();

	@Event( modulesToLoad = ChildModule.class )
	void childEventWrongParam( int i, String s );
	
	@Event( modulesToLoad = ChildModule2.class )
	void childEventWrongModule( int i, String s );

	@Event( forwardToParent = true )
	void wrongParentEventWrongParam();

}
