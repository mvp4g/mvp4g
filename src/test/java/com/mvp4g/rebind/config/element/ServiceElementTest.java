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

package com.mvp4g.rebind.config.element;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServiceElementTest
  extends SimpleMvp4gElementTest {

  private static final String[] properties = SimpleMvp4gElementTest.addProperties(new String[] { "path" });

  @Test
  public void testHasPath() {
    ServiceElement serviceElement = (ServiceElement) element;
    assertFalse(serviceElement.hasPath());
    serviceElement.setPath("path");
    assertTrue(serviceElement.hasPath());
  }

  @Test
  public void testSetGeneratedClassName() {
    String         className      = "Test";
    ServiceElement serviceElement = (ServiceElement) element;
    serviceElement.setGeneratedClassName(className);
    assertEquals(element.getProperty("generatedClassName"),
                 className);
  }

  @Test
  public void testDefaultGeneratedClassName() {
    String         className      = "com.Test";
    ServiceElement serviceElement = (ServiceElement) element;
    serviceElement.setClassName(className);
    assertEquals(serviceElement.getGeneratedClassName(),
                 className + "Async");
  }

  @Override
  protected String[] getProperties() {
    return properties;
  }

  @Override
  protected String getTag() {
    return "service";
  }

  @Override
  protected SimpleMvp4gElement newElement() {
    return new ServiceElement();
  }

}
