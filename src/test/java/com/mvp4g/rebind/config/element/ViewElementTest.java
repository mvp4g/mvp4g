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

public class ViewElementTest
  extends SimpleMvp4gElementTest {

  @Test
  public void testInstantiateAtStartGetterSetter() {
    ViewElement element = new ViewElement();
    assertFalse(element.isInstantiateAtStart());
    element.setInstantiateAtStart(true);
    assertTrue(element.isInstantiateAtStart());
    element.setInstantiateAtStart(false);
    assertFalse(element.isInstantiateAtStart());
  }

  @Override
  protected String getTag() {
    return "view";
  }

  @Override
  protected SimpleMvp4gElement newElement() {
    return new ViewElement();
  }

}
