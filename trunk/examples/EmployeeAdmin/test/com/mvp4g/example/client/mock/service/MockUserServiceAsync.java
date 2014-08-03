package com.mvp4g.example.client.mock.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.service.UserServiceAsync;
import com.mvp4g.example.shared.dto.UserBean;

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

}
