package de.gishmo.mvp4g.client;


import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;
import de.gishmo.mvp4g.client.ui.header.HeaderPresenter;
import de.gishmo.mvp4g.client.ui.navigation.NavigationPresenter;
import de.gishmo.mvp4g.client.ui.page01.Page01Presenter;
import de.gishmo.mvp4g.client.ui.page02.Page02Presenter;
import de.gishmo.mvp4g.client.ui.page03.Page03Presenter;
import de.gishmo.mvp4g.client.ui.page04.Page04Presenter;
import de.gishmo.mvp4g.client.ui.page05.Page05Presenter;
import de.gishmo.mvp4g.client.ui.page06.Page06Presenter;
import de.gishmo.mvp4g.client.ui.shell.ShellPresenter;

@Events(startPresenter = ShellPresenter.class, ginModules = {Mvp4gHyperLinkGinModules.class})
@Debug(logLevel = Debug.LogLevel.DETAILED)
public interface Mvp4gHyperlinkEventBus
    extends EventBus {

  @Start
  @Event(handlers = {Page01Presenter.class},
         bind = {HeaderPresenter.class, NavigationPresenter.class}
  )
  public void start();


  @Event(handlers = ShellPresenter.class)
  public void setHeaderView(Widget widget);

  @Event(handlers = ShellPresenter.class)
  public void setNavigationView(Widget widget);

  @Event(handlers = ShellPresenter.class)
  public void setContentView(Widget widget);

  @Event(handlers = Page01Presenter.class)
  public void showPage01();

  @Event(handlers = Page02Presenter.class)
  public void showPage02();

  @Event(handlers = Page03Presenter.class)
  public void showPage03();

  @Event(handlers = Page04Presenter.class)
  public void showPage04();

  @Event(handlers = Page05Presenter.class)
  public void showPage05();

  @Event(handlers = Page06Presenter.class)
  public void showPage06();
}
