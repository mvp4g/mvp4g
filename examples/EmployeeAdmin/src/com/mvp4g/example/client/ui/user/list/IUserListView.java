package com.mvp4g.example.client.ui.user.list;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.example.shared.dto.UserBean;

import java.util.List;

public interface IUserListView
    extends IsWidget {

  void setUserList(List<UserBean> userList);

  void setUserSelected(boolean enabled);

  public interface IUserListPresenter {

    public void doDeleteUser(UserBean user);

    public void doNewUser();

    public void doShowUser(UserBean user);

  }
}
