/**
 * 
 */
package com.mvp4g.util.exception;

/**
 * Error triggered when two or more Mvp4g elements share the same textual identifier.
 * 
 * @author javier
 * 
 */
public class NonUniqueIdentifierException extends InvalidMvp4gConfigurationException {

	private static final long serialVersionUID = 3388673072418851036L;

	private static final String MESSAGE = "Element identifier '%s' is not globally unique.";

	public NonUniqueIdentifierException( String elementId ) {
		super( getErrorMessage( elementId ) );
	}

	private static String getErrorMessage( String elementId ) {
		return String.format( MESSAGE, elementId );
	}
}
