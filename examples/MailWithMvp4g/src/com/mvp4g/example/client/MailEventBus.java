package com.mvp4g.example.client;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.example.client.bean.MailItem;
import com.mvp4g.example.client.ui.detail.MailDetailPresenter;
import com.mvp4g.example.client.ui.list.MailListPresenter;
import com.mvp4g.example.client.ui.shell.ShellPresenter;
import com.mvp4g.example.client.ui.shortcuts.ShortCutsPresenter;
import com.mvp4g.example.client.ui.top.TopPresenter;

@Events(startPresenter = ShellPresenter.class)
public interface MailEventBus
    extends EventBus {

  @Start
  @Event(handlers = {MailListPresenter.class, ShortCutsPresenter.class},
         bind = {TopPresenter.class, MailDetailPresenter.class})
  public void start();

  @Event(handlers = MailDetailPresenter.class)
  public void itemSelected(MailItem item);

  @Event(handlers = MailListPresenter.class)
  public void newer();

  @Event(handlers = MailListPresenter.class)
  public void older();

  @Event(handlers = ShellPresenter.class)
  public void setTopView(Widget topView);

  @Event(handlers = ShellPresenter.class)
  public void setListView(Widget widget);

  @Event(handlers = ShellPresenter.class)
  public void setShortCutsView(Widget widget);

  @Event(handlers = ShellPresenter.class)
  public void setDetailView(Widget widget);

}
