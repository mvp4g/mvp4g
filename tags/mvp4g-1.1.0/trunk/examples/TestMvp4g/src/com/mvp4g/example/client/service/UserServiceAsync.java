package com.mvp4g.example.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.example.client.bean.UserBean;

public interface UserServiceAsync {

	void create( UserBean user, AsyncCallback<UserBean> callback );
}
