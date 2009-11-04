/**
 * 
 */
package com.mvp4g.util.exception;

/**
 * Signals an anomaly associated with the mvp4g-conf.xml file.
 * 
 * @author javier
 * 
 */
public class InvalidMvp4gConfigurationException extends Exception {

	private static final long serialVersionUID = 1087416699479450919L;

	public InvalidMvp4gConfigurationException( String message ) {
		super( message );
	}

}
