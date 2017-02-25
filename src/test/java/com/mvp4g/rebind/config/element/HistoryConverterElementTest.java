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

import com.mvp4g.client.annotation.History.HistoryConverterType;

import static org.junit.Assert.assertEquals;

public class HistoryConverterElementTest
  extends Mvp4gWithServicesElementTest {

  private static final String[] properties = { "type" };

  @Test
  public void testDefaultConvertParams() {
    HistoryConverterElement element = new HistoryConverterElement();
    assertEquals(element.getType(),
                 HistoryConverterType.DEFAULT.name());

    element.setType(HistoryConverterType.DEFAULT.name());
    assertEquals(element.getType(),
                 HistoryConverterType.DEFAULT.name());

    element = new HistoryConverterElement();
    element.setType(HistoryConverterType.SIMPLE.name());
    assertEquals(element.getType(),
                 HistoryConverterType.SIMPLE.name());

    element = new HistoryConverterElement();
    element.setType(HistoryConverterType.NONE.name());
    assertEquals(element.getType(),
                 HistoryConverterType.NONE.name());
  }

  @Override
  protected String getTag() {
    return "historyConverter";
  }

  @Override
  protected SimpleMvp4gElement newElement() {
    return new HistoryConverterElement();
  }

  @Override
  protected String[] getProperties() {
    return properties;
  }

}
