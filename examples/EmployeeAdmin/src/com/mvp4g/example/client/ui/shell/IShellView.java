package com.mvp4g.example.client.ui.shell;

import com.google.gwt.user.client.ui.Widget;

public interface IShellView {

  void setMasterView(Widget widget);

  void setProfileView(Widget widget);

  void setRoleView(Widget widget);

  public interface IShellPresenter {

  }

}
