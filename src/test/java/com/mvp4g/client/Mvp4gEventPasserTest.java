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

package com.mvp4g.client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Mvp4gEventPasserTest {

  @Test
  public void testConstructor() {
    String test = "TEST";
    Mvp4gEventPasser passer = new Mvp4gEventPasser(test) {

      @Override
      public void pass(Mvp4gModule module) {
        // TODO Auto-generated method stub

      }

    };
    assertEquals(test,
                 passer.eventObjects[0]);
    assertEquals(test,
                 passer.getEventObjects()[0]);
  }

  @Test
  public void testEventObjectsSetter() {
    String test = "TEST";
    Mvp4gEventPasser passer = new Mvp4gEventPasser() {

      @Override
      public void pass(Mvp4gModule module) {
        // TODO Auto-generated method stub

      }

    };
    assertTrue(passer.eventObjects.length == 0);
    assertTrue(passer.getEventObjects().length == 0);
    passer.setEventObject(test);
    assertEquals(test,
                 passer.eventObjects[0]);
    assertEquals(test,
                 passer.getEventObjects()[0]);
  }

}
