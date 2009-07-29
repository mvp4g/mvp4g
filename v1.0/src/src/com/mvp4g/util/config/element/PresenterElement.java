package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.DuplicatePropertyNameException;

/**
 * An Mvp4g Presenter configuration element.</p>
 * 
 * @author javier
 */
public class PresenterElement extends SimpleMvp4gElement {

	public PresenterElement() {
		super( "presenter" );
	}

	public void setView( String view ) throws DuplicatePropertyNameException {
		setProperty( "view", view );
	}

	public String getView() {
		return getProperty( "view" );
	}

	public void setServices( String[] services ) throws DuplicatePropertyNameException {
		setValues( "services", services );
	}

	public String[] getServices() {
		return getValues( "services" );
	}
}
