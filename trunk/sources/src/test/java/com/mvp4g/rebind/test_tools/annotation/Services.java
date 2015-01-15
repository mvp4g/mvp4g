package com.mvp4g.rebind.test_tools.annotation;

import com.google.gwt.user.client.rpc.RemoteService;
import com.mvp4g.client.annotation.Service;

public class Services {

	@Service( path = "path", name = "name", generatedClass = ServiceWithGeneratedClass.class )
	public interface ServiceWithGeneratedClass extends RemoteService {
	}

}
