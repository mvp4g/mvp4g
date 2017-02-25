/*
 * Copyright 2011 Pierre-Laurent Coirier
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

import com.google.gwt.user.client.Command;
import com.mvp4g.client.event.EventBus;

public interface Mvp4gLoader<E extends EventBus> {

  /**
   * Called before a module/code splitter is loaded. To start the code loading, the load command
   * must be executed. This way, asynchronous action can be made.
   *
   * @param eventBus  The event bus loading the code (ie in case of child module, the parent event bus).
   * @param eventName The name of the event that requested the module to be loaded, null if the request
   *                  come from a history token change.
   * @param params    The objects fired with this event, null is no object are fired with the event or
   *                  if the request come from a history token change.
   * @param load      The command to execute when the module needs to be loaded.
   */
  void preLoad(E eventBus,
               String eventName,
               Object[] params,
               Command load);

  /**
   * Called when the module/code splitter is done loaded.
   *
   * @param eventBus  The event bus loading the code (ie in case of child module, the parent event bus)
   * @param eventName The name of the event that requested the module to be loaded, null if the request
   *                  come from a history token change.
   * @param params    The objects fired with this event, null is no object are fired with the event or
   *                  if the request come from a history token change.
   */
  void onSuccess(E eventBus,
                 String eventName,
                 Object[] params);

  /**
   * Called if the module/code splitter couldn't be loaded.
   *
   * @param eventBus  The event bus loading the code (ie in case of child module, the parent event bus).
   * @param eventName The name of the event that requested the module to be loaded, null if the request
   *                  come from a history token change.
   * @param params    The objects fired with this event, null is no object are fired with the event or
   *                  if the request come from a history token change.
   * @param err       The error thrown.
   */
  void onFailure(E eventBus,
                 String eventName,
                 Object[] params,
                 Throwable err);

}
