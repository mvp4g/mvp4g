package com.mvp4g.example.client.main;

import com.mvp4g.client.Mvp4gSplitter;
import com.mvp4g.client.annotation.module.Loader;
import com.mvp4g.example.client.main.presenter.MainPresenter;

@Loader( MainPresenter.class )
public interface StatusSplitter extends Mvp4gSplitter {

}
