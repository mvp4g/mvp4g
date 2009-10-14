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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.user.rebind.SourceWriter;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.HistoryElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;

/**
 * This class reads the mvp4g-conf.xml
 * 
 * @author plcoirier
 * 
 */
public class Mvp4gConfigurationFileReader {

	private SourceWriter sourceWriter = null;
	private TreeLogger logger = null;

	private Mvp4gConfiguration configuration = new Mvp4gConfiguration();

	//associate a presenter name with its class name
	private Map<String, String> presenterClasses = new HashMap<String, String>();

	/**
	 * Create a Mvp4gConfigurationFileReader object
	 * 
	 * @param sourceWriter
	 * @param logger
	 */
	public Mvp4gConfigurationFileReader( SourceWriter sourceWriter, TreeLogger logger ) {
		this.sourceWriter = sourceWriter;
		this.logger = logger;
	}

	/**
	 * Parses the mvp4g-conf.xml and write the information in the source writer
	 * 
	 * @throws UnableToCompleteException
	 *             exception thrown if the configuratin file is not correct
	 */
	public void writeConf() throws UnableToCompleteException {
		try {
			XMLConfiguration xmlConfig = new XMLConfiguration( "mvp4g-conf.xml" );
			sendErrorIfNull( xmlConfig, "mvp4g-conf.xml is missing" );

			loadConfiguration( xmlConfig );

			sourceWriter.println( "EventBus eventBus = new EventBus();" );

			sourceWriter.println();

			writeViews();

			sourceWriter.println();

			writeServices();

			sourceWriter.println();

			writeHistory();

			sourceWriter.println();

			writePresenters();

			sourceWriter.println();

			writeEvents();

			sourceWriter.println();

			writeStartEvent();

		} catch ( ConfigurationException e ) {
			logger.log( TreeLogger.ERROR, e.getMessage() );
			throw new UnableToCompleteException();
		}
	}

	/**
	 * Pre-loads all Mvp4g elements in the configuration file into memory.
	 * 
	 * @param xmlConfig
	 *            raw in-memory representation of mvp4g-config.xml file.
	 * 
	 * @throws UnableToCompleteException
	 *             thrown if the configuration is invalid.
	 */
	private void loadConfiguration( XMLConfiguration xmlConfig ) throws UnableToCompleteException {
		try {
			configuration.load( xmlConfig );
		} catch ( InvalidMvp4gConfigurationException imce ) {
			logger.log( TreeLogger.ERROR, imce.getMessage() );
			throw new UnableToCompleteException();
		}
	}

	/**
	 * Write the history converters included in the configuration file.
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 */
	private void writeHistory() {

		HistoryElement history = configuration.getHistory();

		if ( history != null ) {
			sourceWriter.println( "final PlaceService placeService = new PlaceService(eventBus);" );
			sourceWriter.print( "placeService.setInitEvent( \"" );
			sourceWriter.print( history.getInitEvent() );
			sourceWriter.println( "\");" );

			String name = null;

			for ( HistoryConverterElement converter : configuration.getHistoryConverters() ) {
				name = converter.getName();
				createInstance( name, converter.getClassName() );
				injectServices( name, converter.getServices() );
			}

		}

	}

	/**
	 * Write the views included in the configuration file.
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 */
	private void writeViews() {

		for ( ViewElement view : configuration.getViews() ) {
			createInstance( view.getName(), view.getClassName() );
		}
	}

	/**
	 * Write the presenters included in the configuration file.
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 */
	private void writePresenters() {

		String name = null;
		String className = null;

		for ( PresenterElement presenter : configuration.getPresenters() ) {
			name = presenter.getName();
			className = presenter.getClassName();

			presenterClasses.put( name, className );

			createInstance( name, className );

			sourceWriter.print( name );
			sourceWriter.println( ".setEventBus(eventBus);" );

			sourceWriter.print( name );
			sourceWriter.println( ".setView(" + presenter.getView() + ");" );

			injectServices( name, presenter.getServices() );

		}
	}

	/**
	 * Write the services included in the configuration file.
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 */
	private void writeServices() {

		String name = null;
		String className = null;

		for ( ServiceElement service : configuration.getServices() ) {
			name = service.getName();
			className = service.getClassName();

			sourceWriter.print( "final " );
			sourceWriter.print( className + "Async" );
			sourceWriter.print( " " );
			sourceWriter.print( name );
			sourceWriter.print( " = GWT.create(" );
			sourceWriter.print( className );
			sourceWriter.println( ".class);" );

			if ( service.hasPath() ) {
				sourceWriter.print( "((ServiceDefTarget) " );
				sourceWriter.print( name );
				sourceWriter.print( ").setServiceEntryPoint(\"" );
				sourceWriter.print( service.getPath() );
				sourceWriter.print( "\");" );				
			}
		}
	}

	/**
	 * Write the events included in the configuration file
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 * @param eventsConf
	 *            List of events tag included in the configuration file
	 * @throws UnableToCompleteException
	 *             thrown if the events tag aren't correct.
	 */
	private void writeEvents() throws UnableToCompleteException {

		String type = null;
		String calledMethod = null;
		String objectClass = null;
		String param = null;
		String[] handlers = null;
		String history = null;
		boolean hasHistory = false;

		for ( EventElement event : configuration.getEvents() ) {
			type = event.getType();
			calledMethod = event.getCalledMethod();
			objectClass = getObjectClass( event );
			if ( objectClass == null ) {
				objectClass = Object.class.getName();
				param = "();";
			} else {
				param = "(form);";
			}
			handlers = event.getHandlers();
			history = event.getHistory();
			hasHistory = event.hasHistory();

			sourceWriter.print( "Command<" );
			sourceWriter.print( objectClass );
			sourceWriter.print( "> cmd" );
			sourceWriter.print( type );
			sourceWriter.print( " = new Command<" );
			sourceWriter.print( objectClass );
			sourceWriter.println( ">(){" );
			sourceWriter.indent();
			sourceWriter.print( "public void execute(" );
			sourceWriter.print( objectClass );
			sourceWriter.println( " form, boolean storeInHistory) {" );
			sourceWriter.indent();

			if ( hasHistory ) {
				sourceWriter.println( "if(storeInHistory){" );
				sourceWriter.indent();
				sourceWriter.print( "placeService.place( \"" );
				sourceWriter.print( type );
				sourceWriter.println( "\", form );" );
				sourceWriter.outdent();
				sourceWriter.println( "}" );
			}

			int nbHandlers = handlers.length;
			for ( int i = 0; i < nbHandlers; i++ ) {
				sourceWriter.print( handlers[i] );
				sourceWriter.print( "." );
				sourceWriter.print( calledMethod );
				sourceWriter.println( param );
			}
			sourceWriter.outdent();
			sourceWriter.println( "}" );
			sourceWriter.outdent();
			sourceWriter.println( "};" );
			sourceWriter.print( "eventBus.addEvent(\"" );
			sourceWriter.print( type );
			sourceWriter.print( "\", cmd" );
			sourceWriter.print( type );
			sourceWriter.println( ");" );

			if ( hasHistory ) {
				sourceWriter.print( "placeService.addConverter( \"" );
				sourceWriter.print( type );
				sourceWriter.print( "\"," );
				sourceWriter.print( history );
				sourceWriter.print( ");" );
			}

		}
	}

	/**
	 * Write the start event tag included in the configuration file
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 * @throws UnableToCompleteException
	 *             thrown if the start event tag isn't correct.
	 */
	private void writeStartEvent() throws UnableToCompleteException {

		StartElement start = configuration.getStart();
		String startView = start.getView();

		sourceWriter.println( "RootPanel.get().add(" + startView + ");" );

		if ( start.hasEventType() ) {
			String eventType = start.getEventType();
			sourceWriter.println( "eventBus.dispatch(\"" + eventType + "\");" );
		}

		if ( start.hasHistory() ) {
			sourceWriter.println( "History.fireCurrentHistoryState();" );
		}
	}

	/**
	 * Send an exception if the value is null
	 * 
	 * @param value
	 *            value to test
	 * @param message
	 *            message to throw with the exception
	 * @throws UnableToCompleteException
	 *             thrown if the value is null
	 */
	private void sendErrorIfNull( Object value, String message ) throws UnableToCompleteException {
		if ( value == null ) {
			logger.log( TreeLogger.ERROR, message );
			throw new UnableToCompleteException();
		}
	}

	/**
	 * Returns the supplied name with its first letter in upper case.
	 * 
	 * @param name
	 *            the name to be capitalized.
	 */
	/* package */String capitalized( String name ) {
		return name.substring( 0, 1 ).toUpperCase() + name.substring( 1 );
	}

	/**
	 * Write the lines to create a new instance of an element
	 * 
	 * @param elementName
	 *            name of the element to create
	 * @param className
	 *            class name of the element to create
	 */
	private void createInstance( String elementName, String className ) {
		sourceWriter.print( "final " );
		sourceWriter.print( className );
		sourceWriter.print( " " );
		sourceWriter.print( elementName );
		sourceWriter.print( " = new " );
		sourceWriter.print( className );
		sourceWriter.println( "();" );
	}

	/**
	 * Write the lines to inject services into an element
	 * 
	 * @param elementName
	 *            name of the element where services need to be injected
	 * @param services
	 *            name of the services to inject
	 */
	private void injectServices( String elementName, String[] services ) {
		for ( String service : services ) {
			String methodName = "set" + capitalized( service );
			sourceWriter.print( elementName );
			sourceWriter.println( "." + methodName + "(" + service + ");" );
		}
	}

	@SuppressWarnings( "unchecked" )
	String getObjectClass( EventElement event ) {
		String objectClass = event.getEventObjectClass();
		if ( ( objectClass == null ) || ( objectClass.length() == 0 ) ) {
			String[] handlers = event.getHandlers();
			if ( handlers.length == 0 ) {
				//no handler and no event object class defined, then no class associated to the object
				objectClass = null;
			} else {
				try {
					Class handlerClass = Class.forName( presenterClasses.get( handlers[0] ) );
					String eventMethod = event.getCalledMethod();
					Class[] parameters = null;
					boolean found = false;
					int parameterSize = 0;
					for ( Method method : handlerClass.getMethods() ) {
						if ( eventMethod.equals( method.getName() ) ) {
							parameters = method.getParameterTypes();
							parameterSize = parameters.length;
							if ( parameterSize == 0 ) {
								found = true;
								objectClass = null;
								break;
							} else if ( parameterSize == 1 ) {
								found = true;
								objectClass = parameters[0].getName();
								break;
							}
						}
					}
					if ( !found ) {
						throw new InvalidMvp4gConfigurationException( "Tag " + event.getTagName() + " " + event.getType() + ": handler "
								+ handlers[0] + " doesn't define a method " + event.getCalledMethod() + " with 1 or 0 parameter." );
					}

				} catch ( ClassNotFoundException e ) {
					throw new InvalidMvp4gConfigurationException( "Tag " + event.getTagName() + " " + event.getType() + ": " + handlers[0]
							+ " handler class: " + presenterClasses.get( handlers[0] ) + " is not found" );
				}
			}
		}
		return objectClass;
	}

}
