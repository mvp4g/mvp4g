package com.mvp4g.example.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.example.shared.dto.UserBean;

import java.util.List;

public interface UserServiceAsync {

  public void getUsers(AsyncCallback<List<UserBean>> callback);

  public void deleteUser(UserBean user,
                         AsyncCallback<Void> callback);

  public void createUser(UserBean user,
                         AsyncCallback<UserBean> callback);

  public void updateUser(UserBean user,
                         AsyncCallback<UserBean> callback);

}
