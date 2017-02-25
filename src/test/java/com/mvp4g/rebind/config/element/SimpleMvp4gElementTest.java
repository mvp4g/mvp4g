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

public class SimpleMvp4gElementTest
  extends AbstractMvp4gElementTest<SimpleMvp4gElement> {

  protected static final String[] properties = { "name" };

  protected static String[] addProperties(String[] otherProperties) {

    int      pSize         = properties.length;
    int      size          = pSize + otherProperties.length;
    String[] newProperties = new String[size];

    int i;
    for (i = 0; i < pSize; i++) {
      newProperties[i] = properties[i];
    }

    for (i = pSize; i < size; i++) {
      newProperties[i] = otherProperties[i - pSize];
    }

    return newProperties;

  }

  @Test
  public void testSetClassName() {

    String className = "Test";

    element.setClassName(className);
    assertEquals(element.getProperty("class"),
                 className);
  }

  @Test
  public void testToString() {
    String name      = "test";
    String className = "com.test.Test";

    element.setName(name);
    element.setClassName(className);

    assertEquals("[" + name + " : " + className + "]",
                 element.toString());
  }

  @Override
  protected SimpleMvp4gElement newElement() {
    return new SimpleMvp4gElement();
  }

  @Override
  protected String getTag() {
    return "simple";
  }

  @Override
  protected String getUniqueIdentifierName() {
    return "name";
  }

  @Override
  protected String[] getProperties() {
    return properties;
  }

}
