package com.mvp4g.util.config.loader.annotation;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.Service;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.ServiceElement;

public class ServiceAnnotationsLoader extends Mvp4gAnnotationsLoader<Service> {

	@Override
	protected void loadElement( JClassType c, Service annotation, Mvp4gConfiguration configuration ) {
		
		String className = c.getQualifiedSourceName();
		
		String serviceName = buildElementNameIfNeeded( annotation.name(), className, "" );

		ServiceElement service = new ServiceElement();
		service.setName( serviceName );
		service.setClassName( className );
		service.setPath( annotation.path() );

		addElement( configuration.getServices(), service );
			
	}

}
