package com.mvp4g.example.client;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.ClearHistory;
import com.mvp4g.example.client.history.HistoryConverter1;
import com.mvp4g.example.client.history.HistoryConverter2;
import com.mvp4g.example.client.history.HistoryConverter3;
import com.mvp4g.example.client.history.HistoryConverter4;
import com.mvp4g.example.client.presenters.Handler1;
import com.mvp4g.example.client.presenters.Handler2;
import com.mvp4g.example.client.presenters.Handler3;
import com.mvp4g.example.client.presenters.Handler4;
import com.mvp4g.example.client.presenters.Handler5;
import com.mvp4g.example.client.presenters.Handler6;

@Events( startView = Object.class )
public interface TestEventBus extends EventBus {

	@Event( handlers = Handler1.class )
	void event1OK();

	@Event( handlers = { Handler1.class, Handler2.class } )
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

	@Event( historyConverter = HistoryConverter2.class )
	void eventHistoryOk2();

	@Event( historyConverter = ClearHistory.class )
	void eventHistoryOk3();

	@Event( historyConverter = HistoryConverter1.class )
	void eventHistoryNoConvertMethod();

	@Event( historyConverter = HistoryConverter3.class )
	void eventHistoryNoAnnotation();

	@Event( historyConverter = HistoryConverter4.class )
	void eventHistoryWrongEventBus();

}
