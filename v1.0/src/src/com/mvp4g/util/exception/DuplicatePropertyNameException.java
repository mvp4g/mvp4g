/**
 * 
 */
package com.mvp4g.util.exception;


/**
 * An error produced when an Mvp4g configuration element has duplicate property names. 
 * 
 * @author javier
 *
 */
public class DuplicatePropertyNameException extends InvalidMvp4gConfigurationException {

	private static final long serialVersionUID = -673723383230350358L;

	private static final String MESSAGE = "Element '%s' has duplicate attributes with name '%s'"; 
	
	public DuplicatePropertyNameException(String elementName, String propertyName) {
		super(getErrorMessage(elementName, propertyName));
	}

	private static String getErrorMessage(String elementName, String propertyName) {
		return String.format( MESSAGE, elementName, propertyName );
	}
	
}
