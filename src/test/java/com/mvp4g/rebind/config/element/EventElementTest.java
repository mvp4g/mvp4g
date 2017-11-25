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

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class EventElementTest
  extends AbstractMvp4gElementTest<EventElement> {

  private static final String[] properties     = { "calledMethod",
                                                   "type",
                                                   "history",
                                                   "forwardToParent",
                                                   "name" };
  private static final String[] values         = { "eventObjectClass" };
  private static final String[] flexibleValues = { "siblingsToLoad",
                                                   "splitters",
                                                   "generate" };

  @Test
  public void testGetCalledMethod() {
    element.setType("display");
    assertEquals("onDisplay",
                 element.getCalledMethod());
    element.setCalledMethod("onDisplayCalled");
    assertEquals("onDisplayCalled",
                 element.getCalledMethod());
  }

  @Test
  public void testGetCalledMethodOneCharacter() {
    element.setType("o");
    assertEquals("onO",
                 element.getCalledMethod());
  }

  @Test
  public void testHasHistory() {
    assertFalse(element.hasHistory());
    element.setHistory("converter");
    assertTrue(element.hasHistory());
  }

  @Test
  public void testToString() {
    element.setType("type");
    assertEquals("[type]",
                 element.toString());
  }

  @Test
  public void testIsAsyncPath() {
    EventElement childModuleElement = new EventElement();
    assertFalse(childModuleElement.hasForwardToParent());
    childModuleElement.setForwardToParent("true");
    assertTrue(childModuleElement.hasForwardToParent());

    childModuleElement = new EventElement();
    childModuleElement.setForwardToParent("false");
    assertFalse(childModuleElement.hasForwardToParent());

    childModuleElement = new EventElement();
    childModuleElement.setForwardToParent("123");
    assertFalse(childModuleElement.hasForwardToParent());
  }

  @Test
  public void testActivateSetterGetter() {
    EventElement element = newElement();
    assertNull(element.getActivate());
    assertNull(element.getValues("activate"));
    String[] test = { "test1",
                      "test2" };
    element.setActivate(test);
    List<String> activate = element.getActivate();
    assertTrue(test.length == activate.size());
    for (int i = 0; i < test.length; i++) {
      assertSame(test[i],
                 activate.get(i));
    }
    assertNull(element.getValues("activate"));

    element = newElement();
    element.setValues("activate",
                      test);
    activate = element.getActivate();
    assertTrue(test.length == activate.size());
    for (int i = 0; i < test.length; i++) {
      assertSame(test[i],
                 activate.get(i));
    }
    assertNull(element.getValues("activate"));

  }

  @Override
  protected EventElement newElement() {
    return new EventElement();
  }

  @Override
  protected String getTag() {
    return "event";
  }

  @Override
  protected String getUniqueIdentifierName() {
    return "type";
  }

  @Override
  protected String[] getProperties() {
    return properties;
  }

  @Override
  protected String[] getValues() {
    return values;
  }

  @Override
  protected String[] getFlexibleValues() {
    return flexibleValues;
  }

  @Test
  public void testDeactivateSetterGetter() {
    assertNull(element.getDeactivate());
    assertNull(element.getValues("deactivate"));
    String[] test = { "test1",
                      "test2" };
    element.setDeactivate(test);
    List<String> deactivate = element.getDeactivate();
    assertTrue(test.length == deactivate.size());
    for (int i = 0; i < test.length; i++) {
      assertSame(test[i],
                 deactivate.get(i));
    }
    assertNull(element.getValues("deactivate"));

    element = newElement();
    element.setValues("deactivate",
                      test);
    deactivate = element.getDeactivate();
    assertTrue(test.length == deactivate.size());
    for (int i = 0; i < test.length; i++) {
      assertSame(test[i],
                 deactivate.get(i));
    }
    assertNull(element.getValues("deactivate"));
  }

  @Test
  public void testHandlersGetter() {
    assertNull(element.getHandlers());
    assertNull(element.getValues("handlers"));
    String[] test = { "test1",
                      "test2" };
    element.setHandlers(test);
    List<String> handlers = element.getHandlers();
    assertTrue(test.length == handlers.size());
    for (int i = 0; i < test.length; i++) {
      assertSame(test[i],
                 handlers.get(i));
    }
    assertNull(element.getValues("handlers"));

    element = newElement();
    element.setValues("handlers",
                      test);
    handlers = element.getHandlers();
    assertTrue(test.length == handlers.size());
    for (int i = 0; i < test.length; i++) {
      assertSame(test[i],
                 handlers.get(i));
    }
    assertNull(element.getValues("handlers"));
  }

  @Test
  public void testBindsGetter() {
    assertNull(element.getBinds());
    assertNull(element.getValues("bind"));
    String[] test = { "test1",
                      "test2" };
    element.setBinds(test);
    List<String> binds = element.getBinds();
    assertTrue(test.length == binds.size());
    for (int i = 0; i < test.length; i++) {
      assertSame(test[i],
                 binds.get(i));
    }
    assertNull(element.getValues("bind"));

    element = newElement();
    element.setValues("bind",
                      test);
    binds = element.getBinds();
    assertTrue(test.length == binds.size());
    for (int i = 0; i < test.length; i++) {
      assertSame(test[i],
                 binds.get(i));
    }
    assertNull(element.getValues("bind"));
  }

  public void testModulesToLoadGetter() {
    assertNull(element.getForwardToModules());
    assertNull(element.getValues("forwardToModules"));
    String[] test = { "test1",
                      "test2" };
    element.setForwardToModules(test);
    List<String> modulesToLoad = element.getForwardToModules();
    assertTrue(test.length == modulesToLoad.size());
    for (int i = 0; i < test.length; i++) {
      assertSame(test[i],
                 modulesToLoad.get(i));
    }
    assertNull(element.getValues("forwardToModules"));

    element = newElement();
    element.setValues("forwardToModules",
                      test);
    modulesToLoad = element.getForwardToModules();
    assertTrue(test.length == modulesToLoad.size());
    for (int i = 0; i < test.length; i++) {
      assertSame(test[i],
                 modulesToLoad.get(i));
    }
    assertNull(element.getValues("forwardToModules"));
  }

  @Test
  public void testDefaultHistoryName() {
    String test = "test";
    element.setType(test);
    assertEquals(test,
                 element.getName());
  }

  @Test
  public void testNavigationEventTrue() {
    String test = "true";
    element.setNavigationEvent(test);
    assertEquals(test,
                 element.getNavigationEvent());
    assertTrue(element.isNavigationEvent());
  }

  @Test
  public void testNavigationEventFalse() {
    String test = "false";
    element.setNavigationEvent(test);
    assertEquals(test,
                 element.getNavigationEvent());
    assertFalse(element.isNavigationEvent());
  }

  @Test
  public void testWithTokenGenerationTrue() {
    String test = "true";
    element.setWithTokenGeneration(test);
    assertEquals(test,
                 element.getWithTokenGeneration());
    assertTrue(element.isWithTokenGeneration());
  }

  @Test
  public void testWithTokenGenerationFalse() {
    String test = "false";
    element.setWithTokenGeneration(test);
    assertEquals(test,
                 element.getWithTokenGeneration());
    assertFalse(element.isWithTokenGeneration());
  }

  @Test
  public void testTokenGenerationFromParentTrue() {
    String test = "true";
    element.setTokenGenerationFromParent(test);
    assertEquals(test,
                 element.getTokenGenerationFromParent());
    assertTrue(element.isTokenGenerationFromParent());
  }

  @Test
  public void testTokenGenerationFromParentFalse() {
    String test = "false";
    element.setTokenGenerationFromParent(test);
    assertEquals(test,
                 element.getTokenGenerationFromParent());
    assertFalse(element.isTokenGenerationFromParent());
  }

  @Test
  public void testPassiveTrue() {
    String test = "true";
    element.setPassive(test);
    assertEquals(test,
                 element.getPassive());
    assertTrue(element.isPassive());
  }

  @Test
  public void testPassiveFalse() {
    String test = "false";
    element.setPassive(test);
    assertEquals(test,
                 element.getPassive());
    assertFalse(element.isPassive());
  }

}
