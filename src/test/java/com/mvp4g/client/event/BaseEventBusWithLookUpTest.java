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

package com.mvp4g.client.event;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.client.Mvp4gException;
import com.mvp4g.client.test_tools.EventBusWithLookUpStub;

public class BaseEventBusWithLookUpTest {

  private static final String                 TEST = "test";
  private static final String                 FORM = "form";
  private              EventBusWithLookUpStub bus  = null;

  @Before
  public void setUp() {
    bus = new EventBusWithLookUpStub();
  }

  @Test
  public void testDispatch() {
    bus.dispatch(TEST);
    bus.assertEvent(TEST,
                    new Object[0]);

    bus.dispatch(EventType.TEST_TYPE);
    bus.assertEvent(TEST,
                    new Object[0]);

    bus.dispatch(EventType.TEST_TYPE,
                 FORM);
    bus.assertEvent(TEST,
                    new Object[] { FORM });
  }

  @Test(expected = Mvp4gException.class)
  public void testDispatchWithMvp4gException() {
    try {
      bus.dispatch(EventBusWithLookUpStub.MVP4G_EXCEPTION);
    } catch (ClassCastException e) {
      bus.handleClassCastException(e,
                                   EventBusWithLookUpStub.MVP4G_EXCEPTION);
    }
  }

  @Test(expected = ClassCastException.class)
  public void testDispatchWithClassCastException() {
    try {
      bus.dispatch(EventBusWithLookUpStub.CLASS_CAST_EXCEPTION);
    } catch (ClassCastException e) {
      bus.handleClassCastException(e,
                                   EventBusWithLookUpStub.CLASS_CAST_EXCEPTION);
    }
  }

  private static enum EventType {
    TEST_TYPE(TEST);
    private String type;

    private EventType(String type) {
      this.type = type;
    }

    @Override
    public String toString() {
      return type;
    }
  }

}
