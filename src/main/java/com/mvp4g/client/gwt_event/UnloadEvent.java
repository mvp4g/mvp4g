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

package com.mvp4g.client.gwt_event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a widget unload event. This event should be fired when a widget is removed from the DOM.
 */
public class UnloadEvent
  extends GwtEvent<UnloadHandler> {

  public static Type<UnloadHandler> TYPE = new Type<UnloadHandler>();

  /*
   * (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<UnloadHandler> getAssociatedType() {
    return TYPE;
  }

  /*
   * (non-Javadoc)
   * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
   */
  @Override
  protected void dispatch(UnloadHandler handler) {
    handler.onUnload();
  }

}
