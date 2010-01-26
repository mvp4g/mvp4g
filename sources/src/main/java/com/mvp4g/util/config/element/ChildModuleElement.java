package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class ChildModuleElement extends SimpleMvp4gElement {

	public ChildModuleElement() {
		super("childModule");
	}

	public void setEventToLoadView(String eventToLoadView)
			throws DuplicatePropertyNameException {
		setProperty("eventToLoadView", eventToLoadView);
	}

	public String getEventToLoadView() {
		return getProperty("eventToLoadView");
	}

	public void setAsync(String async) throws DuplicatePropertyNameException {
		setProperty("async", async);
	}

	public String getAsync() {
		String async = getProperty("async");
		// By default it's true
		return (async == null) ? "true" : getProperty("async");
	}

	public boolean isAsync() {
		return "true".equalsIgnoreCase(getAsync());
	}

	public void setAutoLoad(String autoLoad)
			throws DuplicatePropertyNameException {
		setProperty("autoLoad", autoLoad);
	}

	public String getAutoLoad() {
		String autoLoad = getProperty("autoLoad");
		// By default it's true
		return (autoLoad == null) ? "true" : getProperty("autoLoad");
	}

	public boolean isAutoLoad() {
		return "true".equalsIgnoreCase(getAutoLoad());
	}

	public String getHistoryName() {
		return getProperty("historyName");
	}

	public void setHistoryName(String historyName)
			throws DuplicatePropertyNameException {
		setProperty("historyName", historyName);
	}

}
