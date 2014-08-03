package com.mvp4g.example.client.ui.shell;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.EmployeeAdminEventBus;


@Presenter(view = ShellView.class)
public class ShellPresenter
    extends BasePresenter<IShellView, EmployeeAdminEventBus>
    implements IShellView.IShellPresenter {

  public void onSetMasterView(Widget widget) {
    view.setMasterView(widget);
  }

  public void onSetProfileView(Widget widget) {
    view.setProfileView(widget);
  }

  public void onSetRoleView(Widget widget) {
    view.setRoleView(widget);
  }

}
