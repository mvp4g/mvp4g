package com.mvp4g.example.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.mvp4g.example.client.bean.UserBean;

public interface UserServiceAsync extends RemoteService {
	
	public void getUsers(AsyncCallback<List<UserBean>> callback);
	public void deleteUser(UserBean user, AsyncCallback<Void> callback);
	public void createUser(UserBean user, AsyncCallback<Void> callback);
	public void updateUser(UserBean user, AsyncCallback<Void> callback);

}
