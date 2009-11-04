package com.mvp4g.util.config.loader.annotation;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;

public class PresenterAnnotationsLoader extends Mvp4gAnnotationsWithServiceLoader<Presenter> {

	@Override
	Mvp4gWithServicesElement loadElementWithServices( JClassType c, Presenter annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {

		String className = c.getQualifiedSourceName();
		String viewName = buildElementNameIfNeeded( annotation.viewName(), className, "View" );
		String presenterName = buildElementNameIfNeeded( annotation.name(), className, "" ); 

		PresenterElement presenter = new PresenterElement();
		try {
			presenter.setName( presenterName );
			presenter.setClassName( className );
			presenter.setView( viewName );
		} catch ( DuplicatePropertyNameException e ) {
			//setters are only called once, so this error can't occur.
		}
		
		addElement( configuration.getPresenters(), presenter, c, null );

		ViewElement view = new ViewElement();
		try {
			view.setClassName( annotation.view().getName() );
			view.setName( viewName );
		} catch ( DuplicatePropertyNameException e ) {
			//setters are only called once, so this error can't occur.
		}
		

		addElement( configuration.getViews(), view, c, null );
		
		return presenter;
	}
}
