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

import java.lang.annotation.Annotation;
import java.util.Set;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.Mvp4gSplitter;
import com.mvp4g.client.annotation.NotAsync;
import com.mvp4g.rebind.config.Mvp4gConfiguration;
import com.mvp4g.rebind.config.element.EventHandlerElement;
import com.mvp4g.rebind.config.element.Mvp4gWithServicesElement;
import com.mvp4g.rebind.exception.loader.Mvp4gAnnotationException;

/**
 * A class responsible for loading information contained in <code>EventHandler</code> annotation.
 *
 * @author Dan Persa
 */
public abstract class AbstractHandlerAnnotationsLoader<A extends Annotation, T extends EventHandlerElement>
  extends Mvp4gAnnotationsWithServiceLoader<A> {

  /*
   * (non-Javadoc)
   *
   * @see com.mvp4g.rebind.config.loader.annotation.Mvp4gAnnotationsWithServiceLoader
   * #loadElementWithServices (com.google.gwt.core.ext.typeinfo.JClassType,
   * java.lang.annotation.Annotation, com.mvp4g.rebind.config.Mvp4gConfiguration)
   */
  @Override
  Mvp4gWithServicesElement loadElementWithServices(JClassType c,
                                                   A annotation,
                                                   Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {

    String className = c.getQualifiedSourceName();

    String eventHandlerName = buildElementNameIfNeeded(getAnnotationName(annotation),
                                                       className,
                                                       "");

    T eventHandler = loadHandler(c,
                                 annotation,
                                 configuration);
    eventHandler.setName(eventHandlerName);
    eventHandler.setClassName(className);
    eventHandler.setMultiple(Boolean.toString(isAnnotationMultiple(annotation)));

    Class<? extends Mvp4gSplitter> splitter = getAnnotationSplitter(annotation);
    if (!splitter.equals(NotAsync.class)) {
      eventHandler.setAsync(splitter.getCanonicalName());
    }

    addElement(getConfigList(configuration),
               eventHandler,
               c,
               null);

    return eventHandler;
  }

  abstract protected String getAnnotationName(A annotation);

  abstract protected T loadHandler(JClassType c,
                                   A annotation,
                                   Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException;

  abstract protected boolean isAnnotationMultiple(A annotation);

  abstract protected Class<? extends Mvp4gSplitter> getAnnotationSplitter(A annotation);

  abstract protected Set<T> getConfigList(Mvp4gConfiguration configuration);
}
