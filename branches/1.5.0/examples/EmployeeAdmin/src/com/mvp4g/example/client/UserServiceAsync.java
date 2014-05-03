package com.mvp4g.example.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.example.client.bean.UserBean;

import java.util.List;

public interface UserServiceAsync {

  public void getUsers(AsyncCallback<List<UserBean>> callback);

  public void deleteUser(UserBean user,
                         AsyncCallback<Void> callback);

  public void createUser(UserBean user,
                         AsyncCallback<Void> callback);

  public void updateUser(UserBean user,
                         AsyncCallback<Void> callback);

}
