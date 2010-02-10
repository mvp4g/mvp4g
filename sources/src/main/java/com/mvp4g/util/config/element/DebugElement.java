package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class DebugElement extends Mvp4gElement {
	
	public DebugElement() {
		super("debug");		
	}

	private static final String DEBUG_ELEMENT_ID = StartElement.class.getName();

	@Override
	public String getUniqueIdentifierName() {
		return DEBUG_ELEMENT_ID;
	}
	
	public void setEnabled(String enabled) throws DuplicatePropertyNameException{
		setProperty("enabled", enabled);
	}
	
	public boolean isEnabled(){
		return Boolean.TRUE.toString().equals(getProperty("enabled"));
	}

}
