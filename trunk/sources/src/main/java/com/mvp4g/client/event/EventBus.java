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
package com.mvp4g.client.event;

import com.mvp4g.client.Mvp4gException;
import com.mvp4g.client.history.NavigationConfirmationInterface;
import com.mvp4g.client.history.NavigationEventCommand;

/**
 * Interface that defines an event bus. All classes defining an event bus must implement it.
 * 
 * @author plcoirier
 * 
 */
public interface EventBus {

	/**
	 * Set for all events if they should be stored or not in browser history when possible (ie when
	 * associated with an history converter).
	 * 
	 * @param historyStored
	 *            true if events should be stored
	 */
	void setHistoryStored( boolean historyStored );

	/**
	 * Set for next event that can be stored in history if it should be stored or not in browser
	 * history.<br/>
	 * <br/>
	 * This method should be called only right before sending an event that could be stored in
	 * browser history.
	 * 
	 * @param historyStored
	 *            true if events should be stored
	 */
	void setHistoryStoredForNextOne( boolean historyStored );

	/**
	 * Indicate if events are stored in browser history when possible (ie when associated with an
	 * history converter).
	 * 
	 * @return true if events can be stored in browse history
	 */
	boolean isHistoryStored();

	/**
	 * Set whether or not the associated event filters should be executed before sending the event
	 * to the associated event handlers.<br/>
	 * 
	 * @param filteringEnabled
	 *            true if events filters should be executed
	 */
	void setFilteringEnabled( boolean filteringEnabled );

	/**
	 * Set whether or not the associated event filters should be executed before sending the next
	 * event to the associated event handlers.<br/>
	 * <br/>
	 * This method should be called only right before sending an event that could be filtered.
	 * 
	 * @param filteringEnabled
	 *            true if events filters should be executed
	 */
	void setFilteringEnabledForNextOne( boolean filteringEnabled );

	/**
	 * Indicate if event filters should be executed before sending events to handlers.
	 * 
	 * @return true if events filters should be executed
	 */
	boolean isFilteringEnabled();

	/**
	 * Create a new instance of the handler, bind it and add it to event bus.<br/>
	 * <br/>
	 * . Calling this method is equivalent to addHandler(handlerClass, true);
	 * 
	 * 
	 * @param <T>
	 *            type of the handler created
	 * @param handlerClass
	 *            class of the handler to create
	 * @return new instance of the handler created
	 * 
	 * @throws Mvp4gException
	 *             thrown if the instance of the handler can not be created by the event bus
	 */
	<T extends EventHandlerInterface<?>> T addHandler( Class<T> handlerClass ) throws Mvp4gException;

	/**
	 * Create a new instance of the handler, bind it only if this option is set to true and add it
	 * to event bus. If you decide not to set the handler at creation, you will have either make
	 * sure the handler is displayed only after it handles its first method (otherwise the view is
	 * not binded so it seems inactive) or call manualy the bind method.
	 * 
	 * @param <T>
	 *            type of the handler created
	 * @param handlerClass
	 *            class of the handler to create
	 * @param bind
	 *            if true, bind the handler at creation, otherwise do nothing.
	 * @return new instance of the handler created
	 * 
	 * @throws Mvp4gException
	 *             thrown if the instance of the handler can not be created by the event bus
	 */
	<T extends EventHandlerInterface<?>> T addHandler( Class<T> handlerClass, boolean bind ) throws Mvp4gException;

	/**
	 * Remove the instance of the handler from the event bus
	 * 
	 * @param <T>
	 *            type of the handler to remove
	 * @param handler
	 *            handler to remove
	 */
	<T extends EventHandlerInterface<?>> void removeHandler( T handler );

	/**
	 * Add a new event filter
	 * 
	 * @param filter
	 *            new event filter to add
	 */
	void addEventFilter( EventFilter<? extends EventBus> filter );

	/**
	 * Remove event filter
	 * 
	 * @param filter
	 *            event filter to remove
	 */
	void removeEventFilter( EventFilter<? extends EventBus> filter );

	/**
	 * Set a confirmation that will be called before each navigation event or when history token
	 * changes. This will set the navigationConfirmation for the whole application. You can have
	 * only one navigationConfirmation for the whole application.
	 * 
	 * @param navigationConfirmation
	 */
	void setNavigationConfirmation( NavigationConfirmationInterface navigationConfirmation );

	/**
	 * Method to manually ask if an action can occur
	 * 
	 * @param event
	 */
	void confirmNavigation( NavigationEventCommand event );

}
