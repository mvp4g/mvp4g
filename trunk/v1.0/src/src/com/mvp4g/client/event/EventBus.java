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
package com.mvp4g.client.event;

import java.util.HashMap;
import java.util.Map;

import com.mvp4g.client.Mvp4gException;

/**
 * This class is in charged of dispatching an event to the right presenters thanks
 * to commands
 * 
 * @author plcoirier
 * 
 */
@SuppressWarnings("unchecked")
public class EventBus {

	//Associate an event type with the command to execute to dispatch the event	
	protected Map<String, Command> commands = new HashMap<String, Command>();

	/**
	 * Dispatch an event by calling the execute method of the associated command
	 * in order to trigger the presenters that can handle the event
	 * 
	 * @param e
	 *            event to dispatch
	 * @throws Mvp4gException
	 *             exception thrown in case an error occurs while the event is dispatched.<br/>
	 *             The most common cases that this error could be thrown is in case:<ul>
	 *             <li>the type of the event can't be handled by the event bus because no command is associated to it.</li>
	 *             <li>the class type of the object to be used by the handler methods is different from the class type of the handlers method.</li>
	 *             </ul>
	 *             This exception shouldn't be caught. If this exception is raised, it means that there is an error in the application
	 *             that needs to be fixed (most of the time, an error in the configuration file).             
	 */
	public void dispatch(Event e) {
		try {
			commands.get(e.getType()).execute(e.getForm());
		}
		catch (NullPointerException exp) {
			throw new Mvp4gException("Event " + e.getType() + " doesn't exist. Have you forgotten to add it to your Mvp4g configuration file?");
		}
		catch (ClassCastException er) {
			throw new Mvp4gException(
							"Class of the object sent with event "
											+ e.getType()
											+ " is incorrect. It should be the same as the one configured in the Mvp4g configuration file.");
		}
	}

	/**
	 * Add a new event type that can be managed by the event bus by associated
	 * a command to the new event type.
	 * If the event type was already present, it replaces the old command by the specified
	 * command.
	 * 
	 * @param eventType
	 * 		New event type 			
	 * @param cmd
	 * 		New command associated to the event type
	 */
	public void addEvent(String eventType, Command cmd) {
		commands.put(eventType, cmd);
	}

}
