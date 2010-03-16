package com.mvp4g.client.test_tools;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.mvp4g.client.history.PlaceService;

public class HistoryProxyStub implements PlaceService.HistoryProxy {

	private ValueChangeHandler<String> handler = null;
	private boolean issueEvent = false;
	private String token = null;

	public void addValueChangeHandler( ValueChangeHandler<String> handler ) {
		this.handler = handler;

	}

	public void newItem( String token, boolean issueEvent ) {
		this.token = token;
		this.issueEvent = issueEvent;
	}

	public ValueChangeHandler<String> getHandler() {
		return handler;
	}

	public boolean isIssueEvent() {
		return issueEvent;
	}

	public String getToken() {
		return token;
	}

}
