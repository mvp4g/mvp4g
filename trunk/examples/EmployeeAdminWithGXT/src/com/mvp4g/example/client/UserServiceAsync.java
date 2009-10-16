package com.mvp4g.example.client;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.example.client.bean.UserBean;

public interface UserServiceAsync {
	
	public void deleteUser(UserBean user, AsyncCallback<Void> callback);
	public void createUser(UserBean user, AsyncCallback<Void> callback);
	public void updateUser(UserBean user, AsyncCallback<Void> callback);
	public void getUsers( PagingLoadConfig config, AsyncCallback<PagingLoadResult<UserBean>> callback );

}
