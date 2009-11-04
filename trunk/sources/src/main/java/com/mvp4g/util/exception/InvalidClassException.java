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
public class InvalidClassException extends InvalidMvp4gConfigurationException {

	private static final long serialVersionUID = 5810274632221827765L;

	private static final String MESSAGE = "%s %s: This class must extend %s.";

	public InvalidClassException( Mvp4gElement element, String classExpected) {
		super( String.format( MESSAGE, element.getTagName(), element.getUniqueIdentifier(), classExpected ) );
	}

	
}
