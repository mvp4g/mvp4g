package com.mvp4g.example.client.ui.shell;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.ReverseViewInterface;

public interface IShellView
    extends ReverseViewInterface<IShellView.IShellPresenter> {

  void setMasterView(Widget widget);

  void setProfileView(Widget widget);

  void setRoleView(Widget widget);

  public interface IShellPresenter {

  }

}
