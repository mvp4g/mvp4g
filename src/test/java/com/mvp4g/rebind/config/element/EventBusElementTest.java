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

public class EventBusElementTest {

  @Test
  public void testConstructor() {
    String interfaceName     = "test";
    String abstractClassName = "setTest";
    EventBusElement e = new EventBusElement(interfaceName,
                                            abstractClassName,
                                            false);
    assertEquals(interfaceName,
                 e.getInterfaceClassName());
    assertEquals(abstractClassName,
                 e.getAbstractClassName());
    assertFalse(e.isWithLookUp());
  }

}
