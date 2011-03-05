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

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.EventHandler;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.EventHandlerElement;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;

/**
 * A class responsible for loading information contained in <code>EventHandler</code> annotation.
 * 
 * @author Dan Persa
 * 
 */
public class EventHandlerAnnotationsLoader extends Mvp4gAnnotationsWithServiceLoader<EventHandler> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.util.config.loader.annotation.Mvp4gAnnotationsWithServiceLoader
	 * #loadElementWithServices (com.google.gwt.core.ext.typeinfo.JClassType,
	 * java.lang.annotation.Annotation, com.mvp4g.util.config.Mvp4gConfiguration)
	 */
	@Override
	Mvp4gWithServicesElement loadElementWithServices( JClassType c, EventHandler annotation, Mvp4gConfiguration configuration )
			throws Mvp4gAnnotationException {

		String className = c.getQualifiedSourceName();

		if ( c.getAnnotation( Presenter.class ) != null ) {
			String err = "You can't annotate a class with @Presenter and @EventHandler: " + className + ".";
			throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
		}

		String eventHandlerName = buildElementNameIfNeeded( annotation.name(), className, "" );

		EventHandlerElement eventHandler = new EventHandlerElement();
		try {
			eventHandler.setName( eventHandlerName );
			eventHandler.setClassName( className );
			eventHandler.setMultiple( Boolean.toString( annotation.multiple() ) );
		} catch ( DuplicatePropertyNameException e ) {
			// setters are only called once, so this error can't occur.
		}

		addElement( configuration.getEventHandlers(), eventHandler, c, null );

		return eventHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.mvp4g.util.config.loader.annotation.Mvp4gAnnotationsLoader#
	 * getMandatoryInterfaceName()
	 */
	@Override
	protected String getMandatoryInterfaceName() {
		return EventHandlerInterface.class.getCanonicalName();
	}
}
