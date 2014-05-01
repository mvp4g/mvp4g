package com.mvp4g.example.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.mvp4g.example.client.util.display.FullNameIndexDisplayer;
import com.mvp4g.example.client.util.display.IndexDisplayer;
import com.mvp4g.example.client.util.index.IndexGenerator;
import com.mvp4g.example.client.util.index.SameIndexGenerator;
import com.mvp4g.example.client.util.token.BaseTokenGenerator;
import com.mvp4g.example.client.util.token.TokenGenerator;

public class Mvp4gGinModule
  extends AbstractGinModule {

  @Override
  protected void configure() {
    bind(IndexGenerator.class).to(SameIndexGenerator.class);
    bind(TokenGenerator.class).to(BaseTokenGenerator.class);
    bind(IndexDisplayer.class).to(FullNameIndexDisplayer.class);
  }

}
