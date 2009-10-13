package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.DuplicatePropertyNameException;

/**
* A simple Mvp4g configuration element with services.</p>
* 
* This element also have the following attributes: 
* <ul>
* <li/><i>services</i>: list of the services to inject to the element
* </ul>
* 
*@author plcoirier
* 
**/
public class Mvp4gWithServicesElement extends SimpleMvp4gElement {

	public Mvp4gWithServicesElement() {
		super( "withServices" );
	}
	
	public Mvp4gWithServicesElement( String tagName ) {
		super( tagName );
	}

	public void setServices( String[] services ) throws DuplicatePropertyNameException {
		setValues( "services", services );
	}

	public String[] getServices() {
		return getValues( "services" );
	}
}
