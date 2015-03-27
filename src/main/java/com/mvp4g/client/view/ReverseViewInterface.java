package com.mvp4g.client.view;

public interface ReverseViewInterface<T> {

  T getPresenter();

  void setPresenter(T presenter);

}
