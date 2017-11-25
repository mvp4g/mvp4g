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

public class EventFiltersElementTest
  extends AbstractMvp4gElementTest<EventFiltersElement> {

  protected static final String[] properties = { "afterHistory",
                                                 "filterForward",
                                                 "filterStart",
                                                 "forceFilters" };

  @Test
  public void testAfterHistory() {
    EventFiltersElement element = newElement();
    assertFalse(element.isAfterHistory());
    element.setAfterHistory("true");
    assertTrue(element.isAfterHistory());

    element = newElement();
    element.setAfterHistory("false");
    assertFalse(element.isAfterHistory());
  }

  @Override
  protected EventFiltersElement newElement() {
    return new EventFiltersElement();
  }

  @Override
  protected String getTag() {
    return "eventFilters";
  }

  @Override
  protected String getUniqueIdentifierName() {
    return EventFiltersElement.class.getName();
  }

  @Override
  protected String[] getProperties() {
    return properties;
  }

  @Test
  public void testFilterStart() {
    EventFiltersElement element = newElement();
    assertTrue(element.isFilterStart());
    element.setFilterStart("false");
    assertFalse(element.isFilterStart());

    element = newElement();
    element.setFilterStart("true");
    assertTrue(element.isFilterStart());
  }

  @Test
  public void testFilterFoward() {
    EventFiltersElement element = newElement();
    assertTrue(element.isFilterForward());
    element.setFilterForward("false");
    assertFalse(element.isFilterForward());

    element = newElement();
    element.setFilterForward("true");
    assertTrue(element.isFilterForward());
  }

  @Test
  public void testForceFilters() {
    EventFiltersElement element = newElement();
    assertFalse(element.isForceFilters());
    element.setForceFilters("true");
    assertTrue(element.isForceFilters());

    element = newElement();
    element.setForceFilters("false");
    assertFalse(element.isForceFilters());
  }

}
