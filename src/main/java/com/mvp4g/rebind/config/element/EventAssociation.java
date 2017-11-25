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

import java.util.ArrayList;
import java.util.List;

public class EventAssociation<T> {

  private List<T> handlers = new ArrayList<T>();

  private List<T> binds = new ArrayList<T>();

  private List<T> activated = new ArrayList<T>();

  private List<T> deactivated = new ArrayList<T>();

  private List<T> generate = new ArrayList<T>();

  /**
   * @return the handlers
   */
  public List<T> getHandlers() {
    return handlers;
  }

  /**
   * @return the binds
   */
  public List<T> getBinds() {
    return binds;
  }

  /**
   * @return the activated
   */
  public List<T> getActivated() {
    return activated;
  }

  /**
   * @return the deactivated
   */
  public List<T> getDeactivated() {
    return deactivated;
  }

  /**
   * @return the generate
   */
  public List<T> getGenerate() {
    return generate;
  }

}
