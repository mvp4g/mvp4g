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
package com.mvp4g.util.exception.element;

/**
 * An error produced when an Mvp4g configuration element has duplicate property names.
 * 
 * @author javier
 * 
 */
public class DuplicatePropertyNameException extends Exception {

	private static final long serialVersionUID = -673723383230350358L;

	private static final String MESSAGE = "Duplicate attributes '%s'";

	public DuplicatePropertyNameException( String propertyName ) {
		super( getErrorMessage( propertyName ) );
	}

	private static String getErrorMessage( String propertyName ) {
		return String.format( MESSAGE, propertyName );
	}

}
