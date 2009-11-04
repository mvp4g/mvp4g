package com.mvp4g.util.config.loader.annotation;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.History;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;

/**
 * A class responsible for loading information contained in <code>History</code> annotation.
 * 
 * @author plcoirier
 * 
 */
public class HistoryAnnotationsLoader extends Mvp4gAnnotationsWithServiceLoader<History> {

	/*
	 * (non-Javadoc)
	 * @see com.mvp4g.util.config.loader.annotation.Mvp4gAnnotationsWithServiceLoader#loadElementWithServices(com.google.gwt.core.ext.typeinfo.JClassType, java.lang.annotation.Annotation, com.mvp4g.util.config.Mvp4gConfiguration)
	 */
	@Override
	Mvp4gWithServicesElement loadElementWithServices( JClassType c, History annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {

		String className = c.getQualifiedSourceName();
		String historyName = buildElementNameIfNeeded( annotation.name(), className, "" );

		HistoryConverterElement historyConverter = new HistoryConverterElement();
		try {
			historyConverter.setName( historyName );
			historyConverter.setClassName( className );
		} catch ( DuplicatePropertyNameException e ) {
			//setters are only called once, so this error can't occur.
		}

		addElement( configuration.getHistoryConverters(), historyConverter, c, null );

		return historyConverter;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mvp4g.util.config.loader.annotation.Mvp4gAnnotationsLoader#getMandatoryInterfaceName()
	 */
	@Override
	protected String getMandatoryInterfaceName() {
		return HistoryConverter.class.getName();
	}
}
