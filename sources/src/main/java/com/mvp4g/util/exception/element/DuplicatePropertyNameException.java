/**
 * 
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
