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

/**
 * Interface that defines an event handler.<br/>
 * <br/>
 * This interface provides getter and setter for the event bus.<br/>
 * <br/>
 * It is recommended to use directly <code>BaseEventHandler</code>.
 * 
 * 
 * @since 27.04.2010
 * @author Dan Persa
 */
public interface EventHandlerInterface<E extends EventBus> {

	/**
	 * Set an event bus to the event handler
	 * 
	 * @param eventBus
	 *            event bus to set
	 */
	void setEventBus( E eventBus );

	/**
	 * Get the event bus associated with the event handler
	 * 
	 * @return eventBus manipulated by the event handler.
	 */
	E getEventBus();

	/**
	 * Get the event bus associated with the event handler but in token generation mode.
	 * 
	 * The next event fire won't be fired but its token will be returned.
	 * 
	 * @return eventBus manipulated by the event handler.
	 */
	E getTokenGenerator();

	/**
	 * Call by the event bus when the handler handles its first event.
	 * 
	 */
	void bind();

	/**
	 * Indicates if the presenter is activated or not. If the event handler is activated and if the
	 * bind method hasn't been called yet, then the bind method should be called.
	 * 
	 * @return true if the presenter is activated, false otherwise
	 */
	boolean isActivated();

	/**
	 * Set if the event handler is activated or not. By default, an event handler should be
	 * activated.
	 * 
	 * @param activated
	 *            new activation parameter
	 */
	void setActivated( boolean activated );

}
