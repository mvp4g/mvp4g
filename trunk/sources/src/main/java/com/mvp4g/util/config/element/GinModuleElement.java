package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class GinModuleElement extends Mvp4gElement {
	public GinModuleElement() {
		super( "gin" );
	}

	private static final String GIN_ELEMENT_ID = GinModuleElement.class.getName();

	@Override
	public String getUniqueIdentifierName() {
		return GIN_ELEMENT_ID;
	}

	public void setModules( String[] modules ) throws DuplicatePropertyNameException {
		setValues( "modules", modules );
	}

	public String[] getModules() {
		return getValues( "modules" );
	}
}
