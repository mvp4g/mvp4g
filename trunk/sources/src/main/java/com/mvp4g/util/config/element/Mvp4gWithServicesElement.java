/*
 * Copyright 2009 Pierre-Laurent Coirier
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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

	@Override
	public void setValues( String name, String[] values ) throws DuplicatePropertyNameException {
		super.setValues( name, values );
		if ( "services".equals( name ) ) {
			setServices( values );
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

	private void setServices( String[] services ) throws DuplicatePropertyNameException {

		for ( String service : services ) {
			if ( ( service != null ) && ( service.length() > 0 ) ) {
				injectedServices.add( new InjectedElement( service, "set" + capitalized( service ) ) );
			}
		}

	}
}
