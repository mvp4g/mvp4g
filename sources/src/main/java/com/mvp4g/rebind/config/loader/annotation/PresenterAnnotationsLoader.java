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

import java.util.Set;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.Mvp4gSplitter;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.PresenterInterface;
import com.mvp4g.rebind.config.Mvp4gConfiguration;
import com.mvp4g.rebind.config.element.PresenterElement;
import com.mvp4g.rebind.config.element.ViewElement;
import com.mvp4g.rebind.exception.loader.Mvp4gAnnotationException;

/**
 * A class responsible for loading information contained in <code>Presenter</code> annotation.
 * 
 * @author plcoirier
 * 
 */
public class PresenterAnnotationsLoader extends AbstractHandlerAnnotationsLoader<Presenter, PresenterElement> {

	@Override
	protected PresenterElement loadHandler( JClassType c, Presenter annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {

		String className = c.getQualifiedSourceName();
		String viewName = buildElementNameIfNeeded( annotation.viewName(), className, "View" );

		PresenterElement presenter = new PresenterElement();
		presenter.setView( viewName );

		ViewElement view = new ViewElement();
		view.setClassName( annotation.view().getCanonicalName() );
		view.setName( viewName );

		addElement( configuration.getViews(), view, c, null );

		return presenter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mvp4g.rebind.config.loader.annotation.Mvp4gAnnotationsLoader#getMandatoryInterfaceName()
	 */
	@Override
	protected String getMandatoryInterfaceName() {
		return PresenterInterface.class.getCanonicalName();
	}

	@Override
	protected Set<PresenterElement> getConfigList( Mvp4gConfiguration configuration ) {
		return configuration.getPresenters();
	}

	@Override
	protected String getAnnotationName( Presenter annotation ) {
		return annotation.name();
	}

	@Override
	protected boolean isAnnotationMultiple( Presenter annotation ) {
		return annotation.multiple();
	}

	@Override
	protected Class<? extends Mvp4gSplitter> getAnnotationSplitter( Presenter annotation ) {
		return annotation.async();
	}
}
