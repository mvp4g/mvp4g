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
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;

public abstract class Mvp4gAnnotationsWithServiceLoader<T extends Annotation> extends Mvp4gAnnotationsLoader<T> {

	@Override
	protected void loadElement( JClassType c, T annotation, Mvp4gConfiguration configuration ) {
		Mvp4gWithServicesElement element = loadElementWithServices( c, annotation, configuration );

		//check if any services need to be injected
		JParameter[] params = null;
		String className = null;
		String serviceName = null;
		for ( JMethod m : c.getOverridableMethods() ) {
			if ( m.getAnnotation( InjectService.class ) != null ) {
				if (!m.isPublic() ) {
					String err = buildErrorMessage( element ) + "only public setter method can be used to inject a service.";
					throw new InvalidMvp4gConfigurationException( err );
				}
				params = m.getParameters();
				if ( params.length != 1 ) {
					String err = buildErrorMessage( element ) + "only setter method with one parameter can be used to inject a service";
					throw new InvalidMvp4gConfigurationException( err );
				}
				className = params[0].getType().getQualifiedSourceName();
				if ( !className.endsWith( "Async" ) ) {
					String err = buildErrorMessage( element ) + "only instance of Asynchron service class can be injected.";
					throw new InvalidMvp4gConfigurationException( err );
				}
				className = className.substring( 0, className.indexOf( "Async" ) );
				serviceName = getServiceName( configuration, className );

				element.getInjectedServices().add( new InjectedElement( serviceName, m.getName() ) );
			}
		}

	}

	private String buildErrorMessage( Mvp4gWithServicesElement element ) {
		return element.getTagName() + " " + element.getUniqueIdentifier() + ": ";
	}

	private String getServiceName( Mvp4gConfiguration configuration, String serviceClassName ) {
		String serviceName = getElementName( configuration.getServices(), serviceClassName );
		if ( serviceName == null ) {
			serviceName = serviceClassName.replace( '.', '_' );
			ServiceElement service = new ServiceElement();
			service.setClassName( serviceClassName );
			service.setName( serviceName );
			configuration.getServices().add( service );
		}
		return serviceName;
	}

	abstract Mvp4gWithServicesElement loadElementWithServices( JClassType c, T annotation, Mvp4gConfiguration configuration );

}
