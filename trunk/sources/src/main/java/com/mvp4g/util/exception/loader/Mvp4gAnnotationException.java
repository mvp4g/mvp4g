package com.mvp4g.util.exception.loader;

import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;


public class Mvp4gAnnotationException extends InvalidMvp4gConfigurationException {

	private String className = null;
	private String methodName = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933352484580972114L;

	public Mvp4gAnnotationException( String className, String methodName, String message ) {
		super( message );
		this.className = className;
		this.methodName = methodName;
	}

	@Override
	public String getMessage() {

		StringBuilder builder = new StringBuilder( 100 );
		if ( ( className != null ) && ( className.length() > 0 ) ) {
			builder.append( className );
			builder.append( ": " );
		}
		if ( ( methodName != null ) && ( methodName.length() > 0 ) ) {
			builder.append( "Method " );
			builder.append( methodName );
			builder.append( ": " );
		}
		builder.append( super.getMessage() );

		return builder.toString();
	}

}
