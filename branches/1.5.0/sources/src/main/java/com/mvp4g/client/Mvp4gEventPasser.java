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
 * Object uses for the communication between a module and its children to pass an event.
 * 
 * @author plcoirier
 * 
 */
public abstract class Mvp4gEventPasser {

	protected Object[] eventObjects = null;

	/**
	 * Build a passer object by passing a list of objects associated with an event.
	 * 
	 * @param eventObjects
	 */
	public Mvp4gEventPasser( Object... eventObjects ) {
		this.eventObjects = eventObjects;
	}

	/**
	 * @param eventObjects
	 *            objects associated with an event
	 */
	public void setEventObject( Object... eventObjects ) {
		this.eventObjects = eventObjects;
	}

	/**
	 * @return the eventObjects
	 */
	public Object[] getEventObjects() {
		return eventObjects;
	}

	/**
	 * Method call to execute the event of the sendee.
	 * 
	 * @param module
	 *            module of the receiver
	 */
	public abstract void pass( Mvp4gModule module );

}
