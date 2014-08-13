package com.mvp4g.example.client.widgets;

import com.google.gwt.user.client.ui.ResizeComposite;
import com.mvp4g.client.view.ReverseViewInterface;

public class ReverseResizeComposite<T>
    extends ResizeComposite
    implements ReverseViewInterface<T> {

  protected T presenter;

  @Override
  public void setPresenter(T presenter) {
    this.presenter = presenter;
  }

  @Override
  public T getPresenter() {
    return presenter;
  }

}
