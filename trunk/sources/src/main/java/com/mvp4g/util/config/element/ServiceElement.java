package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

/**
 * An Mvp4g Service configuration element.</p>
 * 
 * @author javier
 */
public class ServiceElement extends SimpleMvp4gElement {

	public ServiceElement() {
		super( "service" );
	}

	public void setPath( String path ) throws DuplicatePropertyNameException {
		setProperty( "path", path );
	}

	public String getPath() {
		return getProperty( "path" );
	}

	public boolean hasPath() {
		return getPath() != null;
	}

	public void setGeneratedClassName( String generatedClassName ) throws DuplicatePropertyNameException {
		setProperty( "generatedClassName", generatedClassName );
	}

	public String getGeneratedClassName() {
		String generatedClass = getProperty( "generatedClassName" );
		if ( generatedClass == null ) {
			generatedClass = getClassName() + "Async";
		} else {
			String packageName = getProperty( "package" );
			generatedClass = resolver.getClassNameFrom( packageName, generatedClass );
		}
		return generatedClass;
	}

}
