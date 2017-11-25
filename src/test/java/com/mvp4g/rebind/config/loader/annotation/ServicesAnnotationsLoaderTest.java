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
import com.mvp4g.client.annotation.Service;
import com.mvp4g.rebind.config.element.ServiceElement;
import com.mvp4g.rebind.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.rebind.test_tools.annotation.Services;
import com.mvp4g.rebind.test_tools.annotation.services.ServiceWithName;
import com.mvp4g.rebind.test_tools.annotation.services.SimpleService;

import static org.junit.Assert.assertEquals;

public class ServicesAnnotationsLoaderTest
  extends AbstractMvp4gAnnotationLoaderTest<Service, ServiceAnnotationsLoader> {

  @Test
  public void testPath()
    throws Mvp4gAnnotationException {
    List<JClassType> annotedClasses = new ArrayList<JClassType>();
    annotedClasses.add(oracle.addClass(getSimpleClass()));
    annotedClasses.add(oracle.addClass(getWithNameClass()));
    Set<ServiceElement> services = getSet();
    assertEquals(services.size(),
                 0);
    loader.load(annotedClasses,
                configuration);
    assertEquals(services.size(),
                 2);
    for (ServiceElement service : services) {
      assertEquals(service.getPath(),
                   "path");
    }
  }

  @Test
  public void testGeneratedClass()
    throws Mvp4gAnnotationException {
    List<JClassType> annotedClasses = new ArrayList<JClassType>();
    annotedClasses.add(oracle.addClass(Services.ServiceWithGeneratedClass.class));
    Set<ServiceElement> services = getSet();
    assertEquals(services.size(),
                 0);
    loader.load(annotedClasses,
                configuration);
    assertEquals(services.size(),
                 1);
    assertEquals(services.iterator()
                         .next()
                         .getGeneratedClassName(),
                 Services.ServiceWithGeneratedClass.class.getCanonicalName());

  }

  @Override
  protected ServiceAnnotationsLoader createLoader() {
    return new ServiceAnnotationsLoader();
  }

  @Override
  protected Class<?> getSimpleClass() {
    return SimpleService.class;
  }

  @Override
  protected Set<ServiceElement> getSet() {
    return configuration.getServices();
  }

  @Override
  protected Class<?> getWithNameClass() {
    return ServiceWithName.class;
  }

  @Override
  protected Class<?> getWrongInterface() {
    return null;
  }

}
