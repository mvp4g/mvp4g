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

import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.DefaultMvp4gLogger;

import static org.junit.Assert.assertEquals;

public class DebugElementTest
  extends AbstractMvp4gElementTest<DebugElement> {

  protected static final String[] properties = { "logLevel",
                                                 "logger" };

  @Test
  public void testNullLogLevel() {
    assertEquals(LogLevel.SIMPLE.name(),
                 element.getLogLevel());
  }

  @Test
  public void testLoggerLevel() {
    assertEquals(DefaultMvp4gLogger.class.getName(),
                 element.getLogger());
  }

  @Override
  protected DebugElement newElement() {
    return new DebugElement();
  }

  @Override
  protected String getTag() {
    return "debug";
  }

  @Override
  protected String getUniqueIdentifierName() {
    return DebugElement.class.getName();
  }

  @Override
  protected String[] getProperties() {
    return properties;
  }

}
