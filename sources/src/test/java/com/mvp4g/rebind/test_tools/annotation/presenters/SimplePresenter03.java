package com.mvp4g.rebind.test_tools.annotation.presenters;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventBus;

@Presenter( view = Object.class )
public class SimplePresenter03
		extends SimplePresenter<Object, EventBus> {
}