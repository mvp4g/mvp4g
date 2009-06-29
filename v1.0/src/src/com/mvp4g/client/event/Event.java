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

/**
 * Event sent by presenter to event bus in order to trigger other presenters.<br/><br/>
 * 
 * Events are defined by two attributes: <ul>
 * <li>their type, defined by a string</li>
 * <li>the object linked to the event. this object will be used as parameter of 
 * the handlers method</li>
 * </ul>
 * 
 * 
 * @author plcoirier
 *
 */
public class Event {

	//type of the object
	private String type = null;
	//object used as parameter of the handlers method
	private Object form = null;

	/**
	 * Create an event with only a type.<br/>
	 * No object will be used by the handlers method.
	 *  
	 * @param type
	 * 		type of the event
	 */
	public Event(String type) {
		this.type = type;		
	}
	
	/**
	 * Create an event with a type and an object to be used by handlers method. 
	 * 
	 * @param type
	 * 			type of the Event
	 * @param form
	 * 			object to pass to the handlers method
	 */
	public Event(String type, Object form) {
		this.type = type;
		this.form = form;
	}

	/**
	 * 
	 * @return
	 * 		the type of the event	
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @return
	 * 		the object to be used by the handlers method
	 */
	public Object getForm() {
		return form;
	}

}

