package com.mvp4g.util.config.loader.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.Mvp4gElement;
import com.mvp4g.util.config.element.SimpleMvp4gElement;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;

public abstract class Mvp4gAnnotationsLoader<T extends Annotation> {

	@SuppressWarnings( "unchecked" )
	public void load( List<JClassType> annotedClasses, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {
		for ( JClassType c : annotedClasses ) {
			loadElement( c, c.getAnnotation( (Class<T>)( (ParameterizedType)getClass().getGenericSuperclass() ).getActualTypeArguments()[0] ),
					configuration );
		}
	}

	protected <E extends Mvp4gElement> void addElement( Set<E> loadedElements, E element, JClassType c, JMethod m ) throws Mvp4gAnnotationException {
		checkForDuplicates( loadedElements, element, c, m );
		loadedElements.add( element );
	}

	protected String buildElementNameIfNeeded( String currentName, String className, String suffix ) {
		if ( ( currentName == null ) || ( currentName.length() == 0 ) ) {
			return buildElementName( className, suffix );
		} else {
			return currentName;
		}
	}

	protected String buildElementName( String className, String suffix ) {
		return className.replace( '.', '_' ) + suffix;
	}

	protected <E extends SimpleMvp4gElement> String getElementName( Set<E> loadedElements, String elementClassName ) {
		String elementName = null;
		for ( E element : loadedElements ) {
			if ( elementClassName.equals( element.getClassName() ) ) {
				elementName = element.getName();
				break;
			}
		}
		return elementName;
	}

	private <E extends Mvp4gElement> void checkForDuplicates( Set<E> loadedElements, E element, JClassType c, JMethod m )
			throws Mvp4gAnnotationException {
		if ( loadedElements.contains( element ) ) {
			String err = "Duplicate " + element.getTagName() + " identified by " + "'" + element.getUniqueIdentifierName()
					+ "' found in configuration file.";
			throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), ( m == null ) ? null : m.getName(), err );
		}
	}

	abstract protected void loadElement( JClassType c, T annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException;

}
