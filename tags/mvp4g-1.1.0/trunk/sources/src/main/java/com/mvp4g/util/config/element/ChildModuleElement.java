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
package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

public class ChildModuleElement extends SimpleMvp4gElement {

	public ChildModuleElement() {
		super( "childModule" );
	}

	public void setEventToDisplayView( String eventToDisplayView ) throws DuplicatePropertyNameException {
		setProperty( "eventToDisplayView", eventToDisplayView );
	}

	public String getEventToDisplayView() {
		return getProperty( "eventToDisplayView" );
	}

	public void setAsync( String async ) throws DuplicatePropertyNameException {
		setProperty( "async", async );
	}

	public String getAsync() {
		String async = getProperty( "async" );
		// By default it's true
		return ( async == null ) ? "true" : getProperty( "async" );
	}

	public boolean isAsync() {
		return "true".equalsIgnoreCase( getAsync() );
	}

	public void setAutoDisplay( String autoDisplay ) throws DuplicatePropertyNameException {
		setProperty( "autoDisplay", autoDisplay );
	}

	public String getAutoDisplay() {
		String autoDisplay = getProperty( "autoDisplay" );
		// By default it's true
		return ( autoDisplay == null ) ? "true" : getProperty( "autoDisplay" );
	}

	public boolean isAutoDisplay() {
		return "true".equalsIgnoreCase( getAutoDisplay() );
	}

	public String getHistoryName() {
		return getProperty( "historyName" );
	}

	public void setHistoryName( String historyName ) throws DuplicatePropertyNameException {
		setProperty( "historyName", historyName );
	}

}
