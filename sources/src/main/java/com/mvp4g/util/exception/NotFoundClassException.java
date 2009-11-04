/**
 * 
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

	private static final String MESSAGE = "%s %s: No source code is available for %s.";
	private static final String MESSAGE_WITHOUT = "No source code is available for %s.";

	public NotFoundClassException( Mvp4gElement element, String classNotFound ) {
		super( getErrorMessage( element, classNotFound ) );
	}

	private static String getErrorMessage( Mvp4gElement element, String classNotFound ) {
		return ( element == null ) ? String.format( MESSAGE_WITHOUT, classNotFound ) : String.format( MESSAGE, element.getTagName(), element
				.getUniqueIdentifier(), classNotFound );
	}

}
