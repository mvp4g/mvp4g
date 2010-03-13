/*
 * Copyright 2009 Pierre-Laurent Coirier
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
