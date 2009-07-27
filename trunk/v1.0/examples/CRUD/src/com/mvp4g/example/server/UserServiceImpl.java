package com.mvc4g.example.server;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mvc4g.example.client.bean.UserBean;
import com.mvc4g.example.client.service.UserService;

@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	private AtomicInteger idGenerator = new AtomicInteger( 0 );
	
	public UserBean create(UserBean user) {
		user.setId( idGenerator.incrementAndGet() );
		return user;
	}
}
