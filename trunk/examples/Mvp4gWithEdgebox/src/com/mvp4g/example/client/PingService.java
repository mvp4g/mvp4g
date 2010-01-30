/**
 * 
 */
package com.mvp4g.example.client;

import org.edgebox.gwt.rest.client.RestRemote;
import org.edgebox.gwt.rest.client.RestGWT.Method;
import org.edgebox.gwt.rest.client.annotation.Endpoint;
import org.edgebox.gwt.rest.client.annotation.HttpHeader;
import org.edgebox.gwt.rest.client.annotation.HttpHeaders;
import org.edgebox.gwt.rest.client.annotation.ReqKey;
import org.edgebox.gwt.rest.client.annotation.Timeout;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.mvp4g.client.annotation.Service;

/**
 * @author keke
 * 
 */
@Service(generatedClass=PingService.class)
public interface PingService extends RestRemote {
	@Endpoint(method = Method.Accept, urlTemplate = "/api/ping")
	@HttpHeaders(headers = { @HttpHeader(name = "a", value = "a"),
	        @HttpHeader(name = "b", value = "b") })
	@Timeout(1000)
	Request ping(@ReqKey("q") String query, RequestCallback callback);
}
