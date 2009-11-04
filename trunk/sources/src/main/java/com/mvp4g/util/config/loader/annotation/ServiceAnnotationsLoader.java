package com.mvp4g.util.config.loader.annotation;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.Service;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;

public class ServiceAnnotationsLoader extends Mvp4gAnnotationsLoader<Service> {

	@Override
	protected void loadElement( JClassType c, Service annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {

		String className = c.getQualifiedSourceName();

		String serviceName = buildElementNameIfNeeded( annotation.name(), className, "" );

		ServiceElement service = new ServiceElement();
		try {
			service.setName( serviceName );
			service.setClassName( className );
			service.setPath( annotation.path() );
		} catch ( DuplicatePropertyNameException e ) {
			//setters are only called once, so this error can't occur.
		}

		addElement( configuration.getServices(), service, c, null );

	}

}
