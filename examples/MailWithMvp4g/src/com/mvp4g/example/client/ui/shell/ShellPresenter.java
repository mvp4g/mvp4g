package com.mvp4g.example.client.ui.shell;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MailEventBus;


@Presenter(view = IShellView.class)
public class ShellPresenter
    extends BasePresenter<IShellView, MailEventBus>
    implements IShellView.IShellPresenter {

  public void onSetTopView(Widget widget) {
    view.setTopView(widget);
  }

  public void onSetListView(Widget widget){
    view.setListView(widget);
  }

  public void onSetShortCutsView(Widget widget){
    view.setShortCutsView(widget);
  }

  public void onSetDetailView(Widget widget) {
    view.setDetailView(widget);
  }

}
