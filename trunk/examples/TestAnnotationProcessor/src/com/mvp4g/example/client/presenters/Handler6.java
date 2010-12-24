package com.mvp4g.example.client.presenters;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.TestEventBus;

@Presenter( view = Object.class )
public class Handler6 extends BasePresenter<String, TestEventBus> {

}
