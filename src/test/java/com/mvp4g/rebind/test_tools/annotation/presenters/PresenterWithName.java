package com.mvp4g.rebind.test_tools.annotation.presenters;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter( view = Object.class, name = "name" )
public class PresenterWithName extends BasePresenter<Object, EventBus> {
}
