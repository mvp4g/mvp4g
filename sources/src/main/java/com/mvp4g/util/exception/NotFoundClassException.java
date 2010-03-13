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
package com.mvp4g.util.exception;

import com.mvp4g.util.config.element.Mvp4gElement;

/**
 * An error indicating the encounter of a reference to a non-existing element.
 * 
 * @author javier
 * 
 */
public class NotFoundClassException extends InvalidMvp4gConfigurationException {

	private static final long serialVersionUID = 5810274632221827765L;

	private static final String MESSAGE = "%s %s: No source code is available for %s";
	private static final String MESSAGE_WITHOUT = "No source code is available for %s";

	public NotFoundClassException( Mvp4gElement element, String classNotFound ) {
		super( getErrorMessage( element, classNotFound ) );
	}

	private static String getErrorMessage( Mvp4gElement element, String classNotFound ) {
		return ( element == null ) ? String.format( MESSAGE_WITHOUT, classNotFound ) : String.format( MESSAGE, element.getTagName(), element
				.getUniqueIdentifier(), classNotFound );
	}

}
