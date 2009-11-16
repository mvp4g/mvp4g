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
package com.mvp4g.util;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.History;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.annotation.Service;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;

/**
 * Class uses to create the implementation class of Mvp4gStarter
 * 
 * @author plcoirier
 * 
 */
public class Mvp4gGenerator extends Generator {

	private SourceWriter sourceWriter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.ext.Generator#generate(com.google.gwt.core.ext.TreeLogger,
	 * com.google.gwt.core.ext.GeneratorContext, java.lang.String)
	 */
	@Override
	public String generate( TreeLogger logger, GeneratorContext context, String typeName ) throws UnableToCompleteException {

		Date start = new Date();

		String generatedClassQualifiedName = createClass( logger, context, typeName );

		if ( generatedClassQualifiedName == null ) {
			throw new UnableToCompleteException();
		}

		Date end = new Date();

		logger.log( TreeLogger.INFO, "Mvp4g Compilation: " + ( end.getTime() - start.getTime() ) + "ms." );

		return generatedClassQualifiedName;
	}

	private String createClass( TreeLogger logger, GeneratorContext context, String typeName ) throws UnableToCompleteException {
		sourceWriter = getSourceWriter( logger, context, typeName );
		if ( sourceWriter != null ) {
			writeClass( logger, context );
			sourceWriter.commit( logger );
		}

		TypeOracle typeOracle = context.getTypeOracle();
		JClassType originalType = typeOracle.findType( typeName );
		return originalType.getParameterizedQualifiedSourceName() + "Impl";
	}

	private SourceWriter getSourceWriter( TreeLogger logger, GeneratorContext context, String typeName ) throws UnableToCompleteException {
		logger.log( TreeLogger.INFO, "Generating source for " + typeName, null );

		TypeOracle typeOracle = context.getTypeOracle();
		JClassType originalType = typeOracle.findType( typeName );
		if ( originalType == null ) {
			logger.log( TreeLogger.ERROR, "Unable to find metadata for type '" + typeName + "'", null );
			throw new UnableToCompleteException();
		}

		logger.log( TreeLogger.INFO, "Generating source for " + originalType.getQualifiedSourceName(), null );

		String packageName = originalType.getPackage().getName();
		String originalClassName = originalType.getSimpleSourceName();
		String generatedClassName = originalClassName + "Impl";

		ClassSourceFileComposerFactory classFactory = new ClassSourceFileComposerFactory( packageName, generatedClassName );
		classFactory.addImplementedInterface( originalType.getName() );
		classFactory.addImport( "com.mvp4g.client.history.PlaceService" );
		classFactory.addImport( "com.google.gwt.user.client.ui.RootPanel" );
		classFactory.addImport( "com.google.gwt.core.client.GWT" );
		classFactory.addImport( "com.google.gwt.user.client.History" );
		classFactory.addImport( "com.google.gwt.user.client.rpc.ServiceDefTarget" );

		PrintWriter printWriter = context.tryCreate( logger, packageName, generatedClassName );
		if ( printWriter == null ) {
			return null;
		}

		return classFactory.createSourceWriter( context, printWriter );
	}

	@SuppressWarnings( "unchecked" )
	private void writeClass( TreeLogger logger, GeneratorContext context ) throws UnableToCompleteException {

		try {
			TypeOracle oracle = context.getTypeOracle();

			Map<Class<? extends Annotation>, List<JClassType>> scanResult = AnnotationScanner.scan( logger, oracle, new Class[] { Presenter.class, History.class, Events.class, Service.class } );

			Mvp4gConfiguration configuration = new Mvp4gConfiguration( logger, oracle );
			configuration.load( "mvp4g-conf.xml", scanResult );

			Mvp4gConfigurationFileWriter writer = new Mvp4gConfigurationFileWriter( sourceWriter, configuration );
			writer.writeConf();
		} catch ( InvalidMvp4gConfigurationException e ) {
			logger.log( TreeLogger.ERROR, e.getMessage(), e );
			throw new UnableToCompleteException();
		}

	}
}
