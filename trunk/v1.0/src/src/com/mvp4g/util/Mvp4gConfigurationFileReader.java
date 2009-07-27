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

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.user.rebind.SourceWriter;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.EventElement;
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

	/**
	 * Create a Mvp4gConfigurationFileReader object
	 * 
	 * @param sourceWriter
	 * @param logger
	 */
	public Mvp4gConfigurationFileReader(SourceWriter sourceWriter,
			TreeLogger logger) {
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
			XMLConfiguration xmlConfig = new XMLConfiguration("mvp4g-conf.xml");
			sendErrorIfNull( xmlConfig, "mvc4p-conf.xml is missing");

			loadConfiguration( xmlConfig );
			
			sourceWriter.println("EventBus eventBus = new EventBus();");

			writeViews();
			
			writeServices();

			writePresenters();

			writeEvents();

			writeStartEvent();

		} catch (ConfigurationException e) {
			logger.log(TreeLogger.ERROR, e.getMessage());
			throw new UnableToCompleteException();
		}
	}

	/**
	 * Pre-loads all Mvp4g elements in the configuration file into memory.
	 * 
	 * @param xmlConfig raw in-memory representation of mvp4g-config.xml file.
	 * 
	 * @throws UnableToCompleteException
	 * 			thrown if the configuration is invalid.
	 */
	private void loadConfiguration(XMLConfiguration xmlConfig) throws UnableToCompleteException {
		try {
			configuration.load(xmlConfig);
		} catch (InvalidMvp4gConfigurationException imce) {
			logger.log(TreeLogger.ERROR, imce.getMessage());
			throw new UnableToCompleteException();
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
			String name = view.getName();
			String className = view.getClassName();
			
			sourceWriter.print("final ");			
			sourceWriter.print(className);
			sourceWriter.print(" ");
			sourceWriter.print(name);
			sourceWriter.print(" = new ");
			sourceWriter.print(className);
			sourceWriter.println("();");
		}
	}
	

	/**
	 * Write the presenters included in the configuration file.
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 */
	private void writePresenters() {

		for ( PresenterElement presenter : configuration.getPresenters() ) {
			String name = presenter.getName();
			String className = presenter.getClassName();
			String view = presenter.getView();
			
			sourceWriter.print("final ");			
			sourceWriter.print(className);
			sourceWriter.print(" ");
			sourceWriter.print(name);
			sourceWriter.print(" = new ");
			sourceWriter.print(className);
			sourceWriter.println("();");

			sourceWriter.print(name);
			sourceWriter.println(".setEventBus(eventBus);");
			
			sourceWriter.print(name);
			sourceWriter.println(".setView(" + view + ");");

			for ( String service : presenter.getServices() ) {
			    String methodName = "set" + capitalized( service );
			    sourceWriter.print(name);
			    sourceWriter.println("." + methodName + "(" + service + ");");
			}
		}
	}
	

	/**
	 * Write the services included in the configuration file.
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 */
	private void writeServices() {

		for ( ServiceElement service : configuration.getServices() ) {
			String name = service.getName();
			String className = service.getClassName();
			
			sourceWriter.print("final ");			
			sourceWriter.print(className + "Async");
			sourceWriter.print(" ");
			sourceWriter.print(name);
			sourceWriter.print(" = GWT.create(");
			sourceWriter.print(className);
			sourceWriter.println(".class);");
		}
	}
	
	
	/**
	 * Write the events included in the configuration file
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 * @param eventsConf
	 * 			List of events tag included in the configuration file
	 * @throws UnableToCompleteException
	 * 			thrown if the events tag aren't correct.
	 */	
	private void writeEvents() throws UnableToCompleteException {
		
		for ( EventElement event : configuration.getEvents() ) {
			String type = event.getType();
			String functionCalled = event.getFunctionCalled();
			String objectClass = event.getEventObjectClass();
			String param = event.getEventParamaterString();
			String[] handlers = event.getHandlers();
			
			sourceWriter.print("Command cmd");
			sourceWriter.print(type);
			sourceWriter.print(" = new Command<");
			sourceWriter.print(objectClass);
			sourceWriter.println(">(){");
			sourceWriter.indent();
			sourceWriter.print("public void execute(");
			sourceWriter.print(objectClass);
			sourceWriter.println(" form) {");
			sourceWriter.indent();
			int nbHandlers = handlers.length;
			for (int i = 0; i < nbHandlers; i++) {
				sourceWriter.print(handlers[i]);
				sourceWriter.print(".");
				sourceWriter.print(functionCalled);
				sourceWriter.println(param);
			}
			sourceWriter.outdent();
			sourceWriter.println("}");
			sourceWriter.outdent();
			sourceWriter.println("};");
			sourceWriter.print("eventBus.addEvent(\"");
			sourceWriter.print(type);
			sourceWriter.print("\", cmd");
			sourceWriter.print(type);
			sourceWriter.println(");");
		}
	}
	
	
	/**
	 * Write the start event tag included in the configuration file
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file. 
	 * 
	 * @throws UnableToCompleteException
	 * 			thrown if the start event tag isn't correct.
	 */	
	private void writeStartEvent() throws UnableToCompleteException {

		StartElement start = configuration.getStart(); 
		String startView = start.getView();
		
		sourceWriter.println("RootPanel.get().add(" + startView + ");");
		
		if ( start.hasEventType() ) {
			String eventType = start.getEventType();
			sourceWriter.println("eventBus.dispatch(new Event(\"" + eventType
					+ "\"));");
		}
	}
	
	/**
	 * Send an exception if the value is null
	 * 
	 * @param value
	 * 			value to test
	 * @param message
	 * 			message to throw with the exception
	 * @throws UnableToCompleteException
	 * 			thrown if the value is null
	 */
	private void sendErrorIfNull(Object value, String message)
			throws UnableToCompleteException {
		if (value == null) {
			logger.log(TreeLogger.ERROR, message);
			throw new UnableToCompleteException();
		}
	}
	
	
	/**
	 * Returns the supplied name with its first letter in upper case.
	 * 
	 * @param name
	 * 			the name to be capitalized.
	 */
	/* package */ String capitalized( String name ) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

}
