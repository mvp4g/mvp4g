package com.mvp4g.util.config.element;

public class InjectedElement {

	private String elementName = null;
	private String setterName = null;

	public InjectedElement( String elementName, String setterName ) {
		this.elementName = elementName;
		this.setterName = setterName;
	}

	public String getElementName() {
		return elementName;
	}

	public String getSetterName() {
		return setterName;
	}

}
