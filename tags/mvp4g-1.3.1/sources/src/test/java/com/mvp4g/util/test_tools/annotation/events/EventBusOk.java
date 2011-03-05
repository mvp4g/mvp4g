package com.mvp4g.util.test_tools.annotation.events;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Forward;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.NotFoundHistory;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.util.test_tools.annotation.history_converters.HistoryConverterForEvent;
import com.mvp4g.util.test_tools.annotation.presenters.PresenterWithName;

@Events( startView = Object.class )
public interface EventBusOk extends EventBus {

	@NotFoundHistory
	@Event( handlerNames = "name", calledMethod = "treatEvent1", historyConverterName = "history" )
	public void event1( String obj );

	@Start
	@InitHistory
	@Forward
	@Event( handlers = PresenterWithName.class, historyConverter = HistoryConverterForEvent.class, navigationEvent = true, passive = true )
	public String event2();
}
