package com.mvp4g.example.client.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.view.UselessView;

@Presenter( view = UselessView.class )
public class UselessPresenter extends BasePresenter<UselessView, MyEventBus> {

}
