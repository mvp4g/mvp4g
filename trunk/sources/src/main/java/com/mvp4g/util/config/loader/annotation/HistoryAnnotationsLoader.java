package com.mvp4g.util.config.loader.annotation;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.History;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;

public class HistoryAnnotationsLoader extends Mvp4gAnnotationsWithServiceLoader<History> {

	@Override
	Mvp4gWithServicesElement loadElementWithServices( JClassType c, History annotation, Mvp4gConfiguration configuration ) {

		String className = c.getQualifiedSourceName();
		String historyName = buildElementNameIfNeeded( annotation.name(), className, "" );

		HistoryConverterElement historyConverter = new HistoryConverterElement();
		historyConverter.setName( historyName );
		historyConverter.setClassName( className );

		addElement( configuration.getHistoryConverters(), historyConverter );

		return historyConverter;
	}
}
