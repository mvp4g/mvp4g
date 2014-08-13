package com.mvp4g.example.client.ui.shell;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.ReverseViewInterface;

public interface IShellView
    extends ReverseViewInterface<IShellView.IShellPresenter> {

  void setTopView(Widget widget);

  void setListView(Widget widget);

  void setShortCutsView(Widget widget);

  void setDetailView(Widget widget);

  interface IShellPresenter {
  }

}
