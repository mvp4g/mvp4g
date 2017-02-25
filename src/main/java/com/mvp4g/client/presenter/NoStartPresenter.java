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

package com.mvp4g.client.presenter;

import com.mvp4g.client.event.EventBus;

public class NoStartPresenter
  implements PresenterInterface<Object, EventBus> {

  public EventBus getEventBus() {
    throw new RuntimeException("This message should never be called");
  }

  public void setEventBus(EventBus eventBus) {
    throw new RuntimeException("This message should never be called");
  }

  public EventBus getTokenGenerator() {
    throw new RuntimeException("This message should never be called");
  }

  public void bind() {
    throw new RuntimeException("This message should never be called");
  }

  public boolean isActivated(boolean passive,
                             String eventName,
                             Object... parameters) {
    throw new RuntimeException("This message should never be called");
  }

  public void setActivated(boolean activated) {
    throw new RuntimeException("This message should never be called");
  }

  public Object getView() {
    throw new RuntimeException("This message should never be called");
  }

  public void setView(Object view) {
    throw new RuntimeException("This message should never be called");
  }

}
