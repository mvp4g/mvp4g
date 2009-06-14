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
package com.mvp4g.client;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is in charged of forwarding event to action
 * 
 * It also contains a list of the view
 * 
 * @author plcoirier
 * 
 */
public class EventBus {

	protected Map<String, Command> commands = new HashMap<String, Command>();

	/**
	 * Handle an event by forwarding it to the right action
	 * 
	 * @param e
	 *            event to manage
	 * @throws Exception
	 *             exception thrown if action not manageable by the controller
	 */
	public void dispatch(Event e) {
		commands.get(e.getType()).execute(e.getForm());
	}
	
	public void addEvent(String eventType, Command cmd){
		commands.put(eventType, cmd);
	}

}
