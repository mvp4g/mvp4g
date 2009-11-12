package com.mvp4g.util.test_tools.annotation;

import com.google.gwt.user.client.rpc.RemoteService;
import com.mvp4g.client.annotation.Service;

public class Services {

	@Service(path = "path")
	public interface SimpleService extends RemoteService{}
	
	public interface SimpleServiceAsync extends RemoteService{}

	@Service(path = "path", name="name")
	public interface ServiceWithName extends RemoteService{}

}
