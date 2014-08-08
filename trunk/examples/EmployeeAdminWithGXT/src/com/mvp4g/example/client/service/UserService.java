package com.mvp4g.example.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mvp4g.example.shared.dto.UserBean;

import java.util.List;

@RemoteServiceRelativePath("userService")
public interface UserService
    extends RemoteService {

  public List<UserBean> getUsers();

  public void deleteUser(UserBean user);

  public UserBean createUser(UserBean user);

  public UserBean updateUser(UserBean user);

}
