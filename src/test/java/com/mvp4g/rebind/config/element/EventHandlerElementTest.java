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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventHandlerElementTest
  extends Mvp4gWithServicesElementTest {

  private static final String[] properties = SimpleMvp4gElementTest.addProperties(new String[] { "multiple",
                                                                                                 "async" });

  @Override
  protected String[] getProperties() {
    return properties;
  }

  @Override
  protected String getTag() {
    return "eventHandler";
  }

  @Override
  protected SimpleMvp4gElement newElement() {
    return new EventHandlerElement();
  }

  @Test
  public void testMultiple() {
    EventHandlerElement element = new EventHandlerElement();
    assertFalse(element.isMultiple());
    element.setMultiple("true");
    assertTrue(element.isMultiple());

    element = new EventHandlerElement();
    element.setMultiple("false");
    assertFalse(element.isMultiple());
  }

  @Test
  public void testAsync() {
    EventHandlerElement element = new EventHandlerElement();
    assertFalse(element.isAsync());
    element.setAsync("async");
    assertTrue(element.isAsync());
  }

}
