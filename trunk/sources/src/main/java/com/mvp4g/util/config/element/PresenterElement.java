package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

/**
 * An Mvp4g Presenter configuration element.</p>
 * 
 * @author javier
 */
public class PresenterElement extends Mvp4gWithServicesElement {

	public PresenterElement() {
		super( "presenter" );
	}

	public void setView( String view ) throws DuplicatePropertyNameException {
		setProperty( "view", view );
	}

	public String getView() {
		return getProperty( "view" );
	}

}
