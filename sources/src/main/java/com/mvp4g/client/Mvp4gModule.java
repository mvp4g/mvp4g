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
package com.mvp4g.client;

import com.mvp4g.client.annotation.XmlFilePath;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.history.NavigationConfirmationInterface;
import com.mvp4g.client.history.NavigationEventCommand;

/**
 * This interface defines a module for Mvp4g. This interface is only used by the framework to
 * manipulate modules.
 * 
 * @author plcoirier
 * 
 */
@XmlFilePath( "mvp4g-conf.xml" )
public interface Mvp4gModule {

	/**
	 * Method called to create the module and fire the start event.
	 */
	void createAndStartModule();

	/**
	 * Method called when an event is received from the parent module.
	 */
	void onForward();

	/**
	 * @return start view of the module
	 */
	Object getStartView();

	/**
	 * @return event bus of the module
	 */
	EventBus getEventBus();

	/**
	 * Add a converter to the associate token
	 * 
	 * @param eventType
	 *            type of the event
	 * @param historyName
	 *            name of the event to store in the token
	 * @param hc
	 *            converter to associate with the event
	 */
	void addConverter( String eventType, String historyName, HistoryConverter<?> hc );

	/**
	 * Place an event and its associated object in the browser history
	 * 
	 * @param event
	 *            event to store
	 * @param form
	 *            object associated with the event
	 */
	String place( String event, String form, boolean onlyToken );

	/**
	 * Deal the event received from browser history change or pass it to a child module if needed.
	 * 
	 * @param eventType
	 *            token stored in the browse history (event type + ascendant modules history name)
	 * @param passer
	 *            passer to execute the event.
	 */
	void dispatchHistoryEvent( String eventType, Mvp4gEventPasser passer );

	/**
	 * Clear the history token stored in the browse history url by adding a new empty token
	 */
	void clearHistory();

	/**
	 * 
	 * @param parentModule
	 *            parent module to set
	 */
	void setParentModule( Mvp4gModule parentModule );
	
	/**
	 * Set a confirmation that will be called before each navigation event or when history token
	 * changes.
	 * 
	 * @param navigationConfirmation
	 */
	void setNavigationConfirmation( NavigationConfirmationInterface navigationConfirmation );
	
	/**
	 * Ask for user's confirmation before firing an event
	 * 
	 * @param event
	 * 			event to confirm
	 */
	void confirmEvent( NavigationEventCommand event );

}
