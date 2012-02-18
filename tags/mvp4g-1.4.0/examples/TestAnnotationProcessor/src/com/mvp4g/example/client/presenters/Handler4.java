package com.mvp4g.example.client.presenters;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter( view = Object.class )
public class Handler4 extends BasePresenter<Object, EventBusWithLookup> {

}
