package com.mvp4g.util.exception.loader;

import com.mvp4g.util.config.element.Mvp4gElement;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;

public class Mvp4gXmlException extends InvalidMvp4gConfigurationException {

	private String xmlFilePath = null;
	private String tag = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933352484580972114L;

	public Mvp4gXmlException( Mvp4gElement e, String message ) {
		super( message );
		if ( e != null ) {
			tag = e.getTagName() + " " + e.getUniqueIdentifier();
		}
	}

	@Override
	public String getMessage() {

		StringBuilder builder = new StringBuilder( 100 );
		if ( ( xmlFilePath != null ) && ( xmlFilePath.length() > 0 ) ) {
			builder.append( xmlFilePath );
			builder.append( ": " );
		}
		if ( ( tag != null ) && ( tag.length() > 0 ) ) {
			builder.append( tag );
			builder.append( ": " );
		}
		builder.append( super.getMessage() );

		return builder.toString();
	}

	public void setXmlFilePath( String xmlFilePath ) {
		this.xmlFilePath = xmlFilePath;
	}

}
