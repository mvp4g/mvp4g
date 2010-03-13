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

	public void setErrorEvent( String errorEvent ) throws DuplicatePropertyNameException {
		setProperty( "errorEvent", errorEvent );
	}

	public String getErrorEvent() {
		return getProperty( "errorEvent" );
	}

	public void setBeforeEvent( String beforeEvent ) throws DuplicatePropertyNameException {
		setProperty( "beforeEvent", beforeEvent );
	}

	public String getBeforeEvent() {
		return getProperty( "beforeEvent" );
	}

	public void setAfterEvent( String afterEvent ) throws DuplicatePropertyNameException {
		setProperty( "afterEvent", afterEvent );
	}

	public String getAfterEvent() {
		return getProperty( "afterEvent" );
	}

}
