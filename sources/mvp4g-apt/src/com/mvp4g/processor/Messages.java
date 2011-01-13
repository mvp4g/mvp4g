package com.mvp4g.processor;



public interface Messages {
	
	public static final String MISSING_METHOD = "%s is missing public method %s: %s";
	
	public static final String MISSING_ANNOTATION = "%s should be annotated with @%s";
	
	public static final String MISSING_ANNOTATION_OR = MISSING_ANNOTATION + " or @%s";
	
	public static final String INVALID_EVENT_BUS = "Invalid Event bus: %s can't be injected to %s. Can not convert %s to %s";
	
	public static final String INVALID_VIEW = "Invalid View: %s can't be injected to %s. Can not convert %s to %s";

}
