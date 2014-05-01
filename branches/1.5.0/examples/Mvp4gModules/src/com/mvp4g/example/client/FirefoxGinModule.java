package com.mvp4g.example.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.mvp4g.example.client.main.view.FirefoxMainView;
import com.mvp4g.example.client.main.view.MainView;

public class FirefoxGinModule
  extends AbstractGinModule {

  @Override
  protected void configure() {
    bind(MainView.class).to(FirefoxMainView.class);
  }

}
