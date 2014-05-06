package com.mvp4g.example.client;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.example.client.bean.MailItem;
import com.mvp4g.example.client.presenter.*;

@Events(startPresenter = MailPresenter.class)
public interface MailEventBus
  extends EventBus {

  @Start
  @Event(handlers = {MailListPresenter.class, ShortCutsPresenter.class},
         bind = {TopPresenter.class, NavBarPresenter.class, MailDetailPresenter.class})
  public void start();

  @Event(handlers = MailDetailPresenter.class)
  public void itemSelected(MailItem item);

  @Event(handlers = NavBarPresenter.class)
  public void setNavStatus(int startIndex,
                           int endIndex,
                           int numberOfElements);

  @Event(handlers = MailListPresenter.class)
  public void newer();

  @Event(handlers = MailListPresenter.class)
  public void older();

}