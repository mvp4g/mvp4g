package com.mvp4g.util.config.loader.annotation;

import com.mvp4g.client.annotation.History;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;

public class HistoryAnnotationsLoader extends Mvp4gAnnotationsWithServiceLoader<History> {

	@Override
	Mvp4gWithServicesElement loadElementWithServices( Class<?> c, History annotation, Mvp4gConfiguration configuration ) {

		String historyName = buildElementNameIfNeeded( annotation.name(), c, "" );

		HistoryConverterElement historyConverter = new HistoryConverterElement();
		historyConverter.setName( historyName );
		historyConverter.setClassName( c.getName() );

		addElement( configuration.getHistoryConverters(), historyConverter );

		return historyConverter;
	}
}
