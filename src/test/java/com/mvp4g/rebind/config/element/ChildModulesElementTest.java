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

public class ChildModulesElementTest
  extends AbstractMvp4gElementTest<ChildModulesElement> {

  protected static final String[] properties = { "errorEvent",
                                                 "beforeEvent",
                                                 "afterEvent" };

  @Override
  protected ChildModulesElement newElement() {
    return new ChildModulesElement();
  }

  @Override
  protected String getTag() {
    return "childModules";
  }

  @Override
  protected String getUniqueIdentifierName() {
    return ChildModulesElement.class.getName();
  }

  @Override
  protected String[] getProperties() {
    return properties;
  }

}
