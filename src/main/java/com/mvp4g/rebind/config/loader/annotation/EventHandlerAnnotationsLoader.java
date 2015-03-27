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
package com.mvp4g.rebind.config.loader.annotation;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.Mvp4gSplitter;
import com.mvp4g.client.annotation.EventHandler;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.rebind.config.Mvp4gConfiguration;
import com.mvp4g.rebind.config.element.EventHandlerElement;
import com.mvp4g.rebind.exception.loader.Mvp4gAnnotationException;

import java.util.Set;

/**
 * A class responsible for loading information contained in <code>EventHandler</code> annotation.
 *
 * @author Dan Persa
 */
public class EventHandlerAnnotationsLoader
  extends AbstractHandlerAnnotationsLoader<EventHandler, EventHandlerElement> {

  /*
   * (non-Javadoc)
   *
   * @seecom.mvp4g.rebind.config.loader.annotation.Mvp4gAnnotationsLoader#
   * getMandatoryInterfaceName()
   */
  @Override
  protected String getMandatoryInterfaceName() {
    return EventHandlerInterface.class.getCanonicalName();
  }

  @Override
  protected EventHandlerElement loadHandler(JClassType c,
                                            EventHandler annotation,
                                            Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {
    if (c.getAnnotation(Presenter.class) != null) {
      String err = "You can't annotate a class with @Presenter and @EventHandler.";
      throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                         null,
                                         err);
    }
    return new EventHandlerElement();
  }

  @Override
  protected Set<EventHandlerElement> getConfigList(Mvp4gConfiguration configuration) {
    return configuration.getEventHandlers();
  }

  @Override
  protected String getAnnotationName(EventHandler annotation) {
    return annotation.name();
  }

  @Override
  protected boolean isAnnotationMultiple(EventHandler annotation) {
    return annotation.multiple();
  }

  @Override
  protected Class<? extends Mvp4gSplitter> getAnnotationSplitter(EventHandler annotation) {
    return annotation.async();
  }
}
