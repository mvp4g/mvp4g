package com.mvp4g.rebind.test_tools.annotation.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.mvp4g.client.annotation.Service;

@Service(path = "path")
public interface SimpleService
  extends RemoteService {
}
