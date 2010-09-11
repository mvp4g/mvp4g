package com.mvp4g.example.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mvp4g.example.client.bean.UserBean;

@RemoteServiceRelativePath( "userService" )
public interface UserService extends RemoteService {

	public List<UserBean> getUsers();

	public void deleteUser( UserBean user );

	public void createUser( UserBean user );

	public void updateUser( UserBean user );

}
