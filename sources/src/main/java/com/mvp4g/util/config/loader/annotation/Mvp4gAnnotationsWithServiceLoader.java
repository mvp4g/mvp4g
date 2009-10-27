package com.mvp4g.util.config.loader.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.InjectedElement;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;

public abstract class Mvp4gAnnotationsWithServiceLoader<T extends Annotation> extends Mvp4gAnnotationsLoader<T> {

	@Override
	protected void loadElement( Class<?> c, T annotation, Mvp4gConfiguration configuration ) {
		Mvp4gWithServicesElement element = loadElementWithServices( c, annotation, configuration );

		//check if any services need to be injected
		Class<?>[] params = null;
		String className = null;
		String serviceName = null;
		for ( Method m : c.getMethods() ) {
			if ( m.getAnnotation( InjectService.class ) != null ) {
				params = m.getParameterTypes();
				if ( params.length != 1 ) {
					String err = buildErrorMessage( element ) + "only setter method with one parameter can be used to inject a service";
					throw new InvalidMvp4gConfigurationException( err );
				}
				className = params[0].getName();
				if ( !className.endsWith( "Async" ) ) {
					String err = buildErrorMessage( element ) + "only instance of Asynchron service class can be injected.";
					throw new InvalidMvp4gConfigurationException( err );
				}
				className = className.substring( 0 , className.indexOf( "Async" ));
				serviceName = getServiceName( configuration, className );
				
				element.getInjectedServices().add( new InjectedElement(serviceName, m.getName()) );
			}
		}

	}

	private String buildErrorMessage( Mvp4gWithServicesElement element ) {
		return element.getTagName() + " " + element.getUniqueIdentifier() + ": ";
	}

	private String getServiceName(Mvp4gConfiguration configuration, String serviceClassName){
		String serviceName = null;
		Set<ServiceElement> services = configuration.getServices();
		for(ServiceElement service : services){
			if(serviceClassName.equals( service.getClassName())){
				serviceName = service.getName();
			}
		}
		if(serviceName == null){
			serviceName = serviceClassName.replace( '.', '_' );
			ServiceElement service = new ServiceElement();
			service.setClassName( serviceClassName );
			service.setName( serviceName );
			services.add( service );
		}
		return serviceName;
	}

	abstract Mvp4gWithServicesElement loadElementWithServices( Class<?> c, T annotation, Mvp4gConfiguration configuration );

}
