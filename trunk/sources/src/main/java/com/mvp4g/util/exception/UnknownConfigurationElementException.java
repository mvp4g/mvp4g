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
public class UnknownConfigurationElementException extends InvalidMvp4gConfigurationException {

	private static final long serialVersionUID = 5810274632221827765L;

	private static final String MESSAGE = "%s %s: Encountered a reference to unknown element '%s'";

	public UnknownConfigurationElementException( Mvp4gElement e, String element ) {
		super( getErrorMessage( e, element ) );
	}

	private static String getErrorMessage( Mvp4gElement e, String element ) {
		return String.format( MESSAGE, e.getTagName(), e.getUniqueIdentifier(), element );
	}
}
