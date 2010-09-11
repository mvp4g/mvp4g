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

/**
 * Event bus where events can be fired thanks to their type.
 * 
 * @author plcoirier
 * 
 */
public interface EventBusWithLookup extends EventBus {

	/**
	 * Call the method associated with the event type in order to trigger the presenters that can
	 * handle the event. Forward to the handler the object associated with the event.<br/>
	 * <br/>
	 * The event is stored in GWT History stack if possible (ie if an History Converter is
	 * associated with the event) and needed (ie isHistoryStored() == true).
	 * 
	 * @param eventType
	 *            type of the event to dispatch
	 * @param objects
	 *            objects to fire with the event
	 * @throws Mvp4gException
	 *             exception thrown in case an error occurs while the event is dispatched.<br/>
	 *             The most common cases that this error could be thrown is in case:
	 *             <ul>
	 *             <li>the type of the event can't be handled by the event bus because no command is
	 *             associated to it.</li>
	 *             <li>the class type of the object to be used by the handler methods is different
	 *             from the class type of the handlers method.</li>
	 *             </ul>
	 *             This exception shouldn't be caught. If this exception is raised, it means that
	 *             there is an error in the application that needs to be fixed (most of the time, an
	 *             error in the configuration file).
	 */
	public void dispatch( String eventType, Object... objects );

	/**
	 * Call the method associated with the event type in order to trigger the presenters that can
	 * handle the event. In this case no object is forwarded to the handler(s).<br/>
	 * <br/>
	 * The event is stored in GWT History stack if possible (ie if an History Converter is
	 * associated with the event) and needed (ie isHistoryStored() == true).<br/>
	 * <br/>
	 * Calling dispatch(eventType) is equivalent to calling dispatch(eventType, null).
	 * 
	 * @param eventType
	 *            type of the event to dispatch
	 * @throws Mvp4gException
	 *             exception thrown in case an error occurs while the event is dispatched.<br/>
	 *             The most common cases that this error could be thrown is in case:
	 *             <ul>
	 *             <li>the type of the event can't be handled by the event bus because no command is
	 *             associated to it.</li>
	 *             <li>the class type of the object to be used by the handler methods is different
	 *             from the class type of the handlers method.</li>
	 *             </ul>
	 *             This exception shouldn't be caught. If this exception is raised, it means that
	 *             there is an error in the application that needs to be fixed (most of the time, an
	 *             error in the configuration file).
	 */
	public void dispatch( String eventType );

	/**
	 * Call the method associated with the event type in order to trigger the presenters that can
	 * handle the event. Forward to the handler the object associated with the event. <br/>
	 * In this function, the type is given as a enumeration. The toString method of this enumeration
	 * must return the type of the event defined the configuration file.<br/>
	 * <br/>
	 * The event is stored in GWT History stack if possible (ie if an History Converter is
	 * associated with the event) and needed (ie isHistoryStored() == true).<br/>
	 * <br/>
	 * Calling dispatch(enumEventType, form) is equivalent to calling
	 * dispatch(enumEventType.toString(), form).
	 * 
	 * 
	 * 
	 * @param enumEventType
	 *            type of the event to dispatch
	 * @param objects
	 *            objects to fire with the event
	 * @throws Mvp4gException
	 *             exception thrown in case an error occurs while the event is dispatched.<br/>
	 *             The most common cases that this error could be thrown is in case:
	 *             <ul>
	 *             <li>the type of the event can't be handled by the event bus because no command is
	 *             associated to it.</li>
	 *             <li>the class type of the object to be used by the handler methods is different
	 *             from the class type of the handlers method.</li>
	 *             </ul>
	 *             This exception shouldn't be caught. If this exception is raised, it means that
	 *             there is an error in the application that needs to be fixed (most of the time, an
	 *             error in the configuration file).
	 */
	public <E extends Enum<E>> void dispatch( Enum<E> enumEventType, Object... objects );

	/**
	 * Call the method associated with the event type in order to trigger the presenters that can
	 * handle the event. In this case no object is forwarded to the handler(s).<br/>
	 * <br/>
	 * In this function, the type is given as a enumeration. The toString method of this enumeration
	 * must return the type of the event defined the configuration file.<br/>
	 * <br/>
	 * The event is stored in GWT History stack if possible (ie if an History Converter is
	 * associated with the event) and needed (ie isHistoryStored() == true).<br/>
	 * <br/>
	 * Calling dispatch(enumEventType) is equivalent to calling dispatch(enumEventType.toString(),
	 * null).
	 * 
	 * @param enumEventType
	 *            type of the event to dispatch
	 * @throws Mvp4gException
	 *             exception thrown in case an error occurs while the event is dispatched.<br/>
	 *             The most common cases that this error could be thrown is in case:
	 *             <ul>
	 *             <li>the type of the event can't be handled by the event bus because no command is
	 *             associated to it.</li>
	 *             <li>the class type of the object to be used by the handler methods is different
	 *             from the class type of the handlers method.</li>
	 *             </ul>
	 *             This exception shouldn't be caught. If this exception is raised, it means that
	 *             there is an error in the application that needs to be fixed (most of the time, an
	 *             error in the configuration file).
	 */
	public <E extends Enum<E>> void dispatch( Enum<E> enumEventType );

}
