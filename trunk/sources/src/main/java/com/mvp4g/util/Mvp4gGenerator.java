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
import com.google.gwt.core.client.RunAsyncCallback;
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
import com.mvp4g.client.AbstractMvp4gSplitter;
import com.mvp4g.client.Mvp4gEventPasser;
import com.mvp4g.client.Mvp4gException;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.Mvp4gRunAsync;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.ext.Generator#generate(com.google.gwt.core.ext.TreeLogger,
	 * com.google.gwt.core.ext.GeneratorContext, java.lang.String)
	 */
	@Override
	public String generate( TreeLogger logger, GeneratorContext context, String typeName ) throws UnableToCompleteException {

		Date start = new Date();

		String generatedClassQualifiedName;

		try {
			TypeOracle typeOracle = context.getTypeOracle();

			JClassType module = typeOracle.findType( typeName );
			if ( module == null ) {
				logger.log( TreeLogger.ERROR, "Unable to find metadata for type '" + typeName + "'", null );
				throw new UnableToCompleteException();
			}

			@SuppressWarnings( "unchecked" )
			Map<Class<? extends Annotation>, List<JClassType>> scanResult = AnnotationScanner.scan( logger, typeOracle, new Class[] {
					Presenter.class, History.class, Events.class, Service.class, EventHandler.class } );

			Mvp4gConfiguration configuration = new Mvp4gConfiguration( logger, context );
			String[] propertiesValues = configuration.load( module, scanResult );

			String suffix = getSuffix( propertiesValues );

			SourceWriter sourceWriter = getSourceWriter( logger, context, module, suffix );

			generatedClassQualifiedName = module.getParameterizedQualifiedSourceName() + suffix;

			if ( sourceWriter != null ) {
				logger.log( TreeLogger.INFO, "Generating source for " + generatedClassQualifiedName + " ", null );
				Mvp4gConfigurationFileWriter writer = new Mvp4gConfigurationFileWriter( sourceWriter, configuration );
				writer.writeConf();
				sourceWriter.commit( logger );
			}

		} catch ( InvalidMvp4gConfigurationException e ) {
			logger.log( TreeLogger.ERROR, e.getMessage(), e );
			throw new UnableToCompleteException();
		}

		Date end = new Date();

		logger.log( TreeLogger.INFO, "Mvp4g Compilation: " + ( end.getTime() - start.getTime() ) + "ms." );

		return generatedClassQualifiedName;
	}

	private String getSuffix( String[] propertiesValues ) {
		if ( ( propertiesValues == null ) || ( propertiesValues.length == 0 ) ) {
			return "Impl";
		} else {
			StringBuilder builder = new StringBuilder( propertiesValues.length * 200 );
			for ( String propertyValue : propertiesValues ) {
				builder.append( propertyValue );
			}

			//'-' is not a valid character for java class name
			return ( "Impl_" + builder.toString().hashCode() ).replace( "-", "A" );
		}
	}

	private SourceWriter getSourceWriter( TreeLogger logger, GeneratorContext context, JClassType originalType, String suffix )
			throws UnableToCompleteException {

		String packageName = originalType.getPackage().getName();
		String originalClassName = originalType.getSimpleSourceName();
		String generatedClassName = originalClassName + suffix;

		logger.log( TreeLogger.INFO, "Generating writer for " + packageName + "." + generatedClassName, null );

		PrintWriter printWriter = context.tryCreate( logger, packageName, generatedClassName );
		if ( printWriter == null ) {
			return null;
		}

		ClassSourceFileComposerFactory classFactory = new ClassSourceFileComposerFactory( packageName, generatedClassName );

		classFactory.addImplementedInterface( originalType.getName() );
		String[] classesToImport = getClassesToImport();
		for ( String classToImport : classesToImport ) {
			classFactory.addImport( classToImport );
		}

		return classFactory.createSourceWriter( context, printWriter );
	}

	String[] getClassesToImport() {
		return new String[] { PlaceService.class.getName(), GWT.class.getName(), com.google.gwt.user.client.History.class.getName(),
				ServiceDefTarget.class.getName(), PresenterInterface.class.getName(), EventBus.class.getName(), Mvp4gException.class.getName(),
				HistoryConverter.class.getName(), Mvp4gEventPasser.class.getName(), Mvp4gModule.class.getName(), GinModules.class.getName(),
				Ginjector.class.getName(), BaseEventBus.class.getName(), EventFilter.class.getName(), EventHandlerInterface.class.getName(),
				List.class.getName(), NavigationEventCommand.class.getName(), NavigationConfirmationInterface.class.getName(),
				AbstractMvp4gSplitter.class.getName(), RunAsyncCallback.class.getName(), Mvp4gRunAsync.class.getName() };
	}

}
