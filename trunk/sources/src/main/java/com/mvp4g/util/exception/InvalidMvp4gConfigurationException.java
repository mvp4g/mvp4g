/**
 * 
 */
package com.mvp4g.util.exception;

import com.mvp4g.client.Mvp4gException;

/**
 * Signals an anomaly associated with the mvp4g-conf.xml file.
 * 
 * @author javier
 * 
 */
public class InvalidMvp4gConfigurationException extends Mvp4gException {

	private static final long serialVersionUID = 1087416699479450919L;

	public InvalidMvp4gConfigurationException( String message ) {
		super( message );
	}

}
