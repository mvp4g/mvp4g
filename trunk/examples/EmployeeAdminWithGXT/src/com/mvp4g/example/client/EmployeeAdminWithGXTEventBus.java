package com.mvp4g.example.client;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.example.client.ui.shell.ShellPresenter;
import com.mvp4g.example.client.ui.user.list.UserListPresenter;
import com.mvp4g.example.client.ui.user.profile.UserProfilePresenter;
import com.mvp4g.example.client.ui.user.role.UserRolePresenter;
import com.mvp4g.example.shared.dto.UserBean;

@Events(startPresenter = ShellPresenter.class,
        historyOnStart = false)
@Debug(logLevel = Debug.LogLevel.DETAILED)
public interface EmployeeAdminWithGXTEventBus
    extends EventBus {

  @Start
  @Event(handlers = {UserProfilePresenter.class, UserRolePresenter.class, UserListPresenter.class})
  public void start();

  @Event(handlers = ShellPresenter.class)
  public void setMasterView(Widget widget);

  @Event(handlers = ShellPresenter.class)
  public void setProfileView(Widget widget);

  @Event(handlers = ShellPresenter.class)
  public void setRoleView(Widget widget);

  @Event(handlers = UserListPresenter.class)
  public void showUserList();

  @Event(handlers = {UserProfilePresenter.class, UserRolePresenter.class, UserListPresenter.class})
  public void selectUser(UserBean user);

  @Event(handlers = {UserProfilePresenter.class, UserRolePresenter.class, UserListPresenter.class})
  public void unselectUser();

  @Event(handlers = {UserProfilePresenter.class, UserRolePresenter.class})
  public void createNewUser(UserBean user);

}
