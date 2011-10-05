package com.mvp4g.example.client.time;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.NoStartPresenter;
import com.mvp4g.example.client.time.presenter.TimePresenter;

@Events( startPresenter = NoStartPresenter.class, module = TimeModule.class )
public interface TimeEventBus extends EventBus {

	@Event( handlers = TimePresenter.class )
	void showStatus();

}
