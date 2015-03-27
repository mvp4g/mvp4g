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
 * Default implementation of <code>EventHandlerInterface</code>.<br>
 * <br>
 * This implementation has an event bus attribute.<br>
 * You should extend this class to create a presenter.<br>
 * <br>
 *
 * @param <E> Type of the event bus used by the event handler.
 * @author Dan Persa
 */
public class BaseEventHandler<E extends EventBus>
  implements EventHandlerInterface<E> {

  protected boolean binded    = false;
  protected boolean activated = true;

  protected E eventBus;

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventHandlerInterface#getEventBus()
   */
  public E getEventBus() {
    return eventBus;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * com.mvp4g.client.event.EventHandlerInterface#setEventBus(com.mvp4g.client.event.EventBus)
   */
  public void setEventBus(E eventBus) {
    this.eventBus = eventBus;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventHandlerInterface#bind()
   */
  public void bind() {
        /*
     * Default implementation does nothing: extensions are responsible for (optionally) defining
		 * binding specifics.
		 */
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventHandlerInterface#isActivated()
   */
  public final boolean isActivated(boolean passive,
                                   String eventName,
                                   Object... parameters) {
    boolean activated = this.activated && pass(eventName,
                                               parameters);
    if (activated) {
      if (passive) {
        return binded;
      } else {
        onBeforeEvent();
        if (! binded) {
          bind();
          binded = true;
        }
      }
    }
    return activated;
  }

  /**
   * This method allows to prevent handler to handle a specific event.
   *
   * @param eventName  name of the event to filter
   * @param parameters parameters associated to the event to filter
   * @return true if the handler can handle this event, false otherwise
   */
  protected boolean pass(String eventName,
                         Object... parameters) {
    return true;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.client.event.EventHandlerInterface#setActivated(boolean)
   */
  public void setActivated(boolean activated) {
    this.activated = activated;
  }

  /**
   * Method called before each time an handler has to handle an event.
   */
  public void onBeforeEvent() {

  }

  /**
   * Set the event bus in token generation mode and return it. When the event bus is in token
   * generation mode, for the next call to an event method, it won't fire the event and forward it
   * to handlers but instead, it will generate the event token.
   *
   * @return the event bus in token generation mode.
   */
  public E getTokenGenerator() {
    eventBus.setTokenGenerationModeForNextEvent();
    return eventBus;
  }
}
