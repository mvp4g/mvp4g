package com.mvp4g.example.client.mock.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.UserServiceAsync;
import com.mvp4g.example.client.bean.UserBean;

public class MockUserServiceAsync implements UserServiceAsync, Constants {

	private List<UserBean> users = null;

	public MockUserServiceAsync( List<UserBean> users ) {
		this.users = users;
	}

	public void createUser( UserBean user, AsyncCallback<Void> callback ) {
		callback.onSuccess( null );
	}

	public void deleteUser( UserBean user, AsyncCallback<Void> callback ) {
		callback.onSuccess( null );
	}

	public void getUsers( AsyncCallback<List<UserBean>> callback ) {

		callback.onSuccess( users );
	}

	public void updateUser( UserBean user, AsyncCallback<Void> callback ) {
		callback.onSuccess( null );
	}

	public void getUsers( PagingLoadConfig config, AsyncCallback<PagingLoadResult<UserBean>> callback ) {
		PagingLoadResult<UserBean> result = new BasePagingLoadResult<UserBean>(users, 0, users.size());
		callback.onSuccess( result );
	}

}
