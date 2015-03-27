package com.mvp4g.rebind.test_tools.annotation;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter( view = String.class )
public class OnePresenter extends BasePresenter<String, EventBus> {

}
