package com.mvp4g.util.config.loader.annotation;

import com.mvp4g.client.annotation.Service;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.ServiceElement;

public class ServiceAnnotationsLoader extends Mvp4gAnnotationsLoader<Service> {

	@Override
	protected void loadElement( Class<?> c, Service annotation, Mvp4gConfiguration configuration ) {
		String serviceName = buildElementNameIfNeeded( annotation.name(), c, "" );

		ServiceElement service = new ServiceElement();
		service.setName( serviceName );
		service.setClassName( c.getName() );
		service.setPath( annotation.path() );

		addElement( configuration.getServices(), service );
			
	}

}
