package com.mvc4g.example.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvc4g.example.client.bean.UserBean;

public interface UserServiceAsync {

	void create(UserBean user, AsyncCallback<UserBean> callback);
}
