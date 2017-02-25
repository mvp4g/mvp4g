/*
 * Copyright (c) 2009 - 2017 - Pierre-Laurent Coirer, Frank Hossfeld
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
 * Event bus where events can be fired thanks to their name.
 *
 * @author plcoirier
 */
public interface EventBusWithLookup
  extends EventBus {

  /**
   * Call the method associated with the event name in order to trigger the presenters that can
   * handle the event. Forward to the handler the object associated with the event.<br>
   * <br>
   * The event is stored in GWT History stack if possible (ie if an History Converter is
   * associated with the event) and needed (ie isHistoryStored() == true).
   *
   * @param eventName
   *   name of the event to dispatch
   * @param objects
   *   objects to fire with the event
   *
   * @throws Mvp4gException
   *   exception thrown in case an error occurs while the event is dispatched.<br>
   *   The most common cases that this error could be thrown is in case:
   *   <ul>
   *   <li>the name of the event can't be handled by the event bus because no command is
   *   associated to it.</li>
   *   <li>the class type of the object to be used by the handler methods is different
   *   from the class type of the handlers method.</li>
   *   </ul>
   *   This exception shouldn't be caught. If this exception is raised, it means that
   *   there is an error in the application that needs to be fixed (most of the time, an
   *   error in the configuration file).
   */
  void dispatch(String eventName,
                Object... objects);

  /**
   * Call the method associated with the event name in order to trigger the presenters that can
   * handle the event. In this case no object is forwarded to the handler(s).<br>
   * <br>
   * The event is stored in GWT History stack if possible (ie if an History Converter is
   * associated with the event) and needed (ie isHistoryStored() == true).<br>
   * <br>
   * Calling dispatch(eventName) is equivalent to calling dispatch(eventName, null).
   *
   * @param eventName
   *   name of the event to dispatch
   *
   * @throws Mvp4gException
   *   exception thrown in case an error occurs while the event is dispatched.<br>
   *   The most common cases that this error could be thrown is in case:
   *   <ul>
   *   <li>the name of the event can't be handled by the event bus because no command is
   *   associated to it.</li>
   *   <li>the class type of the object to be used by the handler methods is different
   *   from the class type of the handlers method.</li>
   *   </ul>
   *   This exception shouldn't be caught. If this exception is raised, it means that
   *   there is an error in the application that needs to be fixed (most of the time, an
   *   error in the configuration file).
   */
  void dispatch(String eventName);

  /**
   * Call the method associated with the event name in order to trigger the presenters that can
   * handle the event. Forward to the handler the object associated with the event. <br>
   * In this function, the name is given as a enumeration. The toString method of this enumeration
   * must return the name of the event as defined in the configuration.<br>
   * <br>
   * The event is stored in GWT History stack if possible (ie if an History Converter is
   * associated with the event) and needed (ie isHistoryStored() == true).<br>
   * <br>
   * Calling dispatch(enumEventName, form) is equivalent to calling
   * dispatch(enumEventName.toString(), form).
   *
   * @param enumEventName
   *   name of the event to dispatch
   * @param objects
   *   objects to fire with the event
   * @param <E>
   *   name of the event
   *
   * @throws Mvp4gException
   *   exception thrown in case an error occurs while the event is dispatched.<br>
   *   The most common cases that this error could be thrown is in case:
   *   <ul>
   *   <li>the name of the event can't be handled by the event bus because no command is
   *   associated to it.</li>
   *   <li>the class type of the object to be used by the handler methods is different
   *   from the class type of the handlers method.</li>
   *   </ul>
   *   This exception shouldn't be caught. If this exception is raised, it means that
   *   there is an error in the application that needs to be fixed (most of the time, an
   *   error in the configuration file).
   */
  <E extends Enum<E>> void dispatch(Enum<E> enumEventName,
                                    Object... objects);

  /**
   * Call the method associated with the event name in order to trigger the presenters that can
   * handle the event. In this case no object is forwarded to the handler(s).<br>
   * <br>
   * In this function, the name is given as a enumeration. The toString method of this enumeration
   * must return the name of the event as defined in the configuration.<br>
   * <br>
   * The event is stored in GWT History stack if possible (ie if an History Converter is
   * associated with the event) and needed (ie isHistoryStored() == true).<br>
   * <br>
   * Calling dispatch(enumEventName) is equivalent to calling dispatch(enumEventName.toString(),
   * null).
   *
   * @param enumEventName
   *   name of the event to dispatch
   * @param <E>
   *   name of the event
   *
   * @throws Mvp4gException
   *   exception thrown in case an error occurs while the event is dispatched.<br>
   *   The most common cases that this error could be thrown is in case:
   *   <ul>
   *   <li>the name of the event can't be handled by the event bus because no command is
   *   associated to it.</li>
   *   <li>the class type of the object to be used by the handler methods is different
   *   from the class type of the handlers method.</li>
   *   </ul>
   *   This exception shouldn't be caught. If this exception is raised, it means that
   *   there is an error in the application that needs to be fixed (most of the time, an
   *   error in the configuration file).
   */
  <E extends Enum<E>> void dispatch(Enum<E> enumEventName);

}
