package com.mvp4g.example.client;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mvp4g.example.client.bean.UserBean;

@RemoteServiceRelativePath("userService")
public interface UserService extends RemoteService {
	
	public PagingLoadResult<UserBean> getUsers( PagingLoadConfig config );
	public void deleteUser(UserBean user);
	public void createUser(UserBean user);
	public void updateUser(UserBean user);

}
