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

/**
 * @author plcoirier
 */
public class EventFiltersElement
  extends Mvp4gElement {

  private static final String EVENT_FILTERS_ELEMENT_ID = EventFiltersElement.class.getName();

  public EventFiltersElement() {
    super("eventFilters");
  }

  @Override
  public String getUniqueIdentifierName() {
    // this element does not have a user-specified identifier: use a global label
    return EVENT_FILTERS_ELEMENT_ID;
  }

  public boolean isAfterHistory() {
    return Boolean.TRUE.toString()
                       .equalsIgnoreCase(getAfterHistory());
  }

  public String getAfterHistory() {
    return getProperty("afterHistory");
  }

  public void setAfterHistory(String afterHistory) {
    setProperty("afterHistory",
                afterHistory);
  }

  public boolean isFilterForward() {
    return !Boolean.FALSE.toString()
                         .equalsIgnoreCase(getFilterForward());
  }

  public String getFilterForward() {
    return getProperty("filterForward");
  }

  public void setFilterForward(String filterForward) {
    setProperty("filterForward",
                filterForward);
  }

  public boolean isFilterStart() {
    return !Boolean.FALSE.toString()
                         .equalsIgnoreCase(getFilterStart());
  }

  public String getFilterStart() {
    return getProperty("filterStart");
  }

  public void setFilterStart(String filterStart) {
    setProperty("filterStart",
                filterStart);
  }

  public boolean isForceFilters() {
    return Boolean.TRUE.toString()
                       .equalsIgnoreCase(getForceFilters());
  }

  public String getForceFilters() {
    return getProperty("forceFilters");
  }

  public void setForceFilters(String forceFilters) {
    setProperty("forceFilters",
                forceFilters);
  }

}
