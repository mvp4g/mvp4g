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

/**
 * A simple Mvp4g configuration element.</p>
 * 
 * A simple element consists of two attributes:
 * 
 * <ul>
 * <li/>A <i>name</i> uniquely identifying the element within the configuration;
 * <li/>A <i>class</i> specifying the fully qualified Java class for the element.
 * </ul>
 * 
 * @author javier
 * 
 */
public class SimpleMvp4gElement extends Mvp4gElement {

	protected ClassResolver resolver = new ClassResolver();

	public SimpleMvp4gElement() {
		super( "simple" );
	}

	public SimpleMvp4gElement( String tagName ) {
		super( tagName );
	}

	public void setName( String name ) throws DuplicatePropertyNameException {
		setProperty( "name", name );
	}

	public String getName() {
		return getProperty( "name" );
	}

	public void setClassName( String className ) throws DuplicatePropertyNameException {
		setProperty( "class", className );
	}

	public String getClassName() {
		String packageName = getProperty( "package" );
		String className = getProperty( "class" );
		return resolver.getClassNameFrom( packageName, className );
	}

	@Override
	public String toString() {
		return "[" + getName() + " : " + getClassName() + "]";
	}

	@Override
	public String getUniqueIdentifierName() {
		return "name";
	}
}
