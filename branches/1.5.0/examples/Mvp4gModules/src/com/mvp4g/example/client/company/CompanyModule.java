package com.mvp4g.example.client.company;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.module.HistoryName;
import com.mvp4g.client.annotation.module.Loader;
import com.mvp4g.example.client.main.presenter.MainPresenter;
import com.mvp4g.example.client.util.HasBeenThereHandler;

@Loader(MainPresenter.class)
@HistoryName("company")
public interface CompanyModule
  extends Mvp4gModule,
          HasBeenThereHandler {

}
