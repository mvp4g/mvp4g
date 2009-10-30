package com.mvp4g.util.config.loader.annotation;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ViewElement;

public class PresenterAnnotationsLoader extends Mvp4gAnnotationsWithServiceLoader<Presenter> {

	@Override
	Mvp4gWithServicesElement loadElementWithServices( JClassType c, Presenter annotation, Mvp4gConfiguration configuration ) {

		String className = c.getQualifiedSourceName();
		String viewName = buildElementNameIfNeeded( annotation.viewName(), className, "View" );
		String presenterName = buildElementNameIfNeeded( annotation.name(), className, "" ); 

		PresenterElement presenter = new PresenterElement();
		presenter.setName( presenterName );
		presenter.setClassName( className );
		presenter.setView( viewName );

		addElement( configuration.getPresenters(), presenter );

		ViewElement view = new ViewElement();
		view.setClassName( annotation.view().getName() );
		view.setName( viewName );

		addElement( configuration.getViews(), view );
		
		return presenter;
	}
}
