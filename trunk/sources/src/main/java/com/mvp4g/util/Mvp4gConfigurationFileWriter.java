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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.user.rebind.SourceWriter;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.EventBusElement;
import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.HistoryElement;
import com.mvp4g.util.config.element.InjectedElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.config.element.ViewElement;

/**
 * This class reads the mvp4g-conf.xml
 * 
 * @author plcoirier
 * 
 */
public class Mvp4gConfigurationFileWriter {

	private SourceWriter sourceWriter = null;

	private Mvp4gConfiguration configuration = null;

	/**
	 * Create a Mvp4gConfigurationFileReader object
	 * 
	 * @param sourceWriter
	 * @param logger
	 */
	public Mvp4gConfigurationFileWriter( SourceWriter sourceWriter, Mvp4gConfiguration configuration ) {
		this.sourceWriter = sourceWriter;
		this.configuration = configuration;
	}

	/**
	 * Parses the mvp4g-conf.xml and write the information in the source writer
	 * 
	 * @throws UnableToCompleteException
	 *             exception thrown if the configuratin file is not correct
	 */
	public void writeConf() {

		sourceWriter.indent();

		sourceWriter.println();

		writeEventBusClass();

		sourceWriter.println();

		sourceWriter.println( "public void start(){" );
		sourceWriter.indent();

		writeViews();

		sourceWriter.println();

		writeServices();

		sourceWriter.println();

		writeHistory();

		sourceWriter.println();

		writePresenters();

		sourceWriter.println();

		writeEventBus();

		sourceWriter.println();

		injectEventBus();

		sourceWriter.println();

		writeStartEvent();

		sourceWriter.outdent();
		sourceWriter.println( "};" );

	}

	private void writeEventBusClass() {

		EventBusElement eventBus = configuration.getEventBus();

		sourceWriter.print( "private abstract class AbstractEventBus extends " );
		sourceWriter.print( eventBus.getAbstractClassName() );
		sourceWriter.print( " implements " );
		sourceWriter.print( eventBus.getInterfaceClassName() );
		sourceWriter.println( "{}" );
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

			String eventBusClass = configuration.getEventBus().getInterfaceClassName();

			sourceWriter.print( "final PlaceService<" );
			sourceWriter.print( eventBusClass );
			sourceWriter.print( "> placeService = new PlaceService<" );
			sourceWriter.print( eventBusClass );
			sourceWriter.println( ">(){" );

			sourceWriter.indent();
			sourceWriter.println( "protected void sendInitEvent(){" );
			sourceWriter.indent();
			sourceWriter.print( "getEventBus()." );

			if ( EventBusWithLookup.class.getName().equals( eventBusClass ) ) {
				sourceWriter.print( "dispatch(\"" );
				sourceWriter.print( history.getInitEvent() );
				sourceWriter.println( "\");" );
			} else {
				sourceWriter.print( history.getInitEvent() );
				sourceWriter.println( "();" );
			}

			sourceWriter.outdent();
			sourceWriter.println( "}" );
			sourceWriter.outdent();
			sourceWriter.println( "};" );

			String name = null;

			for ( HistoryConverterElement converter : configuration.getHistoryConverters() ) {
				name = converter.getName();
				createInstance( name, converter.getClassName() );
				injectServices( name, converter.getInjectedServices() );
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

			createInstance( name, className );

			sourceWriter.print( name );
			sourceWriter.println( ".setView(" + presenter.getView() + ");" );

			injectServices( name, presenter.getInjectedServices() );

		}
	}

	/**
	 * Write the presenters included in the configuration file.
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 */
	private void injectEventBus() {

		for ( PresenterElement presenter : configuration.getPresenters() ) {
			sourceWriter.print( presenter.getName() );
			sourceWriter.println( ".setEventBus(eventBus);" );
		}

		if ( configuration.getHistory() != null ) {
			sourceWriter.print( "placeService.setEventBus(eventBus);" );
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
	 * @throws InvalidMvp4gConfigurationException
	 */
	private void writeEventBus() {

		EventBusElement eventBus = configuration.getEventBus();

		sourceWriter.println( "AbstractEventBus eventBus = new AbstractEventBus(){" );
		sourceWriter.indent();

		List<EventElement> eventsWithHistory = new ArrayList<EventElement>();

		String type = null;
		String calledMethod = null;
		String objectClass = null;
		String param = null;
		String[] handlers = null;
		boolean hasHistory = false;

		for ( EventElement event : configuration.getEvents() ) {
			type = event.getType();
			calledMethod = event.getCalledMethod();
			objectClass = event.getEventObjectClass();

			handlers = event.getHandlers();
			hasHistory = event.hasHistory();

			sourceWriter.print( "public void " );
			sourceWriter.print( type );
			sourceWriter.print( "(" );
			if ( objectClass == null ) {
				param = "();";
			} else {
				sourceWriter.print( objectClass );
				sourceWriter.print( " form" );
				param = "(form);";
			}
			sourceWriter.println( "){" );

			sourceWriter.indent();

			if ( hasHistory ) {
				sourceWriter.print( "place( placeService, \"" );
				sourceWriter.print( type );
				sourceWriter.println( "\", form );" );
				eventsWithHistory.add( event );
			}

			for ( String handler : handlers ) {
				sourceWriter.print( handler );
				sourceWriter.print( "." );
				sourceWriter.print( calledMethod );
				sourceWriter.println( param );
			}
			sourceWriter.outdent();
			sourceWriter.println( "}" );

		}

		if ( eventBus.isWithLookUp() ) {
			writeEventLookUp();
		}
		sourceWriter.println( "};" );

		for ( EventElement event : eventsWithHistory ) {
			sourceWriter.print( "placeService.addConverter( \"" );
			sourceWriter.print( event.getType() );
			sourceWriter.print( "\"," );
			sourceWriter.print( event.getHistory() );
			sourceWriter.print( ");" );
		}
	}

	private void writeEventLookUp() {

		sourceWriter.println( "public void dispatch( String eventType, Object form ){" );
		sourceWriter.indent();

		sourceWriter.println( "try{" );
		sourceWriter.indent();

		String type = null;
		String objectClass = null;
		String param = null;

		for ( EventElement event : configuration.getEvents() ) {
			type = event.getType();

			objectClass = event.getEventObjectClass();
			if ( objectClass == null ) {
				param = "();";
			} else {
				param = "( (" + objectClass + ") form);";
			}

			sourceWriter.print( "if ( \"" );
			sourceWriter.print( type );
			sourceWriter.println( "\".equals( eventType ) ){" );

			sourceWriter.indent();
			sourceWriter.print( type );
			sourceWriter.println( param );
			sourceWriter.outdent();
			sourceWriter.print( "} else " );

		}

		sourceWriter.println( "{" );
		sourceWriter.indent();
		sourceWriter
				.println( "throw new Mvp4gException( \"Event \" + eventType + \" doesn't exist. Have you forgotten to add it to your Mvp4g configuration file?\" );" );
		sourceWriter.outdent();
		sourceWriter.println( "}" );

		sourceWriter.outdent();
		sourceWriter.println( "} catch ( ClassCastException e ) {" );
		sourceWriter.indent();
		sourceWriter.println( "handleClassCastException( e, eventType );" );
		sourceWriter.outdent();
		sourceWriter.println( "}" );

		sourceWriter.outdent();
		sourceWriter.println( "}" );
		sourceWriter.outdent();
	}

	/**
	 * Write the start event tag included in the configuration file
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 * @throws UnableToCompleteException
	 *             thrown if the start event tag isn't correct.
	 */
	private void writeStartEvent() {

		StartElement start = configuration.getStart();
		String startView = start.getView();

		sourceWriter.println( "RootPanel.get().add(" + startView + ");" );
		
		if ( start.hasEventType() ) {
			String eventType = start.getEventType();
			String eventBusClass = configuration.getEventBus().getInterfaceClassName();

			sourceWriter.print( "eventBus." );
			if ( EventBusWithLookup.class.getName().equals( eventBusClass ) ) {
				sourceWriter.print( "dispatch(\"" );
				sourceWriter.print( eventType );
				sourceWriter.println( "\");" );
			} else {
				sourceWriter.print( eventType );
				sourceWriter.println( "();" );
			}
			
		}

		if ( start.hasHistory() ) {
			sourceWriter.println( "History.fireCurrentHistoryState();" );
		}
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
	private void injectServices( String elementName, List<InjectedElement> injectedServices ) {
		for ( InjectedElement service : injectedServices ) {
			sourceWriter.print( elementName );
			sourceWriter.println( "." + service.getSetterName() + "(" + service.getElementName() + ");" );
		}
	}

}
