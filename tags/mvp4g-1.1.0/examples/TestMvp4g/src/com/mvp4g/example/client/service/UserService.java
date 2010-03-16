package com.mvp4g.example.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mvp4g.example.client.bean.UserBean;

@RemoteServiceRelativePath( "users.rpc" )
public interface UserService extends RemoteService {

	UserBean create( UserBean user );

}
