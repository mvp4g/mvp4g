package com.mvp4g.util.config.element;

import java.util.ArrayList;
import java.util.List;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

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

	private List<InjectedElement> injectedServices = new ArrayList<InjectedElement>();

	public Mvp4gWithServicesElement() {
		super( "withServices" );
	}

	public Mvp4gWithServicesElement( String tagName ) {
		super( tagName );
	}

	public void setServices( String[] services ) throws DuplicatePropertyNameException {

		for ( String service : services ) {
			injectedServices.add( new InjectedElement( service, "set" + capitalized( service ) ) );
		}

	}

	public List<InjectedElement> getInjectedServices() {
		return injectedServices;
	}

	/**
	 * Returns the supplied name with its first letter in upper case.
	 * 
	 * @param name
	 *            the name to be capitalized.
	 */
	/* package */
	String capitalized( String name ) {
		return name.substring( 0, 1 ).toUpperCase() + name.substring( 1 );
	}

}
