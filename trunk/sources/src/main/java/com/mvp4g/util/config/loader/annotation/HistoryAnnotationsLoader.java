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
import com.mvp4g.client.annotation.History;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;
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
	 * 
	 * @see
	 * com.mvp4g.util.config.loader.annotation.Mvp4gAnnotationsWithServiceLoader#loadElementWithServices
	 * (com.google.gwt.core.ext.typeinfo.JClassType, java.lang.annotation.Annotation,
	 * com.mvp4g.util.config.Mvp4gConfiguration)
	 */
	@Override
	Mvp4gWithServicesElement loadElementWithServices( JClassType c, History annotation, Mvp4gConfiguration configuration )
			throws Mvp4gAnnotationException {

		String className = c.getQualifiedSourceName();
		String historyName = buildElementNameIfNeeded( annotation.name(), className, "" );
		String type = annotation.type().name();

		HistoryConverterElement historyConverter = new HistoryConverterElement();
		historyConverter.setName( historyName );
		historyConverter.setClassName( className );
		historyConverter.setType( type );

		addElement( configuration.getHistoryConverters(), historyConverter, c, null );

		return historyConverter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mvp4g.util.config.loader.annotation.Mvp4gAnnotationsLoader#getMandatoryInterfaceName()
	 */
	@Override
	protected String getMandatoryInterfaceName() {
		return HistoryConverter.class.getCanonicalName();
	}
}
