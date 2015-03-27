package com.mvp4g.client.test_tools;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.mvp4g.client.history.HistoryProxy;

public class HistoryProxyStub implements HistoryProxy {

	private ValueChangeHandler<String> handler = null;
	private boolean issueEvent = false;
	private String token = null;

	public HandlerRegistration addValueChangeHandler( ValueChangeHandler<String> handler ) {
		this.handler = handler;
		return null;
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

	public void back() {
		//not needed		
	}

	public void fireCurrentHistoryState() {
		//not needed		
	}

	public void forward() {
		//not needed		
	}

	public void newItem( String historyToken ) {
		//not needed		
	}

}
