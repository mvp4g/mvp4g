package com.mvp4g.util.test_tools.annotation.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.mvp4g.client.annotation.Service;

@Service( path = "path", name = "name" )
public interface ServiceWithName extends RemoteService {
}