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
package com.mvp4g.util.config.loader.annotation;

import java.lang.annotation.Annotation;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.InjectedElement;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;

/**
 * Class responsible for loading information contained in annotations from classes where methods can
 * contain <code>InjectService</code> annotation.
 * 
 * @author plcoirier
 * 
 */
public abstract class Mvp4gAnnotationsWithServiceLoader<T extends Annotation> extends Mvp4gAnnotationsLoader<T> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.util.config.loader.annotation.Mvp4gAnnotationsLoader#loadElement
	 * (com.google.gwt .core.ext.typeinfo.JClassType, java.lang.annotation.Annotation,
	 * com.mvp4g.util.config.Mvp4gConfiguration)
	 */
	@Override
	protected void loadElement( JClassType c, T annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {
		Mvp4gWithServicesElement element = loadElementWithServices( c, annotation, configuration );

		// check if any services need to be injected
		JParameter[] params = null;
		String className = null;
		String serviceName = null;
		InjectService serviceAnnotation = null;
		for ( JMethod m : c.getOverridableMethods() ) {
			serviceAnnotation = m.getAnnotation( InjectService.class );
			if ( serviceAnnotation != null ) {
				if ( !m.isPublic() ) {
					String err = "Only public setter method can be used to inject a service.";
					throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), m.getName(), err );
				}
				params = m.getParameters();
				if ( params.length != 1 ) {
					String err = "Only setter method with one parameter can be used to inject a service";
					throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), m.getName(), err );
				}

				serviceName = serviceAnnotation.serviceName();

				if ( ( serviceName == null ) || ( serviceName.length() == 0 ) ) {
					className = params[0].getType().getQualifiedSourceName();
					serviceName = getServiceName( configuration, className );
				}
				element.getInjectedServices().add( new InjectedElement( serviceName, m.getName() ) );
			}
		}

	}

	/**
	 * Retrieve the name of service element with the given service class name. If no service with
	 * the given class name is found, create one.
	 * 
	 * @param configuration
	 *            configuration containing loaded elements of the application
	 * @param serviceClassName
	 *            class name of the service element
	 * 
	 * @return name of the service element (either found or create)
	 */
	private String getServiceName( Mvp4gConfiguration configuration, String generatedClassName ) {

		String serviceName = null;
		for ( ServiceElement service : configuration.getServices() ) {
			if ( generatedClassName.equals( service.getGeneratedClassName() ) ) {
				serviceName = service.getName();
				break;
			}
		}

		if ( serviceName == null ) {
			serviceName = generatedClassName.replace( '.', '_' );
			ServiceElement service = new ServiceElement();
			try {

				service.setClassName( generatedClassName.substring( 0, generatedClassName.indexOf( "Async" ) ) );
				service.setName( serviceName );
			} catch ( DuplicatePropertyNameException e ) {
				// setters are only called once, so this error can't occur.
			}
			configuration.getServices().add( service );
		}
		return serviceName;
	}

	/**
	 * Load one class annoted with the annotation
	 * 
	 * @param c
	 *            class annoted with the annotation
	 * @param annotation
	 *            annotation of the class
	 * @param configuration
	 *            configuration containing loaded elements of the application
	 * @throws Mvp4gAnnotationException
	 *             if annotation is not used correctly
	 */
	abstract Mvp4gWithServicesElement loadElementWithServices( JClassType c, T annotation, Mvp4gConfiguration configuration )
			throws Mvp4gAnnotationException;

}
