package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class ChildModulesElement extends Mvp4gElement {
	
	private static final String LOAD_MODULE_ELEMENT_ID = ChildModulesElement.class.getName();
	
	public ChildModulesElement() {
		super( "childModules" );
	}
	
	@Override
	public String getUniqueIdentifierName() {
		return LOAD_MODULE_ELEMENT_ID;
	}
	
	public void setErrorEvent(String errorEvent) throws DuplicatePropertyNameException{
		setProperty("errorEvent", errorEvent);
	}
	
	public String getErrorEvent(){
		return getProperty("errorEvent");
	}
	
	public void setBeforeEvent(String beforeEvent) throws DuplicatePropertyNameException{
		setProperty("beforeEvent", beforeEvent);
	}
	
	public String getBeforeEvent(){
		return getProperty("beforeEvent");
	}
	
	public void setAfterEvent(String afterEvent) throws DuplicatePropertyNameException{
		setProperty("afterEvent", afterEvent);
	}
	
	public String getAfterEvent(){
		return getProperty("afterEvent");
	}

}
