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

package com.mvp4g.rebind.test_tools.annotation;

import com.mvp4g.client.SingleSplitter;
import com.mvp4g.client.annotation.EventHandler;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.BaseEventHandler;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.rebind.test_tools.annotation.services.SimpleService;
import com.mvp4g.rebind.test_tools.annotation.services.SimpleServiceAsync;

@SuppressWarnings("deprecation")
public class EventHandlers {

  @EventHandler(multiple = true)
  public static class MultipleEventHandler
    extends BaseEventHandler<EventBus> {
  }

  @EventHandler(async = SingleSplitter.class)
  public static class AsyncEventHandler
    extends BaseEventHandler<EventBus> {
  }

  @EventHandler(name = "name")
  public static class EventHandlerWithName
    extends BaseEventHandler<EventBus> {
  }

  @EventHandler
  public static class EventHandlerNotPublic
    extends BaseEventHandler<EventBus> {

    @InjectService
    void setSthg(SimpleServiceAsync service) {
    }

  }

  @EventHandler
  public static class EventHandlerNoParameter
    extends BaseEventHandler<EventBus> {

    @InjectService
    public void setSthg() {
    }

  }

  @EventHandler
  public static class EventHandlerWithMoreThanOneParameter
    extends BaseEventHandler<EventBus> {

    @InjectService
    public void setSthg(SimpleServiceAsync service,
                        Boolean test) {
    }

  }

  @EventHandler
  public static class EventHandlerNotAsync
    extends BaseEventHandler<EventBus> {

    @InjectService
    public void setSthg(SimpleService service) {
    }

  }

  @EventHandler
  public static class EventHandlerWithService
    extends BaseEventHandler<EventBus> {

    @InjectService
    public void setSthg(SimpleServiceAsync service) {
    }

  }

  @EventHandler
  public static class EventHandlerWithSameService
    extends BaseEventHandler<EventBus> {

    @InjectService
    public void setSthg(SimpleServiceAsync service) {
    }

  }

  @EventHandler
  public static class EventHandlerWithServiceAndName
    extends BaseEventHandler<EventBus> {

    @InjectService(serviceName = "name")
    public void setSthg(SimpleServiceAsync service) {
    }

  }

  @Presenter(view = Object.class)
  @EventHandler
  public static class PresenterAndEventHandler
    extends BasePresenter<Object, EventBus> {

  }

}
