package com.mvp4g.util.config.loader.annotation;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ViewElement;

public class PresenterAnnotationsLoader extends Mvp4gAnnotationsWithServiceLoader<Presenter> {

	@Override
	Mvp4gWithServicesElement loadElementWithServices( Class<?> c, Presenter annotation, Mvp4gConfiguration configuration ) {

		String viewName = buildElementNameIfNeeded( annotation.viewName(), c, "View" );
		String presenterName = buildElementNameIfNeeded( annotation.name(), c, "" ); 

		PresenterElement presenter = new PresenterElement();
		presenter.setName( presenterName );
		presenter.setClassName( c.getName() );
		presenter.setView( viewName );

		addElement( configuration.getPresenters(), presenter );

		ViewElement view = new ViewElement();
		view.setClassName( annotation.view().getName() );
		view.setName( viewName );

		addElement( configuration.getViews(), view );
		
		return presenter;
	}
}
