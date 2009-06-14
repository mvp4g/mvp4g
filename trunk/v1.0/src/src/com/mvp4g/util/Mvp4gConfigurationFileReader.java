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

import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * This class reads the mvc4g-conf.xml
 * 
 * @author plcoirier
 * 
 */
public class Mvp4gConfigurationFileReader {

	private SourceWriter sourceWriter = null;
	private TreeLogger logger = null;

	/**
	 * Create a Mvc4gConfigurationFileReader object
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
	 * Parses the mvc4g-conf.xml and write the information in the source writer
	 * 
	 * @throws UnableToCompleteException
	 *             exception thrown if the configuratin file is not correct
	 */
	@SuppressWarnings("unchecked")
	public void writeConf() throws UnableToCompleteException {
		try {
			XMLConfiguration xmlConfig = new XMLConfiguration("mvp4g-conf.xml");
			sendErrorIfNull(xmlConfig, "mvc4p-conf.xml is missing");

			sourceWriter.println("EventBus eventBus = new EventBus();");

			writePresenters(xmlConfig.configurationsAt("presenters.presenter"));

			writeEvents(xmlConfig.configurationsAt("events.event"));

			List<HierarchicalConfiguration> startNode = xmlConfig
					.configurationsAt("start");
			if (startNode.size() > 0) {
				writeStartEvent(startNode.get(0));
			}

		} catch (ConfigurationException e) {
			logger.log(TreeLogger.ERROR, e.getMessage());
			throw new UnableToCompleteException();
		}

	}

	private void writePresenters(List<HierarchicalConfiguration> presentersConf)
			throws UnableToCompleteException {

		String name = null;
		String className = null;

		HierarchicalConfiguration presenterConf = null;

		Iterator<HierarchicalConfiguration> it = presentersConf.iterator();
		while (it.hasNext()) {
			presenterConf = it.next();

			name = presenterConf.getString("[@name]");
			sendErrorIfNull(name, "Presenter name is missing");

			className = presenterConf.getString("[@class]");
			sendErrorIfNull(className, "Presenter " + name
					+ ": class name is missing");

			sourceWriter.print("final ");			
			sourceWriter.print(className);
			sourceWriter.print(" ");
			sourceWriter.print(name);
			sourceWriter.print(" = new ");
			sourceWriter.print(className);
			sourceWriter.println("();");

			sourceWriter.print(name);
			sourceWriter.println(".setEventBus(eventBus);");
		}

	}

	private void writeEvents(List<HierarchicalConfiguration> presentersConf)
			throws UnableToCompleteException {

		String type = null;
		String functionCalled = null;
		String[] handlers = null;

		HierarchicalConfiguration eventConf = null;

		Iterator<HierarchicalConfiguration> it = presentersConf.iterator();
		while (it.hasNext()) {
			eventConf = it.next();

			type = eventConf.getString("[@type]");
			sendErrorIfNull(type, "Event type is missing");

			functionCalled = eventConf.getString("[@functionCalled]");
			sendErrorIfNull(functionCalled, "Event " + type
					+ ": functionCalled is missing");

			handlers = eventConf.getStringArray("[@handlers]");
			if ((handlers == null) || (handlers.length == 0)) {
				logger.log(TreeLogger.ERROR, "Event " + type
						+ ": no handlers defined");
				throw new UnableToCompleteException();
			}

			sourceWriter.print("Command cmd");
			sourceWriter.print(type);
			sourceWriter.println(" = new Command(){");
			sourceWriter.indent();
			sourceWriter.println("public void execute(Object form) {");
			sourceWriter.indent();
			int nbHandlers = handlers.length;
			for (int i = 0; i < nbHandlers; i++) {
				sourceWriter.print(handlers[i]);
				sourceWriter.print(".");
				sourceWriter.print(functionCalled);
				sourceWriter.println("(form);");

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

	private void writeStartEvent(HierarchicalConfiguration start)
			throws UnableToCompleteException {

		String eventType = start.getString("[@eventType]");

		sendErrorIfNull(eventType, "Start: Event type is missing");

		sourceWriter.println("eventBus.dispatch(new Event(\"" + eventType
				+ "\"));");

	}

	private void sendErrorIfNull(Object value, String message)
			throws UnableToCompleteException {
		if (value == null) {
			logger.log(TreeLogger.ERROR, message);
			throw new UnableToCompleteException();
		}
	}

}
