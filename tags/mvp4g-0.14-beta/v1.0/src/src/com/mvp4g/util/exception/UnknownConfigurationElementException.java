/**
 * 
 */
package com.mvp4g.util.exception;

/**
 * An error indicating the encounter of a reference to a non-existing element.
 * 
 * @author javier
 * 
 */
public class UnknownConfigurationElementException extends InvalidMvp4gConfigurationException {

	private static final long serialVersionUID = 5810274632221827765L;

	private static final String MESSAGE = "Encountered a reference to unknown element '%s'";

	public UnknownConfigurationElementException( String element ) {
		super( getErrorMessage( element ) );
	}

	private static String getErrorMessage( String element ) {
		return String.format( MESSAGE, element );
	}
}
