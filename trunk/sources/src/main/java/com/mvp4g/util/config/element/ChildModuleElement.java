package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class ChildModuleElement extends SimpleMvp4gElement {
	
	public void setEventToLoadView(String eventToLoadView) throws DuplicatePropertyNameException{
		setProperty("eventToLoadView", eventToLoadView);
	}
	
	public String getEventToLoadView(){
		return getProperty("eventToLoadView");
	}

	public void setAsync(String async) throws DuplicatePropertyNameException {
		setProperty("async", async);
	}

	public String getAsync() {
		String async = getProperty("async");
		//By default it's true
		return (async == null) ? "true" : getProperty("async");
	}

	public boolean isAsync() {
		return "true".equalsIgnoreCase(getAsync());
	}

}
