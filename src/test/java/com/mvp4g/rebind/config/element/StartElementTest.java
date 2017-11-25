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

public class StartElementTest
  extends AbstractMvp4gElementTest<StartElement> {

  protected static final String[] properties = { "eventType",
                                                 "presenter",
                                                 "history",
                                                 "forwardEventType" };

  @Test
  public void testHasEventType() {
    assertFalse(element.hasEventType());
    element.setEventType("test");
    assertTrue(element.hasEventType());
  }

  @Test
  public void testHasHistory() {
    assertFalse(element.hasHistory());
    element.setHistory("true");
    assertTrue(element.hasHistory());
  }

  @Test
  public void testHasHistoryUpper() {
    assertFalse(element.hasHistory());
    element.setHistory("TRUE");
    assertTrue(element.hasHistory());
  }

  @Test
  public void testHasHistoryFalse() {
    assertFalse(element.hasHistory());
    element.setHistory("false");
    assertFalse(element.hasHistory());
  }

  @Test
  public void testHasHistoryAny() {
    assertFalse(element.hasHistory());
    element.setHistory("laksjd123");
    assertFalse(element.hasHistory());
  }

  @Test
  public void testHasForward() {
    assertFalse(element.hasForwardEventType());
    String test = "test";
    element.setForwardEventType(test);
    assertEquals(test,
                 element.getForwardEventType());
    assertTrue(element.hasForwardEventType());
  }

  @Test
  public void testHasStartView() {
    assertFalse(element.hasPresenter());

    element.setPresenter("presenter");
    assertTrue(element.hasPresenter());
  }

  @Override
  protected StartElement newElement() {
    return new StartElement();
  }

  @Override
  protected String getTag() {
    return "start";
  }

  @Override
  protected String getUniqueIdentifierName() {
    return StartElement.class.getName();
  }

  @Override
  protected String[] getProperties() {
    return properties;
  }

}
