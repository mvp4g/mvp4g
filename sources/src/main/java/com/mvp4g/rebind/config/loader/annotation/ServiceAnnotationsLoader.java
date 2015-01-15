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
package com.mvp4g.rebind.config.loader.annotation;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.Service;
import com.mvp4g.rebind.config.Mvp4gConfiguration;
import com.mvp4g.rebind.config.element.ServiceElement;
import com.mvp4g.rebind.exception.loader.Mvp4gAnnotationException;

/**
 * A class responsible for loading information contained in <code>Services</code> annotation.
 * 
 * @author plcoirier
 * 
 */
public class ServiceAnnotationsLoader extends Mvp4gAnnotationsLoader<Service> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.rebind.config.loader.annotation.Mvp4gAnnotationsLoader#loadElement
	 * (com.google.gwt.core.ext.typeinfo.JClassType, java.lang.annotation.Annotation,
	 * com.mvp4g.rebind.config.Mvp4gConfiguration)
	 */
	@Override
	protected void loadElement( JClassType c, Service annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {

		String className = c.getQualifiedSourceName();

		String serviceName = buildElementNameIfNeeded( annotation.name(), className, "" );
		String path = annotation.path();
		Class<?> generatedClass = annotation.generatedClass();
		ServiceElement service = new ServiceElement();
		service.setName( serviceName );
		service.setClassName( className );
		if ( ( path != null ) && ( path.length() > 0 ) ) {
			service.setPath( annotation.path() );
		}
		if ( !Void.class.equals( generatedClass ) ) {
			service.setGeneratedClassName( generatedClass.getCanonicalName() );
		}

		addElement( configuration.getServices(), service, c, null );

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.mvp4g.rebind.config.loader.annotation.Mvp4gAnnotationsLoader#
	 * getMandatoryInterfaceName()
	 */
	@Override
	protected String getMandatoryInterfaceName() {
		return Object.class.getCanonicalName();
	}

}
