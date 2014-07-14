package com.mvp4g.example.client;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.example.client.presenters.TestMvp4gPresenter1;
import com.mvp4g.example.client.presenters.TestMvp4gPresenter10;
import com.mvp4g.example.client.presenters.TestMvp4gPresenter2;
import com.mvp4g.example.client.presenters.TestMvp4gPresenter3;
import com.mvp4g.example.client.presenters.TestMvp4gPresenter4;
import com.mvp4g.example.client.presenters.TestMvp4gPresenter5;
import com.mvp4g.example.client.presenters.TestMvp4gPresenter6;
import com.mvp4g.example.client.presenters.TestMvp4gPresenter7;
import com.mvp4g.example.client.presenters.TestMvp4gPresenter8;
import com.mvp4g.example.client.presenters.TestMvp4gPresenter9;

@Events( startView = MainView.class )
public interface TestEventBus extends EventBus {

	@Event( handlers = { TestMvp4gPresenter1.class, TestMvp4gPresenter2.class, TestMvp4gPresenter3.class, TestMvp4gPresenter4.class,
			TestMvp4gPresenter5.class, TestMvp4gPresenter6.class, TestMvp4gPresenter7.class, TestMvp4gPresenter8.class, TestMvp4gPresenter9.class,
			TestMvp4gPresenter10.class } )
	public void event1( String event );

	@Event( handlers = { TestMvp4gPresenter1.class, TestMvp4gPresenter2.class, TestMvp4gPresenter3.class, TestMvp4gPresenter4.class,
			TestMvp4gPresenter5.class, TestMvp4gPresenter6.class, TestMvp4gPresenter7.class, TestMvp4gPresenter8.class, TestMvp4gPresenter9.class,
			TestMvp4gPresenter10.class } )
	public void event2( String event );

	@Event( handlers = { TestMvp4gPresenter1.class, TestMvp4gPresenter2.class, TestMvp4gPresenter3.class, TestMvp4gPresenter4.class,
			TestMvp4gPresenter5.class, TestMvp4gPresenter6.class, TestMvp4gPresenter7.class, TestMvp4gPresenter8.class, TestMvp4gPresenter9.class,
			TestMvp4gPresenter10.class } )
	public void event3( String event );

	@Event( handlers = { TestMvp4gPresenter1.class, TestMvp4gPresenter2.class, TestMvp4gPresenter3.class, TestMvp4gPresenter4.class,
			TestMvp4gPresenter5.class, TestMvp4gPresenter6.class, TestMvp4gPresenter7.class, TestMvp4gPresenter8.class, TestMvp4gPresenter9.class,
			TestMvp4gPresenter10.class } )
	public void event4( String event );

	@Event( handlers = { TestMvp4gPresenter1.class, TestMvp4gPresenter2.class, TestMvp4gPresenter3.class, TestMvp4gPresenter4.class,
			TestMvp4gPresenter5.class, TestMvp4gPresenter6.class, TestMvp4gPresenter7.class, TestMvp4gPresenter8.class, TestMvp4gPresenter9.class,
			TestMvp4gPresenter10.class } )
	public void event5( String event );

	@Event( handlers = { TestMvp4gPresenter1.class, TestMvp4gPresenter2.class, TestMvp4gPresenter3.class, TestMvp4gPresenter4.class,
			TestMvp4gPresenter5.class, TestMvp4gPresenter6.class, TestMvp4gPresenter7.class, TestMvp4gPresenter8.class, TestMvp4gPresenter9.class,
			TestMvp4gPresenter10.class } )
	public void event6( String event );

	@Event( handlers = { TestMvp4gPresenter1.class, TestMvp4gPresenter2.class, TestMvp4gPresenter3.class, TestMvp4gPresenter4.class,
			TestMvp4gPresenter5.class, TestMvp4gPresenter6.class, TestMvp4gPresenter7.class, TestMvp4gPresenter8.class, TestMvp4gPresenter9.class,
			TestMvp4gPresenter10.class } )
	public void event7( String event );

	@Event( handlers = { TestMvp4gPresenter1.class, TestMvp4gPresenter2.class, TestMvp4gPresenter3.class, TestMvp4gPresenter4.class,
			TestMvp4gPresenter5.class, TestMvp4gPresenter6.class, TestMvp4gPresenter7.class, TestMvp4gPresenter8.class, TestMvp4gPresenter9.class,
			TestMvp4gPresenter10.class } )
	public void event8( String event );

	@Event( handlers = { TestMvp4gPresenter1.class, TestMvp4gPresenter2.class, TestMvp4gPresenter3.class, TestMvp4gPresenter4.class,
			TestMvp4gPresenter5.class, TestMvp4gPresenter6.class, TestMvp4gPresenter7.class, TestMvp4gPresenter8.class, TestMvp4gPresenter9.class,
			TestMvp4gPresenter10.class } )
	public void event9( String event );

	@Event( handlers = { TestMvp4gPresenter1.class, TestMvp4gPresenter2.class, TestMvp4gPresenter3.class, TestMvp4gPresenter4.class,
			TestMvp4gPresenter5.class, TestMvp4gPresenter6.class, TestMvp4gPresenter7.class, TestMvp4gPresenter8.class, TestMvp4gPresenter9.class,
			TestMvp4gPresenter10.class } )
	public void event10( String event );

}
