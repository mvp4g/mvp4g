package com.mvp4g.util.config.loader.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.Mvp4gElement;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;

public abstract class Mvp4gAnnotationsLoader<T extends Annotation> {

	@SuppressWarnings( "unchecked" )
	public void load( List<Class<?>> annotedClasses, Mvp4gConfiguration configuration ) {
		for ( Class<?> c : annotedClasses ) {
			loadElement( c, c.getAnnotation( (Class<T>)( (ParameterizedType)getClass().getGenericSuperclass() ).getActualTypeArguments()[0] ),
					configuration );
		}
	}

	protected <E extends Mvp4gElement> void addElement( Set<E> loadedElements, E element ) throws InvalidMvp4gConfigurationException {
		checkForDuplicates( loadedElements, element );
		loadedElements.add( element );
	}

	protected String buildElementNameIfNeeded( String currentName, Class<?> c, String suffix ) {
		if ( ( currentName == null ) || ( currentName.length() == 0 ) ) {
			return buildElementName( c, suffix );
		} else {
			return currentName;
		}
	}

	protected String buildElementName( Class<?> c, String suffix ) {
		return c.getName().replace( '.', '_' ) + suffix;
	}

	private <E extends Mvp4gElement> void checkForDuplicates( Set<E> loadedElements, E element ) throws InvalidMvp4gConfigurationException {
		if ( loadedElements.contains( element ) ) {
			String err = "Duplicate " + element.getTagName() + " identified by " + "'" + element.getUniqueIdentifierName()
					+ "' found in configuration file.";
			throw new InvalidMvp4gConfigurationException( err );
		}
	}

	abstract protected void loadElement( Class<?> c, T annotation, Mvp4gConfiguration configuration );

}
