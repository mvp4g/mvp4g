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

package com.mvp4g.rebind.config.loader.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.EventHandler;
import com.mvp4g.rebind.config.element.EventHandlerElement;
import com.mvp4g.rebind.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.rebind.test_tools.annotation.EventHandlers;
import com.mvp4g.rebind.test_tools.annotation.handlers.SimpleEventHandler01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EventHandlerAnnotationsLoaderTest
  extends AbstractHandlerAnnotationsLoaderTest<EventHandler, EventHandlerElement, EventHandlerAnnotationsLoader> {

  @Test
  public void testPresenterAndEventHandler() {
    List<JClassType> annotedClasses = new ArrayList<JClassType>();
    JClassType       type           = oracle.addClass(EventHandlers.PresenterAndEventHandler.class);
    annotedClasses.add(type);
    try {
      loader.load(annotedClasses,
                  configuration);
      fail();
    } catch (Mvp4gAnnotationException e) {
      assertEquals(EventHandlers.PresenterAndEventHandler.class.getCanonicalName() + ": You can't annotate a class with @Presenter and @EventHandler.",
                   e.getMessage());
    }
  }

  @Override
  protected Class<?> getClassNotPublic() {
    return EventHandlers.EventHandlerNotPublic.class;
  }

  @Override
  protected Class<?> getClassWithNoParameter() {
    return EventHandlers.EventHandlerNoParameter.class;
  }

  @Override
  protected Class<?> getClassWithMoreThanOne() {
    return EventHandlers.EventHandlerWithMoreThanOneParameter.class;
  }

  @Override
  protected Class<?> getServiceWithName() {
    return EventHandlers.EventHandlerWithServiceAndName.class;
  }

  @Override
  protected Class<?> getService() {
    return EventHandlers.EventHandlerWithService.class;
  }

  @Override
  protected Class<?> getSameService() {
    return EventHandlers.EventHandlerWithSameService.class;
  }

  @Override
  protected Class<?> getClassNotAsync() {
    return EventHandlers.EventHandlerNotAsync.class;
  }

  @Override
  protected EventHandlerAnnotationsLoader createLoader() {
    return new EventHandlerAnnotationsLoader();
  }

  @Override
  protected Class<?> getSimpleClass() {
    return SimpleEventHandler01.class;
  }

  @Override
  protected Set<EventHandlerElement> getSet() {
    return configuration.getEventHandlers();
  }

  @Override
  protected Class<?> getWithNameClass() {
    return EventHandlers.EventHandlerWithName.class;
  }

  @Override
  protected Class<?> getWrongInterface() {
    return Object.class;
  }

  @Override
  protected Class<?> getMultipleClass() {
    return EventHandlers.MultipleEventHandler.class;
  }

  @Override
  protected Class<?> getAsyncClass() {
    return EventHandlers.AsyncEventHandler.class;
  }

}
