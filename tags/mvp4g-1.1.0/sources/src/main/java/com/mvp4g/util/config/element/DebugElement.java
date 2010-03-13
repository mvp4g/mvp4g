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

public class DebugElement extends Mvp4gElement {

	public DebugElement() {
		super( "debug" );
	}

	private static final String DEBUG_ELEMENT_ID = DebugElement.class.getName();

	@Override
	public String getUniqueIdentifierName() {
		return DEBUG_ELEMENT_ID;
	}

	public void setEnabled( String enabled ) throws DuplicatePropertyNameException {
		setProperty( "enabled", enabled );
	}

	public String getEnabled() {
		return getProperty( "enabled" );
	}

	public boolean isEnabled() {
		return Boolean.TRUE.toString().equals( getEnabled() );
	}

}
