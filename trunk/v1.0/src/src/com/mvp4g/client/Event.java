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

/**
 * Event sent by view to controller in order to execute an action
 * 
 * @author plcoirier
 *
 */
public class Event {

	private String type = null;
	private Object form = null;

	/**
	 * Create an event with the name of the action to execute and information to pass to the action 
	 * 
	 * @param type
	 * 			type of the Event
	 * @param form
	 * 			information to pass to the action
	 */
	public Event(String type) {
		this.type = type;		
	}
	
	/**
	 * Create an event with the name of the action to execute and information to pass to the action 
	 * 
	 * @param type
	 * 			type of the Event
	 * @param form
	 * 			information to pass to the action
	 */
	public Event(String type, Object form) {
		this.type = type;
		this.form = form;
	}

	/**
	 * 
	 * @return
	 * 		the name of the action to execute	
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @return
	 * 		the information to pass to the action	
	 */
	public Object getForm() {
		return form;
	}

}

