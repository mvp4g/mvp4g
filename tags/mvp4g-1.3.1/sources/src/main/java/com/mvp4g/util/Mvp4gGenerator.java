/*
 * Copyright 2010 Pierre-Laurent Coirier
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.mvp4g.client.Mvp4gEventPasser;
import com.mvp4g.client.Mvp4gException;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.EventHandler;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.History;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.annotation.Service;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventFilter;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.history.NavigationConfirmationInterface;
import com.mvp4g.client.history.NavigationEventCommand;
import com.mvp4g.client.history.PlaceService;
import com.mvp4g.client.presenter.PresenterInterface;
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
		TypeOracle typeOracle = context.getTypeOracle();
		JClassType originalType = typeOracle.findType( typeName );

		if ( sourceWriter != null ) {
			writeClass( originalType, logger, context );
			sourceWriter.commit( logger );
		}

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
		classFactory.addImport( PlaceService.class.getName() );		
		classFactory.addImport( GWT.class.getName() );
		classFactory.addImport( com.google.gwt.user.client.History.class.getName() );
		classFactory.addImport( ServiceDefTarget.class.getName() );
		classFactory.addImport( PresenterInterface.class.getName() );
		classFactory.addImport( EventBus.class.getName() );
		classFactory.addImport( Mvp4gException.class.getName() );
		classFactory.addImport( HistoryConverter.class.getName() );
		classFactory.addImport( Mvp4gEventPasser.class.getName() );
		classFactory.addImport( Mvp4gModule.class.getName() );
		classFactory.addImport( GinModules.class.getName() );
		classFactory.addImport( Ginjector.class.getName() );
		classFactory.addImport( BaseEventBus.class.getName() );
		classFactory.addImport( EventFilter.class.getName() );		
		classFactory.addImport( EventHandlerInterface.class.getName() );
		classFactory.addImport( List.class.getName() );
		classFactory.addImport( NavigationEventCommand.class.getName() );
		classFactory.addImport( NavigationConfirmationInterface.class.getName() );

		PrintWriter printWriter = context.tryCreate( logger, packageName, generatedClassName );
		if ( printWriter == null ) {
			return null;
		}

		return classFactory.createSourceWriter( context, printWriter );
	}

	@SuppressWarnings( "unchecked" )
	private void writeClass( JClassType module, TreeLogger logger, GeneratorContext context ) throws UnableToCompleteException {

		try {
			TypeOracle oracle = context.getTypeOracle();

			Map<Class<? extends Annotation>, List<JClassType>> scanResult = AnnotationScanner.scan( logger, oracle, new Class[] { Presenter.class,
					History.class, Events.class, Service.class, EventHandler.class } );

			Mvp4gConfiguration configuration = new Mvp4gConfiguration( logger, oracle );
			configuration.load( module, scanResult );

			Mvp4gConfigurationFileWriter writer = new Mvp4gConfigurationFileWriter( sourceWriter, configuration );
			writer.writeConf();
		} catch ( InvalidMvp4gConfigurationException e ) {
			logger.log( TreeLogger.ERROR, e.getMessage(), e );
			throw new UnableToCompleteException();
		}

	}
}
