/*
 * Copyright 2009 Pierre-Laurent Coirier
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
 * An Mvp4g View configuration element.<br>
 *
 * @author javier
 */
public class ViewElement
  extends SimpleMvp4gElement {

  //a flag to indicate if an instance of the view should be created at start
  //a view shouldn't be instantiate at start if it's only injected into a multiple presenter
  //This is not a configuration parameter from developer, it is set by the framework
  boolean instantiateAtStart = false;

  public ViewElement() {
    super("view");
  }

  /**
   * @return the instantiateAtStart
   */
  public boolean isInstantiateAtStart() {
    return instantiateAtStart;
  }

  /**
   * @param instantiateAtStart the instantiateAtStart to set
   */
  public void setInstantiateAtStart(boolean instantiateAtStart) {
    this.instantiateAtStart = instantiateAtStart;
  }

}
